package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.ApplyFlyLogDO;
import com.htfp.service.cac.dao.service.oac.OacApplyFlyLogDalService;
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
@RequestMapping("/background/oacApplyFlyLog")
public class OacApplyFlyLogController {
    @Resource
    OacApplyFlyLogDalService oacApplyFlyLogDalService;

    @RequestMapping(value = "/queryApplyFlyLogByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId) {
        List<ApplyFlyLogDO> applyFlyLogDOList = oacApplyFlyLogDalService.queryApplyFlyLogByApplyFlightPlanId(applyFlightPlanId);
        return applyFlyLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlyLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        List<ApplyFlyLogDO> applyFlyLogDOList = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        return applyFlyLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlyLogByApplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlyLogDO queryApplyFlyLogByApplyFlyId(@RequestParam(value = "applyFlyId") String applyFlyId) {
        ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.queryApplyFlyLogByApplyFlyId(applyFlyId);
        return applyFlyLogDO;
    }

    @RequestMapping(value = "/queryApplyFlyLogByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlyLogDO queryApplyFlyLogByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(Long.valueOf(replyFlyId));
        return applyFlyLogDO;
    }

    @RequestMapping(value = "/queryApplyFlyLogByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlyLogDO> queryApplyFlyLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<ApplyFlyLogDO> applyFlightPlanLogDOList = oacApplyFlyLogDalService.queryApplyFlyLogByCpn(cpn);
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/updateApplyFlyLogStatusByApplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateApplyFlyLogStatusByApplyFlyId(@RequestParam(value = "applyFlyId") String applyFlyId, @RequestParam(value = "status") Integer status) {
        ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.queryApplyFlyLogByApplyFlyId(applyFlyId);
        int id = oacApplyFlyLogDalService.updateApplyFlyLogStatus(applyFlyLogDO, status);
        return id;
    }

    @RequestMapping(value = "/updateApplyFlyLogStatusByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlyId") String replyFlyId, @RequestParam(value = "status") Integer status) {
        ApplyFlyLogDO applyFlyLogDO = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(Long.valueOf(replyFlyId));
        int id = oacApplyFlyLogDalService.updateApplyFlyLogStatus(applyFlyLogDO, status);
        return id;
    }
}
