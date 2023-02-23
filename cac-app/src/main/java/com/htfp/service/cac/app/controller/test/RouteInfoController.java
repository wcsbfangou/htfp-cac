package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.app.controller.test.param.RouteInfo;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.dao.model.entity.RouteInfoDO;
import com.htfp.service.cac.dao.service.RouteInfoDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/21
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/background/route")
public class RouteInfoController {

    @Resource
    RouteInfoDalService routeInfoDalService;

    /**
     * 插入route信息
     *
     * @param routeInfo
     * @return
     */
    @RequestMapping(value = "/insertRouteInfo", method = RequestMethod.POST)
    @ResponseBody
    public int insertAirportInfo(@RequestBody RouteInfo routeInfo) {

        RouteInfoDO routeInfoDO = routeInfoDalService.buildRouteInfoDO(routeInfo.getRouteCode(), routeInfo.getRouteName(), JsonUtils.object2Json(routeInfo.getRoutePointCoordinates()),
                routeInfo.getRouteLength(), routeInfo.getRouteStartTime(), routeInfo.getRouteEndTime(), routeInfo.getRouteIdentificationRadius(), routeInfo.getRouteAlarmRadius(),
                routeInfo.getRouteLevel(), Long.valueOf(routeInfo.getRouteOperatorId()), routeInfo.getRouteStatus());
        int id = routeInfoDalService.insertRouteInfo(routeInfoDO);
        return id;
    }

    /**
     * 根据routeId查询route信息
     *
     * @param routeId
     * @return
     */
    @RequestMapping(value = "/queryRouteInfoByRouteId", method = RequestMethod.POST)
    @ResponseBody
    public RouteInfoDO queryRouteInfoByRouteId(@RequestParam(value = "routeId") String routeId) {
        RouteInfoDO routeInfoDO = routeInfoDalService.queryRouteInfoByRouteId(Long.valueOf(routeId));
        return routeInfoDO;
    }

    @RequestMapping(value = "/queryRouteInfoByRouteCode", method = RequestMethod.POST)
    @ResponseBody
    public List<RouteInfoDO> queryRouteInfoByRouteCode(@RequestParam(value = "routeCode") String routeCode) {
        List<RouteInfoDO> routeInfoDOList = routeInfoDalService.queryRouteInfoByRouteCode(routeCode);
        return routeInfoDOList;
    }

    @RequestMapping(value = "/queryAllRouteInfo", method = RequestMethod.POST)
    @ResponseBody
    public List<RouteInfoDO> queryAllRouteInfo() {
        List<RouteInfoDO> routeInfoDOList = routeInfoDalService.queryAllRouteInfo();
        return routeInfoDOList;
    }
}
