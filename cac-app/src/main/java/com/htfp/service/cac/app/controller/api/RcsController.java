package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.utils.HttpUtils;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.router.biz.model.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.router.biz.model.response.RcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;
import com.htfp.service.cac.router.biz.service.IRcsService;
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
 * @Date 2022-05-17 19:46
 */

@Slf4j
@Controller
@RequestMapping("/rcs")
public class RcsController {

    @Resource
    private HttpValidator httpValidator;

    @Resource(name = "rcsServiceImpl")
    private IRcsService rcsService;

    /**
     * 远程地面站注册
     *
     * @param signInRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsSignIn(@RequestBody SignInRequest signInRequest, HttpServletRequest httpServletRequest) {
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
            // 注册远程地面站
            SignInResponse signInResponse = rcsService.rcsSignIn(signInRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(signInResponse.getCode())) {
                return BaseHttpResponse.fail(signInResponse.getCode(), signInResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("远程地面站注册失败, signInRequest={}", signInRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 远程地面站注销
     *
     * @param signOutRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsSignOut(@RequestBody SignOutRequest signOutRequest, HttpServletRequest httpServletRequest) {
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
            // 注销远程地面站
            SignOutResponse signOutResponse = rcsService.rcsSignOut(signOutRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(signOutResponse.getCode())) {
                return BaseHttpResponse.fail(signOutResponse.getCode(), signOutResponse.getMessage());
            }
        } catch (Exception e) {
            log.error("远程地面站注销失败, signOutRequest={}", signOutRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }

    /**
     * 远程地面站指控指令执行
     *
     * @param rcsControlUavRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/controlUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsControlUav(@RequestBody RcsControlUavRequest rcsControlUavRequest, HttpServletRequest httpServletRequest) {
        BaseHttpResponse httpResponse = BaseHttpResponse.success();
        try {
            // 校验
            ErrorCodeEnum httpRequestValidateResult = httpValidator.httpRequestValidate(rcsControlUavRequest, httpServletRequest);
            if (!ErrorCodeEnum.SUCCESS.equals(httpRequestValidateResult)) {
                return BaseHttpResponse.fail(httpRequestValidateResult);
            }
            // 执行地面站指控指令
            RcsControlUavResponse rcsControlUavResponse = rcsService.rcsControlUav(rcsControlUavRequest);
            if (!ErrorCodeEnum.SUCCESS.getCode().equals(rcsControlUavResponse.getCode())) {
                return BaseHttpResponse.fail(rcsControlUavResponse.getCode(), rcsControlUavResponse.getMessage());
            }
            httpResponse.setData(JsonUtils.object2Json(rcsControlUavResponse.getCommandUavResultParamList()));
        } catch (Exception e) {
            log.error("无人机状态变更失败, rcsControlUavRequest={}", rcsControlUavRequest, e);
            return BaseHttpResponse.fail(ErrorCodeEnum.UNKNOWN_ERROR);
        }
        return httpResponse;
    }
}
