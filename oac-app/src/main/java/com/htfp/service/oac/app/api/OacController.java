package com.htfp.service.oac.app.api;

import com.htfp.service.oac.app.model.BaseHttpResponse;
import com.htfp.service.oac.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/operation")
public class OacController {

    @Resource(name = "flightManagementServiceImpl")
    private IFlightManagementService flightManagementService;

    /**
     * 飞行计划下发
     *
     * @param flightPlanIssuedRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/IssuedFlightPlanResult", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flightPlanIssued(@RequestBody FlightPlanIssuedRequest flightPlanIssuedRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = flightPlanIssuedRequest.validate();
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 飞行计划下发
            FlightPlanIssuedResponse flightPlanIssuedResponse = flightManagementService.flightPlanIssued(flightPlanIssuedRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flightPlanIssuedResponse.getCode())) {
                return BaseHttpResponse.fail(flightPlanIssuedResponse.getCode(), flightPlanIssuedResponse.getMessage());
            }else {
                httpResponse.setData(flightPlanIssuedResponse.getCpn());
            }
        } catch (Exception e) {
            log.error("飞行计划下发失败, flightPlanIssuedRequest={}", flightPlanIssuedRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }
}
