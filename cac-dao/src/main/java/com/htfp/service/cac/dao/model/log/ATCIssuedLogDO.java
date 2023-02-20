package com.htfp.service.cac.dao.model.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class ATCIssuedLogDO implements Serializable {

    private Long id;

    private Long applyFlightPlanId;
    private Long applyFlyId;
    private String replyFlightPlanId;
    private String replyFlyId;
    private Long navigationId;
    private Long uavId;
    private String uavReg;
    private String cpn;
    private Integer atcType;
    private String atcSpecificPosition;
    private String atcEffectTime;
    private Integer atcLimitPeriod;
    private String atcOperator;
    private Integer atcDelivered;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
