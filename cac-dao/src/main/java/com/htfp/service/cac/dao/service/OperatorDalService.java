package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.entity.OperatorInfoMapper;
import com.htfp.service.cac.dao.model.entity.OperatorInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
@Slf4j
@Service
public class OperatorDalService {

    @Resource
    OperatorInfoMapper operatorInfoMapper;

    public OperatorInfoDO queryOperatorInfo(Long operatorId){
        return operatorInfoMapper.selectById(operatorId);
    }

    public OperatorInfoDO queryOperatorInfo(String operatorCode){
        List<OperatorInfoDO> operatorInfoDOList = operatorInfoMapper.selectByOperatorCode(operatorCode);
        if(CollectionUtils.isNotEmpty(operatorInfoDOList)){
            return operatorInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public int insertOperatorInfo(OperatorInfoDO operatorInfoDO){
        return operatorInfoMapper.insertOperatorInfo(operatorInfoDO);
    }

    public int updateOperatorInfo(OperatorInfoDO operatorInfoDO){
        return operatorInfoMapper.updateByOperatorInfo(operatorInfoDO);
    }

    public int deleteOperatorInfoByOperatorId(Long operatorId){
        return operatorInfoMapper.deleteById(operatorId);
    }

    public int deleteOperatorInfoByOperatorCode(String operatorCode){
        return operatorInfoMapper.deleteByOperatorCode(operatorCode);
    }

    public int updateOperatorInfoStatus(OperatorInfoDO operatorInfoDO, Integer status){
        operatorInfoDO.setStatus(status);
        operatorInfoDO.setGmtModify(new Date());
        return updateOperatorInfo(operatorInfoDO);
    }

    public int updateOperatorInfoUniIdAndStatus(OperatorInfoDO operatorInfoDO, String operatorUniId, Integer status){
        operatorInfoDO.setOperatorUniId(operatorUniId);
        operatorInfoDO.setStatus(status);
        operatorInfoDO.setGmtModify(new Date());
        return updateOperatorInfo(operatorInfoDO);
    }

    public int updateOperatorInfoUniId(OperatorInfoDO operatorInfoDO, String operatorUniId){
        operatorInfoDO.setOperatorUniId(operatorUniId);
        operatorInfoDO.setGmtModify(new Date());
        return updateOperatorInfo(operatorInfoDO);
    }

    public OperatorInfoDO buildOperatorInfoDO(String operatorCode, String operatorUniId, String operatorName, Integer operatorType, Integer idCardType, String idCardNumber, String idCardPictureAddress, String companyName, String socialCreditCode, Integer gender, String nationality, String city, String address, String phoneNumber, String emailAddress, Integer status) {
        OperatorInfoDO operatorInfoDO = new OperatorInfoDO();
        operatorInfoDO.setOperatorCode(operatorCode);
        operatorInfoDO.setOperatorUniId(operatorUniId);
        operatorInfoDO.setOperatorName(operatorName);
        operatorInfoDO.setOperatorType(operatorType);
        operatorInfoDO.setIdCardType(idCardType);
        operatorInfoDO.setIdCardNumber(idCardNumber);
        operatorInfoDO.setIdCardPictureAddress(idCardPictureAddress);
        operatorInfoDO.setCompanyName(companyName);
        operatorInfoDO.setSocialCreditCode(socialCreditCode);
        operatorInfoDO.setGender(gender);
        operatorInfoDO.setNationality(nationality);
        operatorInfoDO.setCity(city);
        operatorInfoDO.setAddress(address);
        operatorInfoDO.setPhoneNumber(phoneNumber);
        operatorInfoDO.setEmailAddress(emailAddress);
        operatorInfoDO.setStatus(status);
        operatorInfoDO.setGmtCreate(new Date());
        operatorInfoDO.setGmtModify(new Date());
        return operatorInfoDO;
    }
}
