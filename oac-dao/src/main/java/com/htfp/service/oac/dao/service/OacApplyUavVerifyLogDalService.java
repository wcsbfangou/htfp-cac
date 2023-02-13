package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.OacApplyUavVerifyLogMapper;
import com.htfp.service.oac.dao.model.ApplyUavVerifyLogDO;
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
public class OacApplyUavVerifyLogDalService {

    @Resource
    OacApplyUavVerifyLogMapper oacApplyUavVerifyLogMapper;

    public ApplyUavVerifyLogDO queryApplyUavVerifyLog(Long id){
        return oacApplyUavVerifyLogMapper.selectById(id);
    }

    public ApplyUavVerifyLogDO queryApplyUavVerifyLogByApplyUavVerifyId(String applyUavVerifyId){
        return oacApplyUavVerifyLogMapper.selectByApplyUavVerifyId(applyUavVerifyId);
    }

    public ApplyUavVerifyLogDO queryApplyUavVerifyLogByReplyFlightPlanId(Long replyUavVerifyId){
        return oacApplyUavVerifyLogMapper.selectByReplyUavVerifyId(replyUavVerifyId);
    }

    public List<ApplyUavVerifyLogDO> queryApplyFlightPlanLogByCpn(String cpn){
        List<ApplyUavVerifyLogDO> applyUavVerifyLogList = oacApplyUavVerifyLogMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(applyUavVerifyLogList)){
            return applyUavVerifyLogList;
        } else {
            return null;
        }
    }

    public int insertApplyUavVerifyLog(ApplyUavVerifyLogDO applyUavVerifyLog){
        return oacApplyUavVerifyLogMapper.insertApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyUavVerifyLog(ApplyUavVerifyLogDO applyUavVerifyLog){
        return oacApplyUavVerifyLogMapper.updateByApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int deleteApplyFlightPlanLog(Long id){
        return oacApplyUavVerifyLogMapper.deleteById(id);
    }

    public int deleteApplyUavVerifyLogByApplyUavVerifyId(Long applyUavVerifyId){
        return oacApplyUavVerifyLogMapper.deleteByReplyUavVerifyIdId(applyUavVerifyId);
    }

    public int updateApplyFlightPlanLogStatus(ApplyUavVerifyLogDO applyUavVerifyLog, Integer status){
        applyUavVerifyLog.setStatus(status);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogFlightPlanIdAndStatus(ApplyUavVerifyLogDO applyUavVerifyLog, Long replyUavVerifyId, Integer status){
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLog.setStatus(status);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanId(ApplyUavVerifyLogDO applyUavVerifyLog, Long replyUavVerifyId){
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLog.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLog);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanIdById(Long id, Long replyUavVerifyId){
        ApplyUavVerifyLogDO applyUavVerifyLogDO = new ApplyUavVerifyLogDO();
        applyUavVerifyLogDO.setId(id);
        applyUavVerifyLogDO.setReplyUavVerifyId(replyUavVerifyId);
        applyUavVerifyLogDO.setGmtModify(new Date());
        return updateApplyUavVerifyLog(applyUavVerifyLogDO);
    }

    public ApplyUavVerifyLogDO buildApplyUavVerifyLog(String applyUavVerifyId, Long replyUavVerifyId, String cpn, Integer lng, Integer lat, Integer alt, Integer groundSpeed, Integer relativeHeight, String flightControlSn, String flightControlVersion, String uavDynamicParam, String uavStaticParam, Integer status) {
        ApplyUavVerifyLogDO applyUavVerifyLog = new ApplyUavVerifyLogDO();
        applyUavVerifyLog.setApplyUavVerifyId(applyUavVerifyId);
        applyUavVerifyLog.setReplyUavVerifyId(replyUavVerifyId);
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
