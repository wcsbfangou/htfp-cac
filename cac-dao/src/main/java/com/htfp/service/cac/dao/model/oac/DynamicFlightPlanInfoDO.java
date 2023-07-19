package com.htfp.service.cac.dao.model.oac;

import lombok.Data;

import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2023/6/9
 * @Description 描述
 */
@Data
public class DynamicFlightPlanInfoDO {

    private Long id;

    private Long replyFlightPlanId;
    private Long replyFlyId;
    private String cpn;
    private String uavName;
    private String pilotName;
    private String operatorName;
    private String flightPlanStartTime;
    private String flightPlanEndTime;
    private String takeoffAirportId;
    private String landingAirportId;
    private Boolean isEmergency;
    private Integer missionType;
    private Integer planStatus;
    private Integer flowStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
