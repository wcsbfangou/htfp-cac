package com.htfp.service.cac.router.biz.model.response;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-05-19 19:58
 */

@Data
public class CommandUavResultParam {

    private String uavId;
    private boolean commandResult;
}
