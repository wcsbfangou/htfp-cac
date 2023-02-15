package com.htfp.service.cac.app.service;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
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

}
