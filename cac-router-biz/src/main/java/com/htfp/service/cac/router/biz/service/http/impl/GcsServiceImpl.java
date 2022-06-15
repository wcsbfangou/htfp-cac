package com.htfp.service.cac.router.biz.service.http.impl;

import com.htfp.service.cac.command.biz.model.response.GcsChangeControlUavResponse;
import com.htfp.service.cac.command.biz.model.response.SaveUavControlLogResponse;
import com.htfp.service.cac.command.biz.model.response.UavChangeStatusResponse;
import com.htfp.service.cac.command.biz.model.resquest.GcsChangeControlUavRequest;
import com.htfp.service.cac.command.biz.model.resquest.SaveUavControlLogRequest;
import com.htfp.service.cac.command.biz.model.resquest.UavChangeStatusRequest;
import com.htfp.service.cac.command.biz.service.ICommandService;
import com.htfp.service.cac.command.biz.service.IUavService;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.http.request.ChangeUavParam;
import com.htfp.service.cac.router.biz.model.http.request.ChangeUavStatusParam;
import com.htfp.service.cac.router.biz.model.http.request.CommandUavParam;
import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.service.http.IGcsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.Objects;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:11
 * @Description 地面站服务类
 */
@Slf4j
@Service
public class GcsServiceImpl implements IGcsService {

    @Resource
    UavDalService uavDalService;

    @Resource
    GcsDalService gcsDalService;

