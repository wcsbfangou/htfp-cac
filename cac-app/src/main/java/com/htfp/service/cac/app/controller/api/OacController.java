package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.oac.front.biz.model.request.ATCIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.AlarmIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAlarmMessageInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryFlightPlanInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.response.ATCIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.AlarmIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAlarmMessageInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryFlightPlanInfoResponse;
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
            // 查询无人机动态信息
            QueryUavDynamicInfoResponse queryUavDynamicInfoResponse = frontPageService.queryUavDynamicInfo(queryUavDynamicInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryUavDynamicInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryUavDynamicInfoResponse.getCode(), queryUavDynamicInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryUavDynamicInfoResponse.getQueryUavDynamicInfoResultParamList()));
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
                httpResponse.setData(JsonUtils.object2Json(queryUavRouteInfoResponse.getQueryUavRouteInfoResultParamList()));
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
                httpResponse.setData(JsonUtils.object2Json(queryAirportInfoResponse.getQueryAirportInfoResultParamList()));
            }
        } catch (Exception e) {
            log.error("查询机场信息失败, queryAirportInfoDataRequest={}", queryAirportInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 查询告警信息
     *
     * @param queryAlarmMessageInfoRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/QueryAlarmDetail", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryAlarmMessageInfo(@RequestBody QueryAlarmMessageInfoRequest queryAlarmMessageInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryAlarmMessageInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询无人机告警信息
            QueryAlarmMessageInfoResponse queryAlarmMessageInfoResponse = frontPageService.queryAlarmMessageInfoData(queryAlarmMessageInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryAlarmMessageInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryAlarmMessageInfoResponse.getCode(), queryAlarmMessageInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryAlarmMessageInfoResponse.getQueryAlarmMessageInfoParamList()));
            }
        } catch (Exception e) {
            log.error("查询告警信息失败, queryAlarmMessageInfoRequest={}", queryAlarmMessageInfoRequest, e);
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

    /**
     * 管制信息下发
     *
     * @param atcIssuedRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/SendATCMessage", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse atcIssued(@RequestBody ATCIssuedRequest atcIssuedRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(atcIssuedRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 管制信息下发
            ATCIssuedResponse atcIssuedResponse = frontPageService.atcIssued(atcIssuedRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(atcIssuedResponse.getCode())) {
                return BaseHttpResponse.fail(atcIssuedResponse.getCode(), atcIssuedResponse.getMessage());
            }else {
                httpResponse.setData(atcIssuedResponse.getCpn());
            }
        } catch (Exception e) {
            log.error("管制信息下发失败, atcIssuedRequest={}", atcIssuedRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 告警信息下发
     *
     * @param alarmIssuedRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/SendAlarmSuggestion", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse alarmIssued(@RequestBody AlarmIssuedRequest alarmIssuedRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(alarmIssuedRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 管制信息下发
            AlarmIssuedResponse alarmIssuedResponse = frontPageService.alarmIssued(alarmIssuedRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(alarmIssuedResponse.getCode())) {
                return BaseHttpResponse.fail(alarmIssuedResponse.getCode(), alarmIssuedResponse.getMessage());
            }else {
                httpResponse.setData(alarmIssuedResponse.getCpn());
            }
        } catch (Exception e) {
            log.error("告警信息下发失败, alarmIssuedRequest={}", alarmIssuedRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 查询飞行计划信息
     *
     * @param queryFlightPlanInfoRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/QueryFlightPlanInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryFlightPlanInfo(@RequestBody QueryFlightPlanInfoRequest queryFlightPlanInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryFlightPlanInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询无人机告警信息
            QueryFlightPlanInfoResponse queryFlightPlanInfoResponse = frontPageService.queryFlightPlanInfo(queryFlightPlanInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryFlightPlanInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryFlightPlanInfoResponse.getCode(), queryFlightPlanInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryFlightPlanInfoResponse.getQueryFlightPlanInfoParamList()));
            }
        } catch (Exception e) {
            log.error("查询告警信息失败, queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

}
