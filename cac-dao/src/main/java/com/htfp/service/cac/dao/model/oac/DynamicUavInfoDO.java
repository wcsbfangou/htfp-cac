package com.htfp.service.cac.dao.model.oac;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class DynamicUavInfoDO implements Serializable {

    private Long id;

    private Long replyFlightPlanId;
    private Long replyFlyId;
    private String uavName;
    private String cpn;
    private String uavOperatorName;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer speed;
    private Integer course;
    private Integer fuel;
    private Integer battery;
    private Integer signalStrength;
    private String updateTime;
    private String flightPlanStartTime;
    private String flightPlanEndTime;
    private String startFlyTime;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private Integer landingAirportIdentificationRadius;
    private Integer landingAirportAlarmRadius;
    private Integer landingLng;
    private Integer landingLat;
    private Integer landingAlt;
    private Integer distanceToLandingPoint;
    private Boolean inAlarm;
    private String alarmIds;
    private Boolean accessSystem;
    private Integer planStatus;
    private Integer uavStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
