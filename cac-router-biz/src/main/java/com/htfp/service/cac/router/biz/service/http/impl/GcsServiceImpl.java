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
import com.htfp.service.cac.common.enums.DeliverTypeEnum;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.StaticInfoStatusEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.SnowflakeIdUtils;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.RouteInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.ATCIssuedLogDO;
import com.htfp.service.cac.dao.model.log.AlarmIssuedLogDO;
import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.log.ApplyFlyLogDO;
import com.htfp.service.cac.dao.model.log.ApplyUavVerifyLogDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import com.htfp.service.cac.dao.service.ATCIssuedLogDalService;
import com.htfp.service.cac.dao.service.AlarmIssuedLogDalService;
import com.htfp.service.cac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.ApplyFlyLogDalService;
import com.htfp.service.cac.dao.service.ApplyUavVerifyLogDalService;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.RouteInfoDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.http.request.ATCQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.AlarmQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FinishFlightPlanRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavVerifyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.request.param.OrganizationParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PersonParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PositionParam;
import com.htfp.service.cac.router.biz.model.http.response.ATCQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.AlarmQueryResponse;
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
import com.htfp.service.cac.router.biz.model.http.response.param.ATCQueryResultParam;
import com.htfp.service.cac.router.biz.model.http.response.param.AlarmQueryResultParam;
import com.htfp.service.cac.router.biz.model.http.response.param.FlightPlanQueryResultParam;
import com.htfp.service.cac.router.biz.model.http.response.param.FlyQueryResultParam;
import com.htfp.service.cac.router.biz.model.http.response.param.UavVerifyResultParam;
import com.htfp.service.cac.router.biz.service.http.IGcsService;
import com.htfp.service.oac.biz.model.inner.request.param.UavDynamicParam;
import com.htfp.service.oac.biz.model.inner.request.param.UavStaticParam;
import com.htfp.service.oac.app.service.IFlyingService;
import com.htfp.service.oac.app.service.IPreFlightService;
import com.htfp.service.oac.common.enums.AtcTypeEnum;
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
    private UavDalService uavDalService;

    @Resource
    private GcsDalService gcsDalService;

    @Resource
    private PilotDalService pilotDalService;

    @Resource(name = "preFlightServiceImpl")
    private IPreFlightService preFlightService;

    @Resource(name = "flyingServiceImpl")
    private IFlyingService flyingService;

    @Resource
    private ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    @Resource
    private ApplyUavVerifyLogDalService applyUavVerifyLogDalService;

    @Resource
    private ApplyFlyLogDalService applyFlyLogDalService;

    @Resource
    private AlarmIssuedLogDalService alarmIssuedLogDalService;

    @Resource
    private ATCIssuedLogDalService atcIssuedLogDalService;

    @Resource
    private RouteInfoDalService routeInfoDalService;

    @Resource(name = "uavServiceImpl")
    private IUavService uavService;

    @Resource(name = "commandServiceImpl")
    private ICommandService commandService;

    /**
     * 地面站上线
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
            BaseResponse validateGcsResult = validateGcs(Long.valueOf(flightPlanApplyRequest.getGcsId()));
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                BaseResponse validateUavGcsMappingResult = validateUavGcsMapping(Long.valueOf(flightPlanApplyRequest.getUavId()), Long.valueOf(flightPlanApplyRequest.getGcsId()));
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateUavGcsMappingResult.getCode()))){
                    UavInfoDO queryUavInfo = uavDalService.queryUavInfo(Long.valueOf(flightPlanApplyRequest.getUavId()));
                    if (StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus()))) {
                        // TODO: 2022/12/22 IDC ID && 机器ID
                        // 生成applyFlightPlanId
                        Long applyFlightPlanId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
                        String applicantSubject = null;
                        if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType())) && flightPlanApplyRequest.getApplicantOrganization() != null) {
                            applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantOrganization());
                        } else if (ApplicantTypeEnum.PERSON.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType())) && flightPlanApplyRequest.getApplicantPerson() != null) {
                            applicantSubject = JsonUtils.object2Json(flightPlanApplyRequest.getApplicantPerson());
                        }
                        RouteInfoDO routeInfo = routeInfoDalService.queryRouteInfo(Long.valueOf(flightPlanApplyRequest.getRouteId()));
                        flightPlanApplyRequest.setRoutePointCoordinates(JsonUtils.json2List(routeInfo.getRoutePointCoordinates(), PositionParam.class));
                        ApplyFlightPlanLogDO applyFlightPlanLog = applyFlightPlanLogDalService.buildApplyFlightPlanLogDO(applyFlightPlanId, null, Long.valueOf(flightPlanApplyRequest.getGcsId()), Long.valueOf(flightPlanApplyRequest.getUavId()), queryUavInfo.getUavReg(), queryUavInfo.getCpn(), flightPlanApplyRequest.getApplicantType(), applicantSubject,
                                JsonUtils.object2Json(flightPlanApplyRequest.getPilots()), JsonUtils.object2Json(flightPlanApplyRequest.getAirspaceNumbers()), JsonUtils.object2Json(flightPlanApplyRequest.getRoutePointCoordinates()), flightPlanApplyRequest.getTakeoffAirportId(), flightPlanApplyRequest.getLandingAirportId(),
                                flightPlanApplyRequest.getTakeoffSite(), flightPlanApplyRequest.getLandingSite(), flightPlanApplyRequest.getMissionType(), flightPlanApplyRequest.getStartTime(), flightPlanApplyRequest.getEndTime(), flightPlanApplyRequest.getEmergencyProcedure(),
                                flightPlanApplyRequest.getOperationScenarioType(), flightPlanApplyRequest.getIsEmergency(), flightPlanApplyRequest.getIsVlos(), ApplyStatusEnum.PENDING.getCode());
                        int id = applyFlightPlanLogDalService.insertApplyFlightPlanLog(applyFlightPlanLog);
                        if (id > 0) {
                            com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest oacFlightPlanApplyRequest = buildOacFlightPlanApplyRequest(flightPlanApplyRequest, applyFlightPlanId, queryUavInfo.getCpn());
                            com.htfp.service.oac.biz.model.inner.response.FlightPlanApplyResponse oacFlightPlanApplyResponse = preFlightService.flightPlanApply(oacFlightPlanApplyRequest);
                            // TODO: 2022/12/22 校验oacFlightPlanApplyResponse
                            flightPlanApplyResponse = buildFlightPlanApplyResponse(oacFlightPlanApplyResponse);
                            if (flightPlanApplyResponse.getSuccess()) {
                                applyFlightPlanLogDalService.updateApplyFlightPlanLogReplyFlightPlanIdById(applyFlightPlanLog.getId(), oacFlightPlanApplyResponse.getReplyFlightPlanId());
                                flightPlanApplyResponse.setApplyFlightPlanId(applyFlightPlanId.toString());
                            }
                        } else {
                            flightPlanApplyResponse.fail("飞行计划申请失败，插入数据失败");
                        }
                    } else {
                        flightPlanApplyResponse.fail(ErrorCodeEnum.UAV_NOT_REGISTER);
                    }
                } else {
                    flightPlanApplyResponse.fail(validateUavGcsMappingResult.getCode(), validateUavGcsMappingResult.getMessage());
                }
            } else {
                flightPlanApplyResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            log.info("[router]飞行计划申请end，flightPlanApplyRequest={},flightPlanApplyResponse={}", flightPlanApplyRequest, JsonUtils.object2Json(flightPlanApplyResponse));
        } catch (Exception e) {
            log.error("[router]飞行计划申请异常，flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            flightPlanApplyResponse.fail(e.getMessage());
        }
        return flightPlanApplyResponse;
    }

    com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest buildOacFlightPlanApplyRequest(FlightPlanApplyRequest flightPlanApplyRequest, Long applyFlightPlanId, String cpn) {
        com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest oacFlightPlanApplyRequest = new com.htfp.service.oac.biz.model.inner.request.FlightPlanApplyRequest();
        oacFlightPlanApplyRequest.setCpn(cpn);
        oacFlightPlanApplyRequest.setApplyFlightPlanId(applyFlightPlanId.toString());
        oacFlightPlanApplyRequest.setApplicantType(flightPlanApplyRequest.getApplicantType());
        if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(flightPlanApplyRequest.getApplicantType()))) {
            oacFlightPlanApplyRequest.setApplicantOrganizationParam(buildOacPersonParam(flightPlanApplyRequest.getApplicantOrganization()));
        } else {
            oacFlightPlanApplyRequest.setApplicantPersonParam(buildOacPersonParam(flightPlanApplyRequest.getApplicantPerson()));
        }
        oacFlightPlanApplyRequest.setPilots(buildOacPilots(flightPlanApplyRequest.getPilots()));
        oacFlightPlanApplyRequest.setAirspaceNumbers(flightPlanApplyRequest.getAirspaceNumbers());
        oacFlightPlanApplyRequest.setRoutePointCoordinates(buildOacRoutePointCoordinates(flightPlanApplyRequest.getRoutePointCoordinates()));
        oacFlightPlanApplyRequest.setTakeoffAirportId(flightPlanApplyRequest.getTakeoffAirportId());
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

    com.htfp.service.oac.biz.model.inner.request.param.OrganizationParam buildOacPersonParam(OrganizationParam applicantOrganizationParam) {
        if (applicantOrganizationParam != null) {
            com.htfp.service.oac.biz.model.inner.request.param.OrganizationParam oacApplicantOrganizationParam = new com.htfp.service.oac.biz.model.inner.request.param.OrganizationParam();
            oacApplicantOrganizationParam.setOrgType(applicantOrganizationParam.getOrgType());
            oacApplicantOrganizationParam.setOrgName(applicantOrganizationParam.getOrgName());
            oacApplicantOrganizationParam.setSocialCreditCode(applicantOrganizationParam.getSocialCreditCode());
            oacApplicantOrganizationParam.setContactName(applicantOrganizationParam.getContactName());
            oacApplicantOrganizationParam.setContactPhone(applicantOrganizationParam.getContactPhone());
            oacApplicantOrganizationParam.setContactEmail(applicantOrganizationParam.getContactEmail());
            oacApplicantOrganizationParam.setMemo(applicantOrganizationParam.getMemo());
            return oacApplicantOrganizationParam;
        } else {
            return null;
        }
    }

    com.htfp.service.oac.biz.model.inner.request.param.PersonParam buildOacPersonParam(PersonParam applicantPersonParam) {
        if (applicantPersonParam != null) {
            com.htfp.service.oac.biz.model.inner.request.param.PersonParam oacPersonParam = new com.htfp.service.oac.biz.model.inner.request.param.PersonParam();
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
        } else {
            return null;
        }
    }

    List<com.htfp.service.oac.biz.model.inner.request.param.PersonParam> buildOacPilots(List<PersonParam> pilots) {
        if (CollectionUtils.isNotEmpty(pilots)) {
            List<com.htfp.service.oac.biz.model.inner.request.param.PersonParam> oacPilots = new ArrayList<>();
            for (PersonParam pilot : pilots) {
                com.htfp.service.oac.biz.model.inner.request.param.PersonParam oacPilot = buildOacPersonParam(pilot);
                oacPilots.add(oacPilot);
            }
            return oacPilots;
        } else {
            return null;
        }
    }


    com.htfp.service.oac.biz.model.inner.request.param.PositionParam buildOacPositionParam(PositionParam positionParam) {
        com.htfp.service.oac.biz.model.inner.request.param.PositionParam oacPositionParam = new com.htfp.service.oac.biz.model.inner.request.param.PositionParam();
        oacPositionParam.setAlt(positionParam.getAlt());
        oacPositionParam.setLat(positionParam.getLat());
        oacPositionParam.setLng(positionParam.getLng());
        return oacPositionParam;
    }

    List<com.htfp.service.oac.biz.model.inner.request.param.PositionParam> buildOacRoutePointCoordinates(List<PositionParam> routePointCoordinates) {
        if (CollectionUtils.isNotEmpty(routePointCoordinates)) {
            List<com.htfp.service.oac.biz.model.inner.request.param.PositionParam> oacRoutePointCoordinates = new ArrayList<>();
            for (PositionParam routePointCoordinate : routePointCoordinates) {
                com.htfp.service.oac.biz.model.inner.request.param.PositionParam oacRoutePointCoordinate = buildOacPositionParam(routePointCoordinate);
                oacRoutePointCoordinates.add(oacRoutePointCoordinate);
            }
            return oacRoutePointCoordinates;
        } else {
            return null;
        }
    }

    FlightPlanApplyResponse buildFlightPlanApplyResponse(com.htfp.service.oac.biz.model.inner.response.FlightPlanApplyResponse oacFlightPlanApplyResponse) {
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
            if (queryApplyFlightPlanLog != null) {
                if (!ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                    flightPlanQueryResponse.success();
                    FlightPlanQueryResultParam flightPlanQueryResultParam = new FlightPlanQueryResultParam();
                    flightPlanQueryResultParam.setApplyFlightPlanId(flightPlanQueryRequest.getApplyFlightPlanId());
                    flightPlanQueryResultParam.setReplyFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId());
                    flightPlanQueryResultParam.setStatus(queryApplyFlightPlanLog.getStatus());
                    flightPlanQueryResponse.setFlightPlanQueryResultParam(flightPlanQueryResultParam);
                } else {
                    com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest oacFlightPlanQueryRequest = buildOacFlightPlanQueryRequest(queryApplyFlightPlanLog.getReplyFlightPlanId());
                    com.htfp.service.oac.biz.model.inner.response.FlightPlanQueryResponse oacFlightPlanQueryResponse = preFlightService.flightPlanQuery(oacFlightPlanQueryRequest);
                    // TODO: 2022/12/22 校验oacFlightPlanQueryResponse
                    flightPlanQueryResponse = buildOacFlightPlanQueryResponse(oacFlightPlanQueryResponse);
                    if (flightPlanQueryResponse.getSuccess() && !queryApplyFlightPlanLog.getStatus().equals(flightPlanQueryResponse.getFlightPlanQueryResultParam().getStatus())) {
                        applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, flightPlanQueryResponse.getFlightPlanQueryResultParam().getStatus());
                    }
                }
            } else {
                flightPlanQueryResponse.fail(ErrorCodeEnum.WRONG_APPLY_FLIGHT_PLAN_ID);
            }
            log.info("[router]飞行计划查询end，flightPlanQueryRequest={},flightPlanQueryResponse={}", flightPlanQueryRequest, JsonUtils.object2Json(flightPlanQueryResponse));
        } catch (Exception e) {
            log.error("[router]飞行计划查询异常，flightPlanQueryRequest={}", flightPlanQueryRequest, e);
            flightPlanQueryResponse.fail(e.getMessage());
        }
        return flightPlanQueryResponse;
    }

    com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest buildOacFlightPlanQueryRequest(String replyFlightPlanId) {
        com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest oacFlightPlanQueryRequest = new com.htfp.service.oac.biz.model.inner.request.FlightPlanQueryRequest();
        oacFlightPlanQueryRequest.setReplyFlightPlanId(replyFlightPlanId);
        return oacFlightPlanQueryRequest;
    }

    FlightPlanQueryResponse buildOacFlightPlanQueryResponse(com.htfp.service.oac.biz.model.inner.response.FlightPlanQueryResponse oacFliPlanQueryResponse) {
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
        UavVerifyApplyResponse uavVerifyApplyResponse = new UavVerifyApplyResponse();
        uavVerifyApplyResponse.fail();
        try {
            log.info("[router]无人机接入校验申请start，uavVerifyApplyRequest={}", uavVerifyApplyRequest);
            Long uavId = Long.valueOf(uavVerifyApplyRequest.getUavId());
            ApplyFlightPlanLogDO queryFlightPlanApplyLog = queryFlightPlanApplyLog(uavId);
            if (queryFlightPlanApplyLog != null) {
                UavInfoDO queryUavInfo = uavDalService.queryUavInfo(uavId);
                UavOacMappingDO queryUavOacMapping = uavDalService.queryUavOacMapping(uavId);
                // 无人机认证
                if (queryUavOacMapping == null) {
                    uavVerifyApplyResponse = uavVerify(uavVerifyApplyRequest, queryUavInfo);
                } else {
                    if (LinkStatusEnum.OFFLINE.equals(LinkStatusEnum.getFromCode(queryUavOacMapping.getLinkStatus()))) {
                        uavVerifyApplyResponse = uavVerify(uavVerifyApplyRequest, queryUavInfo);
                    } else {
                        uavVerifyApplyResponse.fail(ErrorCodeEnum.UAV_HAS_VERIFIED);
                    }
                }
                // 更新接入状态
                if (uavVerifyApplyResponse.getSuccess()) {
                    if (queryUavOacMapping == null) {
                        UavOacMappingDO uavOacMapping = uavDalService.buildUavOacMappingDO(uavId, queryUavInfo.getCpn(), MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
                        uavDalService.insertUavOacMapping(uavOacMapping);
                    } else {
                        uavDalService.updateUavOacMappingStatusAndLinkStatus(queryUavOacMapping, MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
                    }
                }
            } else {
                uavVerifyApplyResponse.fail(ErrorCodeEnum.FLIGHT_PLAN_NOT_APPROVED);
            }
            log.info("[router]无人机接入校验申请end，uavVerifyApplyRequest={},uavVerifyApplyRequest={}", uavVerifyApplyRequest, JsonUtils.object2Json(uavVerifyApplyResponse));
        } catch (Exception e) {
            log.error("[router]无人机接入校验申请异常，uavVerifyApplyRequest={}", uavVerifyApplyRequest, e);
            uavVerifyApplyResponse.fail(e.getMessage());
        }
        return uavVerifyApplyResponse;
    }

    private ApplyFlightPlanLogDO queryFlightPlanApplyLog(Long uavId) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = null;
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogDalService.queryApplyFlightPlanLogByUavId(uavId);
        for (ApplyFlightPlanLogDO applyFlightPlanLog : applyFlightPlanLogDOList) {
            if (ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(applyFlightPlanLog.getStatus()))) {
                applyFlightPlanLogDO = applyFlightPlanLog;
                break;
            }
        }
        return applyFlightPlanLogDO;
    }

    private UavVerifyApplyResponse uavVerify(UavVerifyApplyRequest uavVerifyApplyRequest, UavInfoDO queryUavInfo) {
        UavVerifyApplyResponse uavVerifyApplyResponse = new UavVerifyApplyResponse();
        uavVerifyApplyResponse.fail();
        UavGcsMappingDO queryUavGcsMapping = uavDalService.queryUavGcsMapping(queryUavInfo.getId());
        // TODO: 2023/1/11 IDC ID && 机器ID
        // 生成applyUavVerifyId
        Long applyUavVerifyId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
        com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest oacUavVerifyApplyRequest = buildOacUavVerifyApplyRequest(uavVerifyApplyRequest, applyUavVerifyId, queryUavInfo.getCpn());
        com.htfp.service.oac.biz.model.inner.response.UavVerifyApplyResponse oacUavVerifyApplyResponse = flyingService.uavVerifyApply(oacUavVerifyApplyRequest);
        // TODO: 2022/12/22 校验oacUavVerifyApplyResponse
        uavVerifyApplyResponse = buildUavVerifyApplyResponse(oacUavVerifyApplyResponse);
        if (uavVerifyApplyResponse.getSuccess()) {
            String replyUavVerifyId = uavVerifyApplyResponse.getUavVerifyResultParam().getReplyUavVerifyId();
            ApplyStatusEnum uavVerifyApplyStatusEnum = uavVerifyApplyResponse.getUavVerifyResultParam().getUavVerifyPass() ? ApplyStatusEnum.APPROVED : ApplyStatusEnum.UNAPPROVED;
            ApplyUavVerifyLogDO applyUavVerifyLog = applyUavVerifyLogDalService.buildApplyUavVerifyLog(applyUavVerifyId, replyUavVerifyId, queryUavGcsMapping.getGcsId().toString(), queryUavInfo.getId().toString(), queryUavInfo.getUavReg(),
                    queryUavInfo.getCpn(), uavVerifyApplyRequest.getLng(), uavVerifyApplyRequest.getLat(), uavVerifyApplyRequest.getAlt(), uavVerifyApplyRequest.getGroundSpeed(), uavVerifyApplyRequest.getRelativeHeight(),
                    uavVerifyApplyRequest.getFlightControlSn(), uavVerifyApplyRequest.getFlightControlVersion(), JsonUtils.object2Json(uavVerifyApplyRequest.getUavDynamicParam()), JsonUtils.object2Json(uavVerifyApplyRequest.getUavStaticParam()), uavVerifyApplyStatusEnum.getCode());
            int id = applyUavVerifyLogDalService.insertApplyUavVerifyLog(applyUavVerifyLog);
            if (id <= 0) {
                uavVerifyApplyResponse.fail("无人机接入校验失败，插入数据失败");
            }
        }
        return uavVerifyApplyResponse;
    }

    com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest buildOacUavVerifyApplyRequest(UavVerifyApplyRequest uavVerifyApplyRequest, Long applyUavVerifyId, String cpn) {
        com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest oacUavVerifyApplyRequest = new com.htfp.service.oac.biz.model.inner.request.UavVerifyApplyRequest();
        if (uavVerifyApplyRequest.getUavDynamicParam() != null) {
            UavDynamicParam oacUavDynamicParam = new UavDynamicParam();
            oacUavDynamicParam.setTrueCourse(uavVerifyApplyRequest.getUavDynamicParam().getTrueCourse());
            oacUavDynamicParam.setPitchAngle(uavVerifyApplyRequest.getUavDynamicParam().getPitchAngle());
            oacUavDynamicParam.setRollAngle(uavVerifyApplyRequest.getUavDynamicParam().getRollAngle());
            oacUavDynamicParam.setVoltage(uavVerifyApplyRequest.getUavDynamicParam().getVoltage());
            oacUavDynamicParam.setFuel(uavVerifyApplyRequest.getUavDynamicParam().getFuel());
            oacUavDynamicParam.setBattery(uavVerifyApplyRequest.getUavDynamicParam().getBattery());
            oacUavDynamicParam.setFlyMode(uavVerifyApplyRequest.getUavDynamicParam().getFlyMode());
            oacUavDynamicParam.setCameraOn(uavVerifyApplyRequest.getUavDynamicParam().getCameraOn());
            oacUavDynamicParam.setEngineOn(uavVerifyApplyRequest.getUavDynamicParam().getEngineOn());
            oacUavDynamicParam.setAirOn(uavVerifyApplyRequest.getUavDynamicParam().getAirOn());
            oacUavDynamicParam.setAbsoluteSpeed(uavVerifyApplyRequest.getUavDynamicParam().getAbsoluteSpeed());
            oacUavDynamicParam.setAmbientTemperature(uavVerifyApplyRequest.getUavDynamicParam().getAmbientTemperature());
            oacUavDynamicParam.setCurrentFaultCode(uavVerifyApplyRequest.getUavDynamicParam().getCurrentFaultCode());
            oacUavVerifyApplyRequest.setUavDynamicParam(oacUavDynamicParam);
        }
        if (uavVerifyApplyRequest.getUavStaticParam() != null) {
            UavStaticParam oacUavStaticParam = new UavStaticParam();
            oacUavStaticParam.setImei(uavVerifyApplyRequest.getUavStaticParam().getImei());
            oacUavStaticParam.setImsi(uavVerifyApplyRequest.getUavStaticParam().getImsi());
            oacUavStaticParam.setPhoneNumber(uavVerifyApplyRequest.getUavStaticParam().getPhoneNumber());
            oacUavStaticParam.setPowerType(uavVerifyApplyRequest.getUavStaticParam().getPowerType());
            oacUavStaticParam.setHorizontalPositionAcc(uavVerifyApplyRequest.getUavStaticParam().getHorizontalPositionAcc());
            oacUavStaticParam.setVerticalPositionAcc(uavVerifyApplyRequest.getUavStaticParam().getVerticalPositionAcc());
            oacUavStaticParam.setTotalPositionAcc(uavVerifyApplyRequest.getUavStaticParam().getTotalPositionAcc());
            oacUavVerifyApplyRequest.setUavStaticParam(oacUavStaticParam);
        }
        oacUavVerifyApplyRequest.setCpn(cpn);
        oacUavVerifyApplyRequest.setApplyUavVerifyId(applyUavVerifyId.toString());
        oacUavVerifyApplyRequest.setLng(uavVerifyApplyRequest.getLng());
        oacUavVerifyApplyRequest.setLat(uavVerifyApplyRequest.getLat());
        oacUavVerifyApplyRequest.setAlt(uavVerifyApplyRequest.getAlt());
        oacUavVerifyApplyRequest.setGroundSpeed(uavVerifyApplyRequest.getGroundSpeed());
        oacUavVerifyApplyRequest.setRelativeHeight(uavVerifyApplyRequest.getRelativeHeight());
        oacUavVerifyApplyRequest.setFlightControlSn(uavVerifyApplyRequest.getFlightControlSn());
        oacUavVerifyApplyRequest.setFlightControlVersion(uavVerifyApplyRequest.getFlightControlVersion());
        return oacUavVerifyApplyRequest;
    }

    UavVerifyApplyResponse buildUavVerifyApplyResponse(com.htfp.service.oac.biz.model.inner.response.UavVerifyApplyResponse oacUavVerifyApplyResponse) {
        UavVerifyApplyResponse uavVerifyApplyResponse = new UavVerifyApplyResponse();
        uavVerifyApplyResponse.setSuccess(oacUavVerifyApplyResponse.getSuccess());
        uavVerifyApplyResponse.setCode(oacUavVerifyApplyResponse.getCode());
        uavVerifyApplyResponse.setMessage(oacUavVerifyApplyResponse.getMessage());
        if (oacUavVerifyApplyResponse.getSuccess() && oacUavVerifyApplyResponse.getUavVerifyResultParam() != null) {
            UavVerifyResultParam uavVerifyResultParam = new UavVerifyResultParam();
            uavVerifyResultParam.setApplyUavVerifyId(oacUavVerifyApplyResponse.getUavVerifyResultParam().getApplyUavVerifyId());
            uavVerifyResultParam.setReplyUavVerifyId(oacUavVerifyApplyResponse.getUavVerifyResultParam().getReplyUavVerifyId());
            uavVerifyResultParam.setUavVerifyPass(oacUavVerifyApplyResponse.getUavVerifyResultParam().getUavVerifyPass());
            uavVerifyApplyResponse.setUavVerifyResultParam(uavVerifyResultParam);
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
        try {
            log.info("[router]放飞申请start，flyApplyRequest={}", flyApplyRequest);
            BaseResponse validateGcsResult = validateGcs(Long.valueOf(flyApplyRequest.getGcsId()));
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                BaseResponse validateUavGcsMappingResult = validateUavGcsMapping(Long.valueOf(flyApplyRequest.getUavId()), Long.valueOf(flyApplyRequest.getGcsId()));
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateUavGcsMappingResult.getCode()))){
                    List<ApplyFlyLogDO> queryApplyFlyLogList = applyFlyLogDalService.queryApplyFlyLogByUavId(Long.valueOf(flyApplyRequest.getUavId()));
                    if (!judgeUavHasApplyFly(queryApplyFlyLogList)) {
                        UavOacMappingDO queryUavOacMapping = uavDalService.queryUavOacMapping(Long.valueOf(flyApplyRequest.getUavId()), MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
                        UavInfoDO queryUavInfo = uavDalService.queryUavInfo(Long.valueOf(flyApplyRequest.getUavId()));
                        UavNavigationMappingDO queryUavNavigationMapping = uavDalService.queryUavNavigationMapping(queryUavInfo.getId());
                        if (queryUavOacMapping != null && queryUavOacMapping.getReportCode().equals(queryUavInfo.getCpn())) {
                            // TODO: 2022/12/22 IDC ID && 机器ID
                            // 生成applyFlyId
                            Long applyFlyId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
                            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(flyApplyRequest.getApplyFlightPlanId()));
                            if (queryApplyFlightPlanLog.getUavId().equals(Long.valueOf(flyApplyRequest.getUavId())) && ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus()))) {
                                ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.buildApplyFlyLogDO(applyFlyId, null, queryApplyFlightPlanLog.getApplyFlightPlanId(), queryApplyFlightPlanLog.getReplyFlightPlanId(), queryUavNavigationMapping.getNavigationId(),
                                        Long.valueOf(flyApplyRequest.getGcsId()), Long.valueOf(flyApplyRequest.getUavId()), queryUavInfo.getUavReg(), queryUavInfo.getCpn(), JsonUtils.object2Json(flyApplyRequest.getAirspaceNumbers()), flyApplyRequest.getOperationScenarioType(),
                                        flyApplyRequest.getFlyLng(), flyApplyRequest.getFlyLat(), flyApplyRequest.getFlyAlt(), flyApplyRequest.getVin(), flyApplyRequest.getPvin(), flyApplyRequest.getFlightControlSn(), flyApplyRequest.getImei(), ApplyStatusEnum.PENDING.getCode());
                                int id = applyFlyLogDalService.insertApplyFlyLog(applyFlyLogDO);
                                if (id > 0) {
                                    com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest oacFlyApplyRequest = buildOacFlyApplyRequest(flyApplyRequest, applyFlyId, queryApplyFlightPlanLog.getApplyFlightPlanId(), queryApplyFlightPlanLog.getReplyFlightPlanId(), queryUavInfo.getCpn());
                                    com.htfp.service.oac.biz.model.inner.response.FlyApplyResponse oacFlyApplyResponse = flyingService.flyApply(oacFlyApplyRequest);
                                    // TODO: 2022/12/22 校验oacFlyApplyResponse
                                    flyApplyResponse = buildFlyApplyResponse(oacFlyApplyResponse);
                                    if (flyApplyResponse.getSuccess()) {
                                        applyFlyLogDalService.updateApplyFlyLogReplyFlyIdById(applyFlyLogDO.getId(), oacFlyApplyResponse.getReplyFlyId());
                                        flyApplyResponse.setApplyFlyId(applyFlyId.toString());
                                    } else {
                                        applyFlyLogDalService.updateApplyFlyLogStatus(applyFlyLogDO, ApplyStatusEnum.UNAPPROVED.getCode());
                                    }
                                } else {
                                    flyApplyResponse.fail("放飞申请失败，插入数据失败");
                                }
                            } else {
                                flyApplyResponse.fail(ErrorCodeEnum.WRONG_APPLY_FLIGHT_PLAN_ID);
                            }
                        } else {
                            flyApplyResponse.fail("放飞申请失败，未接入运行管控或正在执飞");
                        }
                    } else {
                        flyApplyResponse.fail("放飞申请失败,该无人机已发起过放飞申请,无需重新下发");
                    }
                } else {
                    flyApplyResponse.fail(validateUavGcsMappingResult.getCode(), validateUavGcsMappingResult.getMessage());
                }
            } else {
                flyApplyResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }

            log.info("[router]放飞申请end，flyApplyRequest={},flyApplyResponse={}", flyApplyRequest, JsonUtils.object2Json(flyApplyResponse));
        } catch (Exception e) {
            log.error("[router]放飞申请异常，flyApplyRequest={}", flyApplyRequest, e);
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

    com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest buildOacFlyApplyRequest(FlyApplyRequest flyApplyRequest, Long applyFlyId, Long applyFlightPlanId, String replyFlightPlanId, String cpn) {
        com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest oacFlyApplyRequest = new com.htfp.service.oac.biz.model.inner.request.FlyApplyRequest();
        oacFlyApplyRequest.setApplyFlyId(applyFlyId.toString());
        oacFlyApplyRequest.setApplyFlightPlanId(applyFlightPlanId.toString());
        oacFlyApplyRequest.setReplyFlightPlanId(replyFlightPlanId);
        oacFlyApplyRequest.setCpn(cpn);
        oacFlyApplyRequest.setAirspaceNumbers(flyApplyRequest.getAirspaceNumbers());
        oacFlyApplyRequest.setOperationScenarioType(flyApplyRequest.getOperationScenarioType());
        oacFlyApplyRequest.setFlyLng(flyApplyRequest.getFlyLng());
        oacFlyApplyRequest.setFlyLat(flyApplyRequest.getFlyLat());
        oacFlyApplyRequest.setFlyAlt(flyApplyRequest.getFlyAlt());
        oacFlyApplyRequest.setVin(flyApplyRequest.getVin());
        oacFlyApplyRequest.setPvin(flyApplyRequest.getPvin());
        oacFlyApplyRequest.setFlightControlSn(flyApplyRequest.getFlightControlSn());
        oacFlyApplyRequest.setImei(flyApplyRequest.getImei());
        return oacFlyApplyRequest;
    }

    FlyApplyResponse buildFlyApplyResponse(com.htfp.service.oac.biz.model.inner.response.FlyApplyResponse oacFlyApplyResponse) {
        FlyApplyResponse flyApplyResponse = new FlyApplyResponse();
        flyApplyResponse.setSuccess(oacFlyApplyResponse.getSuccess());
        flyApplyResponse.setCode(oacFlyApplyResponse.getCode());
        flyApplyResponse.setMessage(oacFlyApplyResponse.getMessage());
        return flyApplyResponse;
    }

    /**
     * 放飞查询
     *
     * @param flyQueryRequest
     * @return
     */
    @Override
    public FlyQueryResponse flyQuery(FlyQueryRequest flyQueryRequest) {
        FlyQueryResponse flyQueryResponse = new FlyQueryResponse();
        flyQueryResponse.fail();
        try {
            log.info("[router]放飞申请查询start，flyQueryRequest={}", flyQueryRequest);
            ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByApplyFlyId(Long.valueOf(flyQueryRequest.getApplyFlyId()));
            if (queryApplyFlyLog != null) {
                if (!ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus()))) {
                    flyQueryResponse.success();
                    FlyQueryResultParam flyQueryResultParam = new FlyQueryResultParam();
                    flyQueryResultParam.setApplyFlyId(flyQueryRequest.getApplyFlyId());
                    flyQueryResultParam.setReplyFlyId(queryApplyFlyLog.getReplyFlyId());
                    flyQueryResultParam.setStatus(queryApplyFlyLog.getStatus());
                    flyQueryResponse.setFlyQueryResultParam(flyQueryResultParam);
                } else {
                    com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest oacFlyQueryRequest = buildOacFlyQueryRequest(queryApplyFlyLog.getReplyFlyId());
                    com.htfp.service.oac.biz.model.inner.response.FlyQueryResponse oacFlyQueryResponse = flyingService.flyQuery(oacFlyQueryRequest);
                    // TODO: 2022/12/22 校验oacFlyResponse
                    flyQueryResponse = buildOacFlyQueryResponse(oacFlyQueryResponse);
                    // 如果成功更新flyLog表的状态
                    // TODO: 2023/2/19 status不通用
                    if (flyQueryResponse.getSuccess() && !queryApplyFlyLog.getStatus().equals(flyQueryResponse.getFlyQueryResultParam().getStatus())) {
                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, flyQueryResponse.getFlyQueryResultParam().getStatus());
                        // 如果放飞申请通过,更新reportCode
                        if (ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(flyQueryResponse.getFlyQueryResultParam().getStatus()))) {
                            updateUavOacMappingReportCode(queryApplyFlyLog.getUavId(), queryApplyFlyLog.getReplyFlyId());
                        }
                    }
                }
            } else {
                flyQueryResponse.fail(ErrorCodeEnum.WRONG_APPLY_FLY_ID);
            }
            log.info("[router]放飞申请查询end，flyQueryRequest={},flyQueryResponse={}", flyQueryRequest, JsonUtils.object2Json(flyQueryResponse));
        } catch (Exception e) {
            log.error("[router]放飞申请查询异常，flyQueryRequest={}", flyQueryRequest, e);
            flyQueryResponse.fail(e.getMessage());
        }
        return flyQueryResponse;
    }

    com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest buildOacFlyQueryRequest(String replyFlyId) {
        com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest oacFlyQueryRequest = new com.htfp.service.oac.biz.model.inner.request.FlyQueryRequest();
        oacFlyQueryRequest.setReplyFlyId(replyFlyId);
        return oacFlyQueryRequest;
    }

    FlyQueryResponse buildOacFlyQueryResponse(com.htfp.service.oac.biz.model.inner.response.FlyQueryResponse oacFlyQueryResponse) {
        FlyQueryResponse flyQueryResponse = new FlyQueryResponse();
        if (oacFlyQueryResponse.getSuccess() && oacFlyQueryResponse.getFlyQueryResultParam() != null) {
            FlyQueryResultParam flyQueryResultParam = new FlyQueryResultParam();
            flyQueryResultParam.setApplyFlyId(oacFlyQueryResponse.getFlyQueryResultParam().getApplyFlyId());
            flyQueryResultParam.setReplyFlyId(oacFlyQueryResponse.getFlyQueryResultParam().getReplyFlyId());
            flyQueryResultParam.setStatus(oacFlyQueryResponse.getFlyQueryResultParam().getStatus());
            flyQueryResponse.setFlyQueryResultParam(flyQueryResultParam);
        }
        flyQueryResponse.setSuccess(oacFlyQueryResponse.getSuccess());
        flyQueryResponse.setCode(oacFlyQueryResponse.getCode());
        flyQueryResponse.setMessage(oacFlyQueryResponse.getMessage());
        return flyQueryResponse;
    }

    public boolean updateUavOacMappingReportCode(Long uavId, String reportCode) {
        boolean result = false;
        UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(uavId, MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
        if (uavOacMappingDO != null) {
            int id = uavDalService.updateUavOacMappingReportCode(uavOacMappingDO, reportCode);
            if (id > 0) {
                result = true;
            }
        }
        return result;
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
                //(3)进行处理
                Long uavId = Long.valueOf(gcsChangeUavRequest.getUavId());
                Long masterPilotId = Long.valueOf(gcsChangeUavRequest.getMasterPilotId());
                Long deputyPilotId = StringUtils.isNotEmpty(gcsChangeUavRequest.getDeputyPilotId()) ? Long.valueOf(gcsChangeUavRequest.getDeputyPilotId()) : null;
                //(4)校验可控无人机类型
                BaseResponse response = validateUavStatusChangeParam(uavId, gcsInfo, masterPilotId, deputyPilotId);
                if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                    //(5)构造请求体
                    GcsChangeControlUavRequest gcsChangeControlUavRequest = buildGcsChangeControlUavRequest(gcsId, uavId, gcsChangeUavRequest.getNewArrival(), gcsChangeUavRequest.getUavStatus(), masterPilotId, deputyPilotId);
                    //(6)调用指控模块接口，变更在控无人机
                    GcsChangeControlUavResponse gcsChangeControlUavResponse = commandService.gcsChangeUav(gcsChangeControlUavRequest);
                    //(7)插入或更新uav与gcs的mapping关系表
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(gcsChangeControlUavResponse.getCode()))) {
                        insertOrUpdateUavGcsMapping(uavId, gcsId);
                        insertUavOacMapping(uavId);
                        gcsChangeUavResponse.success();
                    } else {
                        gcsChangeUavResponse.fail(gcsChangeControlUavResponse.getCode(), gcsChangeControlUavResponse.getMessage());
                    }
                } else {
                    gcsChangeUavResponse.fail(response.getCode(), response.getMessage());
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
                BaseResponse validateUavGcsMappingResult = validateUavGcsMapping(Long.valueOf(uavStatusChangeRequest.getUavId()), Long.valueOf(uavStatusChangeRequest.getGcsId()));
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateUavGcsMappingResult.getCode()))){
                    Long uavId = Long.valueOf(uavStatusChangeRequest.getUavId());
                    //(2)校验无人机地面站mapping关系
                    BaseResponse response = validateUavStatusChangeParam(uavId, gcsId);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                        if (!judgeUavHasApplyFly(uavId)) {
                            // (3)构造请求体
                            UavChangeStatusRequest uavChangeStatusRequest = buildUavChangeStatusRequest(gcsId, uavId, uavStatusChangeRequest.getUavStatus());
                            // (4)调用指控模块接口，变更无人机状态
                            UavChangeStatusResponse uavChangeStatusResponse = uavService.uavChangeStatus(uavChangeStatusRequest);
                            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(uavChangeStatusResponse.getCode()))) {
                                uavStatusChangeResponse.success();
                                // (5)如果为终态，更新uav和gcs的mapping关系表的状态，更新，，，，
                                if (UavStatusEnum.SHUT_DOWN.equals(UavStatusEnum.getFromCode(uavStatusChangeRequest.getUavStatus()))) {
                                    UavGcsMappingDO uavGcsMapping = uavDalService.queryValidUavGcsMapping(uavId, gcsId);
                                    UavOacMappingDO uavOacMapping = uavDalService.queryUavOacMapping(uavId);
                                    uavDalService.updateUavGcsMappingStatus(uavGcsMapping, MappingStatusEnum.INVALID);
                                    uavDalService.updateUavOacMappingReportCodeAndStatus(uavOacMapping, null, MappingStatusEnum.INVALID);
                                }
                            } else {
                                uavStatusChangeResponse.fail(uavChangeStatusResponse.getCode(), uavChangeStatusResponse.getMessage());
                            }
                        } else {
                            uavStatusChangeResponse.fail(ErrorCodeEnum.EXECUTING_FLIGHT_PLAN);
                        }
                    } else {
                        uavStatusChangeResponse.fail(response.getCode(), response.getMessage());
                    }
                } else {
                    uavStatusChangeResponse.fail(validateUavGcsMappingResult.getCode(), validateUavGcsMappingResult.getMessage());
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

    private boolean judgeUavHasApplyFly(Long uavId) {
        List<ApplyFlyLogDO> queryApplyFlyLogList = applyFlyLogDalService.queryApplyFlyLogByUavId(uavId);
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
            // 校验请求体
            BaseResponse validateCommandUavParamResult = validateCommandUavParam(gcsId, Long.valueOf(gcsControlUavRequest.getUavId()), Long.valueOf(gcsControlUavRequest.getPilotId()));
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateCommandUavParamResult.getCode()))) {
                // 构造请求体
                SaveUavControlLogRequest saveUavControlLogRequest = buildSaveUavControlLogRequest(gcsId, Long.valueOf(gcsControlUavRequest.getUavId()), Long.valueOf(gcsControlUavRequest.getPilotId()), gcsControlUavRequest.getCommandCode(), gcsControlUavRequest.getCommandResult());
                // 调用指控模块接口，更新指控记录日志
                SaveUavControlLogResponse saveUavControlLogResponse = uavService.saveUavControlLog(saveUavControlLogRequest);
                if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(saveUavControlLogResponse.getCode()))) {
                    gcsControlUavResponse.success();
                } else {
                    gcsControlUavResponse.fail(saveUavControlLogResponse.getCode(), saveUavControlLogResponse.getMessage());
                }
            } else {
                gcsControlUavResponse.fail(validateCommandUavParamResult.getCode(), validateCommandUavParamResult.getMessage());
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
        FinishFlightPlanResponse finishFlightPlanResponse = new FinishFlightPlanResponse();
        finishFlightPlanResponse.fail();
        try {
            log.info("[router]飞行计划结束start，finishFlightPlanRequest={}", finishFlightPlanRequest);
            BaseResponse validateGcsResult = validateGcs(Long.valueOf(finishFlightPlanRequest.getGcsId()));
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                BaseResponse validateUavGcsMappingResult = validateUavGcsMapping(Long.valueOf(finishFlightPlanRequest.getUavId()), Long.valueOf(finishFlightPlanRequest.getGcsId()));
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateUavGcsMappingResult.getCode()))){
                    UavInfoDO queryUavInfo = uavDalService.queryUavInfo(Long.valueOf(finishFlightPlanRequest.getUavId()));
                    UavOacMappingDO queryUavOacMapping = uavDalService.queryUavOacMapping(Long.valueOf(finishFlightPlanRequest.getUavId()), MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
                    ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(finishFlightPlanRequest.getApplyFlightPlanId()));
                    if (queryUavInfo != null && queryUavOacMapping != null && queryApplyFlightPlanLog != null) {
                        // TODO: 2023/2/23 待更改为uav与ApplyFly的mapping关系 并提到外面
                        ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByReplyFlyId(queryUavOacMapping.getReportCode());
                        if (queryApplyFlyLog != null) {
                            ApplyStatusEnum flightPlanStatus = ApplyStatusEnum.getFromCode(queryApplyFlightPlanLog.getStatus());
                            ApplyStatusEnum flyStatus = ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus());
                            if (ApplyStatusEnum.APPROVED.equals(flightPlanStatus) && !ApplyStatusEnum.PENDING.equals(flyStatus)) {
                                com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest oacFinishFlightPlanRequest = buildOacFinishFlightPlanRequest(finishFlightPlanRequest, queryApplyFlightPlanLog.getReplyFlightPlanId(), queryUavInfo.getCpn());
                                com.htfp.service.oac.biz.model.inner.response.FinishFlightPlanResponse oacFinishFlightPlanResponse = flyingService.finishFlightPlan(oacFinishFlightPlanRequest);
                                finishFlightPlanResponse = buildFinishFlightPlanResponse(oacFinishFlightPlanResponse);
                                if (finishFlightPlanResponse.getSuccess()) {
                                    // 更新reportCode和连接状态
                                    uavDalService.updateUavOacMappingReportCodeAndLinkStatus(queryUavOacMapping, queryUavInfo.getCpn(), LinkStatusEnum.OFFLINE);
                                    // 更新飞行计划状态
                                    applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.COMPLETE.getCode());
                                    // 飞行计划处于通过状态，则结束飞行计划
                                    if (ApplyStatusEnum.APPROVED.equals(flyStatus)) {
                                        applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.COMPLETE.getCode());
                                    }
                                    finishFlightPlanResponse.success();
                                }
                            } else {
                                finishFlightPlanResponse.fail("正在执行飞行计划未进行放飞申请，无需结束");
                            }
                        } else {
                            finishFlightPlanResponse.fail(ErrorCodeEnum.WRONG_APPLY_FLIGHT_PLAN_ID);
                        }
                    } else {
                        finishFlightPlanResponse.fail("未查询到相应数据");
                    }
                } else {
                    finishFlightPlanResponse.fail(validateUavGcsMappingResult.getCode(), validateUavGcsMappingResult.getMessage());
                }
            } else {
                finishFlightPlanResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            log.info("[router]飞行计划结束end，finishFlightPlanRequest={}, finishFlightPlanResponse={}", finishFlightPlanRequest, JsonUtils.object2Json(finishFlightPlanResponse));
        } catch (Exception e) {
            log.error("[router]飞行计划结束异常，finishFlightPlanRequest={}", finishFlightPlanRequest, e);
            finishFlightPlanResponse.fail(e.getMessage());
        }
        return finishFlightPlanResponse;
    }

    com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest buildOacFinishFlightPlanRequest(FinishFlightPlanRequest finishFlightPlanRequest, String replyFlightPlanId, String cpn) {
        com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest oacFinishFlightPlanRequest = new com.htfp.service.oac.biz.model.inner.request.FinishFlightPlanRequest();
        oacFinishFlightPlanRequest.setCpn(cpn);
        oacFinishFlightPlanRequest.setReplyFlightPlanId(replyFlightPlanId);
        oacFinishFlightPlanRequest.setTotalRoutePoint(finishFlightPlanRequest.getTotalRoutePoint());
        oacFinishFlightPlanRequest.setCurrentRoutePointIndex(finishFlightPlanRequest.getCurrentRoutePointIndex());
        oacFinishFlightPlanRequest.setIsOver(finishFlightPlanRequest.getIsOver());
        oacFinishFlightPlanRequest.setMessage(finishFlightPlanRequest.getMessage());
        return oacFinishFlightPlanRequest;
    }

    FinishFlightPlanResponse buildFinishFlightPlanResponse(com.htfp.service.oac.biz.model.inner.response.FinishFlightPlanResponse oacFinishFlightPlanResponse) {
        FinishFlightPlanResponse finishFlightPlanResponse = new FinishFlightPlanResponse();
        finishFlightPlanResponse.setSuccess(oacFinishFlightPlanResponse.getSuccess());
        finishFlightPlanResponse.setCode(oacFinishFlightPlanResponse.getCode());
        finishFlightPlanResponse.setMessage(oacFinishFlightPlanResponse.getMessage());
        return finishFlightPlanResponse;
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

    private BaseResponse validateUavGcsMapping(Long uavId, Long gcsId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        UavGcsMappingDO queryUavGcsMapping = uavDalService.queryUavGcsMapping(uavId);
        if(MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryUavGcsMapping.getStatus())) &&
                queryUavGcsMapping.getGcsId().equals(gcsId)){
            baseResponse.success();
        } else {
            baseResponse.fail(ErrorCodeEnum.LACK_OF_MAPPING);
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

    public void insertUavOacMapping(Long uavId) {
        UavOacMappingDO uavOacMapping = uavDalService.queryUavOacMapping(uavId);
        if (uavOacMapping == null) {
            uavOacMapping = uavDalService.buildUavOacMappingDO(uavId, null, MappingStatusEnum.INVALID, LinkStatusEnum.OFFLINE);
            uavDalService.insertUavOacMapping(uavOacMapping);
        }
    }

    /**
     * 管制信息查询
     *
     * @param atcQueryRequest
     * @return
     */
    @Override
    public ATCQueryResponse atcQuery(ATCQueryRequest atcQueryRequest) {
        ATCQueryResponse atcQueryResponse = new ATCQueryResponse();
        atcQueryResponse.fail();
        List<ATCQueryResultParam> atcQueryResultParamList = new ArrayList<>();
        try {
            // log.info("[router]管制信息查询start，atcQueryRequest={}", atcQueryRequest);
            final Long gcsId = Long.valueOf(atcQueryRequest.getGcsId());
            //(1)校验地面站信息以及是否上线
            BaseResponse validateGcsResult = validateGcs(gcsId);
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                Long uavId = Long.valueOf(atcQueryRequest.getUavId());
                //(2)校验无人机地面站mapping关系
                BaseResponse response = validateUavStatusChangeParam(uavId, gcsId);
                if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                    List<ATCIssuedLogDO> atcIssuedLogDOList = atcIssuedLogDalService.queryATCIssuedLogByApplyFlightPlanIdAndAtcDelivered(Long.valueOf(atcQueryRequest.getApplyFlightPlanId()), DeliverTypeEnum.DELIVERING.getCode());
                    if (CollectionUtils.isNotEmpty(atcIssuedLogDOList)) {
                        ATCIssuedLogDO atcIssuedLog = atcIssuedLogDOList.get(0);
                        atcQueryResponse.setAtcQueryResultParam(buildATCQueryResultParam(atcIssuedLog));
                        atcIssuedLogDalService.updateATCIssuedLogDelivered(atcIssuedLog, DeliverTypeEnum.DELIVERED.getCode());
                    }
                    atcQueryResponse.success();
                } else {
                    atcQueryResponse.fail(response.getCode(), response.getMessage());
                }
            } else {
                atcQueryResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            // log.info("[router]管制信息查询end，atcQueryRequest={}，atcQueryResponse={}", atcQueryRequest, JsonUtils.object2Json(atcQueryResponse));
        } catch (Exception e) {
            log.error("[router]管制信息查询异常，atcQueryRequest={}", atcQueryRequest, e);
            atcQueryResponse.fail(e.getMessage());
        }
        return atcQueryResponse;
    }

    private ATCQueryResultParam buildATCQueryResultParam(ATCIssuedLogDO atcIssuedLog) {
        ATCQueryResultParam atcQueryResultParam = new ATCQueryResultParam();
        atcQueryResultParam.setAtcId(atcIssuedLog.getId().toString());
        atcQueryResultParam.setApplyFlightPlanId(atcIssuedLog.getApplyFlightPlanId().toString());
        atcQueryResultParam.setApplyFlyId(atcIssuedLog.getApplyFlyId().toString());
        atcQueryResultParam.setUavId(atcIssuedLog.getUavId());
        atcQueryResultParam.setAtcType(atcIssuedLog.getAtcType());
        atcQueryResultParam.setAtcEffectTime(atcIssuedLog.getAtcEffectTime());
        atcQueryResultParam.setAtcLimitPeriod(atcIssuedLog.getAtcLimitPeriod());
        atcQueryResultParam.setAtcOperator(atcIssuedLog.getAtcOperator());
        atcQueryResultParam.setAtcSpecificPosition(JsonUtils.json2Object(atcIssuedLog.getAtcSpecificPosition(), com.htfp.service.cac.router.biz.model.http.response.param.PositionParam.class));
        return atcQueryResultParam;
    }

    /**
     * 告警信息查询
     *
     * @param alarmQueryRequest
     * @return
     */
    @Override
    public AlarmQueryResponse alarmQuery(AlarmQueryRequest alarmQueryRequest) {
        AlarmQueryResponse alarmQueryResponse = new AlarmQueryResponse();
        alarmQueryResponse.fail();
        List<AlarmQueryResultParam> alarmQueryResultParamList = new ArrayList<>();
        try {
            log.info("[router]告警信息查询start，alarmQueryRequest={}", alarmQueryRequest);
            final Long gcsId = Long.valueOf(alarmQueryRequest.getGcsId());
            //(1)校验地面站信息以及是否上线
            BaseResponse validateGcsResult = validateGcs(gcsId);
            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(validateGcsResult.getCode()))) {
                Long uavId = Long.valueOf(alarmQueryRequest.getUavId());
                //(2)校验无人机地面站mapping关系
                BaseResponse response = validateUavStatusChangeParam(uavId, gcsId);
                if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                    List<AlarmIssuedLogDO> alarmIssuedLogList = alarmIssuedLogDalService.queryAlarmIssuedLogByApplyFlightPlanId(Long.valueOf(alarmQueryRequest.getApplyFlightPlanId()));
                    for (AlarmIssuedLogDO alarmIssuedLog : alarmIssuedLogList) {
                        if (alarmQueryRequest.getApplyFlightPlanId().equals(alarmIssuedLog.getApplyFlightPlanId().toString()) &&
                                alarmQueryRequest.getApplyFlyId().equals(alarmIssuedLog.getApplyFlyId().toString())) {
                            AlarmQueryResultParam alarmQueryResultParam = buildAlarmQueryResultParam(alarmIssuedLog);
                            alarmQueryResultParamList.add(alarmQueryResultParam);
                        }
                    }
                    alarmQueryResponse.setAlarmQueryResultParamList(alarmQueryResultParamList);
                    alarmQueryResponse.success();
                } else {
                    alarmQueryResponse.fail(response.getCode(), response.getMessage());
                }
            } else {
                alarmQueryResponse.fail(validateGcsResult.getCode(), validateGcsResult.getMessage());
            }
            log.info("[router]告警信息查询end，alarmQueryRequest={}，alarmQueryResponse={}", alarmQueryRequest, JsonUtils.object2Json(alarmQueryResponse));
        } catch (Exception e) {
            log.error("[router]告警信息查询异常，alarmQueryRequest={}", alarmQueryRequest, e);
            alarmQueryResponse.fail(e.getMessage());
        }
        return alarmQueryResponse;
    }

    private AlarmQueryResultParam buildAlarmQueryResultParam(AlarmIssuedLogDO alarmIssuedLogDO) {
        AlarmQueryResultParam alarmQueryResultParam = new AlarmQueryResultParam();
        alarmQueryResultParam.setAlarmId(alarmIssuedLogDO.getId().toString());
        alarmQueryResultParam.setApplyFlightPlanId(alarmIssuedLogDO.getApplyFlightPlanId().toString());
        alarmQueryResultParam.setApplyFlyId(alarmIssuedLogDO.getApplyFlyId().toString());
        alarmQueryResultParam.setUavId(alarmIssuedLogDO.getUavId());
        alarmQueryResultParam.setAlarmLevel(alarmIssuedLogDO.getAlarmLevel());
        alarmQueryResultParam.setAlarmContent(alarmIssuedLogDO.getAlarmContent());
        alarmQueryResultParam.setAlarmEffectTime(alarmIssuedLogDO.getAlarmEffectTime());
        alarmQueryResultParam.setAlarmOperator(alarmIssuedLogDO.getAlarmOperator());
        return alarmQueryResultParam;
    }

}
