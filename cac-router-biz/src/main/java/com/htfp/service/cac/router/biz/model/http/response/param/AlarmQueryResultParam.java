package com.htfp.service.cac.router.biz.model.http.response.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class AlarmQueryResultParam {

    private String alarmId;
    private String applyFlightPlanId;
    private String applyFlyId;
    private Long uavId;
    private Integer alarmLevel;
    private String alarmContent;
    private String alarmEffectTime;
    private String alarmOperator;
}
