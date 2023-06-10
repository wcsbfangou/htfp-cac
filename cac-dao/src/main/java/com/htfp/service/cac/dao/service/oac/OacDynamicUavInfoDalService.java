package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacDynamicUavInfoMapper;
import com.htfp.service.cac.dao.model.oac.AirportInfoDO;
import com.htfp.service.cac.dao.model.oac.DynamicUavInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
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
public class OacDynamicUavInfoDalService {

    @Resource
    private OacDynamicUavInfoMapper oacDynamicUavInfoMapper;

    public DynamicUavInfoDO queryDynamicUavInfo(Long id){
        return oacDynamicUavInfoMapper.selectById(id);
    }

    public DynamicUavInfoDO queryDynamicUavInfoByReplyFlyId(Long replyFlyId){
        return oacDynamicUavInfoMapper.selectByReplyFlyId(replyFlyId);
    }

    public DynamicUavInfoDO queryDynamicUavInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicUavInfoMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<DynamicUavInfoDO> queryDynamicUavInfoByCpn(String cpn){
        return oacDynamicUavInfoMapper.selectByCpn(cpn);
    }

    public List<DynamicUavInfoDO> queryByPlanStatusInterval(Integer littlePlanStatus, Integer bigPlanStatus){
        return oacDynamicUavInfoMapper.selectByPlanStatusInterval(littlePlanStatus, bigPlanStatus);
    }

    public List<DynamicUavInfoDO> queryAllDynamicUavInfo() {
        return oacDynamicUavInfoMapper.selectAllDynamicUavInfo();
    }

    public int insertDynamicUavInfo(DynamicUavInfoDO dynamicUavInfoDO){
        return oacDynamicUavInfoMapper.insertDynamicUavInfo(dynamicUavInfoDO);
    }

    public int updateDynamicUavInfo(DynamicUavInfoDO dynamicUavInfoDO){
        dynamicUavInfoDO.setGmtModify(new Date());
        return oacDynamicUavInfoMapper.updateByDynamicUavInfo(dynamicUavInfoDO);
    }

    public int deleteDynamicUavInfo(Long id){
        return oacDynamicUavInfoMapper.deleteById(id);
    }

    public int deleteDynamicUavInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicUavInfoMapper.deleteByReplyFlightPlanId(replyFlightPlanId);
    }

    public int updateDynamicUavInfoUavStatus(DynamicUavInfoDO dynamicUavInfoDO, Integer uavStatus){
        dynamicUavInfoDO.setUavStatus(uavStatus);
        dynamicUavInfoDO.setGmtModify(new Date());
        return updateDynamicUavInfo(dynamicUavInfoDO);
    }

    public int updateDynamicUavInfoPlanStatus(DynamicUavInfoDO dynamicUavInfoDO, Integer planStatus){
        dynamicUavInfoDO.setPlanStatus(planStatus);
        dynamicUavInfoDO.setGmtModify(new Date());
        return updateDynamicUavInfo(dynamicUavInfoDO);
    }

    public DynamicUavInfoDO buildDynamicUavInfoDO(Long replyFlightPlanId, Long replyFlyId, String uavName, String cpn, String uavOperatorName, Integer lng, Integer lat, Integer alt,
                                                      Integer speed, Integer course, Integer fuel, Integer battery, Integer signalStrength, String updateTime, String flightPlanStartTime, String flightPlanEndTime,
                                                      String startFlyTime, String takeoffAirportId, String landingAirportId, String takeoffSite, String landingSite, Integer landingAirportIdentificationRadius, Integer landingAirportAlarmRadius,
                                                      Integer landingLng, Integer landingLat, Integer landingAlt, Integer distanceToLandingPoint, Boolean inAlarm, String alarmIds, Boolean accessSystem, Integer planStatus, Integer uavStatus) {
        DynamicUavInfoDO dynamicUavInfoDO = new DynamicUavInfoDO();
        dynamicUavInfoDO.setReplyFlightPlanId(replyFlightPlanId);
        dynamicUavInfoDO.setReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUavName(uavName);
        dynamicUavInfoDO.setCpn(cpn);
        dynamicUavInfoDO.setUavOperatorName(uavOperatorName);
        dynamicUavInfoDO.setLng(lng);
        dynamicUavInfoDO.setLat(lat);
        dynamicUavInfoDO.setAlt(alt);
        dynamicUavInfoDO.setSpeed(speed);
        dynamicUavInfoDO.setCourse(course);
        dynamicUavInfoDO.setFuel(fuel);
        dynamicUavInfoDO.setBattery(battery);
        dynamicUavInfoDO.setSignalStrength(signalStrength);
        dynamicUavInfoDO.setUpdateTime(updateTime);
        dynamicUavInfoDO.setFlightPlanStartTime(flightPlanStartTime);
        dynamicUavInfoDO.setFlightPlanEndTime(flightPlanEndTime);
        dynamicUavInfoDO.setStartFlyTime(startFlyTime);
        dynamicUavInfoDO.setTakeoffAirportId(takeoffAirportId);
        dynamicUavInfoDO.setLandingAirportId(landingAirportId);
        dynamicUavInfoDO.setTakeoffSite(takeoffSite);
        dynamicUavInfoDO.setLandingSite(landingSite);
        dynamicUavInfoDO.setLandingAirportIdentificationRadius(landingAirportIdentificationRadius);
        dynamicUavInfoDO.setLandingAirportAlarmRadius(landingAirportAlarmRadius);
        dynamicUavInfoDO.setLandingLng(landingLng);
        dynamicUavInfoDO.setLandingLat(landingLat);
        dynamicUavInfoDO.setLandingAlt(landingAlt);
        dynamicUavInfoDO.setDistanceToLandingPoint(distanceToLandingPoint);
        dynamicUavInfoDO.setInAlarm(inAlarm);
        dynamicUavInfoDO.setAlarmIds(alarmIds);
        dynamicUavInfoDO.setAccessSystem(accessSystem);
        dynamicUavInfoDO.setPlanStatus(planStatus);
        dynamicUavInfoDO.setUavStatus(uavStatus);
        dynamicUavInfoDO.setGmtCreate(new Date());
        dynamicUavInfoDO.setGmtModify(new Date());
        return dynamicUavInfoDO;
    }
}
