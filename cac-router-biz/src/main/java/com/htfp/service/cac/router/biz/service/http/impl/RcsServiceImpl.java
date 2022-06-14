package com.htfp.service.cac.router.biz.service.http.impl;

import com.google.common.collect.Maps;
import com.htfp.service.cac.command.biz.model.response.SaveUavControlLogResponse;
import com.htfp.service.cac.command.biz.model.resquest.SaveUavControlLogRequest;
import com.htfp.service.cac.command.biz.service.IUavService;
import com.htfp.service.cac.common.constant.HttpContentTypeConstant;
import com.htfp.service.cac.common.constant.HttpUriConstant;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.http.CustomHttpConfig;
import com.htfp.service.cac.common.utils.http.HttpAsyncClient;
import com.htfp.service.cac.common.utils.http.HttpContentWrapper;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.http.request.CommandUavParam;
import com.htfp.service.cac.router.biz.model.http.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.RouterControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.CommandUavResultParam;
import com.htfp.service.cac.router.biz.model.http.response.RcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.RouterControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.service.http.IRcsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.Objects;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:15
 * @Description 远程地面站服务类
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
            log.info("远程地面站注册start，signRequest={}", signInRequest);
            final Long gcsId = Long.getLong(signInRequest.getGcsId());
            // 校验地面站
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
            log.info("远程地面站注册end，signRequest={}，signInResponse={}", signInRequest, signInResponse);
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
            log.info("远程地面站注销start，signOutRequest={}", signOutRequest);
            final Long gcsId = Long.getLong(signOutRequest.getGcsId());
            //校验地面站
            boolean validateGcsIdResult = gcsDalService.validateGcsId(gcsId);
            if (validateGcsIdResult) {
                // 地面站下线
                GcsIpMappingDO gcsIpMappingDO = gcsDalService.queryGcsIpMapping(gcsId);
                if (gcsIpMappingDO != null) {
                    //(1)校验gcs与Ip的mapping关系
                    if (!gcsIpMappingDO.getGcsIp().equals(signOutRequest.getGcsIp())) {
                        signOutResponse.setMessage("远程地面站注销时IP与注册时IP不一致，不可下线");
                    } else {
                        //(2)校验通过后更新gcs与Ip的mapping关系
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
            log.info("远程地面站注销end，signOutRequest={},signOutResponse={}", signOutRequest, signOutResponse);
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
            log.info("远程地面站指控指令执行start，gcsControlUavRequest={}", rcsControlUavRequest);
            List<CommandUavResultParam> commandUavResultParamList = new ArrayList<>();
            Long rcsId = Long.valueOf(rcsControlUavRequest.getGcsId());
            // 遍历uavList进行处理
            for (CommandUavParam commandUavParam : rcsControlUavRequest.getUavList()) {
                Long uavId = Long.valueOf(commandUavParam.getUavId());
                Long pilotId = Long.valueOf(commandUavParam.getPilotId());
                //(1)校验可控无人机类型
                BaseResponse response = validateControlType(uavId, rcsId, pilotId);
                if(ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(response.getCode()))){
                    //(2)查询无人机与地面站Mapping关系
                    UavGcsMappingDO uavGcsMapping = uavDalService.queryUavGcsMapping(uavId);
                    Long gcsId = uavGcsMapping.getGcsId();
                    //(3)向地面站发起指控命令HTTP请求
                    RouterControlUavResponse routerControlUavResponse = routerControlUav(gcsId, rcsId, commandUavParam);
                    if(routerControlUavResponse != null){
                        //(4)根据响应体判断结果
                        Boolean controlResult = getControlResult(routerControlUavResponse);
                        if(controlResult != null){
                            //(5)构造响应结果
                            CommandUavResultParam commandUavResultParam = new CommandUavResultParam();
                            commandUavResultParam.setUavId(commandUavParam.getUavId());
                            commandUavResultParam.setCommandResult(controlResult);
                            //(6)构造请求体
                            SaveUavControlLogRequest saveUavControlLogRequest = buildSaveUavControlLogRequest(gcsId, rcsId, uavId, pilotId, commandUavParam.getCommandCode(), controlResult);
                            //(7)调用指控模块接口，更新指控记录日志
                            SaveUavControlLogResponse saveUavControlLogResponse = uavService.saveUavControlLog(saveUavControlLogRequest);
                            if (ErrorCodeEnum.SUCCESS.equals(ErrorCodeEnum.getFromCode(saveUavControlLogResponse.getCode()))) {
                                commandUavResultParamList.add(commandUavResultParam);
                                rcsControlUavResponse.success();
                            } else {
                                rcsControlUavResponse.fail(saveUavControlLogResponse.getCode(), saveUavControlLogResponse.getMessage());
                                break;
                            }
                        } else {
                            rcsControlUavResponse.setMessage("请求地面站未获得结果");
                        }
                    } else {
                        rcsControlUavResponse.setMessage("请求地面站异常");
                    }
                } else {
                    rcsControlUavResponse.fail(response.getCode(), response.getMessage());
                    break;
                }
            }
            rcsControlUavResponse.setCommandUavResultParamList(commandUavResultParamList);
            log.info("远程地面站指控指令执行end，gcsControlUavRequest={}，rcsControlUavResponse={}", rcsControlUavRequest, rcsControlUavResponse);
        } catch (Exception e){
            log.error("远程地面站指控指令执行异常，gcsControlUavRequest={}", rcsControlUavRequest, e);
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

    private SaveUavControlLogRequest buildSaveUavControlLogRequest(Long gcsId, Long rcsId, Long uavId, Long pilotId, Integer commandCode, Boolean commandResult){
        SaveUavControlLogRequest saveUavControlLogRequest = new SaveUavControlLogRequest();
        saveUavControlLogRequest.setGcsId(gcsId);
        saveUavControlLogRequest.setRcsId(rcsId);
        saveUavControlLogRequest.setUavId(uavId);
        saveUavControlLogRequest.setPilotId(pilotId);
        saveUavControlLogRequest.setCommandCode(commandCode);
        saveUavControlLogRequest.setCommandResult(commandResult);
        return saveUavControlLogRequest;
    }

    private RouterControlUavResponse routerControlUav(Long gcsId, Long rcsId, CommandUavParam commandUavParam){
        RouterControlUavResponse routerControlUavResponse = null;
        try{
            String url = getUrl(gcsId);
            if(StringUtils.isNotEmpty(url)){
                RouterControlUavRequest routerControlUavRequest = buildRouterControlUavRequest(rcsId, commandUavParam);
                HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                        .contentObject(JsonUtils.object2Json(routerControlUavRequest, false))
                        .gcsId(gcsId)
                        .contentType(HttpContentTypeConstant.JSON_TYPE)
                        .create();
                Map<String, String> httpHeader = builderRequestHeader();
                CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
                String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
                routerControlUavResponse = decodeHttpResponse(httpResponse);
            }
        } catch (Exception e){
            log.error("请求地面站发生异常", e);
        }
        return routerControlUavResponse;
    }

    private String getUrl(Long gcsId){
        GcsIpMappingDO gcsIpMapping = gcsDalService.queryGcsIpMapping(gcsId);
        if(gcsIpMapping==null||MappingStatusEnum.INVALID.equals(MappingStatusEnum.getFromCode(gcsIpMapping.getStatus()))||gcsIpMapping.getGcsIp()==null){
            return null;
        } else {
            return gcsIpMapping.getGcsIp() + "/" + HttpUriConstant.ROUTER_CONTROL_UAV;
        }
    }
    private RouterControlUavRequest buildRouterControlUavRequest(Long rcsId, CommandUavParam commandUavParam){
        RouterControlUavRequest routerControlUavRequest = new RouterControlUavRequest();
        routerControlUavRequest.setGcsId(rcsId.toString());
        routerControlUavRequest.setUavList(Arrays.asList(commandUavParam));
        return routerControlUavRequest;
    }

    public Map<String, String> builderRequestHeader() {
        Map<String, String> map = Maps.newHashMap();
        map.put("Accept","application/json");
        map.put("Content-Type", "application/json;charset=UTF-8");
        return map;
    }

    private CustomHttpConfig buildCustomHttpConfig(){
        return new CustomHttpConfig();
    }

    private RouterControlUavResponse decodeHttpResponse(String response) throws Exception {
        if (response==null ||StringUtils.isBlank(response)) {
            return null;
        } else {
            RouterControlUavResponse routerControlUavResponse = JsonUtils.json2ObjectThrowException(response, RouterControlUavResponse.class);
            routerControlUavResponse.setResultStr(response);
            return routerControlUavResponse;
        }
    }
    
    private Boolean getControlResult(RouterControlUavResponse routerControlUavResponse){
        Boolean result = null;
        if(routerControlUavResponse.getSuccess()){
            CommandUavResultParam commandUavResultParam = JsonUtils.json2Object(routerControlUavResponse.getData(), CommandUavResultParam.class);
            if(commandUavResultParam!=null){
                return commandUavResultParam.getCommandResult();
            }
        }
        return result;
    }
}
