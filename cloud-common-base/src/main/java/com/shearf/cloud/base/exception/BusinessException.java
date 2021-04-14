package com.shearf.cloud.base.exception;

import com.shearf.cloud.base.error.IError;

/**
 * 业务错误
 *
 * @author xiahaihu2009@gmail.com
 * @since 2021/4/14 3:44 下午
 */
public class BusinessException extends BaseException {

    private static final long serialVersionUID = -6801234444442210300L;

    public BusinessException() {

    }

    public BusinessException(IError error) {
        super(error.getErrCode(), error.getErrMsg());
    }

    public BusinessException(int code, String message) {
        super(code, message);
    }

    public BusinessException(int code, String message, Throwable e) {
        super(code, message, e);
    }
}
