package com.htfp.service.oac.front.app.model;


import com.htfp.service.oac.common.enums.ErrorCodeEnum;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 14:55
 * @Description http响应基类
 */
public class BaseHttpResponse {

    private boolean success;
    private Integer code;
    private String message;
    private String data;

    public BaseHttpResponse(boolean success, Integer code, String message, String data) {
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


    public BaseHttpResponse(boolean success, ErrorCodeEnum errorCodeEnum, String data) {
        this.success = success;
        this.code = errorCodeEnum.getCode();
        this.message = errorCodeEnum.getDesc();
        this.data = data;
    }

    public BaseHttpResponse(boolean success, String data) {
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

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

    public static BaseHttpResponse success() {
        return new BaseHttpResponse(true);
    }

    public static BaseHttpResponse success(String data) {
        return new BaseHttpResponse(true, data);
    }

    public static BaseHttpResponse fail(ErrorCodeEnum errorCodeEnum) {
        return new BaseHttpResponse(false, errorCodeEnum);
    }

    public static BaseHttpResponse fail(ErrorCodeEnum errorCodeEnum, String data) {
        return new BaseHttpResponse(false, errorCodeEnum, data);
    }

    public static BaseHttpResponse fail(int code, String msg) {
        return new BaseHttpResponse(false, code, msg);
    }

    public static BaseHttpResponse fail(int code, String msg, String data) {
        return new BaseHttpResponse(false, code, msg, data);
    }

    public static BaseHttpResponse fail(String data) {
        return new BaseHttpResponse(false, ErrorCodeEnum.UNKNOWN_ERROR, data);
    }
}
