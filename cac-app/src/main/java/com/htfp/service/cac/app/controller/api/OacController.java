package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavRouteInfoResponse;
import com.htfp.service.oac.front.biz.service.IFrontPageService;
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

    @Resource(name = "frontPageServiceImpl")
    private IFrontPageService frontPageService;

    /**
     * 查询无人机动态信息
     *
     * @param queryUavDynamicInfoRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryUavDynamicInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryUavDynamicInfo(@RequestBody QueryUavDynamicInfoRequest queryUavDynamicInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryUavDynamicInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 飞行计划下发
            QueryUavDynamicInfoResponse queryUavDynamicInfoResponse = frontPageService.queryUavDynamicInfo(queryUavDynamicInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryUavDynamicInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryUavDynamicInfoResponse.getCode(), queryUavDynamicInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryUavDynamicInfoResponse.getQueryUavDynamicInfoResultParam()));
            }
        } catch (Exception e) {
            log.error("查询无人机动态失败, queryUavDynamicInfoRequest={}", queryUavDynamicInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 查询无人机航线信息
     *
     * @param queryUavRouteInfoRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryUavRouteInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryUavRouteInfo(@RequestBody QueryUavRouteInfoRequest queryUavRouteInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryUavRouteInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询无人机航线信息
            QueryUavRouteInfoResponse queryUavRouteInfoResponse = frontPageService.queryUavRouteInfo(queryUavRouteInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryUavRouteInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryUavRouteInfoResponse.getCode(), queryUavRouteInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryUavRouteInfoResponse.getQueryUavRouteInfoResultParam()));
            }
        } catch (Exception e) {
            log.error("查询无人机航线信息失败, queryUavRouteInfoRequest={}", queryUavRouteInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 查询机场信息
     *
     * @param queryAirportInfoRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryAirportInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryAirportInfo(@RequestBody QueryAirportInfoRequest queryAirportInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryAirportInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询无人机动态信息
            QueryAirportInfoResponse queryAirportInfoResponse = frontPageService.queryAirportInfoData(queryAirportInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryAirportInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryAirportInfoResponse.getCode(), queryAirportInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryAirportInfoResponse.getQueryAirportInfoResultParam()));
            }
        } catch (Exception e) {
            log.error("查询机场信息失败, queryAirportInfoDataRequest={}", queryAirportInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

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
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(flightPlanIssuedRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 飞行计划下发
            FlightPlanIssuedResponse flightPlanIssuedResponse = frontPageService.flightPlanIssued(flightPlanIssuedRequest);
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

    /**
     * 放飞申请结果下发
     *
     * @param flyIssuedRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/AuthorizedTakeoff", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flyIssued(@RequestBody FlyIssuedRequest flyIssuedRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(flyIssuedRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 放飞申请结果下发
            FlyIssuedResponse flyIssuedResponse = frontPageService.flyIssued(flyIssuedRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flyIssuedResponse.getCode())) {
                return BaseHttpResponse.fail(flyIssuedResponse.getCode(), flyIssuedResponse.getMessage());
            }else {
                httpResponse.setData(flyIssuedResponse.getCpn());
            }
        } catch (Exception e) {
            log.error("放飞申请结果下发失败, flyIssuedRequest={}", flyIssuedRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }
}
