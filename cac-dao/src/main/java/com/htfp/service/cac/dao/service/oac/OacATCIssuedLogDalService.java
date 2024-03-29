package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacATCIssuedLogMapper;
import com.htfp.service.cac.dao.model.oac.ATCIssuedLogDO;
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
public class OacATCIssuedLogDalService {

    @Resource
    private OacATCIssuedLogMapper oacAtcIssuedLogMapper;

    public ATCIssuedLogDO queryATCIssuedLog(Long id) {
        return oacAtcIssuedLogMapper.selectById(id);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlightPlanId(Long replyFlightPlanId) {
        return oacAtcIssuedLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlyId(Long replyFlyId) {
        return oacAtcIssuedLogMapper.selectByReplyFlyId(replyFlyId);
    }

    public List<ATCIssuedLogDO> queryATCIssuedLogByCpnAndAtcType(String cpn, Integer atcType) {
        return oacAtcIssuedLogMapper.selectByCpnAndAtcType(cpn, atcType);
    }

    public int insertATCIssuedLog(ATCIssuedLogDO atcIssuedLogDO) {
        return oacAtcIssuedLogMapper.insertAtcIssuedLog(atcIssuedLogDO);
    }

    public int updateATCIssuedLog(ATCIssuedLogDO atcIssuedLogDO) {
        return oacAtcIssuedLogMapper.updateByATCIssuedLog(atcIssuedLogDO);
    }

    public int updateATCIssuedLogDelivered(ATCIssuedLogDO atcIssuedLogDO, Integer atcDelivered) {
        atcIssuedLogDO.setAtcDelivered(atcDelivered);
        atcIssuedLogDO.setGmtModify(new Date());
        return updateATCIssuedLog(atcIssuedLogDO);
    }


    public int deleteATCIssuedLogById(Long id) {
        return oacAtcIssuedLogMapper.deleteById(id);
    }

    public ATCIssuedLogDO buildATCIssuedLog(Long replyFlightPlanId, Long replyFlyId, String cpn, Integer atcType, String atcSpecificPosition, String atcEffectTime, Integer atcLimitPeriod, String atcOperator, Integer atcDelivered) {
        ATCIssuedLogDO atcIssuedLog = new ATCIssuedLogDO();
        atcIssuedLog.setReplyFlightPlanId(replyFlightPlanId);
        atcIssuedLog.setReplyFlyId(replyFlyId);
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
