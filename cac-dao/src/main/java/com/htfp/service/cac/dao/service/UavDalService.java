package com.htfp.service.cac.dao.service;

import com.google.common.cache.Cache;
import com.google.common.cache.CacheBuilder;
import com.htfp.service.cac.common.enums.CommandResultEnum;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.mapper.entity.UavInfoMapper;
import com.htfp.service.cac.dao.mapper.log.CommandAndControlLogMapper;
import com.htfp.service.cac.dao.mapper.log.UavStatusLogMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavGcsMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavNavigationMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavOacMappingMapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.CommandAndControlLogDO;
import com.htfp.service.cac.dao.model.log.UavStatusLogDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;
import java.util.concurrent.TimeUnit;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 16:39
 * @Description 无人机dao服务类
 */
@Slf4j
@Service
public class UavDalService {

    @Resource
    private UavGcsMappingMapper uavGcsMappingMapper;

    @Resource
    private UavNavigationMappingMapper uavNavigationMappingMapper;

    @Resource
    private UavInfoMapper uavInfoMapper;

    @Resource
    private UavStatusLogMapper uavStatusLogMapper;

    @Resource
    private CommandAndControlLogMapper commandAndControlLogMapper;

    @Resource
    private UavOacMappingMapper uavOacMappingMapper;

    private Cache<Long, String> uavReportCache = CacheBuilder.newBuilder()
            .maximumSize(100)
            .expireAfterWrite(365, TimeUnit.DAYS)
            .expireAfterAccess(365, TimeUnit.DAYS)
            .concurrencyLevel(10)
            .recordStats()
            .build();

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

    public int deleteUavGcsMappingByUavId(Long uavId) {
        return uavGcsMappingMapper.deleteByUavId(uavId);
    }

    public boolean validateUavId(Long uavId) {
        UavInfoDO uavInfoDO = uavInfoMapper.selectById(uavId);
        return uavInfoDO != null;
    }

    public UavInfoDO queryUavInfo(Long uavId) {
        return uavInfoMapper.selectById(uavId);
    }

    public UavInfoDO queryUavInfoByCpn(String cpn) {
        return uavInfoMapper.selectByCpn(cpn);
    }

