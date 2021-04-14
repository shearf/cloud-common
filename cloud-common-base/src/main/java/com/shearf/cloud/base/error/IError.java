package com.shearf.cloud.base.error;

/**
 * 通用错误码接口
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/7 16:38
 */
public interface IError {

    /**
     * 获得错误码
     *
     * @return 错误码
     */
    int getErrCode();

    /**
     * 对应错误信息
     *
     * @return 错误信息
     */
    String getErrMsg();
}
