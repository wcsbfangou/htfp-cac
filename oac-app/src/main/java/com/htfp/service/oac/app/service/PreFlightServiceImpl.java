package com.htfp.service.oac.app.service;

import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;
import com.htfp.service.oac.client.service.IPreFlightService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Slf4j
@Service("preFlightServiceImpl")
public class PreFlightServiceImpl implements IPreFlightService {

    @Resource(name="flightManagementServiceImpl")
    IFlightManagementService flightManagementService;

    /**
     * 飞行计划申请
     *
     * @param flightPlanApplyRequest
     * @return
     */
    @Override
    public FlightPlanApplyResponse flightPlanApply(FlightPlanApplyRequest flightPlanApplyRequest) {
        FlightPlanApplyResponse flightPlanApplyResponse = new FlightPlanApplyResponse();
        flightPlanApplyResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flightPlanApplyRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flightPlanApplyResponse = flightManagementService.flightPlanApply(flightPlanApplyRequest);
            } else {
                flightPlanApplyResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("飞行计划申请异常, flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            flightPlanApplyResponse.fail("飞行计划申请异常");
        }
        return flightPlanApplyResponse;
    }

    /**
     * 飞行计划查询
     *
     * @param flightPlanQueryRequest
     * @return
     */
    @Override
    public FlightPlanQueryResponse flightPlanQuery(FlightPlanQueryRequest flightPlanQueryRequest) {
        FlightPlanQueryResponse flightPlanQueryResponse = new FlightPlanQueryResponse();
        flightPlanQueryResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flightPlanQueryRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flightPlanQueryResponse = flightManagementService.flightPlanQuery(flightPlanQueryRequest);
            } else {
                flightPlanQueryResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("飞行计划查询异常, flightPlanQueryRequest={}", flightPlanQueryRequest, e);
            flightPlanQueryResponse.fail("飞行计划查询异常");
        }
        return flightPlanQueryResponse;
    }
}