    @Resource
    PilotDalService pilotDalService;

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
            log.info("地面站注册start，signRequest={}", signInRequest);
            final Long gcsId = Long.valueOf(signInRequest.getGcsId());
            //校验地面站
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                // 更新或插入地面站与Ip的mapping关系表
                GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsId);
                if (gcsIpMappingDO != null) {
                    gcsDalService.updateGcsIpMappingIp(gcsIpMappingDO, signInRequest.getGcsIp());
                } else {
                    gcsIpMappingDO = gcsDalService.buildGcsIpMappingDO(gcsId, signInRequest.getGcsIp());
                    gcsDalService.insertGcsIpMapping(gcsIpMappingDO);
                }
                signInResponse.success();
            } else {
                signInResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("地面站注册end，signInRequest={}，signInResponse={}", signInRequest, signInResponse);
        } catch (Exception e) {
            log.error("地面站注册异常，signRequest={}", signInRequest, e);
            signInResponse.fail(e.getMessage());
        }
        return signInResponse;
    }

    /**
     * 地面站注销
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse gcsSignOut(SignOutRequest signOutRequest) {
        SignOutResponse signOutResponse = new SignOutResponse();
        signOutResponse.fail();
        try {
            log.info("地面站注销start，signOutRequest={}", signOutRequest);
            final Long gcsId = Long.valueOf(signOutRequest.getGcsId());
            // 校验地面站
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                // 判断地面站是否可下线
                List<UavGcsMappingDO> uavGcsMappingDOList = uavDalService.queryUavGcsMapping(gcsId, MappingStatusEnum.VALID);
                if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
                    signOutResponse.setMessage("地面站绑定的无人机还未完成航行，不可下线");
                } else {
                    // 地面站下线
                    GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsId);
                    if (gcsIpMappingDO != null) {
                        //(1)校验gcs与Ip的mapping关系
                        if (!gcsIpMappingDO.getGcsIp().equals(signOutRequest.getGcsIp())) {
                            signOutResponse.setMessage("地面站注销时IP与注册时IP不一致，不可下线");
                        } else {
                            //(2)校验通过后更新gcs与Ip的mapping关系
                            gcsIpMappingDO.setStatus(MappingStatusEnum.INVALID.getCode());
                            gcsIpMappingDO.setGmtModify(new Date());
                            gcsDalService.updateGcsIpMapping(gcsIpMappingDO);
                            signOutResponse.success();
                        }
                    } else {
                        signOutResponse.setMessage("地面站未注册过，不可下线");
                    }
                }
            } else {
                signOutResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("地面站注销end，signOutRequest={},signOutResponse={}", signOutRequest, signOutResponse);
        } catch (Exception e) {
            log.error("地面站注销异常，signOutRequest={}", signOutRequest, e);
            signOutResponse.fail(e.getMessage());
        }
        return signOutResponse;
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
            log.info("地面站在控无人机变更start，gcsChangeUavRequest={}", gcsChangeUavRequest);
            final Long gcsId = Long.valueOf(gcsChangeUavRequest.getGcsId());
            //(1)查询gcsInfo进行校验
            GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(gcsId);
            if (gcsInfo != null) {
                //(2)校验通过后，遍历uavList进行处理
                for (ChangeUavParam changeUavParam : gcsChangeUavRequest.getUavList()) {
                    Long uavId = Long.valueOf(changeUavParam.getUavId());
                    Long masterPilotId = Long.valueOf(changeUavParam.getMasterPilotId());
                    Long deputyPilotId = StringUtils.isNotEmpty(changeUavParam.getDeputyPilotId()) ? Long.valueOf(changeUavParam.getDeputyPilotId()) : null;
                    //(3)校验可控无人机类型
                    BaseResponse response = validateChangeUavParam(uavId, gcsInfo, masterPilotId, deputyPilotId);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                        //(4)构造请求体
                        GcsChangeControlUavRequest gcsChangeControlUavRequest = buildGcsChangeControlUavRequest(gcsId, uavId, changeUavParam.getNewArrival(), changeUavParam.getUavStatus(), masterPilotId, deputyPilotId);
                        // TODO: 2022/6/1 (5)(6)事务
                        //(5)调用指控模块接口，变更在控无人机
                        GcsChangeControlUavResponse gcsChangeControlUavResponse = commandService.gcsChangeUav(gcsChangeControlUavRequest);
                        //(6)插入或更新uav与gcs的mapping关系表
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
                gcsChangeUavResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("地面站在控无人机变更start，gcsChangeUavRequest={}, gcsChangeUavResponse={}", gcsChangeUavRequest, gcsChangeUavResponse);
        } catch (Exception e) {
            log.error("地面站在控无人机变更异常，gcsChangeUavRequest={}", gcsChangeUavRequest, e);
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
            log.info("无人机状态变更start，uavStatusChangeRequest={}", uavStatusChangeRequest);
            final Long gcsId = Long.valueOf(uavStatusChangeRequest.getGcsId());
            //(1)校验地面站信息
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                List<ChangeUavStatusParam> uavList = uavStatusChangeRequest.getUavList();
                //(2)校验通过后，遍历UavList处理
                for (ChangeUavStatusParam changeUavStatusParam : uavList) {
                    Long uavId = Long.valueOf(changeUavStatusParam.getUavId());
                    BaseResponse response = validateChangeUavParam(uavId, gcsId);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))) {
                        // (3)构造请求体
                        UavChangeStatusRequest uavChangeStatusRequest = buildUavChangeStatusRequest(gcsId, uavId, changeUavStatusParam.getUavStatus());
                        // TODO: 2022/6/1 (4)(5)事务
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
                uavStatusChangeResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
            log.info("无人机状态变更end，uavStatusChangeRequest={}，uavStatusChangeResponse={}", uavStatusChangeRequest, uavStatusChangeResponse);
        } catch (Exception e) {
            log.error("无人机状态变更异常，uavStatusChangeRequest={}", uavStatusChangeRequest, e);
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
            log.error("地面站指控指令执行start，gcsControlUavRequest={}", gcsControlUavRequest);
            Long gcsId = Long.valueOf(gcsControlUavRequest.getGcsId());
            for (CommandUavParam commandUavParam : gcsControlUavRequest.getUavList()) {
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
            }
            log.error("地面站指控指令执行end，gcsControlUavRequest={}，gcsControlUavResponse={}", gcsControlUavRequest, gcsControlUavResponse);
        } catch (Exception e) {
            log.error("地面站指控指令执行异常，gcsControlUavRequest={}", gcsControlUavRequest, e);
            gcsControlUavResponse.fail(e.getMessage());
        }
        return gcsControlUavResponse;
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

    private BaseResponse validateChangeUavParam(Long uavId, GcsInfoDO gcsInfo, Long masterPilotId, Long deputyPilotId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        UavInfoDO uavInfo = uavDalService.queryUavInfo(uavId);
        if (uavInfo != null) {
            if (Objects.equals(uavInfo.getTypeId(), gcsInfo.getControllableUavType())) {
                PilotInfoDO masterPilotInfo = pilotDalService.queryPilotInfo(masterPilotId);
                PilotInfoDO deputyPilotInfo = deputyPilotId != null ? pilotDalService.queryPilotInfo(deputyPilotId) : masterPilotInfo;
                if (masterPilotInfo != null && deputyPilotInfo != null) {
                    if (Objects.equals(uavInfo.getTypeId(), masterPilotInfo.getControllableUavType()) || Objects.equals(uavInfo.getTypeId(), deputyPilotInfo.getControllableUavType())) {
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

    private BaseResponse validateChangeUavParam(Long uavId, Long gcsId) {
        BaseResponse baseResponse = new BaseResponse();
        baseResponse.fail();
        boolean validateUavIdResult = uavDalService.validateUavId(uavId);
        if (validateUavIdResult) {
            UavGcsMappingDO uavGcsMapping = uavDalService.queryValidUavGcsMapping(uavId, gcsId);
            if (uavGcsMapping != null) {
                baseResponse.success();
            } else {
                baseResponse.fail(ErrorCodeEnum.LACK_OF_MAPPING);
            }
        } else {
            baseResponse.fail(ErrorCodeEnum.WRONG_UAV_ID);
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
