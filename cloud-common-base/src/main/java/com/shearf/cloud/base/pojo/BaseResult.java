package com.shearf.cloud.base.pojo;

/**
 * 基本结果类
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/7 16:34
 */
public abstract class BaseResult<T> implements IResult<T> {

    /**
     * 错误码
     */
    protected int code;

    /**
     * 错误信息
     */
    protected String message;

    /**
     * 数据实体
     */
    protected T data;

    @Override
    public int getCode() {
        return code;
    }

    @Override
    public String getMessage() {
        return message;
    }

    @Override
    public T getData() {
        return data;
    }
}
