package com.htfp.service.oac.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Data
public class ApplyFlightPlanLogDO implements Serializable {

    private Long id;
    private String applyFlightPlanId;
    private Long replyFlightPlanId;
    private String cpn;
    private Integer applicantType;
    private String applicantSubject;
    private String pilots;
    private String airspaceNumbers;
    private String routePointCoordinates;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private Integer missionType;
    private String startTime;
    private String endTime;
    private String emergencyProcedure;
    private String operationScenarioType;
    private Boolean isEmergency;
    private Boolean isVlos;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
