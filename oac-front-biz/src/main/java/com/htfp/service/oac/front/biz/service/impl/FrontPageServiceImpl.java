package com.htfp.service.oac.front.biz.service.impl;

import com.htfp.service.cac.app.service.IOacService;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.response.FlightPlanReplyResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlyReplyResponse;
import com.htfp.service.oac.common.enums.ApplyStatusEnum;
import com.htfp.service.oac.common.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.common.utils.DateUtils;
import com.htfp.service.oac.common.utils.JsonUtils;
import com.htfp.service.oac.dao.model.AirportInfoDO;
import com.htfp.service.oac.dao.model.ApplyFlightPlanLogDO;
import com.htfp.service.oac.dao.model.ApplyFlyLogDO;
import com.htfp.service.oac.dao.model.DynamicRouteInfoDO;
import com.htfp.service.oac.dao.model.DynamicUavInfoDO;
import com.htfp.service.oac.dao.model.OperatorInfoDO;
import com.htfp.service.oac.dao.model.UavInfoDO;
import com.htfp.service.oac.dao.service.OacAirportInfoDalService;
import com.htfp.service.oac.dao.service.OacApplyFlightPlanLogDalService;
import com.htfp.service.oac.dao.service.OacApplyFlyLogDalService;
import com.htfp.service.oac.dao.service.OacDynamicRouteInfoDalService;
import com.htfp.service.oac.dao.service.OacDynamicUavInfoDalService;
import com.htfp.service.oac.dao.service.OacOperatorDalService;
import com.htfp.service.oac.dao.service.OacUavDalService;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.service.IFrontPageService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Slf4j
@Service("frontPageServiceImpl")
public class FrontPageServiceImpl implements IFrontPageService {

    @Resource
    private OacApplyFlyLogDalService oacApplyFlyLogDalService;

    @Resource
    private OacUavDalService oacUavDalService;

    @Resource
    private OacOperatorDalService oacOperatorDalService;

    @Resource
    private OacAirportInfoDalService oacAirportInfoDalService;

    @Resource
    private OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;

    @Resource
    private OacDynamicUavInfoDalService oacDynamicUavInfoDalService;

    @Resource
    private OacDynamicRouteInfoDalService oacDynamicRouteInfoDalService;

    @Resource(name="oacServiceImpl")
    private IOacService oacService;

