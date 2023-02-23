package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.ATCIssuedLogDO;
import com.htfp.service.cac.dao.service.oac.OacATCIssuedLogDalService;
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
@RequestMapping("/background/oacAtcIssuedLog")
public class OacATCIssuedLogController {
    @Resource
    OacATCIssuedLogDalService oacATCIssuedLogDalService;

    @RequestMapping(value = "/queryATCIssuedLogByReplyFlightPlanId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlightPlanId(@RequestParam(value = "replyFlightPlanId") String replyFlightPlanId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = oacATCIssuedLogDalService.queryATCIssuedLogByReplyFlightPlanId(Long.valueOf(replyFlightPlanId));
        return atcIssuedLogDOList;
    }

    @RequestMapping(value = "/queryATCIssuedLogByReplyFlyId", method = RequestMethod.POST)
    @ResponseBody
    public List<ATCIssuedLogDO> queryATCIssuedLogByReplyFlyId(@RequestParam(value = "replyFlyId") String replyFlyId) {
        List<ATCIssuedLogDO> atcIssuedLogDOList = oacATCIssuedLogDalService.queryATCIssuedLogByReplyFlightPlanId(Long.valueOf(replyFlyId));
        return atcIssuedLogDOList;
    }


    @RequestMapping(value = "/updateATCIssuedLogDeliveredById", method = RequestMethod.POST)
    @ResponseBody
    public Integer updateATCIssuedLogDeliveredByApplyFlightPlanId(@RequestParam(value = "id") String id, Integer delivered) {
        ATCIssuedLogDO atcIssuedLogDO = oacATCIssuedLogDalService.queryATCIssuedLog(Long.valueOf(id));
        int result = oacATCIssuedLogDalService.updateATCIssuedLogDelivered(atcIssuedLogDO, delivered);
        return result;
    }
}
