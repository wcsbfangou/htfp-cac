package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.utils.HttpUtils;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlightPlanQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.request.FlyQueryRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavVerifyApplyRequest;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlightPlanQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyApplyResponse;
import com.htfp.service.cac.router.biz.model.http.response.FlyQueryResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavVerifyApplyResponse;
import com.htfp.service.cac.router.biz.service.http.IGcsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import javax.servlet.http.HttpServletRequest;

/**
 * @Author sunjipeng
 * @Date 2022-05-17 19:32
 */

@Slf4j
@Controller
@RequestMapping("/gcs")
public class GcsController {

    @Resource
    private HttpValidator httpValidator;

    @Resource(name = "gcsServiceImpl")
    private IGcsService gcsService;

    /**
     * 地面站上线
     *
     * @param signInRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsSignIn(@RequestBody SignInRequest signInRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(signInRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 请求体若无IP, 从请求头获取
            if (StringUtils.isBlank(signInRequest.getGcsIp())) {
                String gcsIp = HttpUtils.getIpAddress(httpServletRequest);
                if (StringUtils.isBlank(gcsIp)) {
                    return BaseHttpResponse.fail(ErrorCodeEnum.LACK_OF_GCS_IP);
                } else {
                    signInRequest.setGcsIp(gcsIp);
                }
            }
            // 地面站上线
            SignInResponse signInResponse = gcsService.gcsSignIn(signInRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(signInResponse.getCode())) {
                return BaseHttpResponse.fail(signInResponse.getCode(), signInResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("地面站上线失败, signInRequest={}", signInRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 地面站下线
     *
     * @param signOutRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsSignOut(@RequestBody SignOutRequest signOutRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(signOutRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 请求体若无IP, 从请求头获取
            if (StringUtils.isBlank(signOutRequest.getGcsIp())) {
                String gcsIp = HttpUtils.getIpAddress(httpServletRequest);
                if (StringUtils.isBlank(gcsIp)) {
                    return BaseHttpResponse.fail(ErrorCodeEnum.LACK_OF_GCS_IP);
                } else {
                    signOutRequest.setGcsIp(gcsIp);
                }
            }
            // 注销地面站
            SignOutResponse signOutResponse = gcsService.gcsSignOut(signOutRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(signOutResponse.getCode())) {
                return BaseHttpResponse.fail(signOutResponse.getCode(), signOutResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("地面站下线失败, signOutRequest={}", signOutRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 飞行计划申请
     *
     * @param flightPlanApplyRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/flightPlanApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flightPlanApply(@RequestBody FlightPlanApplyRequest flightPlanApplyRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(flightPlanApplyRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 飞行计划申请
            FlightPlanApplyResponse flightPlanApplyResponse = gcsService.flightPlanApply(flightPlanApplyRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flightPlanApplyResponse.getCode())) {
                return BaseHttpResponse.fail(flightPlanApplyResponse.getCode(), flightPlanApplyResponse.getMessage());
            } else {
                httpResponse.setData(flightPlanApplyResponse.getApplyFlightPlanId());
            }
        } catch (Exception e) {
            log.error("飞行计划申请失败, flightPlanApplyRequest={}", flightPlanApplyRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }


    /**
     * 飞行计划查询
     *
     * @param flightPlanQueryRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/flightPlanQuery", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flightPlanQuery(@RequestBody FlightPlanQueryRequest flightPlanQueryRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(flightPlanQueryRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 飞行计划查询
            FlightPlanQueryResponse flightPlanQueryResponse = gcsService.flightPlanQuery(flightPlanQueryRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flightPlanQueryResponse.getCode())) {
                return BaseHttpResponse.fail(flightPlanQueryResponse.getCode(), flightPlanQueryResponse.getMessage());
            } else {
                httpResponse.setData(JsonUtils.object2Json(flightPlanQueryResponse.getFlightPlanQueryResultParam()));
            }
        } catch (Exception e) {
            log.error("飞行计划查询失败, flightPlanQueryRequest={}", flightPlanQueryRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 无人机接入校验
     *
     * @param uavVerifyApplyRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/uavVerifyApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse uavVerifyApply(@RequestBody UavVerifyApplyRequest uavVerifyApplyRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(uavVerifyApplyRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 无人机接入校验申请
            UavVerifyApplyResponse uavVerifyApplyResponse = gcsService.uavVerifyApply(uavVerifyApplyRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(uavVerifyApplyResponse.getCode())) {
                return BaseHttpResponse.fail(uavVerifyApplyResponse.getCode(), uavVerifyApplyResponse.getMessage());
            } else {
                httpResponse.setData(JsonUtils.object2Json(uavVerifyApplyResponse.getUavVerifyResultParam()));
            }
        } catch (Exception e) {
            log.error("无人机接入校验失败, uavVerifyApplyRequest={}", uavVerifyApplyRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 地面站在控无人机更新
     *
     * @param gcsChangeUavRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changeUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsChangeUav(@RequestBody GcsChangeUavRequest gcsChangeUavRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(gcsChangeUavRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 更新在控无人机
            GcsChangeUavResponse gcsChangeUavResponse = gcsService.gcsChangeUav(gcsChangeUavRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(gcsChangeUavResponse.getCode())) {
                return BaseHttpResponse.fail(gcsChangeUavResponse.getCode(), gcsChangeUavResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("地面站更新在控无人机失败, gcsChangeUavRequest={}", gcsChangeUavRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 无人机状态变更
     *
     * @param uavStatusChangeRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/uavStatusChange", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse uavStatusChange(@RequestBody UavStatusChangeRequest uavStatusChangeRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(uavStatusChangeRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 无人机状态变更
            UavStatusChangeResponse uavStatusChangeResponse = gcsService.uavStatusChange(uavStatusChangeRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(uavStatusChangeResponse.getCode())) {
                return BaseHttpResponse.fail(uavStatusChangeResponse.getCode(), uavStatusChangeResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("无人机状态变更失败, uavStatusChangeRequest={}", uavStatusChangeRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 地面站指控指令执行
     *
     * @param gcsControlUavRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/controlUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsControlUav(@RequestBody GcsControlUavRequest gcsControlUavRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(gcsControlUavRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 执行地面站指控指令
            GcsControlUavResponse gcsControlUavResponse = gcsService.gcsControlUav(gcsControlUavRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(gcsControlUavResponse.getCode())) {
                return BaseHttpResponse.fail(gcsControlUavResponse.getCode(), gcsControlUavResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("无人机状态变更失败, gcsControlUavRequest={}", gcsControlUavRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 放飞申请
     *
     * @param flyApplyRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/flyApply", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flyApply(@RequestBody FlyApplyRequest flyApplyRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(flyApplyRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 放飞申请
            FlyApplyResponse flyApplyResponse = gcsService.flyApply(flyApplyRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flyApplyResponse.getCode())) {
                return BaseHttpResponse.fail(flyApplyResponse.getCode(), flyApplyResponse.getMessage());
            } else {
                httpResponse.setData(flyApplyResponse.getApplyFlyId());
            }
        } catch (Exception e) {
            log.error("放飞申请失败, flyApplyRequest={}", flyApplyRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }


    /**
     * 放飞结果查询
     *
     * @param flyQueryRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/flyQuery", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse flyQuery(@RequestBody FlyQueryRequest flyQueryRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(flyQueryRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 放飞结果查询
            FlyQueryResponse flyQueryResponse = gcsService.flyQuery(flyQueryRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(flyQueryResponse.getCode())) {
                return BaseHttpResponse.fail(flyQueryResponse.getCode(), flyQueryResponse.getMessage());
            } else {
                httpResponse.setData(JsonUtils.object2Json(flyQueryResponse.getFlyQueryResultParam()));
            }
        } catch (Exception e) {
            log.error("飞行计划查询失败, flyQueryRequest={}", flyQueryRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }
}
