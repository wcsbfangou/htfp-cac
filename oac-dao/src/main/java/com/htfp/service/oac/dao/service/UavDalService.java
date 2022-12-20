package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.UavInfoMapper;
import com.htfp.service.oac.dao.model.UavInfoDO;
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
    UavInfoMapper uavInfoMapper;

    public UavInfoDO queryUavInfo(Long id) {
        return uavInfoMapper.selectById(id);
    }

    public UavInfoDO queryUavInfoByUavReg(String uavReg) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavReg(uavReg);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public UavInfoDO queryUavInfoByUavSourceId(String uavSourceId) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByUavSourceId(uavSourceId);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public List<UavInfoDO> queryUavInfoByOperatorUniId(String operatorUniId) {
        List<UavInfoDO> uavInfoDOList = uavInfoMapper.selectByOperatorUniId(operatorUniId);
        if (CollectionUtils.isNotEmpty(uavInfoDOList)) {
            return uavInfoDOList;
        } else {
            return null;
        }
    }

    public Long queryUavCountByOperatorUniIdIncludeDel(String operatorUniId){
        return uavInfoMapper.selectCountByOperatorUniIdIncludeDel(operatorUniId);
    }

    public UavInfoDO queryUavInfoByCpn(String cpn) {
        return uavInfoMapper.selectByCpn(cpn);
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

    public int deleteUavInfoByUavSourceId(String uavSourceId) {
        return uavInfoMapper.deleteByUavSourceId(uavSourceId);
    }

    public int deleteUavInfoByUavReg(String uavReg) {
        return uavInfoMapper.deleteByUavReg(uavReg);
    }

    public int deleteUavInfoByCpn(String cpn) {
        return uavInfoMapper.deleteByCpn(cpn);
    }

    public int deleteUavInfoById(Long id) {
        return uavInfoMapper.deleteById(id);
    }

    public UavInfoDO buildUavInfoDO(String uavSourceId, String uavReg, String uavName, String cpn, String vin, String pvin, String sn, String flightControlSn, String imei, String imsi, String manufacturerName, String productName, Integer productType, Integer productSizeType, Integer maxFlyTime, String operationScenarioType, String operatorUniId, Integer status) {
        UavInfoDO uavInfoDO = new UavInfoDO();
        uavInfoDO.setUavSourceId(uavSourceId);
        uavInfoDO.setUavReg(uavReg);
        uavInfoDO.setUavName(uavName);
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
        uavInfoDO.setOperatorUniId(operatorUniId);
        uavInfoDO.setStatus(status);
        uavInfoDO.setGmtCreate(new Date());
        uavInfoDO.setGmtModify(new Date());
        return uavInfoDO;
    }
}
