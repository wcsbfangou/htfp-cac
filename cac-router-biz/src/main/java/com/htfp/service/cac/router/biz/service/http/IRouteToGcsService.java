package com.htfp.service.cac.router.biz.service.http;


import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
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
}
