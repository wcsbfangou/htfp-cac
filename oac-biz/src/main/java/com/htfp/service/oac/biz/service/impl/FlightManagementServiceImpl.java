package com.htfp.service.oac.biz.service.impl;

import com.htfp.service.oac.biz.model.inner.request.UavDataTransferRequest;
import com.htfp.service.oac.biz.model.inner.response.UavDataTransferResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.common.enums.ApplicantTypeEnum;
import com.htfp.service.oac.common.enums.ApplyStatusEnum;
import com.htfp.service.oac.common.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest;
import com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest;
import com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest;
import com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest;
import com.htfp.service.oac.biz.model.inner.response.FinishFlightPlanResponse;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlightPlanQueryResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.FlyQueryResponse;
import com.htfp.service.oac.biz.model.inner.response.UavVerifyApplyResponse;
import com.htfp.service.oac.biz.model.inner.response.param.FlightPlanQueryResultParam;
import com.htfp.service.oac.biz.model.inner.response.param.FlyQueryResultParam;
import com.htfp.service.oac.biz.model.inner.response.param.UavVerifyResultParam;
import com.htfp.service.oac.common.utils.DateUtils;
import com.htfp.service.oac.common.utils.GpsDistanceUtils;
import com.htfp.service.oac.common.utils.JsonUtils;
import com.htfp.service.oac.common.utils.SnowflakeIdUtils;
import com.htfp.service.cac.dao.model.oac.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.oac.ApplyFlyLogDO;
import com.htfp.service.cac.dao.model.oac.ApplyUavVerifyLogDO;
import com.htfp.service.cac.dao.model.oac.DynamicRouteInfoDO;
import com.htfp.service.cac.dao.model.oac.DynamicUavInfoDO;
import com.htfp.service.cac.dao.model.oac.UavInfoDO;
import com.htfp.service.cac.dao.service.oac.OacApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.oac.OacApplyFlyLogDalService;
import com.htfp.service.cac.dao.service.oac.OacApplyUavVerifyLogDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicRouteInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicUavInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacUavDalService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
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
    private OacUavDalService oacUavDalService;

    @Resource
    private OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;

    @Resource
    private OacApplyFlyLogDalService oacApplyFlyLogDalService;

    @Resource
    private OacApplyUavVerifyLogDalService oacApplyUavVerifyLogDalService;

    @Resource
    private OacDynamicUavInfoDalService oacDynamicUavInfoDalService;

    @Resource
    private OacDynamicRouteInfoDalService oacDynamicRouteInfoDalService;

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
            ApplyFlightPlanLogDO applyFlightPlanLog = oacApplyFlightPlanLogDalService.buildApplyFlightPlanLogDO(flightPlanApplyRequest.getApplyFlightPlanId(), replyFlightPlanId, flightPlanApplyRequest.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
                    JsonUtils.object2Json(flightPlanApplyRequest.getPilots()), JsonUtils.object2Json(flightPlanApplyRequest.getAirspaceNumbers()), JsonUtils.object2Json(flightPlanApplyRequest.getRoutePointCoordinates()), flightPlanApplyRequest.getTakeoffAirportId(),
                    flightPlanApplyRequest.getLandingAirportId(), flightPlanApplyRequest.getTakeoffSite(), flightPlanApplyRequest.getLandingSite(), flightPlanApplyRequest.getMissionType(), flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(),
                    flightPlanApplyRequest.getEmergencyProcedure(), flightPlanApplyRequest.getOperationScenarioType(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getIsVlos(), ApplyStatusEnum.PENDING.getCode());
            int id = oacApplyFlightPlanLogDalService.insertApplyFlightPlanLog(applyFlightPlanLog);
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
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flightPlanQueryRequest.getReplyFlightPlanId()));
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
            List<Long> replyFlightPlanIdList = uavVerify(uavVerifyApplyRequest);
            ApplyStatusEnum uavVerifyApplyStatusEnum;
            UavVerifyResultParam uavVerifyResultParam = new UavVerifyResultParam();
            uavVerifyResultParam.setApplyUavVerifyId(uavVerifyApplyRequest.getApplyUavVerifyId());
            if (CollectionUtils.isNotEmpty(replyFlightPlanIdList)) {
                uavVerifyResultParam.setReplyUavVerifyId(replyUavVerifyId.toString());
                uavVerifyResultParam.setUavVerifyPass(true);
                uavVerifyApplyStatusEnum = ApplyStatusEnum.APPROVED;
                // 更新无人机动态信息表
                // updateDynamicUavInfo(uavVerifyApplyRequest, replyFlightPlanId);
                // TODO: 2023/2/9 更新无人机接入运管管控表
            } else {
                uavVerifyResultParam.setUavVerifyPass(false);
                uavVerifyApplyStatusEnum = ApplyStatusEnum.UNAPPROVED;
            }
            ApplyUavVerifyLogDO applyUavVerifyLog = oacApplyUavVerifyLogDalService.buildApplyUavVerifyLog(uavVerifyApplyRequest.getApplyUavVerifyId(), replyUavVerifyId, uavVerifyApplyRequest.getCpn(), uavVerifyApplyRequest.getLng(),
                    uavVerifyApplyRequest.getLat(), uavVerifyApplyRequest.getAlt(), uavVerifyApplyRequest.getGroundSpeed(), uavVerifyApplyRequest.getRelativeHeight(), uavVerifyApplyRequest.getFlightControlSn(),
                    uavVerifyApplyRequest.getFlightControlVersion(), JsonUtils.object2Json(uavVerifyApplyRequest.getUavDynamicParam()), JsonUtils.object2Json(uavVerifyApplyRequest.getUavStaticParam()), uavVerifyApplyStatusEnum.getCode());
            int id = oacApplyUavVerifyLogDalService.insertApplyUavVerifyLog(applyUavVerifyLog);
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
    List<Long> uavVerify(UavVerifyApplyRequest uavVerifyApplyRequest) {
        List<Long> replyFlightPlanIdList = new ArrayList<>();
        UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(uavVerifyApplyRequest.getCpn());
        if (queryUavInfo != null) {
            List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByCpn(uavVerifyApplyRequest.getCpn());
            for (ApplyFlightPlanLogDO applyFlightPlanLogDO : applyFlightPlanLogDOList) {
                if (ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(applyFlightPlanLogDO.getStatus()))) {
                    replyFlightPlanIdList.add(applyFlightPlanLogDO.getReplyFlightPlanId());
                }
            }
        }
        return replyFlightPlanIdList;
    }

    Boolean updateDynamicUavInfo(UavVerifyApplyRequest uavVerifyApplyRequest, Long replyFlightPlanId) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(replyFlightPlanId);
        dynamicUavInfoDO.setLng(uavVerifyApplyRequest.getLng());
        dynamicUavInfoDO.setLat(uavVerifyApplyRequest.getLat());
        dynamicUavInfoDO.setAlt(uavVerifyApplyRequest.getAlt());
        dynamicUavInfoDO.setSpeed(uavVerifyApplyRequest.getGroundSpeed());
        dynamicUavInfoDO.setCourse(uavVerifyApplyRequest.getUavDynamicParam().getTrueCourse());
        dynamicUavInfoDO.setFuel(uavVerifyApplyRequest.getUavDynamicParam().getFuel());
        dynamicUavInfoDO.setBattery(uavVerifyApplyRequest.getUavDynamicParam().getBattery());
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setDistanceToLandingPoint(GpsDistanceUtils.getDistance(uavVerifyApplyRequest.getLng(), uavVerifyApplyRequest.getLat(), dynamicUavInfoDO.getLandingLng(), dynamicUavInfoDO.getLandingLat()));
        int id = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        if (id > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 无人机遥测数据透传
     *
     * @param uavDataTransferRequest
     * @return
     */
    @Override
    public UavDataTransferResponse uavDataTransfer(UavDataTransferRequest uavDataTransferRequest) {
        UavDataTransferResponse uavDataTransferResponse = new UavDataTransferResponse();
        uavDataTransferResponse.fail();
        try {
            log.info("[oac]无人机遥测数据透传start，uavDataTransferRequest={}", uavDataTransferRequest);
            if (!uavDataTransferRequest.getCpn().equals(uavDataTransferRequest.getReportCode())) {
                DynamicUavInfoDO queryDynamicUavInfo = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(Long.valueOf(uavDataTransferRequest.getReportCode()));
                if (queryDynamicUavInfo != null && queryDynamicUavInfo.getCpn().equals(uavDataTransferRequest.getCpn())) {
                    Boolean updateDynamicUavInfoResult = updateDynamicUavInfo(uavDataTransferRequest, queryDynamicUavInfo);
                    if (updateDynamicUavInfoResult) {
                        uavDataTransferResponse.setCpn(uavDataTransferRequest.getCpn());
                        uavDataTransferResponse.setReportCode(uavDataTransferRequest.getReportCode());
                        uavDataTransferResponse.success();
                    }
                } else {
                    uavDataTransferResponse.fail("无此飞行计划信息或cpn不一致");
                }
            } else {
                uavDataTransferResponse.setCpn(uavDataTransferRequest.getCpn());
                uavDataTransferResponse.setReportCode(uavDataTransferRequest.getReportCode());
                uavDataTransferResponse.success();
            }
            log.info("[oac]无人机遥测数据透传end，uavDataTransferRequest={}, uavDataTransferResponse={}", uavDataTransferRequest, JsonUtils.object2Json(uavDataTransferResponse));
        } catch (Exception e) {
            log.error("[oac]无人机遥测数据透传异常，uavDataTransferRequest={}", uavDataTransferRequest, e);
            uavDataTransferResponse.fail(e.getMessage());
        }
        return uavDataTransferResponse;
    }

    Boolean updateDynamicUavInfo(UavDataTransferRequest uavDataTransferRequest, DynamicUavInfoDO dynamicUavInfo) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        boolean updateDynamicUavInfoResult = false;
        if (uavDataTransferRequest.getLng() != null && uavDataTransferRequest.getLat() != null) {
            dynamicUavInfo.setLng(uavDataTransferRequest.getLng());
            dynamicUavInfo.setLat(uavDataTransferRequest.getLat());
            Integer distanceToLandingPoint = GpsDistanceUtils.getDistance(uavDataTransferRequest.getLng(), uavDataTransferRequest.getLat(), dynamicUavInfo.getLandingLng(), dynamicUavInfo.getLandingLat());
            dynamicUavInfo.setDistanceToLandingPoint(distanceToLandingPoint);
            if(distanceToLandingPoint <= dynamicUavInfo.getLandingAirportIdentificationRadius()){
                dynamicUavInfo.setPlanStatus(FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.getCode());
            } else if(distanceToLandingPoint <= dynamicUavInfo.getLandingAirportAlarmRadius()){
                if(FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.equals(FlightPlanStatusTypeEnum.getFromCode(dynamicUavInfo.getPlanStatus()))){
                    dynamicUavInfo.setPlanStatus(FlightPlanStatusTypeEnum.PREPARE_LANDING.getCode());
                } else {
                    // TODO: 2023/2/20 告警
                    String alarmId = "null";
                    List<String> alarmIdList = JsonUtils.json2List(dynamicUavInfo.getAlarmIds(), String.class);
                    alarmIdList.add(alarmId);
                    dynamicUavInfo.setAlarmIds(JsonUtils.object2Json(alarmIdList));
                }
            }
        }
        if (uavDataTransferRequest.getAlt() != null) {
            dynamicUavInfo.setAlt(uavDataTransferRequest.getAlt());
        }
        if (uavDataTransferRequest.getGroundSpeed() != null) {
            dynamicUavInfo.setSpeed(uavDataTransferRequest.getGroundSpeed());
        }
        if (uavDataTransferRequest.getTrueCourse() != null) {
            dynamicUavInfo.setCourse(uavDataTransferRequest.getTrueCourse());
        }
        if (uavDataTransferRequest.getFuel() != null) {
            dynamicUavInfo.setFuel(uavDataTransferRequest.getFuel());
        }
        if (uavDataTransferRequest.getBattery() != null) {
            dynamicUavInfo.setBattery(uavDataTransferRequest.getBattery());
        }
        if (uavDataTransferRequest.getUavStatus() != null) {
            dynamicUavInfo.setUavStatus(uavDataTransferRequest.getUavStatus());
        }
        dynamicUavInfo.setUpdateTime(currentTime);
        int id = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfo);
        if (id > 0) {
            updateDynamicUavInfoResult = true;
        }
        return updateDynamicUavInfoResult;
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
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flyApplyRequest.getReplyFlightPlanId()));
            ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.buildApplyFlyLogDO(flyApplyRequest.getApplyFlyId(), replyFlyId, queryApplyFlightPlanLog.getApplyFlightPlanId(), queryApplyFlightPlanLog.getReplyFlightPlanId(),
                    flyApplyRequest.getCpn(), JsonUtils.object2Json(flyApplyRequest.getAirspaceNumbers()), flyApplyRequest.getOperationScenarioType(), flyApplyRequest.getFlyLng(), flyApplyRequest.getFlyLat(), flyApplyRequest.getFlyAlt(),
                    flyApplyRequest.getVin(), flyApplyRequest.getPvin(), flyApplyRequest.getFlightControlSn(), flyApplyRequest.getImei(), ApplyStatusEnum.PENDING.getCode());
            int id = oacApplyFlyLogDalService.insertApplyFlyLog(applyFlyLogDO);
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
            ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(Long.valueOf(flyQueryRequest.getReplyFlyId()));
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

    Boolean updateDynamicInfoPlanStatusAndReplyFlyIdByFlightPlanId(Long replyFlightId, Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(replyFlightId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(replyFlightId);
        dynamicRouteInfoDO.setReplyFlyId(replyFlyId);
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
            DynamicUavInfoDO queryDynamicUavInfo = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            DynamicRouteInfoDO queryDynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            if (queryDynamicRouteInfoDO != null && queryDynamicUavInfo != null && queryApplyFlightPlanLog != null && queryApplyFlyLog != null) {
                ApplyStatusEnum flightPlanStatus = ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus());
                ApplyStatusEnum flyStatus = ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus());
                if (ApplyStatusEnum.APPROVED.equals(flightPlanStatus) && !ApplyStatusEnum.PENDING.equals(flyStatus)) {
                    // 更新动态信息状态
                    oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                    oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                    // 更新飞行计划状态
                    oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.COMPLETE.getCode());
                    // 飞行计划处于通过状态，则结束飞行计划
                    if (ApplyStatusEnum.APPROVED.equals(flyStatus)) {
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.COMPLETE.getCode());
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
