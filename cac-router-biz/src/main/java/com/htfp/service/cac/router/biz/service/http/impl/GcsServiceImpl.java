package com.htfp.service.cac.router.biz.service.http.impl;

import com.htfp.service.cac.command.biz.model.response.GcsChangeControlUavResponse;
import com.htfp.service.cac.command.biz.model.response.SaveUavControlLogResponse;
import com.htfp.service.cac.command.biz.model.response.UavChangeStatusResponse;
import com.htfp.service.cac.command.biz.model.resquest.GcsChangeControlUavRequest;
import com.htfp.service.cac.command.biz.model.resquest.SaveUavControlLogRequest;
import com.htfp.service.cac.command.biz.model.resquest.UavChangeStatusRequest;
import com.htfp.service.cac.command.biz.service.ICommandService;
import com.htfp.service.cac.command.biz.service.IUavService;
import com.htfp.service.cac.common.enums.ApplicantTypeEnum;
import com.htfp.service.cac.common.enums.ApplyStatusEnum;
import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.SnowflakeIdUtils;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import com.htfp.service.cac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.http.request.FinishFlightPlanRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavVerifyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.param.ChangeUavParam;
import com.htfp.service.cac.router.biz.model.http.request.param.ChangeUavStatusParam;
import com.htfp.service.cac.router.biz.model.http.request.param.CommandUavParam;
import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.request.param.OrganizationParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PersonParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PositionParam;
import com.htfp.service.cac.router.biz.model.http.response.FinishFlightPlanResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavVerifyApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.param.FlightPlanQueryResultParam;
import com.htfp.service.cac.router.biz.service.http.IGcsService;
import com.htfp.service.oac.client.service.IPreFlightService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:11
 * @Description 地面站服务类
 */
@Slf4j
@Service("gcsServiceImpl")
public class GcsServiceImpl implements IGcsService {

    @Resource
    UavDalService uavDalService;

    @Resource
    GcsDalService gcsDalService;

    @Resource
    PilotDalService pilotDalService;

    @Resource
    IPreFlightService preFlightService;

    @Resource
    ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    @Resource(name = "uavServiceImpl")
    IUavService uavService;

    @Resource(name = "commandServiceImpl")
    ICommandService commandService;

