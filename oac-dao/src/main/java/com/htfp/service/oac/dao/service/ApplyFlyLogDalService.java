package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.ApplyFlyLogMapper;
import com.htfp.service.oac.dao.model.ApplyFlyLogDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/9
 * @Description 描述
 */
@Slf4j
@Service
public class ApplyFlyLogDalService {

    @Resource
    ApplyFlyLogMapper applyFlyMapper;

    public ApplyFlyLogDO queryApplyFlyLog(Long id){
        return applyFlyMapper.selectById(id);
    }

    public ApplyFlyLogDO queryApplyFlyLogByApplyFlyId(String applyFlyId){
        return applyFlyMapper.selectByApplyFlyId(applyFlyId);
    }

    public ApplyFlyLogDO queryApplyFlyLogByReplyFlyId(Long replyFlyId){
        return applyFlyMapper.selectByReplyFlyId(replyFlyId);
    }

    public ApplyFlyLogDO queryApplyFlyLogByApplyFlightPlanId(String applyFlightPlanId){
        return applyFlyMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public ApplyFlyLogDO queryApplyFlightPlanLogByReplyFlightPlanId(Long replyFlightPlanId){
        return applyFlyMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByCpn(String cpn){
        List<ApplyFlyLogDO> applyFlyLogList = applyFlyMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(applyFlyLogList)){
            return applyFlyLogList;
        } else {
            return null;
        }
    }

    public int insertApplyFlyLog(ApplyFlyLogDO applyFlyLogDO){
        return applyFlyMapper.insertApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLog(ApplyFlyLogDO applyFlyLogDO){
        return applyFlyMapper.updateByApplyFlyLog(applyFlyLogDO);
    }

    public int deleteApplyFlyLog(Long id){
        return applyFlyMapper.deleteById(id);
    }

    public int deleteApplyFlyByApplyFlyId(Long applyFlyId){
        return applyFlyMapper.deleteByApplyFlyId(applyFlyId);
    }

    public int updateApplyFlyLogStatus(ApplyFlyLogDO applyFlyLogDO, Integer status){
        applyFlyLogDO.setStatus(status);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLogFlyIdAndStatus(ApplyFlyLogDO applyFlyLogDO, Long replyFlyId, Integer status){
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setStatus(status);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLogReplyFlyId(ApplyFlyLogDO applyFlyLogDO, Long replyFlyId){
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLogReplyFlyIdById(Long id, Long replyFlyId){
        ApplyFlyLogDO applyFlyLogDO = new ApplyFlyLogDO();
        applyFlyLogDO.setId(id);
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public ApplyFlyLogDO buildApplyFlyLogDO(String applyFlyId, Long replyFlyId, String applyFlightPlanId, Long replyFlightPlanId, String cpn, String airspaceNumbers, String operationScenarioType, Integer flyLng, Integer flyLat, Integer flyAlt, String vin, String pvin, String flightControlSn, String imei, Integer status) {
        ApplyFlyLogDO applyFlyLogDO = new ApplyFlyLogDO();
        applyFlyLogDO.setApplyFlyId(applyFlyId);
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setApplyFlightPlanId(applyFlightPlanId);
        applyFlyLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlyLogDO.setCpn(cpn);
        applyFlyLogDO.setAirspaceNumbers(airspaceNumbers);
        applyFlyLogDO.setOperationScenarioType(operationScenarioType);
        applyFlyLogDO.setFlyLng(flyLng);
        applyFlyLogDO.setFlyLat(flyLat);
        applyFlyLogDO.setFlyAlt(flyAlt);
        applyFlyLogDO.setVin(vin);
        applyFlyLogDO.setPvin(pvin);
        applyFlyLogDO.setFlightControlSn(flightControlSn);
        applyFlyLogDO.setImei(imei);
        applyFlyLogDO.setStatus(status);
        applyFlyLogDO.setGmtCreate(new Date());
        applyFlyLogDO.setGmtModify(new Date());
        return applyFlyLogDO;
    }
}
