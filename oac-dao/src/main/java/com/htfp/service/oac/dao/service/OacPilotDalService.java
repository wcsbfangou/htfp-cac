package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.OacPilotInfoMapper;
import com.htfp.service.oac.dao.model.PilotInfoDO;
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
public class OacPilotDalService {

    @Resource
    private OacPilotInfoMapper oacPilotInfoMapper;

    public PilotInfoDO queryPilotInfo(Long id){
        return oacPilotInfoMapper.selectById(id);
    }

    public PilotInfoDO queryPilotInfoByPilotUniId(String pilotUniId){
        return oacPilotInfoMapper.selectByPilotUniId(pilotUniId);
    }

    public List<PilotInfoDO> queryPilotInfoByPilotSourceId(String pilotSourceId){
        List<PilotInfoDO> pilotInfoDOList = oacPilotInfoMapper.selectByPilotSourceId(pilotSourceId);
        if(CollectionUtils.isNotEmpty(pilotInfoDOList)){
            return pilotInfoDOList;
        } else {
            return null;
        }
    }

    public int insertPilotInfo(PilotInfoDO pilotInfoDO){
        return oacPilotInfoMapper.insertPilotInfo(pilotInfoDO);
    }

    public int updatePilotInfo(PilotInfoDO pilotInfoDO){
        return oacPilotInfoMapper.updateByPilotInfo(pilotInfoDO);
    }

    public int deletePilotInfoById(Long id){
        return oacPilotInfoMapper.deleteById(id);
    }

    public int deletePilotInfoByPilotSourceId(String pilotSourceId){
        return oacPilotInfoMapper.deleteByPilotSourceId(pilotSourceId);
    }

    public int updatePilotInfoStatus(PilotInfoDO pilotInfoDO, Integer status){
        pilotInfoDO.setStatus(status);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public PilotInfoDO buildPilotInfoDO(String pilotSourceId, String pilotUniId, String pilotName, Integer pilotType, Integer licenseType, String licenseId, String licensePictureAddress, Integer idCardType, String idCardNumber, String idCardPictureAddress, Integer gender, String nationality, String phoneNumber, String emailAddress, Integer status) {
        PilotInfoDO pilotInfoDO = new PilotInfoDO();
        pilotInfoDO.setPilotSourceId(pilotSourceId);
        pilotInfoDO.setPilotUniId(pilotUniId);
        pilotInfoDO.setPilotName(pilotName);
        pilotInfoDO.setPilotType(pilotType);
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
