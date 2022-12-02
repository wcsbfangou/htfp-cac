package com.htfp.service.cac.command.biz.model.response;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-05-19 19:35
 * @Description 基础响应体
 */
@Data
public class BaseResponse {

    private Integer code;
    private String message;


    public void success() {
        this.setCode(ErrorCodeEnum.SUCCESS.getCode());
        this.setMessage(ErrorCodeEnum.SUCCESS.getDesc());
    }

    public void fail() {
        fail(ErrorCodeEnum.OTHER_BIZ_ERROR);
    }

    public void fail(ErrorCodeEnum errorCodeEnum) {
        this.setCode(errorCodeEnum.getCode());
        this.setMessage(errorCodeEnum.getDesc());
    }

    public void fail(String message) {
        this.setMessage(message);
    }

    public void fail(Integer code, String message) {
        this.setCode(code);
        this.setMessage(message);
    }
}
