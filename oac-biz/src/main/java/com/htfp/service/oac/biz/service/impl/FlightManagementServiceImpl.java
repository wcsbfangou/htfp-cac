package com.htfp.service.oac.biz.service.impl;

import com.htfp.service.cac.client.request.FlightPlanReplyRequest;
import com.htfp.service.cac.client.request.FlyReplyRequest;
import com.htfp.service.cac.client.response.FlightPlanReplyResponse;
import com.htfp.service.cac.client.response.FlyReplyResponse;
import com.htfp.service.cac.client.service.IOacService;
import com.htfp.service.oac.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.client.enums.ApplicantTypeEnum;
import com.htfp.service.oac.client.enums.ApplyStatusEnum;
import com.htfp.service.oac.client.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.client.request.FinishFlightPlanRequest;
import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.request.FlyApplyRequest;
import com.htfp.service.oac.client.request.FlyQueryRequest;
import com.htfp.service.oac.client.request.UavVerifyApplyRequest;
import com.htfp.service.oac.client.response.FinishFlightPlanResponse;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;
import com.htfp.service.oac.client.response.FlyApplyResponse;
import com.htfp.service.oac.client.response.FlyQueryResponse;
import com.htfp.service.oac.client.response.UavVerifyApplyResponse;
import com.htfp.service.oac.client.response.param.FlightPlanQueryResultParam;
import com.htfp.service.oac.client.response.param.FlyQueryResultParam;
import com.htfp.service.oac.client.response.param.UavVerifyResultParam;
import com.htfp.service.oac.common.utils.DateUtils;
import com.htfp.service.oac.common.utils.GpsDistanceUtils;
import com.htfp.service.oac.common.utils.JsonUtils;
import com.htfp.service.oac.common.utils.SnowflakeIdUtils;
import com.htfp.service.oac.dao.model.AirportInfoDO;
import com.htfp.service.oac.dao.model.ApplyFlightPlanLogDO;
import com.htfp.service.oac.dao.model.ApplyFlyLogDO;
import com.htfp.service.oac.dao.model.ApplyUavVerifyLogDO;
import com.htfp.service.oac.dao.model.DynamicRouteInfoDO;
import com.htfp.service.oac.dao.model.DynamicUavInfoDO;
import com.htfp.service.oac.dao.model.OperatorInfoDO;
import com.htfp.service.oac.dao.model.UavInfoDO;
import com.htfp.service.oac.dao.service.AirportInfoDalService;
import com.htfp.service.oac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.oac.dao.service.ApplyFlyLogDalService;
import com.htfp.service.oac.dao.service.ApplyUavVerifyLogDalService;
import com.htfp.service.oac.dao.service.DynamicRouteInfoDalService;
import com.htfp.service.oac.dao.service.DynamicUavInfoDalService;
import com.htfp.service.oac.dao.service.OperatorDalService;
import com.htfp.service.oac.dao.service.UavDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Slf4j
@Service("flightManagementServiceImpl")
public class FlightManagementServiceImpl implements IFlightManagementService {

    @Resource
    UavDalService uavDalService;

    @Resource
    OperatorDalService operatorDalService;

    @Resource
    AirportInfoDalService airportInfoDalService;

    @Resource
    ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    @Resource
    ApplyFlyLogDalService applyFlyLogDalService;

    @Resource
    ApplyUavVerifyLogDalService applyUavVerifyLogDalService;

    @Resource
    DynamicUavInfoDalService dynamicUavInfoDalService;

    @Resource
    DynamicRouteInfoDalService dynamicRouteInfoDalService;

