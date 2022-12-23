package com.htfp.service.oac.client.service;

import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
public interface IPreFlightService {

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

}
