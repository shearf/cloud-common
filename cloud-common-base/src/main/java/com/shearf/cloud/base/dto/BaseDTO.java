package com.shearf.cloud.base.dto;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/8 15:09
 */
public abstract class BaseDTO implements Serializable {

    private static final long serialVersionUID = -9026246666040517180L;

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
