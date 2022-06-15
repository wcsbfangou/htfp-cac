package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.CommandResultEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.mapper.entity.UavInfoMapper;
import com.htfp.service.cac.dao.mapper.log.CommandAndControlLogMapper;
import com.htfp.service.cac.dao.mapper.log.UavStatusLogMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavGcsMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavNavigationMappingMapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.CommandAndControlLogDO;
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
 * @Description 无人机dao服务类
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

    @Resource
    CommandAndControlLogMapper commandAndControlLogMapper;

    public List<UavGcsMappingDO> queryUavGcsMapping(Long gcsId, MappingStatusEnum status) {
        return uavGcsMappingMapper.selectByGcsIdAndStatus(gcsId, status.getCode());
    }

    public UavGcsMappingDO queryUavGcsMapping(Long uavId, Long gcsId) {
        List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByUavIdAndGcsId(uavId, gcsId);
        if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
            return uavGcsMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public UavGcsMappingDO queryUavGcsMapping(Long uavId) {
        List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByUavId(uavId);
        if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
            return uavGcsMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public UavGcsMappingDO queryValidUavGcsMapping(Long uavId, Long gcsId) {
        List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByUavIdAndGcsIdAndStatus(uavId, gcsId, MappingStatusEnum.VALID.getCode());
        if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
            return uavGcsMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public int updateUavGcsMapping(UavGcsMappingDO uavGcsMappingDO) {
        return uavGcsMappingMapper.updateByUavGcsMapping(uavGcsMappingDO);
    }

    public void updateUavGcsMappingStatus(UavGcsMappingDO uavGcsMappingDO, MappingStatusEnum mappingStatusEnum) {
        uavGcsMappingDO.setStatus(mappingStatusEnum.getCode());
        uavGcsMappingDO.setGmtModify(new Date());
        updateUavGcsMapping(uavGcsMappingDO);
    }

    public int insertUavGcsMapping(UavGcsMappingDO uavGcsMappingDO) {
        return uavGcsMappingMapper.insertUavGcsMapping(uavGcsMappingDO);
    }

    public void updateUavGcsMappingGcsId(UavGcsMappingDO uavGcsMappingDO, Long gcsId, MappingStatusEnum mappingStatusEnum) {
        uavGcsMappingDO.setStatus(mappingStatusEnum.getCode());
        uavGcsMappingDO.setGcsId(gcsId);
        uavGcsMappingDO.setGmtModify(new Date());
        updateUavGcsMapping(uavGcsMappingDO);
    }

    public boolean validateUavId(Long uavId) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavId(uavId);
        return CollectionUtils.isNotEmpty(uavInfoDOList);
    }

    public UavInfoDO queryUavInfo(Long uavId) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavId(uavId);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public List<UavInfoDO> queryUavInfo(Integer typeId) {
        return uavInfoMapper.selectByTypeId(typeId);
    }

    public int insertUavInfo(UavInfoDO uavInfoDO) {
        return uavInfoMapper.insertUavInfo(uavInfoDO);
    }

    public int updateUavInfo(UavInfoDO uavInfoDO) {
        return uavInfoMapper.updateByUavInfo(uavInfoDO);
    }

    public int deleteUavInfoByUavId(Long uavId) {
        return uavInfoMapper.deleteByUavId(uavId);
    }

    public int deleteUavInfoById(Long id) {
        return uavInfoMapper.deleteById(id);
    }

    public int updateUavInfoTypeId(UavInfoDO uavInfoDO, Integer typeId) {
        uavInfoDO.setTypeId(typeId);
        uavInfoDO.setGmtModify(new Date());
        return updateUavInfo(uavInfoDO);
    }

    public UavNavigationMappingDO queryUavNavigationMapping(Long uavId) {
        List<UavNavigationMappingDO> uavNavigationMappingDOList = uavNavigationMappingMapper.selectByUavId(uavId);
        if (CollectionUtils.isNotEmpty(uavNavigationMappingDOList)) {
            return uavNavigationMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public int insertUavNavigationMapping(UavNavigationMappingDO uavNavigationMappingDO) {
        return uavNavigationMappingMapper.insertUavNavigationMapping(uavNavigationMappingDO);
    }

    public void updateUavNavigationMappingNavigationId(UavNavigationMappingDO uavNavigationMappingDO, Long navigationId, MappingStatusEnum mappingStatusEnum) {
        uavNavigationMappingDO.setStatus(mappingStatusEnum.getCode());
        uavNavigationMappingDO.setNavigationId(navigationId);
        uavNavigationMappingDO.setGmtModify(new Date());
        updateUavNavigationMapping(uavNavigationMappingDO);
    }

    public int updateUavNavigationMapping(UavNavigationMappingDO uavNavigationMappingDO) {
        return uavNavigationMappingMapper.updateByUavNavigationMapping(uavNavigationMappingDO);
    }

    public void updateUavNavigationMappingStatus(UavNavigationMappingDO uavNavigationMappingInfo, MappingStatusEnum mappingStatusEnum) {
        uavNavigationMappingInfo.setStatus(mappingStatusEnum.getCode());
        uavNavigationMappingInfo.setGmtModify(new Date());
        updateUavNavigationMapping(uavNavigationMappingInfo);
    }

    public int insertUavStatusLog(UavStatusLogDO uavStatusLogDO) {
        return uavStatusLogMapper.insertUavStatusLogDO(uavStatusLogDO);
    }

    public int insertCommandAndControlLog(CommandAndControlLogDO commandAndControlLogDO) {
        return commandAndControlLogMapper.insertCommandAndControlLog(commandAndControlLogDO);
    }

    public UavStatusLogDO buildUavStatusLogDO(Long uavId, Long navigationId, Integer uavStatus) {
        UavStatusLogDO uavStatusLogDO = new UavStatusLogDO();
        uavStatusLogDO.setUavId(uavId);
        uavStatusLogDO.setNavigationId(navigationId);
        uavStatusLogDO.setUavStatus(uavStatus);
        uavStatusLogDO.setGmtCreate(new Date());
        uavStatusLogDO.setGmtModify(new Date());
        return uavStatusLogDO;
    }

    public UavGcsMappingDO buildUavGcsMappingDO(Long uavId, Long gcsId, MappingStatusEnum status) {
        UavGcsMappingDO uavGcsMappingDO = new UavGcsMappingDO();
        uavGcsMappingDO.setUavId(uavId);
        uavGcsMappingDO.setGcsId(gcsId);
        uavGcsMappingDO.setStatus(status.getCode());
        uavGcsMappingDO.setGmtCreate(new Date());
        uavGcsMappingDO.setGmtModify(new Date());
        return uavGcsMappingDO;
    }


    public UavNavigationMappingDO buildUavNavigationMappingDO(Long uavId, Long navigationId, MappingStatusEnum status) {
        UavNavigationMappingDO uavNavigationMappingDO = new UavNavigationMappingDO();
        uavNavigationMappingDO.setUavId(uavId);
        uavNavigationMappingDO.setNavigationId(navigationId);
        uavNavigationMappingDO.setStatus(status.getCode());
        uavNavigationMappingDO.setGmtCreate(new Date());
        uavNavigationMappingDO.setGmtModify(new Date());
        return uavNavigationMappingDO;
    }

    public CommandAndControlLogDO buildCommandAndControlLogDO(Long uavId, Long navigationId, Long gcsId, Long rcsId, Long pilotId, Integer commandCode, CommandResultEnum commandResultEnum) {
        CommandAndControlLogDO commandAndControlLogDO = new CommandAndControlLogDO();
        commandAndControlLogDO.setUavId(uavId);
        commandAndControlLogDO.setNavigationId(navigationId);
        commandAndControlLogDO.setGcsId(gcsId);
        commandAndControlLogDO.setRcsId(rcsId);
        commandAndControlLogDO.setPilotId(pilotId);
        commandAndControlLogDO.setCommandCode(commandCode);
        commandAndControlLogDO.setCommandResult(commandResultEnum.getCode());
        commandAndControlLogDO.setGmtCreate(new Date());
        commandAndControlLogDO.setGmtModify(new Date());
        return commandAndControlLogDO;
    }

    public UavInfoDO buildUavInfoDO(Long uavId, Integer typeId) {
        UavInfoDO uavInfoDO = new UavInfoDO();
        uavInfoDO.setUavId(uavId);
        uavInfoDO.setTypeId(typeId);
        uavInfoDO.setGmtCreate(new Date());
        uavInfoDO.setGmtModify(new Date());
        return uavInfoDO;
    }
}
