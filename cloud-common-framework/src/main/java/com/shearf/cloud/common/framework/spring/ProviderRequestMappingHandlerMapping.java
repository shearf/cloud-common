package com.shearf.cloud.common.framework.spring;

import com.shearf.cloud.common.framework.annotation.Provider;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.mvc.method.RequestMappingInfo;
import org.springframework.web.servlet.mvc.method.annotation.RequestMappingHandlerMapping;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;
import java.util.LinkedList;
import java.util.List;

/**
 * 为服务提供者定制http请求的绑定关系
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/21 11:45
 */
@Slf4j
public class ProviderRequestMappingHandlerMapping extends RequestMappingHandlerMapping {

    @Override
    protected RequestMappingInfo getMappingForMethod(Method method, Class<?> handlerType) {
        Provider annotation = handlerType.getAnnotation(Provider.class);
        if (annotation != null) {
            String interfaceName = getMethodMatchedInterfaceName(method, handlerType);
            if (StringUtils.isNotBlank(interfaceName)) {
                String path = interfaceName + "/" + method.getName();
                Parameter[] parameters = method.getParameters();
                List<String> headerArgsList = new LinkedList<>();
                for (Parameter parameter : parameters) {
                    headerArgsList.add(parameter.getType().getTypeName());
                }

                RequestMappingInfo.Builder builder = RequestMappingInfo
                        .paths(resolveEmbeddedValuesInPatterns(new String[]{path}))
                        .methods(RequestMethod.POST)
                        .produces(MediaType.APPLICATION_JSON_VALUE);
                if (headerArgsList.size() > 0) {
                    builder.headers("args=" + StringUtils.join(headerArgsList, ","));
                }
                return builder.build();
            }

        }
        return super.getMappingForMethod(method, handlerType);
    }

    /**
     * 获得方法对应对接口名称
     *
     * @param method 方法
     * @param handlerType 方法所在的类
     * @return 方法所在的接口名称
     */
    private String getMethodMatchedInterfaceName(Method method, Class<?> handlerType) {
        Class<?>[] interfaces = handlerType.getInterfaces();
        for (Class<?> clz : interfaces) {
            try {
                Method clzMethod = clz.getMethod(method.getName(), method.getParameterTypes());
                if (clzMethod != null) {
                    return clz.getName();
                }
            } catch (NoSuchMethodException e) {
                log.error("找不到方法对应的接口, method:{}, handlerType:{}", method, handlerType);
            }
        }
        return null;
    }
}
