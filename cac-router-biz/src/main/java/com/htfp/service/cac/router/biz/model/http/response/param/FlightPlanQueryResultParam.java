package com.htfp.service.cac.router.biz.model.http.response.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Data
public class FlightPlanQueryResultParam {

    private String applyFlightPlanId;
    private String replyFlightPlanId;
    private Integer status;
}
