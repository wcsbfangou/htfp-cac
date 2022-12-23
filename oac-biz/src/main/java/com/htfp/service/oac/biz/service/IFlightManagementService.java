package com.htfp.service.oac.biz.service;

import com.htfp.service.oac.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
public interface IFlightManagementService {


    /**
     * 飞行计划申请
     * @param flightPlanApplyRequest
     * @return
     */
    FlightPlanApplyResponse flightPlanApply(FlightPlanApplyRequest flightPlanApplyRequest);

    /**
     * 飞行计划查询
     * @param flightPlanQueryRequest
     * @return
     */
    FlightPlanQueryResponse flightPlanQuery(FlightPlanQueryRequest flightPlanQueryRequest);

    /**
     * 飞行计划下发
     * @param flightPlanQueryRequest
     * @return
     */
    FlightPlanIssuedResponse flightPlanIssued(FlightPlanIssuedRequest flightPlanQueryRequest);
}
