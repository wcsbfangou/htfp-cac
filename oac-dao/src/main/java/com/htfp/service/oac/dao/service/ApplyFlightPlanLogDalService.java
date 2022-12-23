package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.ApplyFlightPlanLogMapper;
import com.htfp.service.oac.dao.model.ApplyFlightPlanLogDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Slf4j
@Service
public class ApplyFlightPlanLogDalService {

    @Resource
    ApplyFlightPlanLogMapper applyFlightPlanLogMapper;

    public ApplyFlightPlanLogDO queryApplyFlightPlanLog(Long id){
        return applyFlightPlanLogMapper.selectById(id);
    }

    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByApplyFlightPlanId(String applyFlightPlanId){
        return applyFlightPlanLogMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByReplyFlightPlanId(Long replyFlightPlanId){
        return applyFlightPlanLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByCpn(String cpn){
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(applyFlightPlanLogDOList)){
            return applyFlightPlanLogDOList;
        } else {
            return null;
        }
    }

    public int insertApplyFlightPlanLog(ApplyFlightPlanLogDO applyFlightPlanLogDO){
        return applyFlightPlanLogMapper.insertApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLog(ApplyFlightPlanLogDO applyFlightPlanLogDO){
        return applyFlightPlanLogMapper.updateByApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int deleteApplyFlightPlanLog(Long id){
        return applyFlightPlanLogMapper.deleteById(id);
    }

    public int deleteApplyFlightPlanLogByReplyFlightPlanId(Long replyFlightPlanId){
        return applyFlightPlanLogMapper.deleteByReplyFlightPlanId(replyFlightPlanId);
    }

    public int updateApplyFlightPlanLogStatus(ApplyFlightPlanLogDO applyFlightPlanLogDO, Integer status){
        applyFlightPlanLogDO.setStatus(status);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogFlightPlanIdAndStatus(ApplyFlightPlanLogDO applyFlightPlanLogDO, Long replyFlightPlanId, Integer status){
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setStatus(status);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanId(ApplyFlightPlanLogDO applyFlightPlanLogDO, Long replyFlightPlanId){
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanIdById(Long id, Long replyFlightPlanId){
        ApplyFlightPlanLogDO applyFlightPlanLogDO = new ApplyFlightPlanLogDO();
        applyFlightPlanLogDO.setId(id);
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public ApplyFlightPlanLogDO buildApplyFlightPlanLogDO(String applyFlightPlanId, Long replyFlightPlanId, String cpn, Integer applicantType, String applicantSubject, String pilots, String airspaceNumbers,
                                                          String routePointCoordinates, String takeoffAirportId, String landingAirportId, String takeoffSite, String landingSite, Integer missionType,
                                                          String startTime, String endTime, String emergencyProcedure, String operationScenarioType, Boolean isEmergency, Boolean isVlos, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = new ApplyFlightPlanLogDO();
        applyFlightPlanLogDO.setApplyFlightPlanId(applyFlightPlanId);
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setCpn(cpn);
        applyFlightPlanLogDO.setApplicantType(applicantType);
        applyFlightPlanLogDO.setApplicantSubject(applicantSubject);
        applyFlightPlanLogDO.setPilots(pilots);
        applyFlightPlanLogDO.setAirspaceNumbers(airspaceNumbers);
        applyFlightPlanLogDO.setRoutePointCoordinates(routePointCoordinates);
        applyFlightPlanLogDO.setTakeoffAirportId(takeoffAirportId);
        applyFlightPlanLogDO.setLandingAirportId(landingAirportId);
        applyFlightPlanLogDO.setTakeoffSite(takeoffSite);
        applyFlightPlanLogDO.setLandingSite(landingSite);
        applyFlightPlanLogDO.setMissionType(missionType);
        applyFlightPlanLogDO.setStartTime(startTime);
        applyFlightPlanLogDO.setEndTime(endTime);
        applyFlightPlanLogDO.setEmergencyProcedure(emergencyProcedure);
        applyFlightPlanLogDO.setOperationScenarioType(operationScenarioType);
        applyFlightPlanLogDO.setIsEmergency(isEmergency);
        applyFlightPlanLogDO.setIsVlos(isVlos);
        applyFlightPlanLogDO.setStatus(status);
        applyFlightPlanLogDO.setGmtCreate(new Date());
        applyFlightPlanLogDO.setGmtModify(new Date());
        return applyFlightPlanLogDO;
    }
}
