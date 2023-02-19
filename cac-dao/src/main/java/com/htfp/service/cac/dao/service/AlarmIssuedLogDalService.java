package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.log.AlarmIssuedLogMapper;
import com.htfp.service.cac.dao.model.log.AlarmIssuedLogDO;
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
public class AlarmIssuedLogDalService {

    @Resource
    private AlarmIssuedLogMapper alarmIssuedLogMapper;

    public AlarmIssuedLogDO queryAlarmIssuedLog(Long id) {
        return alarmIssuedLogMapper.selectById(id);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByApplyFlightPlanId(Long applyFlightPlanId) {
        return alarmIssuedLogMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByApplyFlyId(Long applyFlyId) {
        return alarmIssuedLogMapper.selectByApplyFlyId(applyFlyId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByReplyFlightPlanId(String replyFlightPlanId) {
        return alarmIssuedLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByReplyFlyId(String replyFlyId) {
        return alarmIssuedLogMapper.selectByReplyFlyId(replyFlyId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByCpnAndAlarmLevel(String cpn, Integer alarmLevel) {
        return alarmIssuedLogMapper.selectByCpnAndAlarmLevel(cpn, alarmLevel);
    }

    public int insertAlarmIssuedLog(AlarmIssuedLogDO alarmIssuedLogDO) {
        return alarmIssuedLogMapper.insertAlarmIssuedLog(alarmIssuedLogDO);
    }

    public int updateAlarmIssuedLog(AlarmIssuedLogDO alarmIssuedLogDO) {
        return alarmIssuedLogMapper.updateByAlarmIssuedLog(alarmIssuedLogDO);
    }

    public int updateAlarmIssuedLogDelivered(AlarmIssuedLogDO alarmIssuedLogDO, Integer delivered) {
        alarmIssuedLogDO.setAlarmDelivered(delivered);
        alarmIssuedLogDO.setGmtModify(new Date());
        return updateAlarmIssuedLog(alarmIssuedLogDO);
    }


    public int deleteAlarmIssuedLogById(Long id) {
        return alarmIssuedLogMapper.deleteById(id);
    }

    public AlarmIssuedLogDO buildAlarmIssuedLog(Long applyFlightPlanId, Long applyFlyId, String replyFlightPlanId, String replyFlyId, Long navigationId, Long uavId, String uavReg, String cpn, Integer alarmLevel, String alarmContent, String alarmEffectTime, String alarmOperator, Integer alarmDelivered) {
        AlarmIssuedLogDO alarmIssuedLog = new AlarmIssuedLogDO();
        alarmIssuedLog.setApplyFlightPlanId(applyFlightPlanId);
        alarmIssuedLog.setApplyFlyId(applyFlyId);
        alarmIssuedLog.setReplyFlightPlanId(replyFlightPlanId);
        alarmIssuedLog.setReplyFlyId(replyFlyId);
        alarmIssuedLog.setNavigationId(navigationId);
        alarmIssuedLog.setUavId(uavId);
        alarmIssuedLog.setUavReg(uavReg);
        alarmIssuedLog.setCpn(cpn);
        alarmIssuedLog.setAlarmDelivered(alarmLevel);
        alarmIssuedLog.setAlarmContent(alarmContent);
        alarmIssuedLog.setAlarmEffectTime(alarmEffectTime);
        alarmIssuedLog.setAlarmOperator(alarmOperator);
        alarmIssuedLog.setAlarmDelivered(alarmDelivered);
        alarmIssuedLog.setGmtCreate(new Date());
        alarmIssuedLog.setGmtModify(new Date());
        return alarmIssuedLog;
    }
}
