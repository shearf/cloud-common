package com.shearf.cloud.base.pojo;

import org.apache.commons.lang3.builder.ReflectionToStringBuilder;

import java.io.Serializable;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description PO对应数据表字段，提供给DAO层进行操作
 * @since 2020/5/29 15:04
 */
public abstract class BasePO implements Serializable {

    @Override
    public String toString() {
        return new ReflectionToStringBuilder(this)
                .toString();
    }
}
