package com.htfp.service.oac.biz.service;

import com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest;
import com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest;
import com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest;
import com.htfp.service.oac.biz.model.inner.response.FinishFlightPlanResponse;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanQueryResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyQueryResponse;
import com.htfp.service.oac.biz.model.inner.response.UavVerifyApplyResponse;

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
     * 结束飞行计划
     * @param finishFlightPlanRequest
     * @return
     */
    FinishFlightPlanResponse finishFlightPlan(FinishFlightPlanRequest finishFlightPlanRequest);
}
