package com.shearf.cloud.base.exception;

import com.shearf.cloud.base.error.GlobalError;
import com.shearf.cloud.base.error.IError;

/**
 * 业务校验错误
 *
 * @author xiahaihu2009@gmail.com
 * @since 2021/4/14 3:48 下午
 */
public class RuleException extends BaseException {

    private static final long serialVersionUID = 751897544555723146L;

    public RuleException() {
        super(GlobalError.PARAM_ERROR.getErrCode(), GlobalError.PARAM_ERROR.getErrMsg());
    }

    public RuleException(IError error) {
        super(error.getErrCode(), error.getErrMsg());
    }

    public RuleException(String message) {
        super(GlobalError.PARAM_ERROR.getErrCode(), message);
    }

    public RuleException(int code, String message) {
        super(code, message);
    }

    public RuleException(int code, String message, Throwable e) {
        super(code, message, e);
    }
}
