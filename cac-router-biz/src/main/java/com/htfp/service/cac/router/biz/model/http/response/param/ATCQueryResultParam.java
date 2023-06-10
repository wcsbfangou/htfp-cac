package com.htfp.service.cac.router.biz.model.http.response.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class ATCQueryResultParam {

    private String atcId;
    private String applyFlightPlanId;
    private String applyFlyId;
    private Long uavId;
    private Integer atcType;
    private String atcEffectTime;
    private Integer atcLimitPeriod;
    private String atcOperator;
    private PositionParam atcSpecificPosition;
}
