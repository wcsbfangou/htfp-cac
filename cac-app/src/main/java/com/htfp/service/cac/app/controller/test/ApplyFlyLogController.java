package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.log.ApplyFlyLogDO;
import com.htfp.service.cac.dao.service.ApplyFlyLogDalService;
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
@RequestMapping("/background/applyFlyLog")
public class ApplyFlyLogController {
    @Resource
    ApplyFlyLogDalService applyFlyLogDalService;

    @RequestMapping(value = "/queryApplyFlyLogByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId) {
        List<ApplyFlyLogDO> applyFlyLogDOList = applyFlyLogDalService.queryApplyFlyLogByApplyFlightPlanId(Long.valueOf(applyFlightPlanId));
        return applyFlyLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlyLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        List<ApplyFlyLogDO> applyFlyLogDOList = applyFlyLogDalService.queryApplyFlyLogByReplyFlightPlanId(replyFlightPlanId);
        return applyFlyLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlyLogByApplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlyLogDO queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "applyFlyId") String applyFlyId) {
        ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.queryApplyFlyLogByApplyFlyId(Long.valueOf(applyFlyId));
        return applyFlyLogDO;
    }

    @RequestMapping(value = "/queryApplyFlyLogByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlyLogDO queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
        return applyFlyLogDO;
    }

    @RequestMapping(value = "/queryApplyFlyLogByUavId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByUavId(@RequestParam(value = "uavId") String uavId) {
        List<ApplyFlyLogDO> applyFlightPlanLogDOList = applyFlyLogDalService.queryApplyFlyLogByUavId(Long.valueOf(uavId));
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlyLogByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<ApplyFlyLogDO> applyFlightPlanLogDOList = applyFlyLogDalService.queryApplyFlyLogByCpn(cpn);
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/updateApplyFlyLogStatusByApplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateApplyFlyLogStatusByApplyFlyId(@RequestParam(value = "applyFlyId") String applyFlyId, @RequestParam(value = "status")Integer status) {
        ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.queryApplyFlyLogByApplyFlyId(Long.valueOf(applyFlyId));
        int id = applyFlyLogDalService.updateApplyFlyLogStatus(applyFlyLogDO, status);
        return id;
    }

    @RequestMapping(value = "/updateApplyFlyLogStatusByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlyId") String replyFlyId, @RequestParam(value = "status")Integer status) {
        ApplyFlyLogDO applyFlyLogDO = applyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
        int id = applyFlyLogDalService.updateApplyFlyLogStatus(applyFlyLogDO, status);
        return id;
    }
}
