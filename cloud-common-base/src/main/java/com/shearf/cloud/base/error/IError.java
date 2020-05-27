package com.shearf.cloud.base.error;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/7 16:38
 */
public interface IError {

    /**
     * 成功
     */
    int SUCCESS_CODE = 1;

    /**
     * 失败
     */
    int FAIL_CODE = 0;

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
