package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.log.ATCIssuedLogMapper;
import com.htfp.service.cac.dao.model.log.ATCIssuedLogDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Service
public class ATCIssuedLogDalService {

    @Resource
    private ATCIssuedLogMapper atcIssuedLogMapper;

    public ATCIssuedLogDO queryATCIssuedLog(Long id) {
        return atcIssuedLogMapper.selectById(id);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByApplyFlightPlanId(Long applyFlightPlanId) {
        return atcIssuedLogMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByApplyFlyId(Long applyFlyId) {
        return atcIssuedLogMapper.selectByApplyFlyId(applyFlyId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlightPlanId(String replyFlightPlanId) {
        return atcIssuedLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlyId(String replyFlyId) {
        return atcIssuedLogMapper.selectByReplyFlyId(replyFlyId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByCpnAndAtcType(String cpn, Integer atcType) {
        return atcIssuedLogMapper.selectByCpnAndAtcType(cpn, atcType);
    }

    public int insertATCIssuedLog(ATCIssuedLogDO atcIssuedLogDO) {
        return atcIssuedLogMapper.insertAtcIssuedLog(atcIssuedLogDO);
    }

    public int updateATCIssuedLog(ATCIssuedLogDO atcIssuedLogDO) {
        return atcIssuedLogMapper.updateByATCIssuedLog(atcIssuedLogDO);
    }

    public int updateATCIssuedLogDelivered(ATCIssuedLogDO atcIssuedLogDO, Integer delivered) {
        atcIssuedLogDO.setAtcDelivered(delivered);
        atcIssuedLogDO.setGmtModify(new Date());
        return updateATCIssuedLog(atcIssuedLogDO);
    }


    public int deleteATCIssuedLogById(Long id) {
        return atcIssuedLogMapper.deleteById(id);
    }

    public ATCIssuedLogDO buildATCIssuedLog(Long applyFlightPlanId, Long applyFlyId, String replyFlightPlanId, String replyFlyId, Long navigationId, Long uavId, String uavReg, String cpn, Integer atcType, String atcSpecificPosition, String atcEffectTime, Integer atcLimitPeriod, String atcOperator, Integer atcDelivered) {
        ATCIssuedLogDO atcIssuedLog = new ATCIssuedLogDO();
        atcIssuedLog.setApplyFlightPlanId(applyFlightPlanId);
        atcIssuedLog.setApplyFlyId(applyFlyId);
        atcIssuedLog.setReplyFlightPlanId(replyFlightPlanId);
        atcIssuedLog.setReplyFlyId(replyFlyId);
        atcIssuedLog.setNavigationId(navigationId);
        atcIssuedLog.setUavId(uavId);
        atcIssuedLog.setUavReg(uavReg);
        atcIssuedLog.setCpn(cpn);
        atcIssuedLog.setAtcType(atcType);
        atcIssuedLog.setAtcSpecificPosition(atcSpecificPosition);
        atcIssuedLog.setAtcEffectTime(atcEffectTime);
        atcIssuedLog.setAtcLimitPeriod(atcLimitPeriod);
        atcIssuedLog.setAtcOperator(atcOperator);
        atcIssuedLog.setAtcDelivered(atcDelivered);
        atcIssuedLog.setGmtCreate(new Date());
        atcIssuedLog.setGmtModify(new Date());
        return atcIssuedLog;
    }
}
