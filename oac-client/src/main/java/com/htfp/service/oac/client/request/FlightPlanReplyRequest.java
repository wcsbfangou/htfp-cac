package com.htfp.service.oac.client.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlightPlanReplyRequest {

    private String applyIdFlightPlanId;
    private String replyFlightPlanId;
    private Boolean flightPlanPass;

}
