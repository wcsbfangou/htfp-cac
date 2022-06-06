package com.htfp.service.cac.router.biz.model.response;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-05-19 19:35
 */
@Data
public class BaseResponse {

    private Boolean success;
    private Integer code;
    private String message;


    public void success(){
        this.setSuccess(true);
        this.setCode(ErrorCodeEnum.SUCCESS.getCode());
        this.setMessage(ErrorCodeEnum.SUCCESS.getDesc());
    }

    public void fail(){
        this.setSuccess(false);
        fail(ErrorCodeEnum.OTHER_BIZ_ERROR);
    }

    public void fail(ErrorCodeEnum errorCodeEnum){
        this.setSuccess(false);
        this.setCode(errorCodeEnum.getCode());
        this.setMessage(errorCodeEnum.getDesc());
    }

    public void fail(String message){
        this.setSuccess(false);
        this.setMessage(message);
    }

    public void fail(Integer code, String message){
        this.setSuccess(false);
        this.setCode(code);
        this.setMessage(message);
    }
}