    /**
     * 地面站注册
     *
     * @param signInRequest
     * @return
     */
    @Override
    public SignInResponse gcsSignIn(SignInRequest signInRequest) {
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.fail();
        try {
            log.info("[router]地面站上线start，signRequest={}", signInRequest);
            final Long gcsId = Long.valueOf(signInRequest.getGcsId());
            //校验地面站
            boolean validateGcsResult = gcsDalService.validateGcsType(gcsId, GcsTypeEnum.GCS);
            if (validateGcsResult) {
                // 更新或插入地面站与IP的mapping关系表
                GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(gcsId);
                if (queryGcsIpMapping != null) {
                    // 断联地面站续联，并修改连接到运行管控系统的无人机连接状态
                    if (MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus())) &&
                            LinkStatusEnum.DISCONNECT.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                        List<UavGcsMappingDO> disConnectUavGcsMappingList = uavDalService.queryUavGcsMapping(gcsId, MappingStatusEnum.VALID);
                        for (UavGcsMappingDO uavGcsMapping : disConnectUavGcsMappingList) {
                            UavOacMappingDO queryUavOacMapping = uavDalService.queryUavOacMapping(uavGcsMapping.getUavId(), MappingStatusEnum.VALID, LinkStatusEnum.DISCONNECT);
                            if (queryUavOacMapping != null) {
                                uavDalService.updateUavOacMappingLinkStatus(queryUavOacMapping, LinkStatusEnum.ONLINE);
                            }
                        }
                    }
                    // 更新无人机地面站与IP的mapping关系以及状态
                    int id = gcsDalService.updateGcsIpMappingIp(queryGcsIpMapping, signInRequest.getGcsIp());
                    if (id > 0) {
                        signInResponse.success();
                    } else {
                        signInResponse.setMessage("更新gcs与Ip的mapping关系失败");
                    }
                } else {
                    // 插入地面站与IP的mapping关系
                    GcsIpMappingDO gcsIpMapping = gcsDalService.buildGcsIpMappingDO(gcsId, signInRequest.getGcsIp());
                    int id = gcsDalService.insertGcsIpMapping(gcsIpMapping);
                    if (id > 0) {
                        signInResponse.success();
                    } else {
                        signInResponse.setMessage("插入gcs与Ip的mapping关系失败");
                    }
                }
            } else {
                signInResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("[router]地面站上线end，signInRequest={}，signInResponse={}", signInRequest, JsonUtils.object2Json(signInResponse));
        } catch (Exception e) {
            log.error("[router]地面站上线异常，signRequest={}", signInRequest, e);
            signInResponse.fail(e.getMessage());
        }
        return signInResponse;
    }

    /**
     * 地面站下线
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse gcsSignOut(SignOutRequest signOutRequest) {
        SignOutResponse signOutResponse = new SignOutResponse();
        signOutResponse.fail();
        try {
            log.info("[router]地面站下线start，signOutRequest={}", signOutRequest);
            final Long gcsId = Long.valueOf(signOutRequest.getGcsId());
            // 校验地面站
            boolean validateGcsResult = gcsDalService.validateGcsType(gcsId, GcsTypeEnum.GCS);
            if (validateGcsResult) {
                // 判断地面站是否可下线
                List<UavGcsMappingDO> uavGcsMappingDOList = uavDalService.queryUavGcsMapping(gcsId, MappingStatusEnum.VALID);
                if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
                    signOutResponse.setMessage("地面站绑定的无人机还未完成航行，不可下线");
                } else {
                    // 地面站下线
                    GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(gcsId);
                    if (queryGcsIpMapping != null) {
                        //(1)校验gcs与IP的mapping关系
                        if (MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus()))) {
                            if (!queryGcsIpMapping.getGcsIp().equals(signOutRequest.getGcsIp())) {
                                signOutResponse.setMessage("地面站注销时IP与注册时IP不一致，不可下线");
                            } else {
                                //(2)校验通过后更新gcs与Ip的mapping关系
                                int id = gcsDalService.updateGcsIpMappingStatusAndLinkStatus(queryGcsIpMapping, MappingStatusEnum.INVALID, LinkStatusEnum.OFFLINE);
                                if (id > 0) {
                                    signOutResponse.success();
                                } else {
                                    signOutResponse.setMessage("更新gcs与Ip的mapping关系失败");
                                }
                            }
                        } else {
                            signOutResponse.setMessage("地面站已下线，不可重复下线");
                        }
                    } else {
                        signOutResponse.setMessage("地面站未上线过，不可下线");
                    }
                }
            } else {
                signOutResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("[router]地面站下线end，signOutRequest={},signOutResponse={}", signOutRequest, JsonUtils.object2Json(signOutResponse));
        } catch (Exception e) {
            log.error("[router]地面站下线异常，signOutRequest={}", signOutRequest, e);
            signOutResponse.fail(e.getMessage());
        }
        return signOutResponse;
    }

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
            log.info("[router]飞行计划申请start，flightPlanApplyRequest={}", flightPlanApplyRequest);
            // TODO: 2022/12/22 IDC ID && 机器ID
            // 生成applyFlightPlanId
            Long applyFlightPlanId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
            UavInfoDO queryUavInfo = uavDalService.queryUavInfo(flightPlanApplyRequest.getUavId());
            String applicantSubject;
            if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType()))) {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantOrganizationParam());
            } else {
                applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantPersonParam());
            }
            ApplyFlightPlanLogDO applyFlightPlanLog = applyFlightPlanLogDalService.buildApplyFlightPlanLogDO(applyFlightPlanId, null, flightPlanApplyRequest.getGcsId(), flightPlanApplyRequest.getUavId(), queryUavInfo.getUavReg(), queryUavInfo.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
                    JsonUtils.object2Json(flightPlanApplyRequest.getPilots()), JsonUtils.object2Json(flightPlanApplyRequest.getAirspaceNumbers()), JsonUtils.object2Json(flightPlanApplyRequest.getRoutePointCoordinates()), flightPlanApplyRequest.getTakeOffAirportId(), flightPlanApplyRequest.getLandingAirportId(),
                    flightPlanApplyRequest.getTakeoffSite(), flightPlanApplyRequest.getLandingSite(), flightPlanApplyRequest.getMissionType(), flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(), flightPlanApplyRequest.getEmergencyProcedure(),
                    flightPlanApplyRequest.getOperationScenarioType(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getIsVlos(), ApplyStatusEnum.PENDING.getCode());
            int id = applyFlightPlanLogDalService.insertApplyFlightPlanLog(applyFlightPlanLog);
            if (id > 0) {
                com.htfp.service.oac.client.request.FlightPlanApplyRequest oacFlightPlanApplyRequest = buildOacFlightPlanApplyRequest(flightPlanApplyRequest, applyFlightPlanId, queryUavInfo.getCpn());
                com.htfp.service.oac.client.response.FlightPlanApplyResponse oacFlightPlanApplyResponse = preFlightService.flightPlanApply(oacFlightPlanApplyRequest);
                // TODO: 2022/12/22 校验oacFlightPlanApplyResponse
                flightPlanApplyResponse = buildFlightPlanApplyResponse(oacFlightPlanApplyResponse);
                if (flightPlanApplyResponse.getSuccess()) {
                    applyFlightPlanLogDalService.updateApplyFlightPlanLogReplyFlightPlanIdById(applyFlightPlanLog.getId(), oacFlightPlanApplyResponse.getReplyFlightPlanId());
                    flightPlanApplyResponse.setApplyFlightPlanId(applyFlightPlanId.toString());
                }
            } else {
                flightPlanApplyResponse.fail("飞行计划申请失败，插入数据失败");
            }
            log.info("[router]飞行计划申请end，flightPlanApplyRequest={},flightPlanApplyResponse={}", flightPlanApplyRequest, JsonUtils.object2Json(flightPlanApplyResponse));
        } catch (Exception e) {
            log.error("[router]飞行计划申请异常，flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            flightPlanApplyResponse.fail(e.getMessage());
        }
        return flightPlanApplyResponse;
    }

    com.htfp.service.oac.client.request.FlightPlanApplyRequest buildOacFlightPlanApplyRequest(FlightPlanApplyRequest flightPlanApplyRequest, Long applyFlightPlanId, String cpn) {
        com.htfp.service.oac.client.request.FlightPlanApplyRequest oacFlightPlanApplyRequest = new com.htfp.service.oac.client.request.FlightPlanApplyRequest();
        oacFlightPlanApplyRequest.setCpn(cpn);
        oacFlightPlanApplyRequest.setApplyFlightPlanId(applyFlightPlanId.toString());
        oacFlightPlanApplyRequest.setApplicantType(flightPlanApplyRequest.getApplicantType());
        if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType()))) {
            oacFlightPlanApplyRequest.setApplicantOrganizationParam(buildOacPersonParam(flightPlanApplyRequest.getApplicantOrganizationParam()));
        } else {
            oacFlightPlanApplyRequest.setApplicantPersonParam(buildOacPersonParam(flightPlanApplyRequest.getApplicantPersonParam()));
        }
        oacFlightPlanApplyRequest.setPilots(buildOacPilots(flightPlanApplyRequest.getPilots()));
        oacFlightPlanApplyRequest.setAirspaceNumbers(flightPlanApplyRequest.getAirspaceNumbers());
        oacFlightPlanApplyRequest.setRoutePointCoordinates(buildOacRoutePointCoordinates(flightPlanApplyRequest.getRoutePointCoordinates()));
        oacFlightPlanApplyRequest.setTakeOffAirportId(flightPlanApplyRequest.getTakeOffAirportId());
        oacFlightPlanApplyRequest.setLandingAirportId(flightPlanApplyRequest.getLandingAirportId());
        oacFlightPlanApplyRequest.setTakeoffSite(flightPlanApplyRequest.getTakeoffSite());
        oacFlightPlanApplyRequest.setLandingSite(flightPlanApplyRequest.getLandingSite());
        oacFlightPlanApplyRequest.setMissionType(flightPlanApplyRequest.getMissionType());
        oacFlightPlanApplyRequest.setStartTime(flightPlanApplyRequest.getStartTime());
        oacFlightPlanApplyRequest.setEndTime(flightPlanApplyRequest.getEndTime());
        oacFlightPlanApplyRequest.setEmergencyProcedure(flightPlanApplyRequest.getEmergencyProcedure());
        oacFlightPlanApplyRequest.setOperationScenarioType(flightPlanApplyRequest.getOperationScenarioType());
        oacFlightPlanApplyRequest.setIsEmergency(flightPlanApplyRequest.getIsEmergency());
        oacFlightPlanApplyRequest.setIsVlos(flightPlanApplyRequest.getIsVlos());
        return oacFlightPlanApplyRequest;
    }

    com.htfp.service.oac.client.request.param.OrganizationParam buildOacPersonParam(OrganizationParam applicantOrganizationParam) {
        com.htfp.service.oac.client.request.param.OrganizationParam oacApplicantOrganizationParam = new com.htfp.service.oac.client.request.param.OrganizationParam();
        oacApplicantOrganizationParam.setOrgType(applicantOrganizationParam.getOrgType());
        oacApplicantOrganizationParam.setOrgName(applicantOrganizationParam.getOrgName());
        oacApplicantOrganizationParam.setSocialCreditCode(applicantOrganizationParam.getSocialCreditCode());
        oacApplicantOrganizationParam.setContactName(applicantOrganizationParam.getContactName());
        oacApplicantOrganizationParam.setContactPhone(applicantOrganizationParam.getContactPhone());
        oacApplicantOrganizationParam.setContactEmail(applicantOrganizationParam.getContactEmail());
        oacApplicantOrganizationParam.setMemo(applicantOrganizationParam.getMemo());
        return oacApplicantOrganizationParam;
    }

    com.htfp.service.oac.client.request.param.PersonParam buildOacPersonParam(PersonParam applicantPersonParam) {
        com.htfp.service.oac.client.request.param.PersonParam oacPersonParam = new com.htfp.service.oac.client.request.param.PersonParam();
        oacPersonParam.setPersonName(applicantPersonParam.getPersonName());
        oacPersonParam.setNationality(applicantPersonParam.getNationality());
        oacPersonParam.setIdCardType(applicantPersonParam.getIdCardType());
        oacPersonParam.setIdCardNumber(applicantPersonParam.getIdCardNumber());
        oacPersonParam.setLicenseId(applicantPersonParam.getLicenseId());
        oacPersonParam.setIllegalRecordType(applicantPersonParam.getIllegalRecordType());
        oacPersonParam.setContactPhone(applicantPersonParam.getContactPhone());
        oacPersonParam.setContactEmail(applicantPersonParam.getContactEmail());
        oacPersonParam.setMemo(applicantPersonParam.getMemo());
        return oacPersonParam;
    }

    List<com.htfp.service.oac.client.request.param.PersonParam> buildOacPilots(List<PersonParam> pilots) {
        List<com.htfp.service.oac.client.request.param.PersonParam> oacPilots = new ArrayList<>();
        for (PersonParam pilot : pilots) {
            com.htfp.service.oac.client.request.param.PersonParam oacPilot = buildOacPersonParam(pilot);
            oacPilots.add(oacPilot);
        }
        return oacPilots;
    }


    com.htfp.service.oac.client.request.param.PositionParam buildOacPositionParam(PositionParam positionParam) {
        com.htfp.service.oac.client.request.param.PositionParam oacPositionParam = new com.htfp.service.oac.client.request.param.PositionParam();
        oacPositionParam.setAlt(positionParam.getAlt());
        oacPositionParam.setLat(positionParam.getLat());
        oacPositionParam.setLng(positionParam.getLng());
        return oacPositionParam;
    }

    List<com.htfp.service.oac.client.request.param.PositionParam> buildOacRoutePointCoordinates(List<PositionParam> routePointCoordinates) {
        List<com.htfp.service.oac.client.request.param.PositionParam> oacRoutePointCoordinates = new ArrayList<>();
        for (PositionParam routePointCoordinate : routePointCoordinates) {
            com.htfp.service.oac.client.request.param.PositionParam oacRoutePointCoordinate = buildOacPositionParam(routePointCoordinate);
            oacRoutePointCoordinates.add(oacRoutePointCoordinate);
        }
        return oacRoutePointCoordinates;
    }

    FlightPlanApplyResponse buildFlightPlanApplyResponse(com.htfp.service.oac.client.response.FlightPlanApplyResponse oacFlightPlanApplyResponse) {
        FlightPlanApplyResponse flightPlanApplyResponse = new FlightPlanApplyResponse();
        flightPlanApplyResponse.setSuccess(oacFlightPlanApplyResponse.getSuccess());
        flightPlanApplyResponse.setCode(oacFlightPlanApplyResponse.getCode());
        flightPlanApplyResponse.setMessage(oacFlightPlanApplyResponse.getMessage());
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
            log.info("[router]飞行计划查询start，flightPlanQueryRequest={}", flightPlanQueryRequest);
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(flightPlanQueryRequest.getApplyFlightPlanId()));
            if (!ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                flightPlanQueryResponse.success();
                FlightPlanQueryResultParam flightPlanQueryResultParam = new FlightPlanQueryResultParam();
                flightPlanQueryResultParam.setApplyFlightPlanId(flightPlanQueryRequest.getApplyFlightPlanId());
                flightPlanQueryResultParam.setReplyFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId());
                flightPlanQueryResultParam.setStatus(queryApplyFlightPlanLog.getStatus());
                flightPlanQueryResponse.setFlightPlanQueryResultParam(flightPlanQueryResultParam);
            } else {
                com.htfp.service.oac.client.request.FlightPlanQueryRequest oacFlightPlanQueryRequest = buildOacFlightPlanQueryRequest(queryApplyFlightPlanLog.getReplyFlightPlanId());
                com.htfp.service.oac.client.response.FlightPlanQueryResponse oacFlightPlanQueryResponse = preFlightService.flightPlanQuery(oacFlightPlanQueryRequest);
                // TODO: 2022/12/22 校验oacFliPlanQueryResponse
                flightPlanQueryResponse = buildOacFlightPlanQueryResponse(oacFlightPlanQueryResponse);
                if (flightPlanQueryResponse.getSuccess() && !queryApplyFlightPlanLog.getStatus().equals(flightPlanQueryResponse.getFlightPlanQueryResultParam().getStatus())) {
                    applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, flightPlanQueryResponse.getFlightPlanQueryResultParam().getStatus());
                }
            }
            log.info("[router]飞行计划查询end，flightPlanQueryRequest={},flightPlanQueryResponse={}", flightPlanQueryRequest, JsonUtils.object2Json(flightPlanQueryResponse));
        } catch (Exception e) {
            log.error("[router]飞行计划查询异常，flightPlanQueryRequest={}", flightPlanQueryRequest, e);
            flightPlanQueryResponse.fail(e.getMessage());
        }
        return flightPlanQueryResponse;
    }

    com.htfp.service.oac.client.request.FlightPlanQueryRequest buildOacFlightPlanQueryRequest(String replyFlightPlanId) {
        com.htfp.service.oac.client.request.FlightPlanQueryRequest oacFlightPlanQueryRequest = new com.htfp.service.oac.client.request.FlightPlanQueryRequest();
        oacFlightPlanQueryRequest.setReplyFlightPlanId(replyFlightPlanId);
        return oacFlightPlanQueryRequest;
    }

    FlightPlanQueryResponse buildOacFlightPlanQueryResponse(com.htfp.service.oac.client.response.FlightPlanQueryResponse oacFliPlanQueryResponse) {
        FlightPlanQueryResponse flightPlanQueryResponse = new FlightPlanQueryResponse();
        if (oacFliPlanQueryResponse.getSuccess() && oacFliPlanQueryResponse.getFlightPlanQueryResultParam() != null) {
            FlightPlanQueryResultParam flightPlanQueryResultParam = new FlightPlanQueryResultParam();
            flightPlanQueryResultParam.setApplyFlightPlanId(oacFliPlanQueryResponse.getFlightPlanQueryResultParam().getApplyFlightPlanId());
            flightPlanQueryResultParam.setReplyFlightPlanId(oacFliPlanQueryResponse.getFlightPlanQueryResultParam().getReplyFlightPlanId());
            flightPlanQueryResultParam.setStatus(oacFliPlanQueryResponse.getFlightPlanQueryResultParam().getStatus());
            flightPlanQueryResponse.setFlightPlanQueryResultParam(flightPlanQueryResultParam);
        }
        flightPlanQueryResponse.setSuccess(oacFliPlanQueryResponse.getSuccess());
        flightPlanQueryResponse.setCode(oacFliPlanQueryResponse.getCode());
        flightPlanQueryResponse.setMessage(oacFliPlanQueryResponse.getMessage());
        return flightPlanQueryResponse;
    }

    /**
     * 无人机接入校验申请
     *
     * @param uavVerifyApplyRequest
     * @return
     */
    @Override
    public UavVerifyApplyResponse uavVerifyApply(UavVerifyApplyRequest uavVerifyApplyRequest) {
        return null;
    }

    /**
     * 放飞申请
     *
     * @param flyApplyRequest
     * @return
     */
    @Override
    public FlyApplyResponse flyApply(FlyApplyRequest flyApplyRequest) {
        return null;
    }

    /**
     * 放飞查询
     *
     * @param flyQueryRequest
     * @return
     */
    @Override
    public FlyQueryResponse flyQuery(FlyQueryRequest flyQueryRequest) {
        return null;
    }

    /**
     * 地面站在控无人机变更
     *
     * @param gcsChangeUavRequest
     * @return
     */
    @Override
    public GcsChangeUavResponse gcsChangeUav(GcsChangeUavRequest gcsChangeUavRequest) {
        GcsChangeUavResponse gcsChangeUavResponse = new GcsChangeUavResponse();
        gcsChangeUavResponse.fail();
        try {
            log.info("[router]地面站在控无人机变更start，gcsChangeUavRequest={}", gcsChangeUavRequest);
            final Long gcsId = Long.valueOf(gcsChangeUavRequest.getGcsId());
            //(1)校验地面站信息以及是否上线
            BaseResponse validateGcsResult = validateGcs(gcsId);
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                //(2)在循环之前查询gcsInfo
                GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(gcsId);
                //(3)校验通过后，遍历uavList进行处理
                for (ChangeUavParam changeUavParam : gcsChangeUavRequest.getUavList()) {
                    Long uavId = Long.valueOf(changeUavParam.getUavId());
                    Long masterPilotId = Long.valueOf(changeUavParam.getMasterPilotId());
                    Long deputyPilotId = StringUtils.isNotEmpty(changeUavParam.getDeputyPilotId()) ? Long.valueOf(changeUavParam.getDeputyPilotId()) : null;
                    //(4)校验可控无人机类型
                    BaseResponse response = validateUavStatusChangeParam(uavId, gcsInfo, masterPilotId, deputyPilotId);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                        //(5)构造请求体
                        GcsChangeControlUavRequest gcsChangeControlUavRequest = buildGcsChangeControlUavRequest(gcsId, uavId, changeUavParam.getNewArrival(), changeUavParam.getUavStatus(), masterPilotId, deputyPilotId);
                        //(6)调用指控模块接口，变更在控无人机
                        GcsChangeControlUavResponse gcsChangeControlUavResponse = commandService.gcsChangeUav(gcsChangeControlUavRequest);
                        //(7)插入或更新uav与gcs的mapping关系表
                        if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(gcsChangeControlUavResponse.getCode()))) {
                            insertOrUpdateUavGcsMapping(uavId, gcsId);
                            gcsChangeUavResponse.success();
                        } else {
                            gcsChangeUavResponse.fail(gcsChangeControlUavResponse.getCode(), gcsChangeControlUavResponse.getMessage());
                            break;
                        }
                    } else {
                        gcsChangeUavResponse.fail(response.getCode(), response.getMessage());
                        break;
                    }
                }
            } else {
                gcsChangeUavResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            log.info("[router]地面站在控无人机变更end，gcsChangeUavRequest={}, gcsChangeUavResponse={}", gcsChangeUavRequest, JsonUtils.object2Json(gcsChangeUavResponse));
        } catch (Exception e) {
            log.error("[router]地面站在控无人机变更异常，gcsChangeUavRequest={}", gcsChangeUavRequest, e);
            gcsChangeUavResponse.fail(e.getMessage());
        }
        return gcsChangeUavResponse;
    }

    /**
     * 无人机状态变更
     *
     * @param uavStatusChangeRequest
     * @return
     */
    @Override
    public UavStatusChangeResponse uavStatusChange(UavStatusChangeRequest uavStatusChangeRequest) {
        UavStatusChangeResponse uavStatusChangeResponse = new UavStatusChangeResponse();
        uavStatusChangeResponse.fail();
        try {
            log.info("[router]无人机状态变更start，uavStatusChangeRequest={}", uavStatusChangeRequest);
            final Long gcsId = Long.valueOf(uavStatusChangeRequest.getGcsId());
            //(1)校验地面站信息以及是否上线
            BaseResponse validateGcsResult = validateGcs(gcsId);
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                List<ChangeUavStatusParam> uavList = uavStatusChangeRequest.getUavList();
                //(2)校验通过后，遍历UavList处理
                for (ChangeUavStatusParam changeUavStatusParam : uavList) {
                    Long uavId = Long.valueOf(changeUavStatusParam.getUavId());
                    BaseResponse response = validateUavStatusChangeParam(uavId, gcsId);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                        // (3)构造请求体
                        UavChangeStatusRequest uavChangeStatusRequest = buildUavChangeStatusRequest(gcsId, uavId, changeUavStatusParam.getUavStatus());
                        //(4)调用指控模块接口，变更无人机状态
                        UavChangeStatusResponse uavChangeStatusResponse = uavService.uavChangeStatus(uavChangeStatusRequest);
                        if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(uavChangeStatusResponse.getCode()))) {
                            uavStatusChangeResponse.success();
                            // (5)更新uav和gcs的mapping关系表的状态
                            if (UavStatusEnum.SHUT_DOWN.equals(UavStatusEnum.getFromCode(changeUavStatusParam.getUavStatus()))) {
                                UavGcsMappingDO uavGcsMapping = uavDalService.queryValidUavGcsMapping(uavId, gcsId);
                                uavDalService.updateUavGcsMappingStatus(uavGcsMapping, MappingStatusEnum.INVALID);
                            }
                        } else {
                            uavStatusChangeResponse.fail(uavChangeStatusResponse.getCode(), uavChangeStatusResponse.getMessage());
                            break;
                        }
                    } else {
                        uavStatusChangeResponse.fail(response.getCode(), response.getMessage());
                        break;
                    }
                }
            } else {
                uavStatusChangeResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            log.info("[router]无人机状态变更end，uavStatusChangeRequest={}，uavStatusChangeResponse={}", uavStatusChangeRequest, JsonUtils.object2Json(uavStatusChangeResponse));
        } catch (Exception e) {
            log.error("[router]无人机状态变更异常，uavStatusChangeRequest={}", uavStatusChangeRequest, e);
            uavStatusChangeResponse.fail(e.getMessage());
        }
        return uavStatusChangeResponse;
    }

    /**
     * 地面站指控指令执行
     *
     * @param gcsControlUavRequest
     * @return
     */
    @Override
    public GcsControlUavResponse gcsControlUav(GcsControlUavRequest gcsControlUavRequest) {
        GcsControlUavResponse gcsControlUavResponse = new GcsControlUavResponse();
        gcsControlUavResponse.fail();
        try {
            log.error("[router]地面站指控指令执行start，gcsControlUavRequest={}", gcsControlUavRequest);
            Long gcsId = Long.valueOf(gcsControlUavRequest.getGcsId());
            for (CommandUavParam commandUavParam : gcsControlUavRequest.getUavList()) {
                // 校验请求体
                BaseResponse validateCommandUavParamResult = validateCommandUavParam(gcsId, Long.valueOf(commandUavParam.getUavId()), Long.valueOf(commandUavParam.getPilotId()));
                if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateCommandUavParamResult.getCode()))) {
                    // 构造请求体
                    SaveUavControlLogRequest saveUavControlLogRequest = buildSaveUavControlLogRequest(gcsId, Long.valueOf(commandUavParam.getUavId()), Long.valueOf(commandUavParam.getPilotId()), commandUavParam.getCommandCode(), commandUavParam.getCommandResult());
                    //调用指控模块接口，更新指控记录日志
                    SaveUavControlLogResponse saveUavControlLogResponse = uavService.saveUavControlLog(saveUavControlLogRequest);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(saveUavControlLogResponse.getCode()))) {
                        gcsControlUavResponse.success();
                    } else {
                        gcsControlUavResponse.fail(saveUavControlLogResponse.getCode(), saveUavControlLogResponse.getMessage());
                        break;
                    }
                } else {
                    gcsControlUavResponse.fail(validateCommandUavParamResult.getCode(), validateCommandUavParamResult.getMessage());
                    break;
                }
            }
            log.error("[router]地面站指控指令执行end，gcsControlUavRequest={}，gcsControlUavResponse={}", gcsControlUavRequest, JsonUtils.object2Json(gcsControlUavResponse));
        } catch (Exception e) {
            log.error("[router]地面站指控指令执行异常，gcsControlUavRequest={}", gcsControlUavRequest, e);
            gcsControlUavResponse.fail(e.getMessage());
        }
        return gcsControlUavResponse;
    }

    /**
     * 飞行计划结束通知
     *
     * @param finishFlightPlanRequest
     * @return
     */
    @Override
    public FinishFlightPlanResponse finishFlightPlan(FinishFlightPlanRequest finishFlightPlanRequest) {
        return null;
    }

    private GcsChangeControlUavRequest buildGcsChangeControlUavRequest(Long gcsId, Long uavId, Boolean newArrival, Integer uavStatus, Long masterPilot, Long deputyPilot) {
        GcsChangeControlUavRequest gcsChangeControlUavRequest = new GcsChangeControlUavRequest();
        gcsChangeControlUavRequest.setGcsId(gcsId);
        gcsChangeControlUavRequest.setUavId(uavId);
        gcsChangeControlUavRequest.setNewArrival(newArrival);
        gcsChangeControlUavRequest.setUavStatus(uavStatus);
        gcsChangeControlUavRequest.setMasterPilotId(masterPilot);
        gcsChangeControlUavRequest.setDeputyPilotId(deputyPilot);
        return gcsChangeControlUavRequest;
    }

    private SaveUavControlLogRequest buildSaveUavControlLogRequest(Long gcsId, Long uavId, Long pilotId, Integer commandCode, Boolean commandResult) {
        SaveUavControlLogRequest saveUavControlLogRequest = new SaveUavControlLogRequest();
        saveUavControlLogRequest.setGcsId(gcsId);
        saveUavControlLogRequest.setUavId(uavId);
        saveUavControlLogRequest.setPilotId(pilotId);
        saveUavControlLogRequest.setCommandCode(commandCode);
        saveUavControlLogRequest.setCommandResult(commandResult);
        return saveUavControlLogRequest;
    }

    private UavChangeStatusRequest buildUavChangeStatusRequest(Long gcsId, Long uavId, Integer uavStatus) {
        UavChangeStatusRequest uavChangeStatusRequest = new UavChangeStatusRequest();
        uavChangeStatusRequest.setGcsId(gcsId);
        uavChangeStatusRequest.setUavId(uavId);
        uavChangeStatusRequest.setUavStatus(uavStatus);
        return uavChangeStatusRequest;
    }

    private BaseResponse validateGcs(Long gcsId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        if (gcsDalService.validateGcsType(gcsId, GcsTypeEnum.GCS)) {
            if (gcsDalService.validateGcsStatus(gcsId)) {
                baseResponse.success();
            } else {
                baseResponse.fail(ErrorCodeEnum.GCS_NOT_SIGN_IN);
            }
        } else {
            baseResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
        }
        return baseResponse;
    }

    private BaseResponse validateUavStatusChangeParam(Long uavId, GcsInfoDO gcsInfo, Long masterPilotId, Long deputyPilotId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        UavInfoDO uavInfo = uavDalService.queryUavInfo(uavId);
        if (uavInfo != null) {
            if (uavInfo.getUavType() == (uavInfo.getUavType() & gcsInfo.getControllableUavType())) {
                PilotInfoDO masterPilotInfo = pilotDalService.queryPilotInfo(masterPilotId);
                PilotInfoDO deputyPilotInfo = deputyPilotId != null ? pilotDalService.queryPilotInfo(deputyPilotId) : masterPilotInfo;
                if (masterPilotInfo != null && deputyPilotInfo != null) {
                    if (uavInfo.getUavType() == (uavInfo.getUavType() & masterPilotInfo.getControllableUavType()) &&
                            uavInfo.getUavType() == (uavInfo.getUavType() & deputyPilotInfo.getControllableUavType())) {
                        baseResponse.success();
                    } else {
                        baseResponse.fail(ErrorCodeEnum.PILOT_MISMATCH_UAV);
                    }
                } else {
                    baseResponse.fail(ErrorCodeEnum.WRONG_PILOT_ID);
                }
            } else {
                baseResponse.fail(ErrorCodeEnum.GCS_MISMATCH_UAV);
            }
        } else {
            baseResponse.fail(ErrorCodeEnum.WRONG_UAV_ID);
        }
        return baseResponse;
    }

    private BaseResponse validateUavStatusChangeParam(Long uavId, Long gcsId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        boolean validateUavIdResult = uavDalService.validateUavId(uavId);
        if (validateUavIdResult) {
            UavGcsMappingDO uavGcsMapping = uavDalService.queryValidUavGcsMapping(uavId, gcsId);
            if (uavGcsMapping != null) {
                baseResponse.success();
            } else {
                baseResponse.fail(ErrorCodeEnum.LACK_OF_MAPPING.getCode(), "uav与gcs的" + ErrorCodeEnum.LACK_OF_MAPPING.getDesc());
            }
        } else {
            baseResponse.fail(ErrorCodeEnum.WRONG_UAV_ID);
        }
        return baseResponse;
    }

    private BaseResponse validateCommandUavParam(Long gcsId, Long uavId, Long pilotId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(gcsId);
        if (gcsInfo != null) {
            UavInfoDO uavInfo = uavDalService.queryUavInfo(uavId);
            if (uavInfo != null) {
                if (uavInfo.getUavType() == (uavInfo.getUavType() & gcsInfo.getControllableUavType())) {
                    PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(pilotId);
                    if (pilotInfo != null) {
                        if (uavInfo.getUavType() == (uavInfo.getUavType() & pilotInfo.getControllableUavType())) {
                            baseResponse.success();
                        } else {
                            baseResponse.fail(ErrorCodeEnum.PILOT_MISMATCH_UAV);
                        }
                    } else {
                        baseResponse.fail(ErrorCodeEnum.WRONG_PILOT_ID);
                    }
                } else {
                    baseResponse.fail(ErrorCodeEnum.GCS_MISMATCH_UAV);
                }
            } else {
                baseResponse.fail(ErrorCodeEnum.WRONG_UAV_ID);
            }
        } else {
            baseResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
        }
        return baseResponse;
    }

    public void insertOrUpdateUavGcsMapping(Long uavId, Long gcsId) {
        UavGcsMappingDO uavGcsMapping = uavDalService.queryUavGcsMapping(uavId);
        if (uavGcsMapping != null) {
            uavDalService.updateUavGcsMappingGcsId(uavGcsMapping, gcsId, MappingStatusEnum.VALID);
        } else {
            uavGcsMapping = uavDalService.buildUavGcsMappingDO(uavId, gcsId, MappingStatusEnum.VALID);
            uavDalService.insertUavGcsMapping(uavGcsMapping);
        }
    }
}
