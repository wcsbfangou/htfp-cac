package com.htfp.service.oac.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:10
 */
@Data
public class UavInfoDO implements Serializable {

    private Long id;

    private String uavSourceId;
    private String uavReg;
    private String uavName;

    private String cpn;
    private String vin;
    private String pvin;
    private String sn;
    private String flightControlSn;
    private String imei;
    private String imsi;

    private String manufacturerName;
    private String productName;
    private Integer productType;
    private Integer productSizeType;
    private Integer maxFlyTime;
    private String operationScenarioType;
    private String operatorUniId;

    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
