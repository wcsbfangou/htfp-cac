package com.htfp.service.oac.client.service;


import com.htfp.service.oac.client.request.FinishFlightPlanRequest;
import com.htfp.service.oac.client.request.FlyApplyRequest;
import com.htfp.service.oac.client.request.FlyQueryRequest;
import com.htfp.service.oac.client.request.UavVerifyApplyRequest;
import com.htfp.service.oac.client.response.FinishFlightPlanResponse;
import com.htfp.service.oac.client.response.FlyApplyResponse;
import com.htfp.service.oac.client.response.FlyQueryResponse;
import com.htfp.service.oac.client.response.UavVerifyApplyResponse;

/**
 * @Author sunjipeng
 * @Date 2023/2/7
 * @Description 描述
 */
public interface IFlyingService {

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
