package com.htfp.service.cac.app.model;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 14:55
 */
public class BaseHttpResponse<T> {

    private boolean success;
    private Integer code;
    private String message;
    private T data;

    public BaseHttpResponse(boolean success, Integer code, String message, T data) {
        this.success = success;
        this.code = code;
        this.message = message;
        this.data = data;
    }

    public BaseHttpResponse(boolean success, Integer code, String message) {
        this.success = success;
        this.code = code;
        this.message = message;
    }


    public BaseHttpResponse(boolean success, ErrorCodeEnum errorCodeEnum) {
        this.success = success;
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
    }


    public BaseHttpResponse(boolean success, ErrorCodeEnum errorCodeEnum, T data) {
        this.success = success;
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
        this.data = data;
    }

    public BaseHttpResponse(boolean success, T data) {
        this.success = success;
        this.data = data;
    }

    public BaseHttpResponse(boolean success) {
        this.success = success;
    }

    public boolean isSuccess() {
        return success;
    }

    public void setSuccess(boolean success) {
        this.success = success;
    }

    public Integer getCode() {
        return code;
    }

    public void setCode(Integer code) {
        this.code = code;
    }

    public String getMessage() {
        return message;
    }

    public void setMessage(String message) {
        this.message = message;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }

    public static <T> BaseHttpResponse<T> success() {
        return new BaseHttpResponse<T>(true);
    }

    public static <T> BaseHttpResponse<T> success(T data) {
        return new BaseHttpResponse<T>(true, data);
    }

    public static <T> BaseHttpResponse<T> fail(ErrorCodeEnum errorCodeEnum) {
        return new BaseHttpResponse<T>(false, errorCodeEnum);
    }

    public static <T> BaseHttpResponse<T> fail(ErrorCodeEnum errorCodeEnum, T data) {
        return new BaseHttpResponse<T>(false, errorCodeEnum, data);
    }

    public static <T> BaseHttpResponse<T> fail(int code, String msg) {
        return new BaseHttpResponse<T>(false, code, msg);
    }

    public static <T> BaseHttpResponse<T> fail(int code, String msg, T data) {
        return new BaseHttpResponse<T>(false, code, msg, data);
    }

    public static <T> BaseHttpResponse<T> fail(T data) {
        return new BaseHttpResponse<T>(false, ErrorCodeEnum.UNKNOWN_ERROR, data);
    }

}
