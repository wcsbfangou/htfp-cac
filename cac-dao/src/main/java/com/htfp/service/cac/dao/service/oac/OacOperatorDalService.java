package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacOperatorInfoMapper;
import com.htfp.service.cac.dao.model.oac.OperatorInfoDO;
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
public class OacOperatorDalService {

    @Resource
    private OacOperatorInfoMapper oacOperatorInfoMapper;

    public OperatorInfoDO queryOperatorInfo(Long id){
        return oacOperatorInfoMapper.selectById(id);
    }

    public OperatorInfoDO queryOperatorInfoByOperatorUniId(String operatorUniId){
        return oacOperatorInfoMapper.selectByOperatorUniId(operatorUniId);
    }

    public List<OperatorInfoDO> queryOperatorInfoByOperatorSourceId(String operatorSourceId){
        List<OperatorInfoDO> operatorInfoDOList = oacOperatorInfoMapper.selectByOperatorSourceId(operatorSourceId);
        if(CollectionUtils.isNotEmpty(operatorInfoDOList)){
            return operatorInfoDOList;
        } else {
            return null;
        }
    }

    public int insertOperatorInfo(OperatorInfoDO operatorInfoDO){
        return oacOperatorInfoMapper.insertOperatorInfo(operatorInfoDO);
    }

    public int updateOperatorInfo(OperatorInfoDO operatorInfoDO){
        return oacOperatorInfoMapper.updateByOperatorInfo(operatorInfoDO);
    }

    public int deleteOperatorInfoByOperatorSourceId(String operatorSourceId){
        return oacOperatorInfoMapper.deleteByOperatorSourceId(operatorSourceId);
    }

    public int deleteOperatorInfo(Long id){
        return oacOperatorInfoMapper.deleteById(id);
    }

    public int updateOperatorInfoStatus(OperatorInfoDO operatorInfoDO, Integer status){
        operatorInfoDO.setStatus(status);
        operatorInfoDO.setGmtModify(new Date());
        return updateOperatorInfo(operatorInfoDO);
    }

    public OperatorInfoDO buildOperatorInfoDO(String operatorSourceId, String operatorUniId, String operatorName, Integer operatorType, Integer idCardType, String idCardNumber, String idCardPictureAddress, String companyName, String socialCreditCode, Integer gender, String nationality, String city, String address, String phoneNumber, String emailAddress, Integer status) {
        OperatorInfoDO operatorInfoDO = new OperatorInfoDO();
        operatorInfoDO.setOperatorSourceId(operatorSourceId);
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
