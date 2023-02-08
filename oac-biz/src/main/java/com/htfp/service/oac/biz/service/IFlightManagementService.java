package com.htfp.service.oac.biz.service;

import com.htfp.service.oac.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.client.request.FinishFlightPlanRequest;
import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.request.FlyApplyRequest;
import com.htfp.service.oac.client.request.FlyQueryRequest;
import com.htfp.service.oac.client.request.UavVerifyApplyRequest;
import com.htfp.service.oac.client.response.FinishFlightPlanResponse;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;
import com.htfp.service.oac.client.response.FlyApplyResponse;
import com.htfp.service.oac.client.response.FlyQueryResponse;
import com.htfp.service.oac.client.response.UavVerifyApplyResponse;

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

    /**
     * 无人机系统接入校验
     * @param uavVerifyApplyRequest
     * @return
     */
    UavVerifyApplyResponse uavVerifyApply(UavVerifyApplyRequest uavVerifyApplyRequest);

    /**
     * 放飞申请
     * @param flyApplyRequest
     * @return
     */
    FlyApplyResponse flyApply(FlyApplyRequest flyApplyRequest);

    /**
     * 放飞申请查询
     * @param flyQueryRequest
     * @return
     */
    FlyQueryResponse flyQuery(FlyQueryRequest flyQueryRequest);

    /**
     * 放飞结果下发
     * @param flyIssuedRequest
     * @return
     */
    FlyIssuedResponse flyIssued(FlyIssuedRequest flyIssuedRequest);

    /**
     * 结束飞行计划
     * @param finishFlightPlanRequest
     * @return
     */
    FinishFlightPlanResponse finishFlightPlan(FinishFlightPlanRequest finishFlightPlanRequest);
}