    public UavInfoDO queryUavInfoByUavReg(String uavReg) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavReg(uavReg);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public List<UavInfoDO> queryUavInfoByOperatorId(Long operatorId) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByOperatorId(operatorId);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList;
        } else {
            return null;
        }
    }

    public List<UavInfoDO> queryUavInfo(Integer uavType) {
        return uavInfoMapper.selectByUavType(uavType);
    }

    public int insertUavInfo(UavInfoDO uavInfoDO) {
        return uavInfoMapper.insertUavInfo(uavInfoDO);
    }

    public int updateUavInfo(UavInfoDO uavInfoDO) {
        return uavInfoMapper.updateByUavInfo(uavInfoDO);
    }

    public int updateUavInfoStatus(UavInfoDO uavInfoDO, Integer status) {
        uavInfoDO.setStatus(status);
        uavInfoDO.setGmtModify(new Date());
        return updateUavInfo(uavInfoDO);
    }

    public int updateUavInfoCpnAndStatus(UavInfoDO uavInfoDO, String cpn, Integer status) {
        uavInfoDO.setCpn(cpn);
        uavInfoDO.setStatus(status);
        uavInfoDO.setGmtModify(new Date());
        return updateUavInfo(uavInfoDO);
    }

    public int updateUavInfoCpn(UavInfoDO uavInfoDO, String cpn) {
        uavInfoDO.setCpn(cpn);
        uavInfoDO.setGmtModify(new Date());
        return updateUavInfo(uavInfoDO);
    }

    public int deleteUavInfoByUavId(Long uavId) {
        return uavInfoMapper.deleteById(uavId);
    }

    public int deleteUavInfoById(Long id) {
        return uavInfoMapper.deleteById(id);
    }

    public int updateUavInfoTypeId(UavInfoDO uavInfoDO, Integer typeId) {
        uavInfoDO.setUavType(typeId);
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

    public int deleteUavNavigationMappingByUavId(Long uavId) {
        return uavNavigationMappingMapper.deleteByUavId(uavId);
    }

    public int insertUavStatusLog(UavStatusLogDO uavStatusLogDO) {
        return uavStatusLogMapper.insertUavStatusLogDO(uavStatusLogDO);
    }

    public int deleteUavStatusById(Long id) {
        return uavStatusLogMapper.deleteById(id);
    }

    public int insertCommandAndControlLog(CommandAndControlLogDO commandAndControlLogDO) {
        return commandAndControlLogMapper.insertCommandAndControlLog(commandAndControlLogDO);
    }

    public int deleteCommandAndControlLogById(Long id) {
        return commandAndControlLogMapper.deleteById(id);
    }

    public UavOacMappingDO queryUavOacMapping(Long uavId) {
        return uavOacMappingMapper.selectByUavId(uavId);
    }

    public String queryUavReportCode(Long uavId) {
        String reportCode = uavReportCache.getIfPresent(uavId);
        if (StringUtils.isEmpty(reportCode)) {
            UavOacMappingDO uavOacMapping = uavOacMappingMapper.selectByUavId(uavId);
            if (uavOacMapping != null) {
                reportCode = uavOacMapping.getReportCode();
            }
        }
        return reportCode;
    }

    public UavOacMappingDO queryUavOacMapping(Long uavId, MappingStatusEnum mappingStatusEnum) {
        return uavOacMappingMapper.selectByUavIdAndStatus(uavId, mappingStatusEnum.getCode());
    }

    public UavOacMappingDO queryUavOacMapping(Long uavId, MappingStatusEnum mappingStatusEnum, LinkStatusEnum linkStatusEnum) {
        return uavOacMappingMapper.selectByUavIdAndStatusAndLinkStatus(uavId, mappingStatusEnum.getCode(), linkStatusEnum.getCode());
    }

    public int updateUavOacMapping(UavOacMappingDO uavOacMappingDO) {
        return uavOacMappingMapper.updateByUavOacMapping(uavOacMappingDO);
    }

    public int insertUavOacMapping(UavOacMappingDO uavOacMappingDO) {
        int id = uavOacMappingMapper.insertUavOacMapping(uavOacMappingDO);
        if (id > 0) {
            uavReportCacheSaveReportCode(uavOacMappingDO.getUavId(), uavOacMappingDO.getReportCode());
        }
        return id;
    }

    public int deleteUavOacMapping(Long uavId) {
        uavReportCache.invalidate(uavId);
        return uavOacMappingMapper.deleteByUavId(uavId);
    }

    public int updateUavOacMappingReportCode(UavOacMappingDO uavOacMappingDO, String reportCode) {
        uavOacMappingDO.setReportCode(reportCode);
        uavOacMappingDO.setGmtModify(new Date());
        int id = updateUavOacMapping(uavOacMappingDO);
        if (id > 0) {
            uavReportCacheSaveReportCode(uavOacMappingDO.getUavId(), reportCode);
        }
        return id;
    }

    public int updateUavOacMappingReportCodeAndLinkStatus(UavOacMappingDO uavOacMappingDO, String reportCode, LinkStatusEnum linkStatusEnum) {
        uavOacMappingDO.setLinkStatus(linkStatusEnum.getCode());
        uavOacMappingDO.setReportCode(reportCode);
        uavOacMappingDO.setGmtModify(new Date());
        int id = updateUavOacMapping(uavOacMappingDO);
        if (id > 0) {
            uavReportCacheSaveReportCode(uavOacMappingDO.getUavId(), reportCode);
        }
        return id;
    }

    public int updateUavOacMappingReportCodeAndStatus(UavOacMappingDO uavOacMappingDO, String reportCode, MappingStatusEnum mappingStatusEnum) {
        uavOacMappingDO.setStatus(mappingStatusEnum.getCode());
        uavOacMappingDO.setReportCode(reportCode);
        uavOacMappingDO.setGmtModify(new Date());
        int id = updateUavOacMapping(uavOacMappingDO);
        if (id > 0) {
            uavReportCacheSaveReportCode(uavOacMappingDO.getUavId(), reportCode);
        }
        return id;
    }

    public int updateUavOacReportCodeAndMappingStatusAndLinkStatus(UavOacMappingDO uavOacMappingDO, String reportCode, MappingStatusEnum mappingStatusEnum, LinkStatusEnum linkStatusEnum) {
        uavOacMappingDO.setReportCode(reportCode);
        uavOacMappingDO.setStatus(mappingStatusEnum.getCode());
        uavOacMappingDO.setLinkStatus(linkStatusEnum.getCode());
        uavOacMappingDO.setGmtModify(new Date());
        int id = updateUavOacMapping(uavOacMappingDO);
        if (id > 0) {
            uavReportCacheSaveReportCode(uavOacMappingDO.getUavId(), reportCode);
        }
        return id;
    }

    void uavReportCacheSaveReportCode(Long uavId, String reportCode) {
        if (StringUtils.isNotBlank(reportCode)) {
            uavReportCache.put(uavId, reportCode);
        } else {
            uavReportCache.invalidate(uavId);
        }
    }

    public int updateUavOacMappingStatus(UavOacMappingDO uavOacMappingDO, MappingStatusEnum statusEnum) {
        uavOacMappingDO.setStatus(statusEnum.getCode());
        uavOacMappingDO.setGmtModify(new Date());
        return updateUavOacMapping(uavOacMappingDO);
    }

    public int updateUavOacMappingLinkStatus(UavOacMappingDO uavOacMappingDO, LinkStatusEnum linkStatusEnum) {
        uavOacMappingDO.setLinkStatus(linkStatusEnum.getCode());
        uavOacMappingDO.setGmtModify(new Date());
        return updateUavOacMapping(uavOacMappingDO);
    }

    public UavOacMappingDO buildUavOacMappingDO(Long uavId, String reportCode, MappingStatusEnum mappingStatusEnum, LinkStatusEnum linkStatusEnum) {
        UavOacMappingDO uavOacMappingDO = new UavOacMappingDO();
        uavOacMappingDO.setUavId(uavId);
        uavOacMappingDO.setReportCode(reportCode);
        uavOacMappingDO.setStatus(mappingStatusEnum.getCode());
        uavOacMappingDO.setLinkStatus(linkStatusEnum.getCode());
        uavOacMappingDO.setGmtCreate(new Date());
        uavOacMappingDO.setGmtModify(new Date());
        return uavOacMappingDO;
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

    public UavInfoDO buildUavInfoDO(String uavReg, String uavName, Integer uavType, String cpn, String vin, String pvin, String sn, String flightControlSn, String imei, String imsi, String manufacturerName, String productName, Integer productType, Integer productSizeType, Integer maxFlyTime, String operationScenarioType, Long operatorId, String videoStreamAddress, Integer status) {
        UavInfoDO uavInfoDO = new UavInfoDO();
        uavInfoDO.setUavReg(uavReg);
        uavInfoDO.setUavName(uavName);
        uavInfoDO.setUavType(uavType);
        uavInfoDO.setCpn(cpn);
        uavInfoDO.setVin(vin);
        uavInfoDO.setPvin(pvin);
        uavInfoDO.setSn(sn);
        uavInfoDO.setFlightControlSn(flightControlSn);
        uavInfoDO.setImei(imei);
        uavInfoDO.setImsi(imsi);
        uavInfoDO.setManufacturerName(manufacturerName);
        uavInfoDO.setProductName(productName);
        uavInfoDO.setProductType(productType);
        uavInfoDO.setProductSizeType(productSizeType);
        uavInfoDO.setMaxFlyTime(maxFlyTime);
        uavInfoDO.setOperationScenarioType(operationScenarioType);
        uavInfoDO.setOperatorId(operatorId);
        uavInfoDO.setVideoStreamAddress(videoStreamAddress);
        uavInfoDO.setStatus(status);
        uavInfoDO.setGmtCreate(new Date());
        uavInfoDO.setGmtModify(new Date());
        return uavInfoDO;
    }
}
