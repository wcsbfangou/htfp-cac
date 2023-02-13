package com.htfp.service.oac.app.service;


import com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest;
import com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest;
import com.htfp.service.oac.biz.model.inner.response.FinishFlightPlanResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyQueryResponse;
import com.htfp.service.oac.biz.model.inner.response.UavVerifyApplyResponse;

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
