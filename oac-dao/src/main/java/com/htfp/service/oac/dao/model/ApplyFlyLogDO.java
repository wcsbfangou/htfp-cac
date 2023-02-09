package com.htfp.service.oac.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2023/2/9
 * @Description 描述
 */
@Data
public class ApplyFlyLogDO implements Serializable {

    private Long id;
    private String applyFlyId;
    private Long replyFlyId;
    private String applyFlightPlanId;
    private Long replyFlightPlanId;
    private String cpn;
    private String airspaceNumbers;
    private String operationScenarioType;
    private Integer flyLng;
    private Integer flyLat;
    private Integer flyAlt;
    private String vin;
    private String pvin;
    private String flightControlSn;
    private String imei;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
