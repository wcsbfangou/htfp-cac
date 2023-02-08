package com.htfp.service.cac.client.service;

import com.htfp.service.cac.client.request.FlightPlanReplyRequest;
import com.htfp.service.cac.client.response.FlightPlanReplyResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
public interface IOacService {

    /**
     * 飞行计划回复
     * @param flightPlanQueryRequest
     * @return
     */
    FlightPlanReplyResponse flightPlanReply(FlightPlanReplyRequest flightPlanQueryRequest);
}