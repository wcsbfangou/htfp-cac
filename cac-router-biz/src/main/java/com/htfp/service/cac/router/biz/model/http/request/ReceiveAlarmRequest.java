package com.htfp.service.cac.router.biz.model.http.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class ReceiveAlarmRequest {

    private String uavId;
    private String uavReg;
    private Integer alarmLevel;
    private String content;
    private String effectTime;

}
