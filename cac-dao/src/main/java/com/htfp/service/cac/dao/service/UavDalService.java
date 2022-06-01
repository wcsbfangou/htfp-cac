package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.MappingStatusEnums;
import com.htfp.service.cac.dao.mapper.entity.UavInfoMapper;
import com.htfp.service.cac.dao.mapper.log.UavStatusLogMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavGcsMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavNavigationMappingMapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
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

    public UavGcsMappingDO queryUavGcsMapping(Long uavId, Long gcsId){
        List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByUavIdAndGcsIp(uavId, gcsId);
        if(CollectionUtils.isNotEmpty(uavGcsMappingDOList)){
            return uavGcsMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public UavGcsMappingDO queryUavGcsMapping(Long uavId){
        List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(uavGcsMappingDOList)){
            return uavGcsMappingDOList.get(0);
        } else {
            return null;
        }
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

    public int insertUavGcsMapping (UavGcsMappingDO uavGcsMappingDO){
        return uavGcsMappingMapper.insertUavGcsMapping(uavGcsMappingDO);
    }

    public void updateUavGcsMappingGcsId(UavGcsMappingDO uavGcsMappingDO, Long gcsId, MappingStatusEnums mappingStatusEnums){
        uavGcsMappingDO.setStatus(mappingStatusEnums.getCode());
        uavGcsMappingDO.setGcsId(gcsId);
        uavGcsMappingDO.setGmtModify(new Date());
        updateUavGcsMapping(uavGcsMappingDO);
    }

    public boolean validateUavId(Long uavId){
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavId(uavId);
        return CollectionUtils.isNotEmpty(uavInfoDOList);
    }

    public UavInfoDO queryUavInfo(Long uavId){
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(uavInfoDOList)){
            return uavInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public UavNavigationMappingDO queryUavNavigationMapping(Long uavId){
        List<UavNavigationMappingDO> uavNavigationMappingDOList = uavNavigationMappingMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(uavNavigationMappingDOList)){
            return uavNavigationMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public int insertUavNavigationMapping (UavNavigationMappingDO uavNavigationMappingDO){
        return uavNavigationMappingMapper.insertUavNavigationMapping(uavNavigationMappingDO);
    }

    public void updateUavNavigationMappingNavigationId(UavNavigationMappingDO uavNavigationMappingDO, Long navigationId, MappingStatusEnums mappingStatusEnums){
        uavNavigationMappingDO.setStatus(mappingStatusEnums.getCode());
        uavNavigationMappingDO.setNavigationId(navigationId);
        uavNavigationMappingDO.setGmtModify(new Date());
        updateUavNavigationMapping(uavNavigationMappingDO);
    }

    public int updateUavNavigationMapping(UavNavigationMappingDO uavNavigationMappingDO){
        return uavNavigationMappingMapper.updateByUavNavigationMapping(uavNavigationMappingDO);
    }

    public void updateUavNavigationMappingStatus(UavNavigationMappingDO uavNavigationMappingInfo, MappingStatusEnums mappingStatusEnums){
        uavNavigationMappingInfo.setStatus(mappingStatusEnums.getCode());
        uavNavigationMappingInfo.setGmtModify(new Date());
        updateUavNavigationMapping(uavNavigationMappingInfo);
    }

    public int insertUavStatusLog (UavStatusLogDO uavStatusLogDO){
        return uavStatusLogMapper.insertUavStatusLogDO(uavStatusLogDO);
    }

    public UavStatusLogDO buildNewUavStatusLogDO(Long uavId, Long navigationId, Integer uavStatus) {
        UavStatusLogDO uavStatusLogDO  = new UavStatusLogDO();
        uavStatusLogDO.setUavId(uavId);
        uavStatusLogDO.setNavigationId(navigationId);
        uavStatusLogDO.setUavStatus(uavStatus);
        uavStatusLogDO.setGmtCreate(new Date());
        uavStatusLogDO.setGmtModify(new Date());
        return uavStatusLogDO;
    }

    public UavGcsMappingDO buildNewUavGcsMappingDO(Long uavId, Long gcsId, MappingStatusEnums status) {
        UavGcsMappingDO uavGcsMappingDO  = new UavGcsMappingDO();
        uavGcsMappingDO.setUavId(uavId);
        uavGcsMappingDO.setGcsId(gcsId);
        uavGcsMappingDO.setStatus(status.getCode());
        uavGcsMappingDO.setGmtCreate(new Date());
        uavGcsMappingDO.setGmtModify(new Date());
        return uavGcsMappingDO;
    }


    public UavNavigationMappingDO buildUavNavigationMappingDO(Long uavId, Long navigationId, MappingStatusEnums status) {
        UavNavigationMappingDO uavNavigationMappingDO = new UavNavigationMappingDO();
        uavNavigationMappingDO.setUavId(uavId);
        uavNavigationMappingDO.setNavigationId(navigationId);
        uavNavigationMappingDO.setStatus(status.getCode());
        uavNavigationMappingDO.setGmtCreate(new Date());
        uavNavigationMappingDO.setGmtModify(new Date());
        return uavNavigationMappingDO;
    }
}
