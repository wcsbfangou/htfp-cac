package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacDynamicRouteInfoMapper;
import com.htfp.service.cac.dao.model.oac.DynamicRouteInfoDO;
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
public class OacDynamicRouteInfoDalService {
    @Resource
    private OacDynamicRouteInfoMapper oacDynamicRouteInfoMapper;

    public DynamicRouteInfoDO queryDynamicRouteInfo(Long id){
        return oacDynamicRouteInfoMapper.selectById(id);
    }

    public DynamicRouteInfoDO queryDynamicRouteInfoByReplyFlyId(Long flyId){
        return oacDynamicRouteInfoMapper.selectByReplyFlyId(flyId);
    }

    public DynamicRouteInfoDO queryDynamicRouteInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicRouteInfoMapper.selectByReplyFlightPlanId(replyFlightPlanId);
    }

    public List<DynamicRouteInfoDO> queryDynamicRouteInfoByCpn(String cpn){
        List<DynamicRouteInfoDO> dynamicRouteInfoDOList = oacDynamicRouteInfoMapper.selectByCpn(cpn);
        if(CollectionUtils.isNotEmpty(dynamicRouteInfoDOList)){
            return dynamicRouteInfoDOList;
        } else {
            return null;
        }
    }

    public int insertDynamicRouteInfo(DynamicRouteInfoDO dynamicUavInfoDO){
        return oacDynamicRouteInfoMapper.insertDynamicRouteInfo(dynamicUavInfoDO);
    }

    public int updateDynamicRouteInfo(DynamicRouteInfoDO dynamicUavInfoDO){
        return oacDynamicRouteInfoMapper.updateByDynamicRouteInfo(dynamicUavInfoDO);
    }

    public int deleteDynamicRouteInfo(Long id){
        return oacDynamicRouteInfoMapper.deleteById(id);
    }

    public int deleteDynamicRouteInfoByReplyFlightPlanId(Long replyFlightPlanId){
        return oacDynamicRouteInfoMapper.deleteByReplyFlightPlanId(replyFlightPlanId);
    }

    public int updateDynamicRouteInfoPlanStatus(DynamicRouteInfoDO dynamicUavInfoDO, Integer planStatus){
        dynamicUavInfoDO.setPlanStatus(planStatus);
        dynamicUavInfoDO.setGmtModify(new Date());
        return updateDynamicRouteInfo(dynamicUavInfoDO);
    }

    public DynamicRouteInfoDO buildDynamicRouteInfoDO(Long replyFlightPlanId, Long replyFlyId, String uavName, String cpn, String routePointCoordinates, Integer currentLegStartLng, Integer currentLegStartLat, Integer currentLegStartAlt,
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
