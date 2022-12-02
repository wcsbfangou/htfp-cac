package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.NavigationStatusEnum;
import com.htfp.service.cac.dao.mapper.log.NavigationLogMapper;
import com.htfp.service.cac.dao.model.log.NavigationLogDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 22:13
 * @Description 航行的dao服务类
 */

@Slf4j
@Service
public class NavigationDalService {

    @Resource
    NavigationLogMapper navigationLogMapper;

    public List<NavigationLogDO> queryNavigationLog(Long navigationId) {
        return navigationLogMapper.selectByNavigationId(navigationId);
    }

    public int updateNavigationLog(NavigationLogDO navigationLogDO) {
        return navigationLogMapper.updateByNavigationLog(navigationLogDO);
    }

    public int insertNavigationLog(NavigationLogDO navigationLogDO) {
        return navigationLogMapper.insertNavigationLog(navigationLogDO);
    }

    public int deleteNavigationLogById(Long id) {
        return navigationLogMapper.deleteById(id);
    }

    public void updateNavigationLogStatus(NavigationLogDO navigationLog, NavigationStatusEnum navigationStatusEnum) {
        navigationLog.setNavigationStatus(navigationStatusEnum.getCode());
        navigationLog.setGmtModify(new Date());
        updateNavigationLog(navigationLog);
    }

    public NavigationLogDO buildNavigationLogDO(Long navigationId, Long uavId, Long gcsId, Long masterPilotId, Long deputyPilotId, NavigationStatusEnum navigationStatusEnum){
        NavigationLogDO navigationLogDO = new NavigationLogDO();
        navigationLogDO.setNavigationId(navigationId);
        navigationLogDO.setGcsId(gcsId);
        navigationLogDO.setUavId(uavId);
        navigationLogDO.setMasterPilotId(masterPilotId);
        navigationLogDO.setDeputyPilotId(deputyPilotId);
        navigationLogDO.setNavigationStatus(navigationStatusEnum.getCode());
        navigationLogDO.setGmtCreate(new Date());
        navigationLogDO.setGmtModify(new Date());
        return navigationLogDO;
    }
}
