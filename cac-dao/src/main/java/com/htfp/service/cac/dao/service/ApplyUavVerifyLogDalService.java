package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.log.ApplyUavVerifyLogMapper;
import com.htfp.service.cac.dao.model.log.ApplyUavVerifyLogDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/1/12
 * @Description 描述
 */
@Slf4j
@Service
public class ApplyUavVerifyLogDalService {

    @Resource
    private ApplyUavVerifyLogMapper applyUavVerifyLogMapper;

    public ApplyUavVerifyLogDO queryApplyUavVerifyLog(Long id){
        return applyUavVerifyLogMapper.selectById(id);
    }

    public ApplyUavVerifyLogDO queryApplyUavVerifyLogByApplyUavVerifyId(Long applyUavVerifyId){
        return applyUavVerifyLogMapper.selectByApplyUavVerifyId(applyUavVerifyId);
    }

    public ApplyUavVerifyLogDO queryApplyUavVerifyLogByReplyFlightPlanId(String replyUavVerifyId){
        return applyUavVerifyLogMapper.selectByReplyUavVerifyId(replyUavVerifyId);
    }

    public List<ApplyUavVerifyLogDO> queryApplyFlightPlanLogByGcsId(Long gcsId){
        List<ApplyUavVerifyLogDO> applyUavVerifyLogList = applyUavVerifyLogMapper.selectByGcsId(gcsId);
        if(CollectionUtils.isNotEmpty(applyUavVerifyLogList)){
            return applyUavVerifyLogList;
        } else {
            return null;
        }
    }

    public List<ApplyUavVerifyLogDO> queryApplyUavVerifyLogDOByUavId(Long uavId){
        List<ApplyUavVerifyLogDO> applyUavVerifyLogList = applyUavVerifyLogMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(applyUavVerifyLogList)){
            return applyUavVerifyLogList;
        } else {
            return null;
        }
    }

    public List<ApplyUavVerifyLogDO> queryApplyFlightPlanLogByUavReg(String uavReg){
        List<ApplyUavVerifyLogDO> applyUavVerifyLogList = applyUavVerifyLogMapper.selectByUavReg(uavReg);
        if(CollectionUtils.isNotEmpty(applyUavVerifyLogList)){
            return applyUavVerifyLogList;
        } else {
            return null;
        }
    }

    public List<ApplyUavVerifyLogDO> queryApplyFlightPlanLogByCpn(String cpn){
        List<ApplyUavVerifyLogDO> applyUavVerifyLogList = applyUavVerifyLogMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(applyUavVerifyLogList)){
            return applyUavVerifyLogList;
        } else {
            return null;
        }
    }

    public int insertApplyUavVerifyLog(ApplyUavVerifyLogDO applyUavVerifyLog){
        return applyUavVerifyLogMapper.insertApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyUavVerifyLog(ApplyUavVerifyLogDO applyUavVerifyLog){
        return applyUavVerifyLogMapper.updateByApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int deleteApplyFlightPlanLog(Long id){
        return applyUavVerifyLogMapper.deleteById(id);
    }

    public int deleteApplyUavVerifyLogByApplyUavVerifyId(Long applyUavVerifyId){
        return applyUavVerifyLogMapper.deleteByApplyUavVerifyIdId(applyUavVerifyId);
    }

    public int updateApplyFlightPlanLogStatus(ApplyUavVerifyLogDO applyUavVerifyLog, Integer status){
        applyUavVerifyLog.setStatus(status);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogFlightPlanIdAndStatus(ApplyUavVerifyLogDO applyUavVerifyLog, String replyUavVerifyId, Integer status){
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLog.setStatus(status);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanId(ApplyUavVerifyLogDO applyUavVerifyLog, String replyUavVerifyId){
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanIdById(Long id, String replyUavVerifyId){
        ApplyUavVerifyLogDO applyUavVerifyLogDO = new ApplyUavVerifyLogDO();
        applyUavVerifyLogDO.setId(id);
        applyUavVerifyLogDO.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLogDO.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLogDO);
    }

    public ApplyUavVerifyLogDO buildApplyUavVerifyLog(Long applyUavVerifyId, String replyUavVerifyId, String gcsId, String uavId, String uavReg, String cpn, Integer lng, Integer lat, Integer alt, Integer groundSpeed, Integer relativeHeight, String flightControlSn, String flightControlVersion, String uavDynamicParam, String uavStaticParam, Integer status) {
        ApplyUavVerifyLogDO applyUavVerifyLog = new ApplyUavVerifyLogDO();
        applyUavVerifyLog.setApplyUavVerifyId(applyUavVerifyId);
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLog.setGcsId(gcsId);
        applyUavVerifyLog.setUavId(uavId);
        applyUavVerifyLog.setUavReg(uavReg);
        applyUavVerifyLog.setCpn(cpn);
        applyUavVerifyLog.setLng(lng);
        applyUavVerifyLog.setLat(lat);
        applyUavVerifyLog.setAlt(alt);
        applyUavVerifyLog.setGroundSpeed(groundSpeed);
        applyUavVerifyLog.setRelativeHeight(relativeHeight);
        applyUavVerifyLog.setFlightControlSn(flightControlSn);
        applyUavVerifyLog.setFlightControlVersion(flightControlVersion);
        applyUavVerifyLog.setUavDynamicParam(uavDynamicParam);
        applyUavVerifyLog.setUavStaticParam(uavStaticParam);
        applyUavVerifyLog.setStatus(status);
        applyUavVerifyLog.setGmtCreate(new Date());
        applyUavVerifyLog.setGmtModify(new Date());
        return applyUavVerifyLog;
    }

}
