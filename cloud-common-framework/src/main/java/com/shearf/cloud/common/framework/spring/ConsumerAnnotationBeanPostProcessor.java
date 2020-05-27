package com.shearf.cloud.common.framework.spring;

import com.shearf.cloud.common.framework.annotation.Consumer;
import com.shearf.cloud.common.framework.spring.bean.ConsumerBean;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.BeansException;
import org.springframework.beans.PropertyValues;
import org.springframework.beans.factory.BeanClassLoaderAware;
import org.springframework.beans.factory.BeanCreationException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.annotation.InjectionMetadata;
import org.springframework.beans.factory.config.InstantiationAwareBeanPostProcessorAdapter;
import org.springframework.beans.factory.support.MergedBeanDefinitionPostProcessor;
import org.springframework.beans.factory.support.RootBeanDefinition;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.core.PriorityOrdered;
import org.springframework.lang.NonNull;
import org.springframework.util.ReflectionUtils;
import org.springframework.util.StringUtils;

import java.lang.reflect.Field;
import java.lang.reflect.Modifier;
import java.util.LinkedList;
import java.util.List;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ConcurrentMap;

import static org.springframework.core.annotation.AnnotationUtils.getAnnotation;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/17 18:18
 */
public class ConsumerAnnotationBeanPostProcessor extends InstantiationAwareBeanPostProcessorAdapter
        implements MergedBeanDefinitionPostProcessor, PriorityOrdered, ApplicationContextAware, BeanClassLoaderAware,
        DisposableBean {

    public static final String BEAN_NAME = "consumerAnnotationBeanPostProcessor";

    private static final Logger logger = LoggerFactory.getLogger(ConsumerAnnotationBeanPostProcessor.class);

    private ApplicationContext applicationContext;

    private ClassLoader classLoader;

    private final ConcurrentMap<String, InjectionMetadata> injectionMetadataCache =
            new ConcurrentHashMap<>(256);

    private final ConcurrentMap<String, ConsumerBean<?>> consumerBeansCache =
            new ConcurrentHashMap<>();

    @Override
    public void setBeanClassLoader(@NonNull ClassLoader classLoader) {
        this.classLoader = classLoader;
    }

    @Override
    public void destroy() throws Exception {
        for (ConsumerBean<?> consumerBean : consumerBeansCache.values()) {
            if (logger.isInfoEnabled()) {
                logger.info(consumerBean + " was destroying!");
            }
            consumerBean.destroy();
        }

        injectionMetadataCache.clear();
        consumerBeansCache.clear();

        if (logger.isInfoEnabled()) {
            logger.info(getClass() + " was destroying!");
        }
    }

    @Override
    public PropertyValues postProcessProperties(PropertyValues pvs, Object bean, String beanName) throws BeansException {
        InjectionMetadata injectionMetadata = findConsumerMetadata(beanName, bean.getClass(), pvs);
        try {
            injectionMetadata.inject(bean, beanName, pvs);
        } catch (BeanCreationException ex) {
            throw ex;
        } catch (Throwable ex) {
            throw new BeanCreationException(beanName, "Injection of @Reference dependencies failed", ex);
        }
        return pvs;
    }

    @Override
    public void postProcessMergedBeanDefinition(@NonNull RootBeanDefinition beanDefinition,
                                                @NonNull Class<?> beanType,
                                                @NonNull String beanName) {
        InjectionMetadata metadata = findConsumerMetadata(beanName, beanType, null);
        metadata.checkConfigMembers(beanDefinition);
    }

    private InjectionMetadata findConsumerMetadata(String beanName, Class<?> clazz, PropertyValues pvs) {
        // Fall back to class name as cache key, for backwards compatibility with custom callers.
        String cacheKey = (StringUtils.hasLength(beanName) ? beanName : clazz.getName());
        // Quick check on the concurrent map first, with minimal locking.
        InjectionMetadata metadata = this.injectionMetadataCache.get(cacheKey);
        if (InjectionMetadata.needsRefresh(metadata, clazz)) {
            synchronized (this.injectionMetadataCache) {
                metadata = this.injectionMetadataCache.get(cacheKey);
                if (InjectionMetadata.needsRefresh(metadata, clazz)) {
                    if (metadata != null) {
                        metadata.clear(pvs);
                    }
                    try {
                        metadata = buildConsumerMetadata(clazz);
                        this.injectionMetadataCache.put(cacheKey, metadata);
                    } catch (NoClassDefFoundError err) {
                        throw new IllegalStateException("Failed to introspect bean class [" + clazz.getName() +
                                "] for reference metadata: could not find class that it depends on", err);
                    }
                }
            }
        }
        return metadata;
    }

    private InjectionMetadata buildConsumerMetadata(Class<?> clazz) {
        List<InjectionMetadata.InjectedElement> consumerFieldElementList = findFieldConsumerMetadata(clazz);
        return new InjectionMetadata(clazz, consumerFieldElementList);
    }

    private List<InjectionMetadata.InjectedElement> findFieldConsumerMetadata(Class<?> clazz) {
        List<InjectionMetadata.InjectedElement> elements = new LinkedList<>();
        ReflectionUtils.doWithFields(clazz, new ReflectionUtils.FieldCallback() {
            @Override
            public void doWith(@NonNull Field field) throws IllegalArgumentException, IllegalAccessException {
                Consumer consumer = getAnnotation(field, Consumer.class);
                if (consumer != null) {
                    if (Modifier.isStatic(field.getModifiers())) {
                        if (logger.isWarnEnabled()) {
                            logger.warn("@Reference annotation is not supported on static fields: " + field);
                        }
                        return;
                    }
                    elements.add(new ConsumerFieldElement(field, consumer));
                }

            }
        });
        return elements;
    }


    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    @Override
    public int getOrder() {
        return LOWEST_PRECEDENCE;
    }

    private class ConsumerFieldElement extends InjectionMetadata.InjectedElement {

        private final Field field;

        private final Consumer consumer;

        public ConsumerFieldElement(Field field, Consumer consumer) {
            super(field, null);
            this.field = field;
            this.consumer = consumer;
        }

        @Override
        protected void inject(@NonNull Object target, String requestingBeanName, PropertyValues pvs) throws Throwable {
            Class<?> consumerClass = field.getType();
            ConsumerBean<?> consumerBean = buildConsumerBean(consumer, consumerClass);
            ReflectionUtils.makeAccessible(field);
            field.set(target, consumerBean.getObject());
        }
    }

    private ConsumerBean<?> buildConsumerBean(Consumer consumer, Class<?> consumerClass) {
        String beanKey = generateConsumerBeanKey(consumer, consumerClass);
        ConsumerBean<?> consumerBean = consumerBeansCache.get(beanKey);
        if (consumerBean == null) {
            ConsumerBean<Object> createConsumerBean = new ConsumerBean<>();
            createConsumerBean.setApplicationContext(applicationContext);
            createConsumerBean.setProvider(consumer.value());
            createConsumerBean.setInterfaceClass(consumerClass);
            consumerBean = createConsumerBean;

            consumerBeansCache.putIfAbsent(beanKey, createConsumerBean);
        }
        return consumerBean;
    }

    private String generateConsumerBeanKey(Consumer consumer, Class<?> consumerClass) {
        return consumer.value() + "/" + consumerClass.getName();
    }
}
