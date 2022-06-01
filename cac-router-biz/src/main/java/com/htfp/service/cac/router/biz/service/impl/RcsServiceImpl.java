package com.htfp.service.cac.router.biz.service.impl;

import com.htfp.service.cac.command.biz.model.response.SaveUavControlLogResponse;
import com.htfp.service.cac.command.biz.model.resquest.SaveUavControlLogRequest;
import com.htfp.service.cac.command.biz.service.IUavService;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.request.CommandUavParam;
import com.htfp.service.cac.router.biz.model.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.response.BaseResponse;
import com.htfp.service.cac.router.biz.model.response.RcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;
import com.htfp.service.cac.router.biz.service.IRcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.Objects;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:15
 */
@Slf4j
@Service
public class RcsServiceImpl implements IRcsService {

    @Resource
    UavDalService uavDalService;

    @Resource
    GcsDalService gcsDalService;

    @Resource
    PilotDalService pilotDalService;

    @Resource(name = "uavServiceImpl")
    IUavService uavService;

    /**
     * 远程地面站注册
     *
     * @param signInRequest
     * @return
     */
    @Override
    public SignInResponse rcsSignIn(SignInRequest signInRequest) {
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.fail();
        try {
            final Long gcsId = Long.getLong(signInRequest.getGcsId());
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsId);
                if (gcsIpMappingDO != null) {
                    gcsDalService.updateGcsIpMappingIp(gcsIpMappingDO, signInRequest.getGcsIp());
                } else {
                    gcsIpMappingDO = gcsDalService.buildNewGcsIpMappingDO(gcsId, signInRequest.getGcsIp());
                    gcsDalService.insertGcsIpMapping(gcsIpMappingDO);
                }
                signInResponse.success();
            } else {
                signInResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
        } catch (Exception e) {
            log.error("远程地面站注册异常，signRequest={}", signInRequest, e);
            signInResponse.fail(e.getMessage());
        }
        return signInResponse;
    }

    /**
     * 远程地面站注销
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse rcsSignOut(SignOutRequest signOutRequest) {
        SignOutResponse signOutResponse = new SignOutResponse();
        signOutResponse.fail();
        try {
            final Long gcsId = Long.getLong(signOutRequest.getGcsId());
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsId);
                if (gcsIpMappingDO != null) {
                    if (!gcsIpMappingDO.getGcsIp().equals(signOutRequest.getGcsIp())) {
                        signOutResponse.setMessage("远程地面站注销时IP与注册时IP不一致，不可下线");
                    } else {
                        gcsIpMappingDO.setStatus(MappingStatusEnum.INVALID.getCode());
                        gcsIpMappingDO.setGmtModify(new Date());
                        gcsDalService.updateGcsIpMapping(gcsIpMappingDO);
                        signOutResponse.success();
                    }
                } else {
                    signOutResponse.setMessage("远程地面站未注册过，不可下线");
                }
            } else {
                signOutResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
        } catch (Exception e) {
            log.error("远程地面站注销异常，signOutRequest={}", signOutRequest, e);
            signOutResponse.fail(e.getMessage());
        }
        return signOutResponse;
    }

    /**
     * 远程地面站指控指令执行
     *
     * @param rcsControlUavRequest
     * @return
     */
    @Override
    public RcsControlUavResponse rcsControlUav(RcsControlUavRequest rcsControlUavRequest) {
        RcsControlUavResponse rcsControlUavResponse = new RcsControlUavResponse();
        rcsControlUavResponse.fail();
        try{
            Long rcsId = Long.valueOf(rcsControlUavRequest.getGcsId());
            for (CommandUavParam commandUavParam : rcsControlUavRequest.getUavList()) {
                Long uavId = Long.valueOf(commandUavParam.getUavId());
                Long pilotId = Long.valueOf(commandUavParam.getPilotId());
                BaseResponse response = validateControlType(uavId, rcsId, pilotId);
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))){
                    UavGcsMappingDO uavGcsMapping = uavDalService.queryUavGcsMapping(uavId);
                    Long gcsId = uavGcsMapping.getGcsId();
                    GcsIpMappingDO gcsIpMapping = gcsDalService.queryGcsIpMapping(gcsId);
                    // TODO: 2022/6/1 http 请求
                    SaveUavControlLogRequest saveUavControlLogRequest = buildSaveUavControlLogRequest(gcsId, rcsId, uavId, pilotId, commandUavParam.getCommandCode(), );
                    SaveUavControlLogResponse saveUavControlLogResponse = uavService.saveUavControlLog(saveUavControlLogRequest);
                    if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(saveUavControlLogResponse.getCode()))) {
                        rcsControlUavResponse.success();
                    } else {
                        rcsControlUavResponse.fail(saveUavControlLogResponse.getCode(), saveUavControlLogResponse.getMessage());
                        break;
                    }
                } else {
                    rcsControlUavResponse.fail(response.getCode(), response.getMessage());
                    break;
                }
            }
        } catch (Exception e){
            log.error("远程地面站指控指令执行异常，gcsControlUavRequest={}", rcsControlUavResponse, e);
            rcsControlUavResponse.fail(e.getMessage());
        }
        return rcsControlUavResponse;
    }

    private BaseResponse validateControlType(Long uavId, Long rcsId, Long pilotId){
        BaseResponse response= new BaseResponse();
        response.fail();
        UavInfoDO uavInfo = uavDalService.queryUavInfo(uavId);
        GcsInfoDO rcsInfo = gcsDalService.queryGcsInfo(rcsId);
        PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(pilotId);
        if(Objects.equals(uavInfo.getTypeId(), rcsInfo.getControllableUavType())){
            if(Objects.equals(uavInfo.getTypeId(), pilotInfo.getControllableUavType())){
                response.success();
            }else{
                response.fail(ErrorCodeEnum.PILOT_MISMATCH_UAV);
            }
        } else {
            response.fail(ErrorCodeEnum.GCS_MISMATCH_UAV);
        }
        return response;
    }

    SaveUavControlLogRequest buildSaveUavControlLogRequest(Long gcsId, Long rcsId, Long uavId, Long pilotId, Integer commandCode, Boolean commandResult){
        SaveUavControlLogRequest saveUavControlLogRequest = new SaveUavControlLogRequest();
        saveUavControlLogRequest.setGcsId(gcsId);
        saveUavControlLogRequest.setRcsId(rcsId);
        saveUavControlLogRequest.setUavId(uavId);
        saveUavControlLogRequest.setPilotId(pilotId);
        saveUavControlLogRequest.setCommandCode(commandCode);
        saveUavControlLogRequest.setCommandResult(commandResult);
        return saveUavControlLogRequest;
    }
}
