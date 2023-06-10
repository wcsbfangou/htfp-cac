package com.htfp.service.cac.router.biz.service.http;


import com.htfp.service.cac.router.biz.model.inner.request.ATCSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.AlarmSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.response.ATCSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.AlarmSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlightPlanReplyResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlyReplyResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
public interface IRouteToGcsService {

    /**
     * 飞行计划回复
     * @param flightPlanReplyRequest
     * @return
     */
    FlightPlanReplyResponse flightPlanReply(FlightPlanReplyRequest flightPlanReplyRequest);

    /**
     * 飞行计划回复
     * @param flyReplyRequest
     * @return
     */
    FlyReplyResponse flyReply(FlyReplyRequest flyReplyRequest);

    /**
     * 飞行计划下发
     * @param atcSendRequest
     * @return
     */
    ATCSendResponse atcSend(ATCSendRequest atcSendRequest);

    /**
     * 告警信息下发
     * @param alarmSendRequest
     * @return
     */
    AlarmSendResponse alarmSend(AlarmSendRequest alarmSendRequest);
}
