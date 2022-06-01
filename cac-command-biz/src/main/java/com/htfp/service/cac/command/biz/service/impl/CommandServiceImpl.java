package com.htfp.service.cac.command.biz.service.impl;

import com.htfp.service.cac.command.biz.model.response.GcsChangeControlUavResponse;
import com.htfp.service.cac.command.biz.model.resquest.GcsChangeControlUavRequest;
import com.htfp.service.cac.command.biz.service.ICommandService;
import com.htfp.service.cac.common.enums.MappingStatusEnums;
import com.htfp.service.cac.common.enums.NavigationStatusEnums;
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
        try{
            Long navigationId;
            // TODO: 2022/6/1 事务
            if(gcsChangeControlUavRequest.getNewArrival()){
                // TODO: 2022/6/1 IDC ID && 机器ID
                navigationId = SnowflakeIdUtils.generateSnowFlakeId(1,1);
                insertUavStatusLog(gcsChangeControlUavRequest.getUavId(), navigationId, gcsChangeControlUavRequest.getUavStatus());
                insertOrUpdateUavNavigationMapping(gcsChangeControlUavRequest.getUavId(), navigationId);
            }else{
                UavNavigationMappingDO uavNavigationMapping = uavDalService.queryUavNavigationMapping(gcsChangeControlUavRequest.getUavId());
                 if(uavNavigationMapping==null||MappingStatusEnums.INVALID.equals(MappingStatusEnums.getFromCode(uavNavigationMapping.getStatus()))) {
                     gcsChangeControlUavResponse.fail("无人机已结束航行或未开始航行");
                     return gcsChangeControlUavResponse;
                 }
                 navigationId = uavNavigationMapping.getNavigationId();
            }
            insertNavigationLog(navigationId, gcsChangeControlUavRequest.getUavId(), gcsChangeControlUavRequest.getGcsId(), gcsChangeControlUavRequest.getMasterPilotId(), gcsChangeControlUavRequest.getDeputyPilotId(), NavigationStatusEnums.PROGRESSING);
        } catch (Exception e){
            log.error("地面站在控无人机变更异常，gcsChangeControlUavRequest={}", gcsChangeControlUavRequest, e);
            gcsChangeControlUavResponse.fail(e.getMessage());
        }
        return gcsChangeControlUavResponse;
    }

    public void insertOrUpdateUavNavigationMapping(Long uavId, Long navigationId){
        UavNavigationMappingDO uavNavigationMapping = uavDalService.queryUavNavigationMapping(uavId);
        if(uavNavigationMapping!=null) {
            uavDalService.updateUavNavigationMappingNavigationId(uavNavigationMapping, navigationId, MappingStatusEnums.VALID);
        } else {
            uavNavigationMapping = uavDalService.buildUavNavigationMappingDO(uavId, navigationId, MappingStatusEnums.VALID);
            uavDalService.insertUavNavigationMapping(uavNavigationMapping);
        }
    }

    private void insertUavStatusLog(Long uavId, Long navigationId, Integer status){
        UavStatusLogDO uavStatusLog = uavDalService.buildNewUavStatusLogDO(uavId, navigationId, status);
        uavDalService.insertUavStatusLog(uavStatusLog);
    }

    private void insertNavigationLog(Long navigationId, Long uavId, Long gcsId, Long masterPilotId, Long deputyPilotId, NavigationStatusEnums navigationStatusEnums){
        NavigationLogDO navigationLogDO = navigationDalService.buildNavigationLogDO(navigationId, uavId, gcsId, masterPilotId, deputyPilotId, navigationStatusEnums);
        navigationDalService.insertNavigationLog(navigationLogDO);
    }
}
