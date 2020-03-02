package com.shearf.cloud.common.framework;

import com.shearf.cloud.base.BaseResult;
import com.shearf.cloud.base.error.IError;

/**
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @description TODO
 * @since 2020/1/7 16:37
 */
public class ApiResponse<T> extends BaseResult<T> implements Response<T> {

    private static final long serialVersionUID = 4036500041349413244L;

    public ApiResponse() {
        this.code = IError.FAIL_CODE;
    }

    public ApiResponse(int code, String message) {
        this.code = code;
        this.message = message;
    }

    public ApiResponse(int code) {
        this.code = code;
    }

    public ApiResponse(IError error) {
        this.code = error.getErrCode();
        this.message = error.getErrMsg();
    }

    public ApiResponse(T data) {
        this.data = data;
        this.code = IError.SUCCESS_CODE;
    }

    @Override
    public boolean isSuccess() {
        return code == IError.SUCCESS_CODE;
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(IError.SUCCESS_CODE);
    }

    public static <T> ApiResponse<T> success(T data) {
        return new ApiResponse<>(data);
    }


    public static <T> ApiResponse<T> fail(IError error) {
        return new ApiResponse<>(error);
    }

    public static <T> ApiResponse<T> fail(int code, String message) {
        return new ApiResponse<>(code, message);
    }
}
