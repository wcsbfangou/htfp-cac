package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.service.oac.OacApplyFlightPlanLogDalService;
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
@RequestMapping("/background/oacApplyFlightPlanLog")
public class OacApplyFlightPlanLogController {
    @Resource
    OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;

    @RequestMapping(value = "/queryApplyFlightPlanLogByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(applyFlightPlanId);
        return applyFlightPlanLogDO;
    }

    @RequestMapping(value = "/queryApplyFlightPlanLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        return applyFlightPlanLogDO;
    }

    @RequestMapping(value = "/queryApplyFlightPlanLogByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByCpn(cpn);
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/updateApplyFlightPlanLogStatusByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(applyFlightPlanId);
        int id = oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(applyFlightPlanLogDO, status);
        return id;
    }

    @RequestMapping(value = "/updateApplyFlightPlanLogStatusByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        int id = oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(applyFlightPlanLogDO, status);
        return id;
    }
}
