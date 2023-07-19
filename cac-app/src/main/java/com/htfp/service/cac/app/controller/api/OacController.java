package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.oac.front.biz.model.request.ATCIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.AlarmIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanRevokeRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAlarmMessageInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryFlightPlanInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicFlightPlanRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavVideoStreamAddressRequest;
import com.htfp.service.oac.front.biz.model.response.ATCIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.AlarmIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanRevokeResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAlarmMessageInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryFlightPlanInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicFlightPlanResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavRouteInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryVideoStreamAddressResponse;
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
            // 查询机场信息
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
     * 查询无人机视频拉流地址
     *
     * @param queryUavVideoStreamAddressRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryUavVideoStreamAddress", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryUavVideoStreamAddress(@RequestBody QueryUavVideoStreamAddressRequest queryUavVideoStreamAddressRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryUavVideoStreamAddressRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询无人机视频拉流地址
            QueryVideoStreamAddressResponse queryVideoStreamAddressResponse = frontPageService.queryUavVideoStreamAddress(queryUavVideoStreamAddressRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryVideoStreamAddressResponse.getCode())) {
                return BaseHttpResponse.fail(queryVideoStreamAddressResponse.getCode(), queryVideoStreamAddressResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryVideoStreamAddressResponse.getQueryVideoStreamAddressResultParam()));
            }
        } catch (Exception e) {
            log.error("查询无人机视频拉流地址失败, queryUavVideoStreamAddressRequest={}", queryUavVideoStreamAddressRequest, e);
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
    @RequestMapping(value = "/queryAlarmDetail", method = RequestMethod.POST)
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
                httpResponse.setData(JsonUtils.object2Json(queryAlarmMessageInfoResponse.getQueryAlarmMessageInfoResultParamList()));
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
    @RequestMapping(value = "/issuedFlightPlanResult", method = RequestMethod.POST)
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
     * 飞行计划撤销
     *
     * @param flightPlanRevokeRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/revokeFlightPlan", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flightPlanRevoke(@RequestBody FlightPlanRevokeRequest flightPlanRevokeRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(flightPlanRevokeRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 飞行计划撤销
            FlightPlanRevokeResponse flightPlanRevokeResponse = frontPageService.flightPlanRevoke(flightPlanRevokeRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flightPlanRevokeResponse.getCode())) {
                return BaseHttpResponse.fail(flightPlanRevokeResponse.getCode(), flightPlanRevokeResponse.getMessage());
            }else {
                httpResponse.setData(flightPlanRevokeResponse.getCpn());
            }
        } catch (Exception e) {
            log.error("飞行计划撤销失败, flightPlanRevokeRequest={}", flightPlanRevokeRequest, e);
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
    @RequestMapping(value = "/authorizedTakeoff", method = RequestMethod.POST)
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
    @RequestMapping(value = "/sendATCMessage", method = RequestMethod.POST)
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
    @RequestMapping(value = "/sendAlarmSuggestion", method = RequestMethod.POST)
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
     * 查询动态飞行计划信息
     *
     * @param queryUavDynamicFlightPlanRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/queryUavDynamicFlightPlan", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryFlightPlanInfo(@RequestBody QueryUavDynamicFlightPlanRequest queryUavDynamicFlightPlanRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryUavDynamicFlightPlanRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询动态飞行计划信息
            QueryUavDynamicFlightPlanResponse queryUavDynamicFlightPlanResponse = frontPageService.queryUavDynamicFlightPlanInfo(queryUavDynamicFlightPlanRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryUavDynamicFlightPlanResponse.getCode())) {
                return BaseHttpResponse.fail(queryUavDynamicFlightPlanResponse.getCode(), queryUavDynamicFlightPlanResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryUavDynamicFlightPlanResponse.getQueryUavDynamicFlightPlanResultParamList()));
            }
        } catch (Exception e) {
            log.error("查询态飞行计划信息失败, queryUavDynamicFlightPlanRequest={}", queryUavDynamicFlightPlanRequest, e);
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
    @RequestMapping(value = "/queryFlightPlanInfo", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse queryFlightPlanInfo(@RequestBody QueryFlightPlanInfoRequest queryFlightPlanInfoRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum errorCodeEnum = ErrorCodeEnum.getFromCode(queryFlightPlanInfoRequest.validate().getCode());
            if (!ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                return BaseHttpResponse.fail(errorCodeEnum);
            }
            // 查询飞行计划信息
            QueryFlightPlanInfoResponse queryFlightPlanInfoResponse = frontPageService.queryUavFlightPlanInfo(queryFlightPlanInfoRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(queryFlightPlanInfoResponse.getCode())) {
                return BaseHttpResponse.fail(queryFlightPlanInfoResponse.getCode(), queryFlightPlanInfoResponse.getMessage());
            }else {
                httpResponse.setData(JsonUtils.object2Json(queryFlightPlanInfoResponse.getQueryFlightPlanInfoResultParam()));
            }
        } catch (Exception e) {
            log.error("查询飞行计划信息失败, queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

}
