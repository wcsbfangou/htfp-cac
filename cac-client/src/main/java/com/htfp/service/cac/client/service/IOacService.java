package com.htfp.service.cac.client.service;

import com.htfp.service.cac.client.request.FlightPlanIssuedRequest;
import com.htfp.service.cac.client.response.FlightPlanIssuedResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
public interface IOacService {

    /**
     * 飞行计划下发
     * @param flightPlanQueryRequest
     * @return
     */
    FlightPlanIssuedResponse flightPlanIssued(FlightPlanIssuedRequest flightPlanQueryRequest);
}
