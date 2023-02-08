package com.htfp.service.oac.app.service;

import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import com.htfp.service.oac.client.request.FinishFlightPlanRequest;
import com.htfp.service.oac.client.request.FlyApplyRequest;
import com.htfp.service.oac.client.request.FlyQueryRequest;
import com.htfp.service.oac.client.request.UavVerifyApplyRequest;
import com.htfp.service.oac.client.response.FinishFlightPlanResponse;
import com.htfp.service.oac.client.response.FlyApplyResponse;
import com.htfp.service.oac.client.response.FlyQueryResponse;
import com.htfp.service.oac.client.response.UavVerifyApplyResponse;
import com.htfp.service.oac.client.service.IFlyingService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2023/2/8
 * @Description 描述
 */
@Slf4j
@Service("flyingServiceImpl")
public class FlyingServiceImpl implements IFlyingService {

    @Resource(name="flightManagementServiceImpl")
    IFlightManagementService flightManagementService;

    /**
     * 无人机系统接入校验
     *
     * @param uavVerifyApplyRequest
     * @return
     */
    @Override
    public UavVerifyApplyResponse uavVerifyApply(UavVerifyApplyRequest uavVerifyApplyRequest) {
        UavVerifyApplyResponse uavVerifyApplyResponse = new UavVerifyApplyResponse();
        uavVerifyApplyResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = uavVerifyApplyRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                uavVerifyApplyResponse = flightManagementService.uavVerifyApply(uavVerifyApplyRequest);
            } else {
                uavVerifyApplyResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("无人机系统接入校验异常, uavVerifyApplyRequest={}", uavVerifyApplyRequest, e);
            uavVerifyApplyResponse.fail("无人机系统接入校验异常");
        }
        return uavVerifyApplyResponse;
    }

    /**
     * 放飞申请
     *
     * @param flyApplyRequest
     * @return
     */
    @Override
    public FlyApplyResponse flyApply(FlyApplyRequest flyApplyRequest) {
        FlyApplyResponse flyApplyResponse = new FlyApplyResponse();
        flyApplyResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flyApplyRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flyApplyResponse = flightManagementService.flyApply(flyApplyRequest);
            } else {
                flyApplyResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("放飞申请校验异常, flyApplyRequest={}", flyApplyRequest, e);
            flyApplyResponse.fail("放飞申请接入校验异常");
        }
        return flyApplyResponse;
    }

    /**
     * 放飞申请查询
     *
     * @param flyQueryRequest
     * @return
     */
    @Override
    public FlyQueryResponse flyQuery(FlyQueryRequest flyQueryRequest) {
        FlyQueryResponse flyQueryResponse = new FlyQueryResponse();
        flyQueryResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = flyQueryRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                flyQueryResponse = flightManagementService.flyQuery(flyQueryRequest);
            } else {
                flyQueryResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("放飞申请查询校验异常, flyQueryRequest={}", flyQueryRequest, e);
            flyQueryResponse.fail("放飞申请查询校验异常");
        }
        return flyQueryResponse;
    }

    /**
     * 结束飞行计划
     *
     * @param finishFlightPlanRequest
     * @return
     */
    @Override
    public FinishFlightPlanResponse finishFlightPlan(FinishFlightPlanRequest finishFlightPlanRequest) {
        FinishFlightPlanResponse finishFlightPlanResponse = new FinishFlightPlanResponse();
        finishFlightPlanResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = finishFlightPlanRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                finishFlightPlanResponse = flightManagementService.finishFlightPlan(finishFlightPlanRequest);
            } else {
                finishFlightPlanResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("结束飞行计划校验异常, flyQueryRequest={}", finishFlightPlanRequest, e);
            finishFlightPlanResponse.fail("结束飞行计划校验异常");
        }
        return finishFlightPlanResponse;
    }
}
