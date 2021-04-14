package com.shearf.cloud.base.exception;

/**
 * 基本错误类
 *
 * @author xiahaihu2009@gmail.com
 * @since 2021/4/14 3:38 下午
 */
public class BaseException extends Exception {

    private static final long serialVersionUID = -4821015550620071496L;

    private int code;

    public BaseException() {
    }

    public BaseException(String message) {
        super(message);
    }

    public BaseException(String message, Throwable cause) {
        super(message, cause);
    }

    public BaseException(int code, String message, Throwable cause) {
        super(message, cause);
        this.code = code;
    }

    public BaseException(int code, String message) {
        super(message);
        this.code = code;
    }

    public int getCode() {
        return code;
    }
}
