package com.shearf.cloud.common.framework;

import com.shearf.cloud.base.error.GlobalError;
import com.shearf.cloud.base.pojo.BaseResult;
import com.shearf.cloud.base.error.IError;
import org.apache.commons.lang3.builder.ToStringBuilder;

/**
 * API请求
 *
 * @author xiahaihu2009@gmail.com
 * @version 0.0.1
 * @since 2020/1/7 16:37
 */
public class ApiResponse<T> extends BaseResult<T> implements Response<T> {

    private static final long serialVersionUID = 4036500041349413244L;

    public ApiResponse() {
        this.code = GlobalError.FAIL.getErrCode();
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
        this.code = GlobalError.SUCCESS.getErrCode();
    }

    @Override
    public boolean isSuccess() {
        return code == GlobalError.SUCCESS.getErrCode();
    }

    public static <T> ApiResponse<T> success() {
        return new ApiResponse<>(GlobalError.SUCCESS.getErrCode());
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

    @Override
    public String toString() {
        return new ToStringBuilder(this)
                .append("code", code)
                .append("message", message)
                .append("data", data)
                .toString();
    }
}
