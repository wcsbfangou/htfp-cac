package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.log.ApplyFlyLogMapper;
import com.htfp.service.cac.dao.model.log.ApplyFlyLogDO;
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
    private ApplyFlyLogMapper applyFlyMapper;

    public ApplyFlyLogDO queryApplyFlyLog(Long id){
        return applyFlyMapper.selectById(id);
    }

    public ApplyFlyLogDO queryApplyFlyLogByApplyFlyId(Long applyFlyId){
        return applyFlyMapper.selectByApplyFlyId(applyFlyId);
    }

    public ApplyFlyLogDO queryApplyFlyLogByReplyFlyId(String replyFlyId){
        return applyFlyMapper.selectByReplyFlyId(replyFlyId);
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByApplyFlightPlanId(Long applyFlightPlanId){
        return applyFlyMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByReplyFlightPlanId(String replyFlightPlanId){
        return applyFlyMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByGcsId(Long gcsId){
        List<ApplyFlyLogDO> applyFlyLogList = applyFlyMapper.selectByGcsId(gcsId);
        if(CollectionUtils.isNotEmpty(applyFlyLogList)){
            return applyFlyLogList;
        } else {
            return null;
        }
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByUavId(Long uavId){
        List<ApplyFlyLogDO> applyFlyLogList = applyFlyMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(applyFlyLogList)){
            return applyFlyLogList;
        } else {
            return null;
        }
    }

    public List<ApplyFlyLogDO> queryApplyFlyLogByUavReg(String uavReg){
        List<ApplyFlyLogDO> applyFlyLogList = applyFlyMapper.selectByUavReg(uavReg);
        if(CollectionUtils.isNotEmpty(applyFlyLogList)){
            return applyFlyLogList;
        } else {
            return null;
        }
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

    public int updateApplyFlyLogFlyIdAndStatus(ApplyFlyLogDO applyFlyLogDO, String replyFlyId, Integer status){
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setStatus(status);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLogReplyFlyId(ApplyFlyLogDO applyFlyLogDO, String replyFlyId){
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public int updateApplyFlyLogReplyFlyIdById(Long id, String replyFlyId){
        ApplyFlyLogDO applyFlyLogDO = new ApplyFlyLogDO();
        applyFlyLogDO.setId(id);
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setGmtModify(new Date());
        return updateApplyFlyLog(applyFlyLogDO);
    }

    public ApplyFlyLogDO buildApplyFlyLogDO(Long applyFlyId, String replyFlyId, Long applyFlightPlanId, String replyFlightPlanId, Long navigationId, Long gcsId, Long uavId, String uavReg, String cpn, String airspaceNumbers, String operationScenarioType, Integer flyLng, Integer flyLat, Integer flyAlt, String vin, String pvin, String flightControlSn, String imei, Integer status) {
        ApplyFlyLogDO applyFlyLogDO = new ApplyFlyLogDO();
        applyFlyLogDO.setApplyFlyId(applyFlyId);
        applyFlyLogDO.setReplyFlyId(replyFlyId);
        applyFlyLogDO.setApplyFlightPlanId(applyFlightPlanId);
        applyFlyLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlyLogDO.setNavigationId(navigationId);
        applyFlyLogDO.setGcsId(gcsId);
        applyFlyLogDO.setUavId(uavId);
        applyFlyLogDO.setUavReg(uavReg);
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
