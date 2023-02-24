package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.DynamicRouteInfoDO;
import com.htfp.service.cac.dao.service.oac.OacDynamicRouteInfoDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/23
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/background/oacDynamicRouteInfo")
public class OacDynamicRouteController {

    @Resource
    OacDynamicRouteInfoDalService oacDynamicRouteInfoDalService;

    @RequestMapping(value = "/queryDynamicRouteInfoByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public DynamicRouteInfoDO queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        return dynamicRouteInfoDO;
    }

    @RequestMapping(value = "/queryDynamicRouteInfoByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public DynamicRouteInfoDO queryDynamicRouteInfoByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(Long.valueOf(replyFlyId));
        return dynamicRouteInfoDO;
    }

    @RequestMapping(value = "/queryDynamicRouteInfoByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<DynamicRouteInfoDO> queryApplyFlyLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<DynamicRouteInfoDO> dynamicUavInfoDOList = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByCpn(cpn);
        return dynamicUavInfoDOList;
    }

    @RequestMapping(value = "/updateDynamicRouteInfoStatusByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateDynamicUavInfoStatusByApplyFlyId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId, @RequestParam(value = "planStatus") Integer planStatus) {
        DynamicRouteInfoDO dynamicUavInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        int id = oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(dynamicUavInfoDO, planStatus);
        return id;
    }

    @RequestMapping(value = "/updateDynamicRouteInfoStatusByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateDynamicUavInfoStatusByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId, @RequestParam(value = "planStatus") Integer planStatus) {
        DynamicRouteInfoDO dynamicUavInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(Long.valueOf(replyFlyId));
        int id = oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(dynamicUavInfoDO, planStatus);
        return id;
    }
}
