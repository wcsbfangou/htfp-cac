package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.DynamicRouteInfoMapper;
import com.htfp.service.oac.dao.model.DynamicRouteInfoDO;
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
public class DynamicRouteInfoDalService {
    @Resource
    DynamicRouteInfoMapper dynamicRouteInfoMapper;

    public DynamicRouteInfoDO queryDynamicRouteInfo(Long id){
        return dynamicRouteInfoMapper.selectById(id);
    }

    public DynamicRouteInfoDO queryDynamicRouteInfoByReplyFlyId(Long flyId){
        return dynamicRouteInfoMapper.selectByReplyFlyId(flyId);
    }

    public DynamicRouteInfoDO queryDynamicRouteInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return dynamicRouteInfoMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<DynamicRouteInfoDO> queryDynamicRouteInfoByCpn(String cpn){
        List<DynamicRouteInfoDO> dynamicRouteInfoDOList = dynamicRouteInfoMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(dynamicRouteInfoDOList)){
            return dynamicRouteInfoDOList;
        } else {
            return null;
        }
    }

    public int insertDynamicRouteInfo(DynamicRouteInfoDO dynamicUavInfoDO){
        return dynamicRouteInfoMapper.insertDynamicRouteInfo(dynamicUavInfoDO);
    }

    public int updateDynamicRouteInfo(DynamicRouteInfoDO dynamicUavInfoDO){
        return dynamicRouteInfoMapper.updateByDynamicRouteInfo(dynamicUavInfoDO);
    }

    public int deleteDynamicRouteInfo(Long id){
        return dynamicRouteInfoMapper.deleteById(id);
    }

    public int deleteDynamicRouteInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return dynamicRouteInfoMapper.deleteByReplyFlightPlanId(replyFlightPlanId);
    }

    public int updateDynamicRouteInfoPlanStatus(DynamicRouteInfoDO dynamicUavInfoDO, Integer planStatus){
        dynamicUavInfoDO.setPlanStatus(planStatus);
        dynamicUavInfoDO.setGmtModify(new Date());
        return updateDynamicRouteInfo(dynamicUavInfoDO);
    }

    public DynamicRouteInfoDO buildDynamicUavInfoDO(Long replyFlightPlanId, Long replyFlyId, String uavName, String cpn, String routePointCoordinates, Integer currentLegStartLng, Integer currentLegStartLat, Integer currentLegStartAlt,
                                                  Integer currentLegEndLng, Integer currentLegEndLat, Integer currentLegEndAlt, String takeoffSite, String landingSite, Integer planStatus) {
        DynamicRouteInfoDO dynamicRouteInfoDO = new DynamicRouteInfoDO();
        dynamicRouteInfoDO.setReplyFlightPlanId(replyFlightPlanId);
        dynamicRouteInfoDO.setReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setUavName(uavName);
        dynamicRouteInfoDO.setCpn(cpn);
        dynamicRouteInfoDO.setRoutePointCoordinates(routePointCoordinates);
        dynamicRouteInfoDO.setCurrentLegStartLng(currentLegStartLng);
        dynamicRouteInfoDO.setCurrentLegStartLat(currentLegStartLat);
        dynamicRouteInfoDO.setCurrentLegStartAlt(currentLegStartAlt);
        dynamicRouteInfoDO.setCurrentLegEndLng(currentLegEndLng);
        dynamicRouteInfoDO.setCurrentLegEndLat(currentLegEndLat);
        dynamicRouteInfoDO.setCurrentLegEndAlt(currentLegEndAlt);
        dynamicRouteInfoDO.setTakeoffSite(takeoffSite);
        dynamicRouteInfoDO.setLandingSite(landingSite);
        dynamicRouteInfoDO.setPlanStatus(planStatus);
        dynamicRouteInfoDO.setGmtCreate(new Date());
        dynamicRouteInfoDO.setGmtModify(new Date());
        return dynamicRouteInfoDO;
    }
}
