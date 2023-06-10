package com.htfp.service.cac.router.biz.service.http;

import com.htfp.service.cac.router.biz.model.http.request.ATCQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.AlarmQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FinishFlightPlanRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavVerifyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.response.ATCQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.AlarmQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.FinishFlightPlanResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavVerifyApplyResponse;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 9:46
 * @Description 地面站服务类
 */
public interface IGcsService {

    /**
     * 地面站注册
     * @param signInRequest
     * @return
     */
    SignInResponse gcsSignIn(SignInRequest signInRequest);

    /**
     * 地面站注销
     * @param signOutRequest
     * @return
     */
    SignOutResponse gcsSignOut(SignOutRequest signOutRequest);

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
     * 无人机接入校验申请
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
     * 放飞查询
     * @param flyQueryRequest
     * @return
     */
    FlyQueryResponse flyQuery(FlyQueryRequest flyQueryRequest);

    /**
     * 地面站在控无人机变更
     * @param gcsChangeUavRequest
     * @return
     */
    GcsChangeUavResponse gcsChangeUav(GcsChangeUavRequest gcsChangeUavRequest);

    /**
     * 无人机状态变更
     * @param uavStatusChangeRequest
     * @return
     */
    UavStatusChangeResponse uavStatusChange(UavStatusChangeRequest uavStatusChangeRequest);

    /**
     * 地面站指控指令执行
     * @param gcsControlUavRequest
     * @return
     */
    GcsControlUavResponse gcsControlUav(GcsControlUavRequest gcsControlUavRequest);

    /**
     * 飞行计划结束通知
     * @param finishFlightPlanRequest
     * @return
     */
    FinishFlightPlanResponse finishFlightPlan(FinishFlightPlanRequest finishFlightPlanRequest);

    /**
     * 管制信息查询
     * @param atcQueryRequest
     * @return
     */
    ATCQueryResponse atcQuery(ATCQueryRequest atcQueryRequest);

    /**
     * 告警信息查询
     * @param alarmQueryRequest
     * @return
     */
    AlarmQueryResponse alarmQuery(AlarmQueryRequest alarmQueryRequest);
}
