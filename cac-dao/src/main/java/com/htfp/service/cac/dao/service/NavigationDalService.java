package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.NavigationStatusEnums;
import com.htfp.service.cac.dao.mapper.log.NavigationLogMapper;
import com.htfp.service.cac.dao.model.log.NavigationLogDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 22:13
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

    public void updateNavigationLogStatus(NavigationLogDO navigationLog, NavigationStatusEnums navigationStatusEnums) {
        navigationLog.setNavigationStatus(navigationStatusEnums.getCode());
        navigationLog.setGmtModify(new Date());
        updateNavigationLog(navigationLog);
    }

}
