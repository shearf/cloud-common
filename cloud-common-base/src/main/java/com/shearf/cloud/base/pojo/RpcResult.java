package com.shearf.cloud.base.pojo;

import com.shearf.cloud.base.error.IError;
import org.apache.commons.lang3.builder.ToStringBuilder;
import org.apache.commons.lang3.builder.ToStringStyle;

/**
 *
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/8 11:17
 */
public class RpcResult<T> extends BaseResult<T> {

    private static final long serialVersionUID = 141971695531890787L;

    public RpcResult() {
        this.code = IError.FAIL_CODE;
        this.message = "操作失败";
    }

    public RpcResult(int code) {
        this.code = code;
        String message = "";
        if (IError.SUCCESS_CODE == code) {
            message = IError.SUCCESS_MESSAGE;
        } else if (IError.FAIL_CODE == code) {
            message = IError.FAIL_MESSAGE;
        }
        this.message = message;
    }

    public RpcResult(int code, String message) {
        this.code = code;
        this.message = message != null ? message : "";
    }

    public RpcResult(T data) {
        this.code = IError.SUCCESS_CODE;
        this.message = IError.SUCCESS_MESSAGE;
        this.data = data;
    }

    @Override
    public boolean isSuccess() {
        return code == IError.SUCCESS_CODE;
    }

    public static <T> RpcResult<T> success() {
        return new RpcResult<>();
    }

    public static <T> RpcResult<T> success(T data) {
        return new RpcResult<>(data);
    }

    public static <T> RpcResult<T> fail() {
        return new RpcResult<>(IError.FAIL_CODE);
    }

    public static <T> RpcResult<T> fail(int code, String message) {
        return new RpcResult<>(code, message);
    }

    public static <T> RpcResult<T> fail(IError error) {
        return new RpcResult<>(error.getErrCode(), error.getErrMsg());
    }

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .append("data", data)
                .toString();
    }
}
