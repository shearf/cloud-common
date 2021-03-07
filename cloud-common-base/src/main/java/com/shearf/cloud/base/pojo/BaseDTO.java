package com.shearf.cloud.base.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * 传输数据类
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/8 15:09
 */
public abstract class BaseDTO implements Serializable {

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
