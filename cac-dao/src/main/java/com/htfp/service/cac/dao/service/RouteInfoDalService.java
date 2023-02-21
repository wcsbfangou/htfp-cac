package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.entity.RouteInfoMapper;
import com.htfp.service.cac.dao.model.entity.RouteInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/21
 * @Description 描述
 */
@Slf4j
@Service
public class RouteInfoDalService {

    @Resource
    private RouteInfoMapper routeInfoMapper;

    public RouteInfoDO queryRouteInfo(Long id) {
        return routeInfoMapper.selectById(id);
    }

    public RouteInfoDO queryRouteInfoByRouteId(Long routeId) {
        return routeInfoMapper.selectById(routeId);
    }

    public List<RouteInfoDO> queryRouteInfoByRouteCode(String routeCode) {
        return routeInfoMapper.selectByRouteCode(routeCode);
    }

    public List<RouteInfoDO> queryAllRouteInfo() {
        return routeInfoMapper.selectAllRoute();
    }

    public int insertRouteInfo(RouteInfoDO routeInfo) {
        return routeInfoMapper.insertRouteInfo(routeInfo);
    }

    public int updateRouteInfo(RouteInfoDO routeInfo) {
        return routeInfoMapper.updateByRouteInfo(routeInfo);
    }

    public int updateRouteInfoStatus(RouteInfoDO routeInfo, Integer status) {
        routeInfo.setRouteStatus(status);
        routeInfo.setGmtModify(new Date());
        return updateRouteInfo(routeInfo);
    }


    public int deleteRouteInfoByRouteId(Long routeId) {
        return routeInfoMapper.deleteById(routeId);
    }


    public int deleteRouteInfoByRouteCode(String routeCode) {
        return routeInfoMapper.deleteByRouteCode(routeCode);
    }


    public RouteInfoDO buildRouteInfoDO(String routeCode, String routeName, String routePointCoordinates, Integer routeLength, String routeStartTime, String routeEndTime, Integer routeIdentificationRadius, Integer routeAlarmRadius, Integer routeLevel, Long routeOperatorId, Integer routeStatus) {
        RouteInfoDO routeInfo = new RouteInfoDO();
        routeInfo.setRouteCode(routeCode);
        routeInfo.setRouteName(routeName);
        routeInfo.setRoutePointCoordinates(routePointCoordinates);
        routeInfo.setRouteLength(routeLength);
        routeInfo.setRouteStartTime(routeStartTime);
        routeInfo.setRouteEndTime(routeEndTime);
        routeInfo.setRouteIdentificationRadius(routeIdentificationRadius);
        routeInfo.setRouteAlarmRadius(routeAlarmRadius);
        routeInfo.setRouteLevel(routeLevel);
        routeInfo.setRouteOperatorId(routeOperatorId);
        routeInfo.setRouteStatus(routeStatus);
        routeInfo.setGmtCreate(new Date());
        routeInfo.setGmtModify(new Date());
        return routeInfo;
    }
}
