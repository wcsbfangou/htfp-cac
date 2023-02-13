package com.htfp.service.oac.app.service;

import com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanQueryResponse;

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
