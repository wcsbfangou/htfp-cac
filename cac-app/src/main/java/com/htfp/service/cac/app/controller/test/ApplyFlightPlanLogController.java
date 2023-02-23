package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.service.ApplyFlightPlanLogDalService;
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
@RequestMapping("/background/applyFlightPlanLog")
public class ApplyFlightPlanLogController {

    @Resource
    ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    @RequestMapping(value = "/queryApplyFlightPlanLogByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(applyFlightPlanId));
        return applyFlightPlanLogDO;
    }

    @RequestMapping(value = "/queryApplyFlightPlanLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public ApplyFlightPlanLogDO queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(replyFlightPlanId);
        return applyFlightPlanLogDO;
    }

    @RequestMapping(value = "/queryApplyFlightPlanLogByUavId", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByUavId(@RequestParam(value = "uavId") String uavId) {
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogDalService.queryApplyFlightPlanLogByUavId(Long.valueOf(uavId));
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/queryApplyFlightPlanLogByCpn", method = RequestMethod.POST)
    @ResponseBody
    public List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogByCpn(@RequestParam(value = "cpn") String cpn) {
        List<ApplyFlightPlanLogDO> applyFlightPlanLogDOList = applyFlightPlanLogDalService.queryApplyFlightPlanLogByCpn(cpn);
        return applyFlightPlanLogDOList;
    }

    @RequestMapping(value = "/updateApplyFlightPlanLogStatusByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(applyFlightPlanId));
        int id = applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(applyFlightPlanLogDO, status);
        return id;
    }

    @RequestMapping(value = "/updateApplyFlightPlanLogStatusByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public Integer queryApplyFlightPlanLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId, Integer status) {
        ApplyFlightPlanLogDO applyFlightPlanLogDO = applyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(replyFlightPlanId);
        int id = applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(applyFlightPlanLogDO, status);
        return id;
    }

}
