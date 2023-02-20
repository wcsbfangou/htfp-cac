package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryAlarmMessageInfoParam {

    private String alarmId;
    private String flyId;
    private String cpn;
    private Integer alarmLevel;
    private String alarmContent;
    private String alarmEffectTime;
    private String alarmOperator;
    private Boolean alarmDeliver;

}
