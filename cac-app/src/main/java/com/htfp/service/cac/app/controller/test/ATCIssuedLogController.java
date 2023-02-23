package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.log.ATCIssuedLogDO;
import com.htfp.service.cac.dao.service.ATCIssuedLogDalService;
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
@RequestMapping("/background/atcIssuedLog")
public class ATCIssuedLogController {
    @Resource
    ATCIssuedLogDalService atcIssuedLogDalService;

    @RequestMapping(value = "/queryATCIssuedLogByApplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByApplyFlightPlanId(@RequestParam(value = "applyFlightPlanId") String applyFlightPlanId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = atcIssuedLogDalService.queryATCIssuedLogByApplyFlightPlanId(Long.valueOf(applyFlightPlanId));
        return atcIssuedLogDOList;
    }

    @RequestMapping(value = "/queryATCIssuedLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = atcIssuedLogDalService.queryATCIssuedLogByReplyFlightPlanId(replyFlightPlanId);
        return atcIssuedLogDOList;
    }

    @RequestMapping(value = "/queryATCIssuedLogByApplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByApplyFlyId(@RequestParam(value = "applyFlyId") String applyFlyId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = atcIssuedLogDalService.queryATCIssuedLogByApplyFlyId(Long.valueOf(applyFlyId));
        return atcIssuedLogDOList;
    }

    @RequestMapping(value = "/queryATCIssuedLogByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = atcIssuedLogDalService.queryATCIssuedLogByReplyFlightPlanId(replyFlyId);
        return atcIssuedLogDOList;
    }


    @RequestMapping(value = "/updateATCIssuedLogDeliveredById", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateATCIssuedLogDeliveredByApplyFlightPlanId(@RequestParam(value = "id") String id, Integer delivered) {
        ATCIssuedLogDO atcIssuedLogDO = atcIssuedLogDalService.queryATCIssuedLog(Long.valueOf(id));
        int result = atcIssuedLogDalService.updateATCIssuedLogDelivered(atcIssuedLogDO, delivered);
        return result;
    }
}
