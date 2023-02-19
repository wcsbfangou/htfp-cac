package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacAlarmIssuedLogMapper;
import com.htfp.service.cac.dao.model.oac.AlarmIssuedLogDO;
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
public class OacAlarmIssuedLogDalService {

    @Resource
    private OacAlarmIssuedLogMapper oacAlarmIssuedLogMapper;

    public AlarmIssuedLogDO queryAlarmIssuedLog(Long id) {
        return oacAlarmIssuedLogMapper.selectById(id);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByReplyFlightPlanId(Long replyFlightPlanId) {
        return oacAlarmIssuedLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByReplyFlyId(Long replyFlyId) {
        return oacAlarmIssuedLogMapper.selectByReplyFlyId(replyFlyId);
    }

    public List<AlarmIssuedLogDO> queryAlarmIssuedLogByCpnAndAlarmLevel(String cpn, Integer alarmLevel) {
        return oacAlarmIssuedLogMapper.selectByCpnAndAlarmLevel(cpn, alarmLevel);
    }

    public int insertAlarmIssuedLog(AlarmIssuedLogDO alarmIssuedLogDO) {
        return oacAlarmIssuedLogMapper.insertAlarmIssuedLog(alarmIssuedLogDO);
    }

    public int updateAlarmIssuedLog(AlarmIssuedLogDO alarmIssuedLogDO) {
        return oacAlarmIssuedLogMapper.updateByAlarmIssuedLog(alarmIssuedLogDO);
    }

    public int updateAlarmIssuedLogDelivered(AlarmIssuedLogDO alarmIssuedLogDO, Integer delivered) {
        alarmIssuedLogDO.setAlarmDelivered(delivered);
        alarmIssuedLogDO.setGmtModify(new Date());
        return updateAlarmIssuedLog(alarmIssuedLogDO);
    }


    public int deleteAlarmIssuedLogById(Long id) {
        return oacAlarmIssuedLogMapper.deleteById(id);
    }

    public AlarmIssuedLogDO buildAlarmIssuedLog(Long replyFlightPlanId, Long replyFlyId, String cpn, Integer alarmLevel, String alarmContent, String alarmEffectTime, String alarmOperator, Integer alarmDelivered) {
        AlarmIssuedLogDO alarmIssuedLog = new AlarmIssuedLogDO();
        alarmIssuedLog.setReplyFlightPlanId(replyFlightPlanId);
        alarmIssuedLog.setReplyFlyId(replyFlyId);
        alarmIssuedLog.setCpn(cpn);
        alarmIssuedLog.setAlarmLevel(alarmLevel);
        alarmIssuedLog.setAlarmContent(alarmContent);
        alarmIssuedLog.setAlarmEffectTime(alarmEffectTime);
        alarmIssuedLog.setAlarmOperator(alarmOperator);
        alarmIssuedLog.setAlarmDelivered(alarmDelivered);
        alarmIssuedLog.setGmtCreate(new Date());
        alarmIssuedLog.setGmtModify(new Date());
        return alarmIssuedLog;
    }
}