    /**
     * 飞行计划下发
     *
     * @param flightPlanIssuedRequest
     * @return
     */
    @Override
    public FlightPlanIssuedResponse flightPlanIssued(FlightPlanIssuedRequest flightPlanIssuedRequest) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.fail();
        try {
            log.info("[oac]飞行计划下发start，flightPlanIssuedRequest={}", flightPlanIssuedRequest);
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flightPlanIssuedRequest.getFlightPlanId()));
            if (queryApplyFlightPlanLog != null) {
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                    if (flightPlanIssuedRequest.getPass()) {
                        oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.APPROVED.getCode());
                        // 飞行计划通过,存储动态信息
                        if (!initializeDynamicInfo(queryApplyFlightPlanLog, flightPlanIssuedRequest.getCpn(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode())) {
                            //ROLLBACK
                            oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, queryApplyFlightPlanLog.getStatus());
                            flightPlanIssuedResponse.fail();
                        }
                    } else {
                        oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.UNAPPROVED.getCode());
                    }
                    FlightPlanReplyRequest cacFlightPlanReplyRequest = buildCacFlightPlanReplyRequest(flightPlanIssuedRequest, queryApplyFlightPlanLog.getApplyFlightPlanId());
                    FlightPlanReplyResponse cacFlightPlanReplyResponse = oacService.flightPlanReply(cacFlightPlanReplyRequest);
                    flightPlanIssuedResponse = buildFlightPlanIssuedResponse(cacFlightPlanReplyResponse, flightPlanIssuedRequest.getCpn());
                    log.info("[oac]飞行计划下发end，flightPlanIssuedRequest={},flightPlanIssuedResponse={}", flightPlanIssuedRequest, JsonUtils.object2Json(flightPlanIssuedResponse));
                } else {
                    flightPlanIssuedResponse.fail("飞行计划已下发，不允许重复下发");
                }
            } else {
                flightPlanIssuedResponse.fail("未申请该飞行计划，不允许下发");
            }
        } catch (Exception e) {
            log.error("[oac]飞行计划下发异常，flightPlanIssuedRequest={}", flightPlanIssuedRequest, e);
            flightPlanIssuedResponse.fail(e.getMessage());
        }
        return flightPlanIssuedResponse;
    }

    FlightPlanReplyRequest buildCacFlightPlanReplyRequest(FlightPlanIssuedRequest flightPlanIssuedRequest, String applyFlightPlanId) {
        FlightPlanReplyRequest cacFlightPlanReplyRequest = new FlightPlanReplyRequest();
        cacFlightPlanReplyRequest.setCpn(flightPlanIssuedRequest.getCpn());
        cacFlightPlanReplyRequest.setApplyFlightPlanId(applyFlightPlanId);
        cacFlightPlanReplyRequest.setReplyFlightPlanId(flightPlanIssuedRequest.getFlightPlanId());
        cacFlightPlanReplyRequest.setPass(flightPlanIssuedRequest.getPass());
        return cacFlightPlanReplyRequest;
    }

    FlightPlanIssuedResponse buildFlightPlanIssuedResponse(FlightPlanReplyResponse cacFlightPlanReplyResponse, String cpn) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.setSuccess(cacFlightPlanReplyResponse.getSuccess());
        flightPlanIssuedResponse.setCode(cacFlightPlanReplyResponse.getCode());
        flightPlanIssuedResponse.setMessage(cacFlightPlanReplyResponse.getMessage());
        flightPlanIssuedResponse.setCpn(cpn);
        return flightPlanIssuedResponse;
    }

    Boolean initializeDynamicInfo(ApplyFlightPlanLogDO queryApplyFlightPlanLog, String cpn, Integer flightPlanStatus) {
        Boolean result = false;
        UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(cpn);
        if (queryUavInfo != null) {
            OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(queryUavInfo.getOperatorUniId());
            AirportInfoDO queryAirportInfo = oacAirportInfoDalService.queryAirportInfoByUavReg(queryApplyFlightPlanLog.getLandingAirportId());
            if (queryOperatorInfo != null && queryAirportInfo != null) {
                String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
                DynamicUavInfoDO dynamicUavInfo = oacDynamicUavInfoDalService.buildDynamicUavInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn, queryOperatorInfo.getOperatorName(),
                        null, null, null, null, null, null, null, null, currentTime, queryApplyFlightPlanLog.getStartTime(), queryApplyFlightPlanLog.getEndTime(),
                        null, queryApplyFlightPlanLog.getTakeoffAirportId(), queryApplyFlightPlanLog.getLandingAirportId(), queryApplyFlightPlanLog.getTakeoffSite(), queryApplyFlightPlanLog.getLandingSite(),
                        queryAirportInfo.getIdentificationAreaRadius(), queryAirportInfo.getAlarmAreaRadius(), queryAirportInfo.getLng(), queryAirportInfo.getLat(), queryAirportInfo.getAlt(),
                        null, null, null, false, flightPlanStatus, null);
                DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.buildDynamicUavInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn,
                        queryApplyFlightPlanLog.getRoutePointCoordinates(), null, null, null, null, null, null,
                        queryApplyFlightPlanLog.getTakeoffSite(), queryAirportInfo.getLandingSites(), flightPlanStatus);
                // TODO: 2022/12/23 事务
                int insertDynamicUavInfoResult = oacDynamicUavInfoDalService.insertDynamicUavInfo(dynamicUavInfo);
                int insertDynamicRouteInfoResult = oacDynamicRouteInfoDalService.insertDynamicRouteInfo(dynamicRouteInfoDO);
                if (insertDynamicUavInfoResult > 0 && insertDynamicRouteInfoResult > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 放飞结果下发
     *
     * @param flyIssuedRequest
     * @return
     */
    @Override
    public FlyIssuedResponse flyIssued(FlyIssuedRequest flyIssuedRequest) {
        FlyIssuedResponse flyIssuedResponse = new FlyIssuedResponse();
        flyIssuedResponse.fail();
        try {
            Long replyFlyId = Long.valueOf(flyIssuedRequest.getFlyId());
            log.info("[oac]放飞申请结果下发start，flyIssuedRequest={}", flyIssuedRequest);
            ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
            if (queryApplyFlyLog != null) {
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus()))) {
                    FlightPlanStatusTypeEnum flightPlanStatusTypeEnum;
                    if (flyIssuedRequest.getPass()) {
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.APPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED;
                    } else {
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.UNAPPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_UNAPPROVED;
                    }
                    if (!updateDynamicInfoPlanStatusByReplyFlyId(replyFlyId, flightPlanStatusTypeEnum)) {
                        //ROLLBACK
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, queryApplyFlyLog.getStatus());
                        flyIssuedResponse.fail();
                    } else {
                        flyIssuedResponse.setCpn(flyIssuedRequest.getCpn());
                    }
                    FlyReplyRequest cacFlyReplyRequest = buildCacFlyReplyRequest(flyIssuedRequest, queryApplyFlyLog.getApplyFlyId());
                    FlyReplyResponse cacFlyReplyResponse = oacService.flyReply(cacFlyReplyRequest);
                    flyIssuedResponse = buildFlyIssuedResponse(cacFlyReplyResponse);
                    log.info("[oac]放飞申请结果下发end，flyIssuedRequest={}, flyIssuedResponse={}", flyIssuedRequest, JsonUtils.object2Json(flyIssuedResponse));
                } else {
                    flyIssuedResponse.fail("放飞申请结果已下发，不允许重复下发");
                }
            } else {
                flyIssuedResponse.fail("未进行放飞申请，不允许下发");
            }
        } catch (Exception e) {
            log.error("[oac]放飞申请结果下发异常，flyIssuedRequest={}", flyIssuedRequest, e);
            flyIssuedResponse.fail(e.getMessage());
        }
        return flyIssuedResponse;
    }

    FlyReplyRequest buildCacFlyReplyRequest(FlyIssuedRequest flyIssuedRequest, String applyFlyId) {
        FlyReplyRequest cacFlyReplyRequest = new FlyReplyRequest();
        cacFlyReplyRequest.setCpn(flyIssuedRequest.getCpn());
        cacFlyReplyRequest.setApplyFlyId(applyFlyId);
        cacFlyReplyRequest.setReplyFlyId(flyIssuedRequest.getFlyId());
        cacFlyReplyRequest.setPass(flyIssuedRequest.getPass());
        return cacFlyReplyRequest;
    }

    FlyIssuedResponse buildFlyIssuedResponse(FlyReplyResponse cacFlyReplyResponse) {
        FlyIssuedResponse flyIssuedResponse = new FlyIssuedResponse();
        flyIssuedResponse.setSuccess(cacFlyReplyResponse.getSuccess());
        flyIssuedResponse.setCode(cacFlyReplyResponse.getCode());
        flyIssuedResponse.setMessage(cacFlyReplyResponse.getMessage());
        return flyIssuedResponse;
    }

    Boolean updateDynamicInfoPlanStatusByReplyFlyId(Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = oacDynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0) {
            return true;
        } else {
            return false;
        }
    }
}
