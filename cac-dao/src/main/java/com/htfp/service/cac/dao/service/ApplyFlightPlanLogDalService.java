package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.log.ApplyFlightPlanLogMapper;
import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
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

    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByApplyFlightPlanId(Long applyFlightPlanId){
        return applyFlightPlanLogMapper.selectByApplyFlightPlanId(applyFlightPlanId);
    }

    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByReplyFlightPlanId(String replyFlightPlanId){
        return applyFlightPlanLogMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByGcsId(Long gcsId){
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogMapper.selectByGcsId(gcsId);
        if(CollectionUtils.isNotEmpty(applyFlightPlanLogDOList)){
            return applyFlightPlanLogDOList;
        } else {
            return null;
        }
    }

    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByUavId(Long uavId){
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogMapper.selectByUavId(uavId);
        if(CollectionUtils.isNotEmpty(applyFlightPlanLogDOList)){
            return applyFlightPlanLogDOList;
        } else {
            return null;
        }
    }

    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByUavReg(String uavReg){
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogMapper.selectByUavReg(uavReg);
        if(CollectionUtils.isNotEmpty(applyFlightPlanLogDOList)){
            return applyFlightPlanLogDOList;
        } else {
            return null;
        }
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

    public int deleteApplyFlightPlanLogByApplyFlightPlanId(Long applyFlightPlanId){
        return applyFlightPlanLogMapper.deleteByApplyFlightPlanId(applyFlightPlanId);
    }

    public int updateApplyFlightPlanLogStatus(ApplyFlightPlanLogDO applyFlightPlanLogDO, Integer status){
        applyFlightPlanLogDO.setStatus(status);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogFlightPlanIdAndStatus(ApplyFlightPlanLogDO applyFlightPlanLogDO, String replyFlightPlanId, Integer status){
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setStatus(status);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanId(ApplyFlightPlanLogDO applyFlightPlanLogDO, String replyFlightPlanId){
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public int updateApplyFlightPlanLogReplyFlightPlanIdById(Long id, String replyFlightPlanId){
        ApplyFlightPlanLogDO applyFlightPlanLogDO = new ApplyFlightPlanLogDO();
        applyFlightPlanLogDO.setId(id);
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setGmtModify(new Date());
        return updateApplyFlightPlanLog(applyFlightPlanLogDO);
    }

    public ApplyFlightPlanLogDO buildApplyFlightPlanLogDO(Long applyFlightPlanId, String replyFlightPlanId, String gcsId, String uavId, String uavReg, String cpn, Integer applicantType, String applicantSubject, String pilots, String airspaceNumbers, String routePointCoordinates, String takeoffAirportId, String landingAirportId, String takeoffSite, String landingSite, Integer missionType, String startTime, String endTime, String emergencyProcedure, String operationScenarioType, Boolean isEmergency, Boolean isVlos, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = new ApplyFlightPlanLogDO();
        applyFlightPlanLogDO.setApplyFlightPlanId(applyFlightPlanId);
        applyFlightPlanLogDO.setReplyFlightPlanId(replyFlightPlanId);
        applyFlightPlanLogDO.setGcsId(gcsId);
        applyFlightPlanLogDO.setUavId(uavId);
        applyFlightPlanLogDO.setUavReg(uavReg);
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
