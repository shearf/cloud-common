package com.shearf.cloud.common.framework.annotation;

import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.RestController;

import java.lang.annotation.*;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description 服务提供者标示
 * @since 2020/1/9 00:55
 */
@Target({ElementType.TYPE})
@RestController
@Retention(RetentionPolicy.RUNTIME)
@Documented
@Service
public @interface Provider {

    String value() default "";
}
