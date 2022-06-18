package com.htfp.service.cac.command.biz.service.impl;

import com.htfp.service.cac.command.biz.model.response.GcsChangeControlUavResponse;
import com.htfp.service.cac.command.biz.model.resquest.GcsChangeControlUavRequest;
import com.htfp.service.cac.command.biz.service.ICommandService;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.NavigationStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.SnowflakeIdUtils;
import com.htfp.service.cac.dao.model.log.NavigationLogDO;
import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import com.htfp.service.cac.dao.service.NavigationDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-05-31 23:08
 * @Description 指控服务
 */
@Slf4j
@Service
public class CommandServiceImpl implements ICommandService {

    @Resource
    UavDalService uavDalService;

    @Resource
    NavigationDalService navigationDalService;

    /**
     * 地面站在控无人机变更
     *
     * @param gcsChangeControlUavRequest
     * @return
     */
    @Override
    public GcsChangeControlUavResponse gcsChangeUav(GcsChangeControlUavRequest gcsChangeControlUavRequest) {
        GcsChangeControlUavResponse gcsChangeControlUavResponse = new GcsChangeControlUavResponse();
        gcsChangeControlUavResponse.fail();
        try {
            log.info("[command]地面站在控无人机变更start，gcsChangeControlUavRequest={}", gcsChangeControlUavRequest);
            Long navigationId;
            // TODO: 2022/6/1 事务
            // 判断是否为新启动的无人机
            if (gcsChangeControlUavRequest.getNewArrival()) {
                // TODO: 2022/6/1 IDC ID && 机器ID
                // 生成navigationId
                navigationId = SnowflakeIdUtils.generateSnowFlakeId(1, 1);
                // 插入一条新的uavStatusLog
                insertUavStatusLog(gcsChangeControlUavRequest.getUavId(), navigationId, gcsChangeControlUavRequest.getUavStatus());
                // 插入或更新Uav与Navigation的Mapping关系
                insertOrUpdateUavNavigationMapping(gcsChangeControlUavRequest.getUavId(), navigationId);
            } else {
                //校验无人机是否结束航行或未开始航行
                UavNavigationMappingDO uavNavigationMapping = uavDalService.queryUavNavigationMapping(gcsChangeControlUavRequest.getUavId());
                if (uavNavigationMapping == null || MappingStatusEnum.INVALID.equals(MappingStatusEnum.getFromCode(uavNavigationMapping.getStatus()))) {
                    gcsChangeControlUavResponse.fail("无人机已结束航行或未开始航行");
                    return gcsChangeControlUavResponse;
                }
                navigationId = uavNavigationMapping.getNavigationId();
            }
            // NavigationLog表插入一条log
            insertNavigationLog(navigationId, gcsChangeControlUavRequest.getUavId(), gcsChangeControlUavRequest.getGcsId(), gcsChangeControlUavRequest.getMasterPilotId(), gcsChangeControlUavRequest.getDeputyPilotId(), NavigationStatusEnum.PROGRESSING);
            gcsChangeControlUavResponse.success();
            log.info("[command]地面站在控无人机变更end，gcsChangeControlUavRequest={}，gcsChangeControlUavResponse={}", gcsChangeControlUavRequest, JsonUtils.object2Json(gcsChangeControlUavResponse));
        } catch (Exception e) {
            log.error("[command]地面站在控无人机变更异常，gcsChangeControlUavRequest={}", gcsChangeControlUavRequest, e);
            gcsChangeControlUavResponse.fail(e.getMessage());
        }
        return gcsChangeControlUavResponse;
    }

    public void insertOrUpdateUavNavigationMapping(Long uavId, Long navigationId) {
        UavNavigationMappingDO uavNavigationMapping = uavDalService.queryUavNavigationMapping(uavId);
        if (uavNavigationMapping != null) {
            uavDalService.updateUavNavigationMappingNavigationId(uavNavigationMapping, navigationId, MappingStatusEnum.VALID);
        } else {
            uavNavigationMapping = uavDalService.buildUavNavigationMappingDO(uavId, navigationId, MappingStatusEnum.VALID);
            uavDalService.insertUavNavigationMapping(uavNavigationMapping);
        }
    }

    private void insertUavStatusLog(Long uavId, Long navigationId, Integer status) {
        UavStatusLogDO uavStatusLog = uavDalService.buildUavStatusLogDO(uavId, navigationId, status);
        uavDalService.insertUavStatusLog(uavStatusLog);
    }

    private void insertNavigationLog(Long navigationId, Long uavId, Long gcsId, Long masterPilotId, Long deputyPilotId, NavigationStatusEnum navigationStatusEnum) {
        NavigationLogDO navigationLogDO = navigationDalService.buildNavigationLogDO(navigationId, uavId, gcsId, masterPilotId, deputyPilotId, navigationStatusEnum);
        navigationDalService.insertNavigationLog(navigationLogDO);
    }
}
