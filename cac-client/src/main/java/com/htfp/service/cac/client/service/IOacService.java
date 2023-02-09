package com.htfp.service.cac.client.service;

import com.htfp.service.cac.client.request.FlightPlanReplyRequest;
import com.htfp.service.cac.client.request.FlyReplyRequest;
import com.htfp.service.cac.client.response.FlightPlanReplyResponse;
import com.htfp.service.cac.client.response.FlyReplyResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
public interface IOacService {

    /**
     * 飞行计划回复
     * @param flightPlanReplyRequest
     * @return
     */
    FlightPlanReplyResponse flightPlanReply(FlightPlanReplyRequest flightPlanReplyRequest);

    /**
     * 放飞申请回复
     * @param flyReplyRequest
     * @return
     */
    FlyReplyResponse flyReply(FlyReplyRequest flyReplyRequest);
}
