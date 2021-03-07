package com.shearf.cloud.base.pojo;

import java.io.Serializable;

/**
 * 通用结果类
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/7 16:26
 */
public interface IResult<T> extends Serializable {

    /**
     * 获得错误码
     *
     * @return 错误码
     */
    int getCode();

    /**
     * 获得错误信息
     *
     * @return 错误信息
     */
    String getMessage();

    /**
     * 获得结果
     *
     * @return 获得结果
     */
    T getData();

    /**
     * 是否成功
     *
     * @return 操作成功返回true
     */
    boolean isSuccess();
}
