package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.app.validator.BaseValidator;
import com.htfp.service.cac.router.biz.model.request.*;
import com.htfp.service.cac.app.model.BaseHttpResponse;
import com.htfp.service.cac.router.biz.service.IGcsService;
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
 * @Date 2022-05-17 19:32
 */

@Slf4j
@Controller
@RequestMapping("/gcs")
public class GcsController {

    @Resource(name = "httpValidator")
    private BaseValidator httpValidator;

    @Resource(name = "gcsServiceImpl")
    private IGcsService gcsService;

    /**
     * 地面站注册
     * @param signInRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsSignIn(@RequestBody SignInRequest signInRequest, HttpServletRequest httpServletRequest) {

        return null;
    }

    /**
     * 地面站注销
     * @param signOutRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsSignOut(@RequestBody SignOutRequest signOutRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    /**
     * 地面站在控无人机更新
     * @param gcsChangeUavRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/changeUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsChangeUav(@RequestBody GcsChangeUavRequest gcsChangeUavRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    /**
     * 无人机状态变更
     * @param uavStatusChangeRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/uavStatusChange", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse uavStatusChange(@RequestBody UavStatusChangeRequest uavStatusChangeRequest, HttpServletRequest httpServletRequest) {
        return null;
    }

    /**
     * 地面站指控指令执行
     * @param gcsControlUavRequest
     * @param httpServletRequest
     * @return
     */
    @RequestMapping(value = "/controlUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse gcsControlUav(@RequestBody GcsControlUavRequest gcsControlUavRequest, HttpServletRequest httpServletRequest) {
        return null;
    }
}
