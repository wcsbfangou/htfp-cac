package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class QueryFlightPlanInfoResultParam {

    private String cpn;
    private String shortCpn;
    private String flightPlanId;
    private String shortFlightPlanId;
    private String applyFlightPlanId;
    private String uavName;
    private String uavProductName;
    private Integer uavProductType;
    private Integer uavProductSizeType;
    private String uavManufactureName;
    private String routePointName;
    private Integer routePointLength;
    private List<CoordinateParam> routePointCoordinates;
    private String flightPlanStartTime;
    private String flightPlanEndTime;
    private String flightPlanApplyTime;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private Boolean isEmergency;
    private Boolean isVlos;
    private Integer missionType;
    private String emergencyProcedure;
    private String operationScenarioType;
    private Integer uavPlanStatus;
    private String pilotName;
    private String operatorName;
}
