package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/8
 * @Description 描述
 */
@Data
public class QueryUavDynamicFlightPlanResultParam {

    private String cpn;
    private String shortCpn;
    private String flightPlanId;
    private String shortFlightPlanId;
    private String uavName;
    private Integer uavType;
    private String pilotName;
    private String operatorName;
    private String flightPlanStartTime;
    private String flightPlanEndTime;
    private String takeoffAirportId;
    private String landingAirportId;
    private Boolean isEmergency;
    private Integer missionType;
    private Integer uavPlanStatus;
    private Integer uavFlowStatus;
}
