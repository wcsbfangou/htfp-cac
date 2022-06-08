package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.router.biz.model.background.PilotInfoParam;
import com.htfp.service.cac.router.biz.model.background.PilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.QueryPilotInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 14:45
 */
@Slf4j
@Controller
@RequestMapping("/background/pilot")
public class PilotInfoController {

    @Resource
    private HttpValidator httpValidator;

    @Resource
    private PilotDalService pilotDalService;


    @RequestMapping(value = "/queryPilotInfoByPilotId", method = RequestMethod.POST)
    @ResponseBody
    public QueryPilotInfoResponse queryPilotByPilotId(@RequestParam(value = "pilotId") String pilotId) {
        QueryPilotInfoResponse queryPilotInfoResponse = new QueryPilotInfoResponse();
        queryPilotInfoResponse.fail();
        try {
            if (StringUtils.isBlank(pilotId)) {
                queryPilotInfoResponse.fail(ErrorCodeEnum.LACK_OF_PILOT_ID);
            }
            PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(Long.valueOf(pilotId));
            if (pilotInfo != null) {
                PilotInfoParam pilotInfoParam = new PilotInfoParam();
                pilotInfoParam.setPilotId(pilotInfo.getPilotId().toString());
                pilotInfoParam.setPilotName(pilotInfo.getPilotName());
                pilotInfoParam.setControllableUavType(pilotInfo.getControllableUavType());
                queryPilotInfoResponse.setData(pilotInfoParam);
                queryPilotInfoResponse.success();
            }
        } catch (Exception e) {
            log.error("查询驾驶员信息异常, pilotId={}", pilotId, e);
        }
        return queryPilotInfoResponse;
    }

    @RequestMapping(value = "/updatePilotInfoControllableUavType", method = RequestMethod.POST)
    @ResponseBody
    public boolean updatePilotInfoControllableUavType(@RequestParam(value = "pilotId") String pilotId, @RequestParam(value = "controllableUavType") Integer controllableUavType) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(pilotId) && controllableUavType != null) {
                PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(Long.valueOf(pilotId));
                int id = pilotDalService.updatePilotInfoControllableUavType(pilotInfo, controllableUavType);
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("更新驾驶员可控无人机类型异常, pilotId={}, controllableUavType={}", pilotId, controllableUavType, e);
        }
        return result;
    }

    @RequestMapping(value = "/insertPilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean insertPilotInfo(@RequestBody PilotInfoRequest pilotInfoRequest) {
        boolean result = false;
        try {
            if (ErrorCodeEnum.SUCCESS.equals(httpValidator.httpRequestValidate(pilotInfoRequest))) {
                PilotInfoDO pilotInfoDO = pilotDalService.buildPilotInfoDO(Long.valueOf(pilotInfoRequest.getPilotId()), pilotInfoRequest.getPilotName(), pilotInfoRequest.getControllableUavType());
                int id = pilotDalService.insertPilotInfo(pilotInfoDO);
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("插入驾驶员信息异常, pilotInfoRequest={}", pilotInfoRequest, e);
        }
        return result;
    }

    @RequestMapping(value = "/deletePilotInfoByPilotId", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePilotInfoByPilotId(@RequestParam(value = "pilotId") String pilotId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(pilotId)) {
                int id = pilotDalService.deletePilotInfoByPilotId(Long.valueOf(pilotId));
                if (id > 0) {
                    result = true;
                }
            }

        } catch (Exception e) {
            log.error("删除驾驶员信息异常, pilotId={}", pilotId, e);
        }
        return result;
    }
}
