package com.shearf.cloud.common.framework.spring.bean;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.BeansException;
import org.springframework.beans.factory.DisposableBean;
import org.springframework.beans.factory.FactoryBean;
import org.springframework.context.ApplicationContext;
import org.springframework.context.ApplicationContextAware;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.client.RestTemplate;

import java.lang.reflect.InvocationHandler;
import java.lang.reflect.Method;
import java.lang.reflect.Proxy;
import java.util.LinkedList;
import java.util.List;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/17 16:38
 */
public class ConsumerBean<T> implements FactoryBean<T>, DisposableBean, ApplicationContextAware {

    private Class<?> interfaceClass;

    private String provider;

    private ApplicationContext applicationContext;

    public void setInterfaceClass(Class<?> interfaceClass) {
        this.interfaceClass = interfaceClass;
    }

    public void setProvider(String provider) {
        this.provider = provider;
    }

    @Override
    @SuppressWarnings("unchecked")
    public T getObject() throws Exception {
        String interfaceUrl = "http://" + provider + "/" + interfaceClass.getTypeName();
        RestTemplate restTemplate = applicationContext.getBean("restTemplate", RestTemplate.class);
        ConsumerInvocationHandler handler = new ConsumerInvocationHandler(restTemplate, interfaceUrl);
        return (T) Proxy.newProxyInstance(interfaceClass.getClassLoader(), new Class[]{interfaceClass}, handler);
    }

    @Override
    public Class<?> getObjectType() {
        return interfaceClass;
    }

    @Override
    public boolean isSingleton() {
        return true;
    }

    @Override
    public void destroy() throws Exception {

    }

    @Override
    public void setApplicationContext(@NonNull ApplicationContext applicationContext) throws BeansException {
        this.applicationContext = applicationContext;
    }

    private static class ConsumerInvocationHandler implements InvocationHandler {

        private final RestTemplate restTemplate;

        private final String interfaceUrl;

        public ConsumerInvocationHandler(RestTemplate restTemplate, String interfaceUrl) {
            this.restTemplate = restTemplate;
            this.interfaceUrl = interfaceUrl;
        }

        @Override
        public Object invoke(Object proxy, Method method, Object[] args) throws Throwable {
            String url = interfaceUrl + "/" + method.getName();
            HttpHeaders httpHeaders = new HttpHeaders();
            httpHeaders.add(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);

            // 拼装请求参数与请求体
            List<Object> body = new LinkedList<>();
            List<String> argList = new LinkedList<>();
            if (args != null) {
                for (Object object : args) {
                    body.add(object);
                    argList.add(object.getClass().getTypeName());
                }
                if (argList.size() > 0) {
                    httpHeaders.add("args", StringUtils.join(argList, ","));
                }
            }

            HttpEntity<List<Object>> httpRequest = new HttpEntity<>(body, httpHeaders);
            return restTemplate.postForObject(url, httpRequest, method.getReturnType());
        }
    }
}
