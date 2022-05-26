package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.MappingStatusEnums;
import com.htfp.service.cac.dao.mapper.entity.UavInfoMapper;
import com.htfp.service.cac.dao.mapper.log.UavStatusLogMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavGcsMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavNavigationMappingMapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 16:39
 */
@Slf4j
@Service
public class UavDalService {

    @Resource
    UavGcsMappingMapper uavGcsMappingMapper;

    @Resource
    UavNavigationMappingMapper uavNavigationMappingMapper;

    @Resource
    UavInfoMapper uavInfoMapper;

    @Resource
    UavStatusLogMapper uavStatusLogMapper;

    public List<UavGcsMappingDO> queryUavGcsMapping(Long gcsId, MappingStatusEnums status){
        return uavGcsMappingMapper.selectByGcsIdAndStatus(gcsId, status.getCode());
    }

    public List<UavGcsMappingDO> queryUavGcsMapping(Long uavId, Long gcsId){
        return uavGcsMappingMapper.selectByUavIdAndGcsIp(uavId, gcsId);
    }

    public List<UavGcsMappingDO> queryValidUavGcsMapping(Long uavId, Long gcsId){
        return uavGcsMappingMapper.selectByUavIdAndGcsIdAndStatus(uavId, gcsId, MappingStatusEnums.VALID.getCode());
    }

    public boolean validateUavGcsMapping(Long uavId, Long gcsId){
        List<UavGcsMappingDO> uavGcsMappingDOList = queryValidUavGcsMapping(uavId, gcsId);
        return CollectionUtils.isNotEmpty(uavGcsMappingDOList);
    }

    public int updateUavGcsMapping(UavGcsMappingDO uavGcsMappingDO){
        return uavGcsMappingMapper.updateByUavGcsMapping(uavGcsMappingDO);
    }
    public void updateUavGcsMappingStatus(UavGcsMappingDO uavGcsMappingDO, MappingStatusEnums mappingStatusEnums){
        uavGcsMappingDO.setStatus(mappingStatusEnums.getCode());
        uavGcsMappingDO.setGmtModify(new Date());
        updateUavGcsMapping(uavGcsMappingDO);
    }

    public boolean validateUavId(Long uavId){
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavId(uavId);
        return CollectionUtils.isNotEmpty(uavInfoDOList);
    }
    public List<UavNavigationMappingInfoDO> queryUavNavigationMapping(Long uavId){
        return uavNavigationMappingMapper.selectByUavId(uavId);
    }

    public int updateUavNavigationMapping(UavNavigationMappingInfoDO uavNavigationMappingInfoDO){
        return uavNavigationMappingMapper.updateByUavNavigationMapping(uavNavigationMappingInfoDO);
    }

    public void updateUavNavigationMappingStatus(UavNavigationMappingInfoDO uavNavigationMappingInfo, MappingStatusEnums mappingStatusEnums){
        uavNavigationMappingInfo.setStatus(mappingStatusEnums.getCode());
        uavNavigationMappingInfo.setGmtModify(new Date());
        updateUavNavigationMapping(uavNavigationMappingInfo);
    }

    public int insertUavStatusLog (UavStatusLogDO uavStatusLogDO){
        return uavStatusLogMapper.insertUavStatusLogDO(uavStatusLogDO);
    }

    public UavStatusLogDO buildUavStatusLogDO(Long uavId, Long navigationId, Integer uavStatus) {
        UavStatusLogDO uavStatusLogDO  = new UavStatusLogDO();
        uavStatusLogDO.setUavId(uavId);
        uavStatusLogDO.setNavigationId(navigationId);
        uavStatusLogDO.setUavStatus(uavStatus);
        uavStatusLogDO.setGmtCreate(new Date());
        uavStatusLogDO.setGmtModify(new Date());
        return uavStatusLogDO;
    }


}
