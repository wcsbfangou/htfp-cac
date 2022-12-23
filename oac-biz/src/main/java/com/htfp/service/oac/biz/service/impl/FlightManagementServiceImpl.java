package com.htfp.service.oac.biz.service.impl;

import com.htfp.service.cac.client.service.IOacService;
import com.htfp.service.oac.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.client.enums.ApplicantTypeEnum;
import com.htfp.service.oac.client.enums.ApplyStatusEnum;
import com.htfp.service.oac.client.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.client.request.FlightPlanApplyRequest;
import com.htfp.service.oac.client.request.FlightPlanQueryRequest;
import com.htfp.service.oac.client.response.FlightPlanApplyResponse;
import com.htfp.service.oac.client.response.FlightPlanQueryResponse;
import com.htfp.service.oac.client.response.param.FlightPlanQueryResultParam;
import com.htfp.service.oac.common.utils.DateUtils;
import com.htfp.service.oac.common.utils.JsonUtils;
import com.htfp.service.oac.common.utils.SnowflakeIdUtils;
import com.htfp.service.oac.dao.model.AirportInfoDO;
import com.htfp.service.oac.dao.model.ApplyFlightPlanLogDO;
import com.htfp.service.oac.dao.model.DynamicRouteInfoDO;
import com.htfp.service.oac.dao.model.DynamicUavInfoDO;
import com.htfp.service.oac.dao.model.OperatorInfoDO;
import com.htfp.service.oac.dao.model.UavInfoDO;
import com.htfp.service.oac.dao.service.AirportInfoDalService;
import com.htfp.service.oac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.oac.dao.service.DynamicRouteInfoDalService;
import com.htfp.service.oac.dao.service.DynamicUavInfoDalService;
import com.htfp.service.oac.dao.service.OperatorDalService;
import com.htfp.service.oac.dao.service.UavDalService;
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
            UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(flightPlanApplyRequest.getCpn());
            String applicantSubject;
            if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType()))) {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantOrganizationParam());
            } else {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantPersonParam());
            }
            ApplyFlightPlanLogDO applyFlightPlanLog = applyFlightPlanLogDalService.buildApplyFlightPlanLogDO(flightPlanApplyRequest.getApplyFlightPlanId(), replyFlightPlanId, queryUavInfo.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
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
                    FlightPlanStatusTypeEnum flightPlanStatusTypeEnum;
                    if (flightPlanIssuedRequest.getPass()) {
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.APPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED;
                    } else {
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.UNAPPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_UNAPPROVED;
                    }
                    if (!initializeDynamicInfo(queryApplyFlightPlanLog, flightPlanIssuedRequest.getCpn(), flightPlanStatusTypeEnum.getCode())) {
                        //ROLLBACK
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, queryApplyFlightPlanLog.getStatus());
                        flightPlanIssuedResponse.fail();
                    } else {
                        flightPlanIssuedResponse.setCpn(flightPlanIssuedRequest.getCpn());
                    }
                    com.htfp.service.cac.client.request.FlightPlanIssuedRequest cacFlightPlanIssuedRequest = buildCacFlightPlanIssuedRequest(flightPlanIssuedRequest, queryApplyFlightPlanLog.getApplyFlightPlanId());
                    com.htfp.service.cac.client.response.FlightPlanIssuedResponse cacFlightPlanIssuedResponse = oacService.flightPlanIssued(cacFlightPlanIssuedRequest);
                    flightPlanIssuedResponse = buildFlightPlanIssuedResponse(cacFlightPlanIssuedResponse);
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

    com.htfp.service.cac.client.request.FlightPlanIssuedRequest buildCacFlightPlanIssuedRequest(FlightPlanIssuedRequest flightPlanIssuedRequest, String applyFlightPlanId) {
        com.htfp.service.cac.client.request.FlightPlanIssuedRequest cacFlightPlanIssuedRequest = new com.htfp.service.cac.client.request.FlightPlanIssuedRequest();
        cacFlightPlanIssuedRequest.setCpn(flightPlanIssuedRequest.getCpn());
        cacFlightPlanIssuedRequest.setApplyFlightPlanId(applyFlightPlanId);
        cacFlightPlanIssuedRequest.setReplyFlightPlanId(flightPlanIssuedRequest.getFlightPlanId());
        cacFlightPlanIssuedRequest.setPass(flightPlanIssuedRequest.getPass());
        return cacFlightPlanIssuedRequest;
    }

    FlightPlanIssuedResponse buildFlightPlanIssuedResponse(com.htfp.service.cac.client.response.FlightPlanIssuedResponse cacFlightPlanIssuedResponse) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.setSuccess(cacFlightPlanIssuedResponse.getSuccess());
        flightPlanIssuedResponse.setCode(cacFlightPlanIssuedResponse.getCode());
        flightPlanIssuedResponse.setMessage(cacFlightPlanIssuedResponse.getMessage());
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
}
