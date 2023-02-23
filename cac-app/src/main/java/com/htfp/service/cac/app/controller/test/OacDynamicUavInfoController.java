package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.DynamicUavInfoDO;
import com.htfp.service.cac.dao.service.oac.OacDynamicUavInfoDalService;
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
@RequestMapping("/background/oacDynamicUavInfo")
public class OacDynamicUavInfoController {

    @Resource
    OacDynamicUavInfoDalService oacDynamicUavInfoDalService;

    @RequestMapping(value = "/queryDynamicUavInfoByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public DynamicUavInfoDO queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        return dynamicUavInfoDO;
    }

    @RequestMapping(value = "/queryDynamicUavInfoByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public DynamicUavInfoDO queryApplyFlightPlanLogByApplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(Long.valueOf(replyFlyId));
        return dynamicUavInfoDO;
    }

    @RequestMapping(value = "/queryDynamicUavInfoByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<DynamicUavInfoDO> queryApplyFlyLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<DynamicUavInfoDO> dynamicUavInfoDOList = oacDynamicUavInfoDalService.queryDynamicUavInfoByCpn(cpn);
        return dynamicUavInfoDOList;
    }

    @RequestMapping(value = "/queryDynamicUavInfoByInterval", method = RequestMethod.POST)
    @ResponseBody
    public List<DynamicUavInfoDO> queryApplyFlyLogByCpn(@RequestParam(value = "littlePlanStatus") Integer littlePlanStatus, @RequestParam(value = "bigPlanStatus") Integer bigPlanStatus) {
        List<DynamicUavInfoDO> dynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(littlePlanStatus, bigPlanStatus);
        return dynamicUavInfoDOList;
    }

    @RequestMapping(value = "/updateDynamicUavInfoStatusByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateDynamicUavInfoStatusByApplyFlyId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId, Integer planStatus) {
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        int id = oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(dynamicUavInfoDO, planStatus);
        return id;
    }

    @RequestMapping(value = "/updateDynamicUavInfoStatusByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateDynamicUavInfoStatusByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId, Integer planStatus) {
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(replyFlyId));
        int id = oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(dynamicUavInfoDO, planStatus);
        return id;
    }
}
