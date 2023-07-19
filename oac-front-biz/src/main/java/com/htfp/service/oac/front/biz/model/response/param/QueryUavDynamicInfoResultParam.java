package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class QueryUavDynamicInfoResultParam {

    private String shortCpn;
    private String shortFlightPlanId;
    private String cpn;
    private String flightPlanId;
    private String flyId;
    private String uavName;
    private Integer uavProductType;
    private String uavOperatorName;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer speed;
    private Integer course;
    private Integer fuel;
    private Integer battery;
    private Integer signal;
    private String updateTime;
    private String flightPlanStartTime;
    private String flightPlanEndTime;
    private String startFlyTime;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private Integer distanceToLandingPoint;
    private Boolean inAlarm;
    private List<String> alarmIds;
    private Integer uavPlanStatus;
    private Integer uavStatus;
}
