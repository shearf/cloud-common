package com.shearf.cloud.base.error;

/**
 * 全局错误
 *
 * @author xiahaihu2009@gmail.com
 * @since 2021/4/14 3:28 下午
 */
public enum GlobalError implements IError {

    /**
     * 操作成功
     */
    SUCCESS(1, "操作成功"),

    /**
     * 操作失败
     */
    FAIL(0, "操作失败"),

    /**
     * 参数异常
     */
    PARAM_ERROR(100, "参数异常"),
    ;

    /**
     * 错误码
     */
    private final int code;

    /**
     * 错误信息
     */
    private final String message;

    GlobalError(int code, String message) {
        this.code = code;
        this.message = message;
    }

    @Override
    public int getErrCode() {
        return code;
    }

    @Override
    public String getErrMsg() {
        return message;
    }
}
