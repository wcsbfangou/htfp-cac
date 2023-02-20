package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class QueryFlightPlanInfoParam {

    private String cpn;
    private String uavName;
    private String flightPlanId;
    private List<String> routePointCoordinates;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private String startTime;
    private String endTime;
    private Integer missionType;
    private String emergencyProcedure;
    private String operationScenarioType;
    private Boolean isEmergency;
    private Boolean isVlos;
    private Integer planStatus;
}