    @Resource
    IOacService oacService;

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
        try {
            log.info("[oac]飞行计划申请start，flightPlanApplyRequest={}", flightPlanApplyRequest);
            // TODO: 2022/12/22 IDC ID && 机器ID
            // 雪花算法生成replyFlightPlanId
            Long replyFlightPlanId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
            String applicantSubject;
            if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType()))) {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantOrganizationParam());
            } else {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantPersonParam());
            }
            ApplyFlightPlanLogDO applyFlightPlanLog = applyFlightPlanLogDalService.buildApplyFlightPlanLogDO(flightPlanApplyRequest.getApplyFlightPlanId(), replyFlightPlanId, flightPlanApplyRequest.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
                    JsonUtils.object2Json(flightPlanApplyRequest.getPilots()), JsonUtils.object2Json(flightPlanApplyRequest.getAirspaceNumbers()), JsonUtils.object2Json(flightPlanApplyRequest.getRoutePointCoordinates()), flightPlanApplyRequest.getTakeOffAirportId(),
                    flightPlanApplyRequest.getLandingAirportId(), flightPlanApplyRequest.getTakeoffSite(), flightPlanApplyRequest.getLandingSite(), flightPlanApplyRequest.getMissionType(), flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(),
                    flightPlanApplyRequest.getEmergencyProcedure(), flightPlanApplyRequest.getOperationScenarioType(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getIsVlos(), ApplyStatusEnum.PENDING.getCode());
            int id = applyFlightPlanLogDalService.insertApplyFlightPlanLog(applyFlightPlanLog);
            if (id > 0) {
                flightPlanApplyResponse.success();
                flightPlanApplyResponse.setReplyFlightPlanId(replyFlightPlanId.toString());
            } else {
                flightPlanApplyResponse.fail("飞行计划申请失败，插入数据失败");
            }
            log.info("[oac]飞行计划申请end，flightPlanApplyRequest={},flightPlanApplyResponse={}", flightPlanApplyRequest, JsonUtils.object2Json(flightPlanApplyResponse));
        } catch (Exception e) {
            log.error("[oac]飞行计划申请异常，flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            flightPlanApplyResponse.fail(e.getMessage());
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
        try {
            log.info("[oac]飞行计划查询start，flightPlanQueryRequest={}", flightPlanQueryRequest);
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flightPlanQueryRequest.getReplyFlightPlanId()));
            flightPlanQueryResponse.success();
            FlightPlanQueryResultParam flightPlanQueryResultParam = new FlightPlanQueryResultParam();
            flightPlanQueryResultParam.setApplyFlightPlanId(queryApplyFlightPlanLog.getApplyFlightPlanId());
            flightPlanQueryResultParam.setReplyFlightPlanId(flightPlanQueryRequest.getReplyFlightPlanId());
            flightPlanQueryResultParam.setStatus(queryApplyFlightPlanLog.getStatus());
            flightPlanQueryResponse.setFlightPlanQueryResultParam(flightPlanQueryResultParam);
            log.info("[oac]飞行计划查询end，flightPlanQueryRequest={},flightPlanQueryResponse={}", flightPlanQueryRequest, JsonUtils.object2Json(flightPlanQueryResponse));
        } catch (Exception e) {
            log.error("[oac]飞行计划查询异常，flightPlanQueryRequest={}", flightPlanQueryRequest, e);
            flightPlanQueryResponse.fail(e.getMessage());
        }
        return flightPlanQueryResponse;
    }

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
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flightPlanIssuedRequest.getFlightPlanId()));
            if (queryApplyFlightPlanLog != null) {
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                    if (flightPlanIssuedRequest.getPass()) {
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.APPROVED.getCode());
                        // 飞行计划通过,存储动态信息
                        if (!initializeDynamicInfo(queryApplyFlightPlanLog, flightPlanIssuedRequest.getCpn(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode())) {
                            //ROLLBACK
                            applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, queryApplyFlightPlanLog.getStatus());
                            flightPlanIssuedResponse.fail();
                        }
                    } else {
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.UNAPPROVED.getCode());
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
        UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(cpn);
        if (queryUavInfo != null) {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfoByOperatorUniId(queryUavInfo.getOperatorUniId());
            AirportInfoDO queryAirportInfo = airportInfoDalService.queryAirportInfoByUavReg(queryApplyFlightPlanLog.getLandingAirportId());
            if (queryOperatorInfo != null && queryAirportInfo != null) {
                String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
                DynamicUavInfoDO dynamicUavInfo = dynamicUavInfoDalService.buildDynamicUavInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn, queryOperatorInfo.getOperatorName(),
                        null, null, null, null, null, null, null, null, currentTime, queryApplyFlightPlanLog.getStartTime(), queryApplyFlightPlanLog.getEndTime(),
                        null, queryApplyFlightPlanLog.getTakeoffAirportId(), queryApplyFlightPlanLog.getLandingAirportId(), queryApplyFlightPlanLog.getTakeoffSite(), queryApplyFlightPlanLog.getLandingSite(),
                        queryAirportInfo.getIdentificationAreaRadius(), queryAirportInfo.getAlarmAreaRadius(), queryAirportInfo.getLng(), queryAirportInfo.getLat(), queryAirportInfo.getAlt(),
                        null, null, null, null, flightPlanStatus, null);
                DynamicRouteInfoDO dynamicRouteInfoDO = dynamicRouteInfoDalService.buildDynamicUavInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn,
                        queryApplyFlightPlanLog.getRoutePointCoordinates(), null, null, null, null, null, null,
                        queryApplyFlightPlanLog.getTakeoffSite(), queryAirportInfo.getLandingSites(), flightPlanStatus);
                // TODO: 2022/12/23 事务
                int insertDynamicUavInfoResult = dynamicUavInfoDalService.insertDynamicUavInfo(dynamicUavInfo);
                int insertDynamicRouteInfoResult = dynamicRouteInfoDalService.insertDynamicRouteInfo(dynamicRouteInfoDO);
                if (insertDynamicUavInfoResult > 0 && insertDynamicRouteInfoResult > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

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
        try {
            log.info("[oac]无人机系统接入校验start，uavVerifyApplyRequest={}", uavVerifyApplyRequest);
            // TODO: 2022/12/22 IDC ID && 机器ID
            // 雪花算法生成replyUavVerifyId
            Long replyUavVerifyId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
            // 校验无人机
            Long replyFlightPlanId = uavVerify(uavVerifyApplyRequest);
            ApplyStatusEnum uavVerifyApplyStatusEnum;
            UavVerifyResultParam uavVerifyResultParam = new UavVerifyResultParam();
            uavVerifyResultParam.setApplyUavVerifyId(uavVerifyApplyRequest.getApplyUavVerifyId());
            if (replyFlightPlanId != null) {
                uavVerifyResultParam.setReplyUavVerifyId(replyUavVerifyId.toString());
                uavVerifyResultParam.setUavVerifyPass(true);
                uavVerifyApplyStatusEnum = ApplyStatusEnum.APPROVED;
                // 更新无人机动态信息表
                updateDynamicUavInfo(uavVerifyApplyRequest, replyFlightPlanId);
                // TODO: 2023/2/9 更新无人机接入运管表
            } else {
                uavVerifyResultParam.setUavVerifyPass(false);
                uavVerifyApplyStatusEnum = ApplyStatusEnum.UNAPPROVED;
            }
            ApplyUavVerifyLogDO applyUavVerifyLog = applyUavVerifyLogDalService.buildApplyUavVerifyLog(uavVerifyApplyRequest.getApplyUavVerifyId(), replyUavVerifyId, uavVerifyApplyRequest.getCpn(), uavVerifyApplyRequest.getLng(),
                    uavVerifyApplyRequest.getLat(), uavVerifyApplyRequest.getAlt(), uavVerifyApplyRequest.getGroundSpeed(), uavVerifyApplyRequest.getRelativeHeight(), uavVerifyApplyRequest.getFlightControlSn(),
                    uavVerifyApplyRequest.getFlightControlVersion(), JsonUtils.object2Json(uavVerifyApplyRequest.getUavDynamicParam()), JsonUtils.object2Json(uavVerifyApplyRequest.getUavStaticParam()), uavVerifyApplyStatusEnum.getCode());
            int id = applyUavVerifyLogDalService.insertApplyUavVerifyLog(applyUavVerifyLog);
            if (id > 0) {
                uavVerifyApplyResponse.success();
                uavVerifyApplyResponse.setUavVerifyResultParam(uavVerifyResultParam);
            } else {
                uavVerifyApplyResponse.fail("无人机系统接入校验失败，插入数据失败");
            }

            log.info("[oac]无人机系统接入校验end，uavVerifyApplyRequest={},uavVerifyApplyResponse={}", uavVerifyApplyRequest, JsonUtils.object2Json(uavVerifyApplyResponse));
        } catch (Exception e) {
            log.error("[oac]无人机系统接入校验异常，uavVerifyApplyRequest={}", uavVerifyApplyRequest, e);
            uavVerifyApplyResponse.fail(e.getMessage());
        }
        return uavVerifyApplyResponse;
    }

    // TODO: 2023/2/8 验证无人机信息
    Long uavVerify(UavVerifyApplyRequest uavVerifyApplyRequest) {
        Long replyFlightPlanId = null;
        UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(uavVerifyApplyRequest.getCpn());
        if (queryUavInfo != null) {
            List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogDalService.queryApplyFlightPlanLogByCpn(uavVerifyApplyRequest.getCpn());
            for (ApplyFlightPlanLogDO applyFlightPlanLogDO : applyFlightPlanLogDOList) {
                if (ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(applyFlightPlanLogDO.getStatus()))) {
                    replyFlightPlanId = applyFlightPlanLogDO.getReplyFlightPlanId();
                    break;
                }
            }
        }
        return replyFlightPlanId;
    }

    Boolean updateDynamicUavInfo(UavVerifyApplyRequest uavVerifyApplyRequest, Long replyFlightPlanId) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = dynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(replyFlightPlanId);
        dynamicUavInfoDO.setLng(uavVerifyApplyRequest.getLng());
        dynamicUavInfoDO.setLat(uavVerifyApplyRequest.getLat());
        dynamicUavInfoDO.setAlt(uavVerifyApplyRequest.getAlt());
        dynamicUavInfoDO.setSpeed(uavVerifyApplyRequest.getGroundSpeed());
        dynamicUavInfoDO.setCourse(uavVerifyApplyRequest.getUavDynamicParam().getTrueCourse());
        dynamicUavInfoDO.setFuel(uavVerifyApplyRequest.getUavDynamicParam().getFuel());
        dynamicUavInfoDO.setBattery(uavVerifyApplyRequest.getUavDynamicParam().getBattery());
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setDistanceToLandingPoint(GpsDistanceUtils.getDistance(uavVerifyApplyRequest.getLng(), uavVerifyApplyRequest.getLat(), dynamicUavInfoDO.getLandingLng(), dynamicUavInfoDO.getLandingLat()));
        int id = dynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        if (id > 0) {
            return true;
        } else {
            return false;
        }
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
        try {
            log.info("[oac]放飞申请start，flyApplyRequest={}", flyApplyRequest);
            // TODO: 2022/12/22 IDC ID && 机器ID
            // 雪花算法生成replyFlyId
            Long replyFlyId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flyApplyRequest.getReplyFlightPlanId()));
            ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.buildApplyFlyLogDO(flyApplyRequest.getApplyFlyId(), replyFlyId, queryApplyFlightPlanLog.getApplyFlightPlanId(), queryApplyFlightPlanLog.getReplyFlightPlanId(),
                    flyApplyRequest.getCpn(), JsonUtils.object2Json(flyApplyRequest.getAirspaceNumbers()), flyApplyRequest.getOperationScenarioType(), flyApplyRequest.getFlyLng(), flyApplyRequest.getFlyLat(), flyApplyRequest.getFlyAlt(),
                    flyApplyRequest.getVin(), flyApplyRequest.getPvin(), flyApplyRequest.getFlightControlSn(), flyApplyRequest.getImei(), ApplyStatusEnum.PENDING.getCode());
            int id = applyFlyLogDalService.insertApplyFlyLog(applyFlyLogDO);
            if (id > 0) {
                flyApplyResponse.success();
                flyApplyResponse.setReplyFlyId(replyFlyId.toString());
                updateDynamicInfoPlanStatusAndReplyFlyIdByFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId(), replyFlyId, FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED);
            } else {
                flyApplyResponse.fail("放飞申请失败，插入数据失败");
            }
            log.info("[oac]放飞申请end，flyApplyRequest={}, flyApplyResponse={}", flyApplyRequest, JsonUtils.object2Json(flyApplyResponse));
        } catch (Exception e) {
            log.error("[oac]放飞申请异常，flyApplyRequest={}", flyApplyRequest, e);
            flyApplyResponse.fail(e.getMessage());
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
        try {
            log.info("[oac]放飞申请查询start，flightPlanQueryRequest={}", flyQueryRequest);
            ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.queryApplyFlyLogByReplyFlyId(Long.valueOf(flyQueryRequest.getReplyFlyId()));
            flyQueryResponse.success();
            FlyQueryResultParam flyQueryResultParam = new FlyQueryResultParam();
            flyQueryResultParam.setApplyFlyId(applyFlyLogDO.getApplyFlyId());
            flyQueryResultParam.setReplyFlyId(flyQueryRequest.getReplyFlyId());
            flyQueryResultParam.setStatus(applyFlyLogDO.getStatus());
            flyQueryResponse.setFlyQueryResultParam(flyQueryResultParam);
            log.info("[oac]放飞申请查询end，flyQueryRequest={}, flyQueryResponse={}", flyQueryRequest, JsonUtils.object2Json(flyQueryResponse));
        } catch (Exception e) {
            log.error("[oac]放飞申请查询异常，flyQueryRequest={}", flyQueryRequest, e);
            flyQueryResponse.fail(e.getMessage());
        }
        return flyQueryResponse;
    }

    /**
     * 放飞申请结果下发
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
            ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
            if (queryApplyFlyLog != null) {
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus()))) {
                    FlightPlanStatusTypeEnum flightPlanStatusTypeEnum;
                    if (flyIssuedRequest.getPass()) {
                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.APPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED;
                    } else {
                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.UNAPPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_UNAPPROVED;
                    }
                    if (!updateDynamicInfoPlanStatusByReplyFlyId(replyFlyId, flightPlanStatusTypeEnum)) {
                        //ROLLBACK
                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, queryApplyFlyLog.getStatus());
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
        DynamicUavInfoDO dynamicUavInfoDO = dynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = dynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = dynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = dynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0) {
            return true;
        } else {
            return false;
        }
    }

    Boolean updateDynamicInfoPlanStatusAndReplyFlyIdByFlightPlanId(Long replyFlightId, Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = dynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(replyFlightId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = dynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(replyFlightId);
        dynamicRouteInfoDO.setReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = dynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = dynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0) {
            return true;
        } else {
            return false;
        }
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
        try {
            log.info("[oac]飞行计划结束start，finishFlightPlanRequest={}", finishFlightPlanRequest);
            DynamicUavInfoDO queryDynamicUavInfo = dynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            DynamicRouteInfoDO queryDynamicRouteInfoDO = dynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            if (queryDynamicRouteInfoDO!=null && queryDynamicUavInfo != null && queryApplyFlightPlanLog != null && queryApplyFlyLog != null) {
                ApplyStatusEnum flightPlanStatus = ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus());
                ApplyStatusEnum flyStatus = ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus());
                if (ApplyStatusEnum.APPROVED.equals(flightPlanStatus) && !ApplyStatusEnum.PENDING.equals(flyStatus)) {
                    // 更新动态信息状态
                    dynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                    dynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                    // 更新飞行计划状态
                    applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.COMPLETE.getCode());
                    // 飞行计划处于通过状态，则结束飞行计划
                    if (ApplyStatusEnum.APPROVED.equals(flyStatus)) {
                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.COMPLETE.getCode());
                    }
                } else {
                    finishFlightPlanResponse.fail("飞行计划未处于通过状态或飞行计划，不需要结束");
                }
            } else {
                finishFlightPlanResponse.fail("未查询到相应数据");
            }

            log.info("[oac]飞行计划结束end，finishFlightPlanRequest={}, finishFlightPlanResponse={}", finishFlightPlanRequest, JsonUtils.object2Json(finishFlightPlanResponse));
        } catch (Exception e) {
            log.error("[oac]飞行计划结束异常，finishFlightPlanRequest={}", finishFlightPlanRequest, e);
            finishFlightPlanResponse.fail(e.getMessage());
        }
        return finishFlightPlanResponse;
    }
}
