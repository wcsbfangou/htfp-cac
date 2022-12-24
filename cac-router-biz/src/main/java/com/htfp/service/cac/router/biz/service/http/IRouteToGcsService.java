package com.htfp.service.cac.router.biz.service.http;


import com.htfp.service.cac.client.request.FlightPlanReplyRequest;
import com.htfp.service.cac.client.response.FlightPlanReplyResponse;

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
}
