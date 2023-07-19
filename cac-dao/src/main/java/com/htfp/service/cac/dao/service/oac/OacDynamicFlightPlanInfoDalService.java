package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacDynamicFlightPlanInfoMapper;
import com.htfp.service.cac.dao.model.oac.DynamicFlightPlanInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/6/9
 * @Description 描述
 */
@Slf4j
@Service
public class OacDynamicFlightPlanInfoDalService {
    @Resource
    private OacDynamicFlightPlanInfoMapper oacDynamicFlightPlanInfoMapper;

    public DynamicFlightPlanInfoDO queryDynamicFlightPlanInfo(Long id){
        return oacDynamicFlightPlanInfoMapper.selectById(id);
    }

    public DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoByReplyFlyId(Long replyFlyId){
        return oacDynamicFlightPlanInfoMapper.selectByReplyFlyId(replyFlyId);
    }

    public DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicFlightPlanInfoMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<DynamicFlightPlanInfoDO> queryDynamicUavInfoByCpn(String cpn){
        return oacDynamicFlightPlanInfoMapper.selectByCpn(cpn);
    }

    public List<DynamicFlightPlanInfoDO> queryByPlanStatusInterval(Integer littlePlanStatus, Integer bigPlanStatus){
        return oacDynamicFlightPlanInfoMapper.selectByPlanStatusInterval(littlePlanStatus, bigPlanStatus);
    }

    public List<DynamicFlightPlanInfoDO> queryByPlanStatus(Integer planStatus){
        return oacDynamicFlightPlanInfoMapper.selectByPlanStatus(planStatus);
    }

    public List<DynamicFlightPlanInfoDO> queryAllDynamicUavInfo() {
        return oacDynamicFlightPlanInfoMapper.selectAllDynamicFlightPlanInfo();
    }

    public int insertDynamicFlightPlanInfo(DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO){
        return oacDynamicFlightPlanInfoMapper.insertDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
    }

    public int updateDynamicFlightPlanInfo(DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO){
        dynamicFlightPlanInfoDO.setGmtModify(new Date());
        return oacDynamicFlightPlanInfoMapper.updateByDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
    }

    public int deleteDynamicFlightPlanInfoDO(Long id){
        return oacDynamicFlightPlanInfoMapper.deleteById(id);
    }

    public int deleteDynamicUavInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicFlightPlanInfoMapper.deleteByReplyFlightPlanId(replyFlightPlanId);
    }

    public int updateDynamicFlightPlanInfoFLowStatus(DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO, Integer flowStatus){
        dynamicFlightPlanInfoDO.setFlowStatus(flowStatus);
        dynamicFlightPlanInfoDO.setGmtModify(new Date());
        return updateDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
    }

    public int updateDynamicFlightPlanInfoPlanStatus(DynamicFlightPlanInfoDO dynamicUavInfoDO, Integer planStatus){
        dynamicUavInfoDO.setPlanStatus(planStatus);
        dynamicUavInfoDO.setGmtModify(new Date());
        return updateDynamicFlightPlanInfo(dynamicUavInfoDO);
    }

    public DynamicFlightPlanInfoDO buildDynamicFlightPlanInfoDO(Long replyFlightPlanId, Long replyFlyId, String uavName, String cpn, String pilotName, String operatorName, String flightPlanStartTime, String flightPlanEndTime,
                                                  String takeoffAirportId, String landingAirportId, Boolean isEmergency, Integer missionType, Integer planStatus, Integer flowStatus) {
        DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO = new DynamicFlightPlanInfoDO();
        dynamicFlightPlanInfoDO.setReplyFlightPlanId(replyFlightPlanId);
        dynamicFlightPlanInfoDO.setReplyFlyId(replyFlyId);
        dynamicFlightPlanInfoDO.setUavName(uavName);
        dynamicFlightPlanInfoDO.setCpn(cpn);
        dynamicFlightPlanInfoDO.setPilotName(pilotName);
        dynamicFlightPlanInfoDO.setOperatorName(operatorName);
        dynamicFlightPlanInfoDO.setFlightPlanStartTime(flightPlanStartTime);
        dynamicFlightPlanInfoDO.setFlightPlanEndTime(flightPlanEndTime);
        dynamicFlightPlanInfoDO.setTakeoffAirportId(takeoffAirportId);
        dynamicFlightPlanInfoDO.setLandingAirportId(landingAirportId);
        dynamicFlightPlanInfoDO.setIsEmergency(isEmergency);
        dynamicFlightPlanInfoDO.setMissionType(missionType);
        dynamicFlightPlanInfoDO.setPlanStatus(planStatus);
        dynamicFlightPlanInfoDO.setFlowStatus(flowStatus);
        dynamicFlightPlanInfoDO.setGmtCreate(new Date());
        dynamicFlightPlanInfoDO.setGmtModify(new Date());
        return dynamicFlightPlanInfoDO;
    }
}
