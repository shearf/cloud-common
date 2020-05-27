package com.shearf.cloud.common.framework.spring.adapter;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONArray;
import com.shearf.cloud.common.framework.annotation.Provider;
import org.apache.commons.io.IOUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.HttpHeaders;
import org.springframework.http.MediaType;
import org.springframework.lang.NonNull;
import org.springframework.web.method.HandlerMethod;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.method.AbstractHandlerMethodAdapter;

import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import java.io.PrintWriter;
import java.nio.charset.StandardCharsets;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/15 14:42
 */
public class ProviderHandlerMethodAdapter extends AbstractHandlerMethodAdapter {

    @Override
    protected boolean supportsInternal(HandlerMethod handlerMethod) {
        Provider annotation = handlerMethod.getBean().getClass().getAnnotation(Provider.class);
        return annotation != null;
    }

    @Override
    protected ModelAndView handleInternal(HttpServletRequest request, @NonNull HttpServletResponse response, @NonNull HandlerMethod handlerMethod) throws Exception {
        String args = request.getHeader("args");
        String body = IOUtils.toString(request.getInputStream(), StandardCharsets.UTF_8);
        Object invoke;
        if (StringUtils.isNotBlank(body) && StringUtils.isNotBlank(args)) {
            String[] splitArgs = StringUtils.split(args, ",");
            JSONArray objects = JSON.parseArray(body);
            int size = splitArgs.length;
            Object[] argvs = new Object[size];
            for (int i = 0; i < size; i++) {
                switch (splitArgs[i]) {
                    case "java.lang.String":
                        argvs[i] = objects.getString(i);
                        break;
                    case "java.lang.Integer":
                        argvs[i] = objects.getInteger(i);
                        break;
                    default:
                        argvs[i] = objects.getObject(i, Class.forName(splitArgs[i]));
                        break;
                }
            }
            invoke = handlerMethod.getMethod().invoke(handlerMethod.getBean(), argvs);
        } else {
            invoke = handlerMethod.getMethod().invoke(handlerMethod.getBean());
        }
        response.setHeader(HttpHeaders.CONTENT_TYPE, MediaType.APPLICATION_JSON_VALUE);
        try(PrintWriter writer = response.getWriter()) {
            writer.write(JSON.toJSONString(invoke));
        }
        return null;
    }

    @Override
    protected long getLastModifiedInternal(@NonNull HttpServletRequest request, @NonNull HandlerMethod handlerMethod) {
        return -1L;
    }
}
