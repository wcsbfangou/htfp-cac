package com.htfp.service.oac.biz.service.impl;

import com.htfp.service.cac.dao.model.oac.AirportInfoDO;
import com.htfp.service.cac.dao.model.oac.AlarmIssuedLogDO;
import com.htfp.service.cac.dao.model.oac.DynamicFlightPlanInfoDO;
import com.htfp.service.cac.dao.model.oac.OperatorInfoDO;
import com.htfp.service.cac.dao.service.oac.OacAirportInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacAlarmIssuedLogDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicFlightPlanInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacOperatorDalService;
import com.htfp.service.oac.biz.model.inner.request.UavDataTransferRequest;
import com.htfp.service.oac.biz.model.inner.request.param.PersonParam;
import com.htfp.service.oac.biz.model.inner.response.UavDataTransferResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.common.enums.AlarmLevelTypeEnum;
import com.htfp.service.oac.common.enums.ApplicantTypeEnum;
import com.htfp.service.oac.common.enums.ApplyStatusEnum;
import com.htfp.service.oac.common.enums.DeliverTypeEnum;
import com.htfp.service.oac.common.enums.ErrorCodeEnum;
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
import com.htfp.service.oac.common.enums.UavFlowStatusEnums;
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
import org.apache.commons.lang3.StringUtils;
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
    private OacOperatorDalService oacOperatorDalService;

    @Resource
    private OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;

    @Resource
    private OacApplyFlyLogDalService oacApplyFlyLogDalService;

    @Resource
    private OacApplyUavVerifyLogDalService oacApplyUavVerifyLogDalService;

    @Resource
    private OacDynamicFlightPlanInfoDalService oacDynamicFlightPlanInfoDalService;

    @Resource
    private OacDynamicUavInfoDalService oacDynamicUavInfoDalService;

    @Resource
    private OacDynamicRouteInfoDalService oacDynamicRouteInfoDalService;

    @Resource
    private OacAlarmIssuedLogDalService oacAlarmIssuedLogDalService;

    @Resource
    private OacAirportInfoDalService oacAirportInfoDalService;

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
            AirportInfoDO takeoffAirportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(flightPlanApplyRequest.getTakeoffAirportId());
            AirportInfoDO landingAirportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(flightPlanApplyRequest.getLandingAirportId());
            if (takeoffAirportInfo != null && landingAirportInfo != null) {
                // TODO: 2022/12/22 IDC ID && 机器ID
                // 雪花算法生成replyFlightPlanId
                Long replyFlightPlanId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
                if (initializeFLightPlanInfo(flightPlanApplyRequest, replyFlightPlanId)) {
                    flightPlanApplyResponse.success();
                    flightPlanApplyResponse.setReplyFlightPlanId(replyFlightPlanId.toString());
                } else {
                    flightPlanApplyResponse.fail("飞行计划申请失败，插入数据失败");
                }
            } else {
                flightPlanApplyResponse.fail(ErrorCodeEnum.WRONG_AIRPORT_ID);
            }
            log.info("[oac]飞行计划申请end，flightPlanApplyRequest={},flightPlanApplyResponse={}", flightPlanApplyRequest, JsonUtils.object2Json(flightPlanApplyResponse));
        } catch (Exception e) {
            log.error("[oac]飞行计划申请异常，flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            flightPlanApplyResponse.fail(e.getMessage());
        }
        return flightPlanApplyResponse;
    }

    private boolean initializeFLightPlanInfo(FlightPlanApplyRequest flightPlanApplyRequest, Long replyFlightPlanId) {
        boolean result = false;
        UavInfoDO queryUavInfoDO = oacUavDalService.queryUavInfoByCpn(flightPlanApplyRequest.getCpn());
        if (queryUavInfoDO != null && StringUtils.isNotBlank(queryUavInfoDO.getUavName())) {
            OperatorInfoDO queryOperatorInfoDO = oacOperatorDalService.queryOperatorInfoByOperatorUniId(queryUavInfoDO.getOperatorUniId());
            if (queryOperatorInfoDO != null && StringUtils.isNotBlank(queryOperatorInfoDO.getOperatorName())) {
                String pilotName = fetchPilotName(flightPlanApplyRequest.getPilots());
                String applicantSubject = null;
                if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType())) && flightPlanApplyRequest.getApplicantOrganizationParam() != null) {
                    applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantOrganizationParam());
                } else if (ApplicantTypeEnum.PERSON.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType())) && flightPlanApplyRequest.getApplicantPersonParam() != null) {
                    applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantPersonParam());
                }
                ApplyFlightPlanLogDO applyFlightPlanLog = oacApplyFlightPlanLogDalService.buildApplyFlightPlanLogDO(flightPlanApplyRequest.getApplyFlightPlanId(), replyFlightPlanId, flightPlanApplyRequest.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
                        JsonUtils.object2Json(flightPlanApplyRequest.getPilots()), JsonUtils.object2Json(flightPlanApplyRequest.getAirspaceNumbers()), JsonUtils.object2Json(flightPlanApplyRequest.getRoutePointCoordinates()), flightPlanApplyRequest.getTakeoffAirportId(),
                        flightPlanApplyRequest.getLandingAirportId(), flightPlanApplyRequest.getTakeoffSite(), flightPlanApplyRequest.getLandingSite(), flightPlanApplyRequest.getMissionType(), flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(),
                        flightPlanApplyRequest.getEmergencyProcedure(), flightPlanApplyRequest.getOperationScenarioType(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getIsVlos(), ApplyStatusEnum.PENDING.getCode());
                DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.buildDynamicFlightPlanInfoDO(replyFlightPlanId, null, queryUavInfoDO.getUavName(), flightPlanApplyRequest.getCpn(),  pilotName, queryOperatorInfoDO.getOperatorName(),
                        flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(), flightPlanApplyRequest.getTakeoffAirportId(), flightPlanApplyRequest.getLandingAirportId(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getMissionType(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_SUBMITTED.getCode(), UavFlowStatusEnums.NORMAL.getCode());
                // TODO: 2023/6/9 事务
                int insertApplyFlightPlanLogResult = oacApplyFlightPlanLogDalService.insertApplyFlightPlanLog(applyFlightPlanLog);
                int insertDynamicFlightPlanInfoResult = oacDynamicFlightPlanInfoDalService.insertDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
                if (insertApplyFlightPlanLogResult > 0 && insertDynamicFlightPlanInfoResult > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

    private String fetchPilotName(List<PersonParam> pilotList) {
        String pilotNames = "N/A";
        StringBuilder sb = new StringBuilder();
        if (CollectionUtils.isNotEmpty(pilotList)) {
            for (PersonParam pilot : pilotList) {
                sb.append(pilot.getPersonName());
                sb.append(",");
            }
            if (sb.length() > 0) {
                sb.setLength(sb.length() - 1);
                pilotNames = sb.toString();
            }
        }
        return pilotNames;
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
            if (queryApplyFlightPlanLog != null) {
                flightPlanQueryResponse.success();
                FlightPlanQueryResultParam flightPlanQueryResultParam = new FlightPlanQueryResultParam();
                flightPlanQueryResultParam.setApplyFlightPlanId(queryApplyFlightPlanLog.getApplyFlightPlanId());
                flightPlanQueryResultParam.setReplyFlightPlanId(flightPlanQueryRequest.getReplyFlightPlanId());
                flightPlanQueryResultParam.setStatus(queryApplyFlightPlanLog.getStatus());
                flightPlanQueryResponse.setFlightPlanQueryResultParam(flightPlanQueryResultParam);
            } else {
                flightPlanQueryResponse.fail(ErrorCodeEnum.WRONG_REPLY_FLIGHT_PLAN_ID);
            }

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
            // log.info("[oac]无人机遥测数据透传start，uavDataTransferRequest={}", uavDataTransferRequest);
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
            // log.info("[oac]无人机遥测数据透传end，uavDataTransferRequest={}, uavDataTransferResponse={}", uavDataTransferRequest, JsonUtils.object2Json(uavDataTransferResponse));
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
            if (distanceToLandingPoint <= dynamicUavInfo.getLandingAirportIdentificationRadius() && distanceToLandingPoint > dynamicUavInfo.getLandingAirportAlarmRadius() && FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.equals(FlightPlanStatusTypeEnum.getFromCode(dynamicUavInfo.getPlanStatus()))) {
                if (updateDynamicInfoPlanStatusByReplyFlightPlanId(dynamicUavInfo.getReplyFlightPlanId(), FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA)) {
                    dynamicUavInfo.setPlanStatus(FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.getCode());
                }
            } else if (distanceToLandingPoint <= dynamicUavInfo.getLandingAirportAlarmRadius()) {
                if (FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.equals(FlightPlanStatusTypeEnum.getFromCode(dynamicUavInfo.getPlanStatus()))) {
                    if (updateDynamicInfoPlanStatusByReplyFlightPlanId(dynamicUavInfo.getReplyFlightPlanId(), FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA)) {
                        dynamicUavInfo.setPlanStatus(FlightPlanStatusTypeEnum.PREPARE_LANDING.getCode());
                    }
                } else {
                    // TODO: 2023/2/20 告警需要优化,单独提出来一个模块搞报警
                    List<AlarmIssuedLogDO> alarmIssuedLogDOList = oacAlarmIssuedLogDalService.queryAlarmIssuedLogByReplyFlightPlanId(dynamicUavInfo.getReplyFlightPlanId());
                    if (CollectionUtils.isEmpty(alarmIssuedLogDOList)) {
                        AlarmIssuedLogDO alarmIssuedLog = oacAlarmIssuedLogDalService.buildAlarmIssuedLog(dynamicUavInfo.getReplyFlightPlanId(), dynamicUavInfo.getReplyFlyId(), dynamicUavInfo.getCpn(), AlarmLevelTypeEnum.ALARM.getCode(), "无人机未经降落审批进入降落区域", currentTime, "oacSystem", DeliverTypeEnum.GENERATED.getCode());
                        int id = oacAlarmIssuedLogDalService.insertAlarmIssuedLog(alarmIssuedLog);
                        if (id > 0) {
                            List<String> alarmIdList = new ArrayList<>();
                            if (dynamicUavInfo.getAlarmIds() != null) {
                                alarmIdList = JsonUtils.json2List(dynamicUavInfo.getAlarmIds(), String.class);
                            }
                            alarmIdList.add(alarmIssuedLog.getId().toString());
                            dynamicUavInfo.setInAlarm(true);
                            dynamicUavInfo.setAlarmIds(JsonUtils.object2Json(alarmIdList));
                        }
                    }
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

    private Boolean updateDynamicInfoPlanStatusByReplyFlightPlanId(Long replyFlightPlanId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(replyFlightPlanId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(replyFlightPlanId);
        dynamicFlightPlanInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicRouteInfoId = oacDynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        int updateDynamicFlightPlanInfoId = oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
        if (updateDynamicRouteInfoId > 0 && updateDynamicFlightPlanInfoId > 0) {
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
            List<ApplyFlyLogDO> queryApplyFlyLogList = oacApplyFlyLogDalService.queryApplyFlyLogByCpn(flyApplyRequest.getCpn());
            if (!judgeUavHasApplyFly(queryApplyFlyLogList)) {
                // TODO: 2022/12/22 IDC ID && 机器ID
                // 雪花算法生成replyFlyId
                Long replyFlyId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
                ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flyApplyRequest.getReplyFlightPlanId()));
                if (queryApplyFlightPlanLog.getCpn().equals(flyApplyRequest.getCpn()) && ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                    ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.buildApplyFlyLogDO(flyApplyRequest.getApplyFlyId(), replyFlyId, queryApplyFlightPlanLog.getApplyFlightPlanId(), queryApplyFlightPlanLog.getReplyFlightPlanId(),
                            flyApplyRequest.getCpn(), JsonUtils.object2Json(flyApplyRequest.getAirspaceNumbers()), flyApplyRequest.getOperationScenarioType(), flyApplyRequest.getFlyLng(), flyApplyRequest.getFlyLat(), flyApplyRequest.getFlyAlt(),
                            flyApplyRequest.getVin(), flyApplyRequest.getPvin(), flyApplyRequest.getFlightControlSn(), flyApplyRequest.getImei(), ApplyStatusEnum.PENDING.getCode());
                    int id = oacApplyFlyLogDalService.insertApplyFlyLog(applyFlyLogDO);
                    if (id > 0) {
                        Boolean updateDynamicInfoResult = updateDynamicInfoByReplyFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId(), replyFlyId, FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED, flyApplyRequest.getFlyLng(), flyApplyRequest.getFlyLat(), flyApplyRequest.getFlyAlt());
                        if (updateDynamicInfoResult) {
                            flyApplyResponse.success();
                            flyApplyResponse.setReplyFlyId(replyFlyId.toString());
                        } else {
                            flyApplyResponse.fail("放飞申请失败，插入数据失败");
                        }
                    } else {
                        flyApplyResponse.fail("放飞申请失败，插入数据失败");
                    }
                } else {
                    flyApplyResponse.fail(ErrorCodeEnum.WRONG_REPLY_FLIGHT_PLAN_ID);
                }

            } else {
                flyApplyResponse.fail("放飞申请失败,已发起过放飞申请,无需重新下发");
            }
            log.info("[oac]放飞申请end，flyApplyRequest={}, flyApplyResponse={}", flyApplyRequest, JsonUtils.object2Json(flyApplyResponse));
        } catch (Exception e) {
            log.error("[oac]放飞申请异常，flyApplyRequest={}", flyApplyRequest, e);
            flyApplyResponse.fail(e.getMessage());
        }
        return flyApplyResponse;
    }

    private boolean judgeUavHasApplyFly(List<ApplyFlyLogDO> queryApplyFlyLogList) {
        boolean result = false;
        if (CollectionUtils.isNotEmpty(queryApplyFlyLogList)) {
            for (ApplyFlyLogDO applyFlyLog : queryApplyFlyLogList) {
                ApplyStatusEnum applyStatusEnum = ApplyStatusEnum.getFromCode(applyFlyLog.getStatus());
                if (ApplyStatusEnum.APPROVED.equals(applyStatusEnum) || ApplyStatusEnum.PENDING.equals(applyStatusEnum)) {
                    result = true;
                    break;
                }
            }
        }
        return result;
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
            if (applyFlyLogDO != null) {
                flyQueryResponse.success();
                FlyQueryResultParam flyQueryResultParam = new FlyQueryResultParam();
                flyQueryResultParam.setApplyFlyId(applyFlyLogDO.getApplyFlyId());
                flyQueryResultParam.setReplyFlyId(flyQueryRequest.getReplyFlyId());
                flyQueryResultParam.setStatus(applyFlyLogDO.getStatus());
                flyQueryResponse.setFlyQueryResultParam(flyQueryResultParam);
            } else {
                flyQueryResponse.fail(ErrorCodeEnum.WRONG_REPLY_FLY_ID);
            }
            log.info("[oac]放飞申请查询end，flyQueryRequest={}, flyQueryResponse={}", flyQueryRequest, JsonUtils.object2Json(flyQueryResponse));
        } catch (Exception e) {
            log.error("[oac]放飞申请查询异常，flyQueryRequest={}", flyQueryRequest, e);
            flyQueryResponse.fail(e.getMessage());
        }
        return flyQueryResponse;
    }

    Boolean updateDynamicInfoByReplyFlightPlanId(Long replyFlightId, Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum, Integer lng, Integer lat, Integer alt) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(replyFlightId);
        dynamicUavInfoDO.setReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        if (lng != null && lat != null && alt != null) {
            Integer distance = GpsDistanceUtils.getDistance(lng, lat, dynamicUavInfoDO.getLandingLng(), dynamicUavInfoDO.getLandingLat());
            dynamicUavInfoDO.setLng(lng);
            dynamicUavInfoDO.setLat(lat);
            dynamicUavInfoDO.setAlt(alt);
            dynamicUavInfoDO.setDistanceToLandingPoint(distance);
        }
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(replyFlightId);
        dynamicRouteInfoDO.setReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());

        DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(replyFlightId);
        dynamicFlightPlanInfoDO.setReplyFlyId(replyFlyId);
        dynamicFlightPlanInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = oacDynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        int updateDynamicFlightPlanInfoId = oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0 && updateDynamicFlightPlanInfoId > 0) {
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
            DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getReplyFlightPlanId()));
            if (queryDynamicRouteInfoDO != null && queryDynamicUavInfo != null && queryApplyFlightPlanLog != null) {
                ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(queryDynamicUavInfo.getReplyFlyId());
                if (queryApplyFlyLog != null) {
                    ApplyStatusEnum flightPlanStatus = ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus());
                    ApplyStatusEnum flyStatus = ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus());
                    if (ApplyStatusEnum.APPROVED.equals(flightPlanStatus) && !ApplyStatusEnum.PENDING.equals(flyStatus)) {
                        // 更新动态信息状态
                        oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                        oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                        oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_FINISHED.getCode());
                        // 更新飞行计划状态
                        oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.COMPLETE.getCode());
                        // 飞行计划处于通过状态，则结束飞行计划
                        if (ApplyStatusEnum.APPROVED.equals(flyStatus)) {
                            oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.COMPLETE.getCode());
                        }
                        finishFlightPlanResponse.success();
                    } else {
                        finishFlightPlanResponse.fail("飞行计划未处于通过状态或处于飞行状态，不需要结束");
                    }
                } else {
                    finishFlightPlanResponse.fail(ErrorCodeEnum.WRONG_REPLY_FLIGHT_PLAN_ID);
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
