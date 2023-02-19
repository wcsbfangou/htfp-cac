package com.htfp.service.cac.inner.app.service;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.inner.request.ATCSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.AlarmSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.response.ATCSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.AlarmSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlightPlanReplyResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlyReplyResponse;
import com.htfp.service.cac.router.biz.service.http.IRouteToGcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Service("oacServiceImpl")
public class OacServiceImpl implements IOacService {

    @Resource(name="routeToGcsServiceImpl")
    private IRouteToGcsService routeToGcsService;

    /**
     * 飞行计划回复
     *
     * @param flightPlanReplyRequest
     * @return
     */
    @Override
    public FlightPlanReplyResponse flightPlanReply(FlightPlanReplyRequest flightPlanReplyRequest) {
        FlightPlanReplyResponse flightPlanReplyResponse = new FlightPlanReplyResponse();
        flightPlanReplyResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flightPlanReplyRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flightPlanReplyResponse = routeToGcsService.flightPlanReply(flightPlanReplyRequest);
            } else {
                flightPlanReplyResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("飞行计划回复异常, flightPlanReplyRequest={}", flightPlanReplyRequest, e);
            flightPlanReplyResponse.fail("飞行计划回复异常");
        }
        return flightPlanReplyResponse;
    }

    /**
     * 放飞计划回复
     *
     * @param flyReplyRequest
     * @return
     */
    @Override
    public FlyReplyResponse flyReply(FlyReplyRequest flyReplyRequest) {
        FlyReplyResponse flyReplyResponse = new FlyReplyResponse();
        flyReplyResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flyReplyRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flyReplyResponse = routeToGcsService.flyReply(flyReplyRequest);
            } else {
                flyReplyResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("放飞计划回复异常, flyReplyRequest={}", flyReplyRequest, e);
            flyReplyResponse.fail("放飞计划回复异常");
        }
        return flyReplyResponse;
    }

    /**
     * 管制信息发送
     *
     * @param atcSendRequest
     * @return
     */
    @Override
    public ATCSendResponse atcSend(ATCSendRequest atcSendRequest) {
        ATCSendResponse atcSendResponse = new ATCSendResponse();
        atcSendResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = atcSendRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                atcSendResponse = routeToGcsService.atcSend(atcSendRequest);
            } else {
                atcSendResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("管制信息下发异常, atcSendRequest={}", atcSendRequest, e);
            atcSendResponse.fail("管制信息下发异常");
        }
        return atcSendResponse;
    }

    /**
     * 告警信息发送
     *
     * @param alarmSendRequest
     * @return
     */
    @Override
    public AlarmSendResponse alarmSend(AlarmSendRequest alarmSendRequest) {
        AlarmSendResponse alarmSendResponse = new AlarmSendResponse();
        alarmSendResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = alarmSendRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                alarmSendResponse = routeToGcsService.alarmSend(alarmSendRequest);
            } else {
                alarmSendResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("告警信息下发异常, alarmSendRequest={}", alarmSendRequest, e);
            alarmSendResponse.fail("告警信息下发异常");
        }
        return alarmSendResponse;
    }

}
