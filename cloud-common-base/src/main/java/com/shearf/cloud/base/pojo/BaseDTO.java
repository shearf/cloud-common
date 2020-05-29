package com.shearf.cloud.base.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

import java.io.Serializable;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description Service Manager等传输数据
 * @since 2020/1/8 15:09
 */
public abstract class BaseDTO implements Serializable {

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
