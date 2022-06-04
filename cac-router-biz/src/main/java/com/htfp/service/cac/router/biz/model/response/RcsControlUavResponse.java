package com.htfp.service.cac.router.biz.model.response;

import lombok.Data;

import java.util.List;


/**
 * @Author sunjipeng
 * @Date 2022-05-19 20:00
 */

@Data
public class RcsControlUavResponse extends BaseResponse {

    private List<CommandUavResultParam> commandUavResultParamList;
}
