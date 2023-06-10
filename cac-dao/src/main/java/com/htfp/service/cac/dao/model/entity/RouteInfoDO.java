package com.htfp.service.cac.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2023/2/21
 * @Description 描述
 */
@Data
public class RouteInfoDO implements Serializable {

    private Long id;

    private String routeCode;
    private String routeName;
    private String routePointCoordinates;
    private Integer routeLength;
    private String routeStartTime;
    private String routeEndTime;
    private Integer routeIdentificationRadius;
    private Integer routeAlarmRadius;
    private Integer routeLevel;
    private Long routeOperatorId;
    private Integer routeStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
