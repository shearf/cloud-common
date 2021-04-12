package com.shearf.cloud.common.framework.annotation;

import java.lang.annotation.*;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description 消费者标示
 * @since 2020/1/15 17:00
 */
@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Consumer {

    /**
     * 提供者名称
     *
     * @return 提供者名称
     */
    String value();
}
