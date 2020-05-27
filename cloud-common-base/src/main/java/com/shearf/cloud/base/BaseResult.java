package com.shearf.cloud.base;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/7 16:34
 */
public abstract class BaseResult<T> implements IResult<T> {

    protected int code;

    protected String message;

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
