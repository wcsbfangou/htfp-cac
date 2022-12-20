package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.entity.PilotInfoMapper;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-31 22:09
 * @Description 驾驶员dao服务类
 */

@Slf4j
@Service
public class PilotDalService {

    @Resource
    PilotInfoMapper pilotInfoMapper;

    public PilotInfoDO queryPilotInfo(Long pilotId){
        return pilotInfoMapper.selectById(pilotId);
    }

    public PilotInfoDO queryPilotInfo(String pilotCode){
        List<PilotInfoDO> pilotInfoDOList = pilotInfoMapper.selectByPilotCode(pilotCode);
        if(CollectionUtils.isNotEmpty(pilotInfoDOList)){
            return pilotInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public List<PilotInfoDO> queryPilotInfo(Integer controllableUavType){
        return pilotInfoMapper.selectByControllableUavType(controllableUavType);
    }

    public int insertPilotInfo(PilotInfoDO pilotInfoDO){
        return pilotInfoMapper.insertPilotInfo(pilotInfoDO);
    }

    public int updatePilotInfo(PilotInfoDO pilotInfoDO){
        return pilotInfoMapper.updateByPilotInfo(pilotInfoDO);
    }

    public int deletePilotInfoByPilotId(Long pilotId){
        return pilotInfoMapper.deleteById(pilotId);
    }

    public int deletePilotInfoByPilotCode(String pilotCode){
        return pilotInfoMapper.deleteByPilotCode(pilotCode);
    }

    public int updatePilotInfoControllableUavType(PilotInfoDO pilotInfoDO, Integer controllableUavType){
        pilotInfoDO.setControllableUavType(controllableUavType);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public int updatePilotInfoStatus(PilotInfoDO pilotInfoDO, Integer status){
        pilotInfoDO.setStatus(status);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public int updatePilotInfoUniIdAndStatus(PilotInfoDO pilotInfoDO, String pilotUniId, Integer status){
        pilotInfoDO.setPilotUniId(pilotUniId);
        pilotInfoDO.setStatus(status);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public int updatePilotInfoUniId(PilotInfoDO pilotInfoDO, String pilotUniId){
        pilotInfoDO.setPilotUniId(pilotUniId);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public PilotInfoDO buildPilotInfoDO(String pilotCode, String pilotUniId, String pilotName, Integer pilotType, Integer controllableUavType, Integer licenseType, String licenseId, String licensePictureAddress, Integer idCardType, String idCardNumber, String idCardPictureAddress, Integer gender, String nationality, String phoneNumber, String emailAddress, Integer status) {
        PilotInfoDO pilotInfoDO = new PilotInfoDO();
        pilotInfoDO.setPilotCode(pilotCode);
        pilotInfoDO.setPilotUniId(pilotUniId);
        pilotInfoDO.setPilotName(pilotName);
        pilotInfoDO.setPilotType(pilotType);
        pilotInfoDO.setControllableUavType(controllableUavType);
        pilotInfoDO.setLicenseType(licenseType);
        pilotInfoDO.setLicenseId(licenseId);
        pilotInfoDO.setLicensePictureAddress(licensePictureAddress);
        pilotInfoDO.setIdCardType(idCardType);
        pilotInfoDO.setIdCardNumber(idCardNumber);
        pilotInfoDO.setIdCardPictureAddress(idCardPictureAddress);
        pilotInfoDO.setGender(gender);
        pilotInfoDO.setNationality(nationality);
        pilotInfoDO.setPhoneNumber(phoneNumber);
        pilotInfoDO.setEmailAddress(emailAddress);
        pilotInfoDO.setStatus(status);
        pilotInfoDO.setGmtCreate(new Date());
        pilotInfoDO.setGmtModify(new Date());
        return pilotInfoDO;
    }
}
