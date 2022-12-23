package com.htfp.service.oac.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class DynamicRouteInfoDO implements Serializable {

    private Long id;

    private Long replyFlightPlanId;
    private Long replyFlyId;
    private String uavName;
    private String cpn;
    private String routePointCoordinates;
    private Integer currentLegStartLng;
    private Integer currentLegStartLat;
    private Integer currentLegStartAlt;
    private Integer currentLegEndLng;
    private Integer currentLegEndLat;
    private Integer currentLegEndAlt;
    private String takeoffSite;
    private String landingSite;
    private Integer planStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
