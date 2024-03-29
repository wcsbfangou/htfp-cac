package com.htfp.service.cac.command.biz.service.impl;

import com.htfp.service.cac.command.biz.model.response.SaveUavControlLogResponse;
import com.htfp.service.cac.command.biz.model.response.UavChangeStatusResponse;
import com.htfp.service.cac.command.biz.model.resquest.SaveUavControlLogRequest;
import com.htfp.service.cac.command.biz.model.resquest.UavChangeStatusRequest;
import com.htfp.service.cac.command.biz.service.IUavService;
import com.htfp.service.cac.common.enums.CommandResultEnum;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.NavigationStatusEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.dao.model.log.CommandAndControlLogDO;
import com.htfp.service.cac.dao.model.log.NavigationLogDO;
import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import com.htfp.service.cac.dao.service.NavigationDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 21:05
 * @Description 无人机服务
 */
@Slf4j
@Service
public class UavServiceImpl implements IUavService {

    @Resource
    private UavDalService uavDalService;

    @Resource
    private NavigationDalService navigationDalService;

    /**
     * 无人机状态变更
     *
     * @param uavChangeStatusRequest
     * @return
     */
    @Override
    public UavChangeStatusResponse uavChangeStatus(UavChangeStatusRequest uavChangeStatusRequest) {
        UavChangeStatusResponse uavChangeStatusResponse = new UavChangeStatusResponse();
        uavChangeStatusResponse.fail();
        try {
            log.info("[command]无人机状态更新流程start，uavChangeStatusRequest={}", uavChangeStatusRequest);
            // 根据Uav与Navigation的mapping关系校验
            UavNavigationMappingDO uavNavigationMappingInfo = uavDalService.queryUavNavigationMapping(uavChangeStatusRequest.getUavId());
            if (uavNavigationMappingInfo != null) {
                // TODO: 2022/6/1 事务
                // UavStatusLog中插入一条数据
                insertUavStatusLog(uavChangeStatusRequest.getUavId(), uavNavigationMappingInfo.getNavigationId(), uavChangeStatusRequest.getUavStatus());
                // 如果无人机状态为终态:(1)更新Uav与Navigation的mapping表status为失效状态;(2)更新NavigationLog的Status为完结状态
                if (UavStatusEnum.SHUT_DOWN.equals(UavStatusEnum.getFromCode(uavChangeStatusRequest.getUavStatus()))) {
                    uavDalService.updateUavNavigationMappingStatus(uavNavigationMappingInfo, MappingStatusEnum.INVALID);
                    updateNavigationLogStatus(uavNavigationMappingInfo.getNavigationId(), NavigationStatusEnum.FINISH);
                }
                uavChangeStatusResponse.success();
            } else {
                uavChangeStatusResponse.fail(ErrorCodeEnum.LACK_OF_MAPPING.getCode(), "uav与navigation的" + ErrorCodeEnum.LACK_OF_MAPPING.getDesc());
            }
            log.info("[command]无人机状态更新流程end，uavChangeStatusRequest={}，uavChangeStatusResponse={}", uavChangeStatusRequest, JsonUtils.object2Json(uavChangeStatusResponse));
        } catch (Exception e) {
            log.error("[command]无人机状态更新流程异常，uavChangeStatusRequest={}", uavChangeStatusRequest, e);
            uavChangeStatusResponse.fail(e.getMessage());
        }
        return uavChangeStatusResponse;
    }

    /**
     * 存储无人机指控指令
     *
     * @param saveUavControlLogRequest
     * @return
     */
    @Override
    public SaveUavControlLogResponse saveUavControlLog(SaveUavControlLogRequest saveUavControlLogRequest) {
        SaveUavControlLogResponse saveUavControlLogResponse = new SaveUavControlLogResponse();
        saveUavControlLogResponse.fail();
        try {
            log.info("[command]存储无人机指控指令流程start，saveUavControlLogRequest={}", saveUavControlLogRequest);
            // 根据Uav与Navigation的mapping关系校验
            UavNavigationMappingDO uavNavigationMappingInfo = uavDalService.queryUavNavigationMapping(saveUavControlLogRequest.getUavId());
            if (uavNavigationMappingInfo != null && MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(uavNavigationMappingInfo.getStatus()))) {
                // 插入指挥控制日志
                insertCommandAndControlLog(saveUavControlLogRequest.getUavId(), uavNavigationMappingInfo.getNavigationId(), saveUavControlLogRequest.getGcsId(), saveUavControlLogRequest.getRcsId(), saveUavControlLogRequest.getPilotId(), saveUavControlLogRequest.getCommandCode(), CommandResultEnum.getFromResult(saveUavControlLogRequest.getCommandResult()));
                saveUavControlLogResponse.success();
            } else {
                saveUavControlLogResponse.fail(ErrorCodeEnum.LACK_OF_MAPPING.getCode(), "uav与navigation的" + ErrorCodeEnum.LACK_OF_MAPPING.getDesc());
            }
            log.info("[command]存储无人机指控指令流程end，saveUavControlLogRequest={}, saveUavControlLogResponse={}", saveUavControlLogRequest, JsonUtils.object2Json(saveUavControlLogResponse));
        } catch (Exception e) {
            log.error("[command]存储无人机指控指令流程异常，saveUavControlLogRequest={}", saveUavControlLogRequest, e);
            saveUavControlLogResponse.fail(e.getMessage());
        }
        return saveUavControlLogResponse;
    }

    private void insertUavStatusLog(Long uavId, Long navigationId, Integer status) {
        UavStatusLogDO uavStatusLog = uavDalService.buildUavStatusLogDO(uavId, navigationId, status);
        uavDalService.insertUavStatusLog(uavStatusLog);
    }

    void updateNavigationLogStatus(Long navigationId, NavigationStatusEnum navigationStatusEnum) {
        List<NavigationLogDO> navigationLogDOList = navigationDalService.queryNavigationLog(navigationId);
        for (NavigationLogDO navigationLog : navigationLogDOList) {
            navigationDalService.updateNavigationLogStatus(navigationLog, navigationStatusEnum);
        }
    }

    private void insertCommandAndControlLog(Long uavId, Long navigationId, Long gcsId, Long rcsId, Long pilotId, Integer commandCode, CommandResultEnum commandResultEnum) {
        CommandAndControlLogDO commandAndControlLogDO = uavDalService.buildCommandAndControlLogDO(uavId, navigationId, gcsId, rcsId, pilotId, commandCode, commandResultEnum);
        uavDalService.insertCommandAndControlLog(commandAndControlLogDO);
    }
}
