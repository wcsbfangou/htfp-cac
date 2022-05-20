package com.htfp.service.cac.app.controller.api;

import com.htfp.service.cac.router.biz.model.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.app.model.BaseHttpResponse;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author sunjipeng
 * @Date 2022-05-17 19:46
 */

@Slf4j
@Controller
@RequestMapping("/rcs")
public class RcsController {
    @RequestMapping(value = "/signIn", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsSignIn(@RequestBody SignInRequest signInRequest, HttpServletRequest request) {

        return null;
    }
    @RequestMapping(value = "/signOut", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsSignOut(@RequestBody SignOutRequest signOutRequest, HttpServletRequest request) {
        return null;
    }
    @RequestMapping(value = "/controlUav", method = RequestMethod.POST)
    @ResponseBody
    public BaseHttpResponse rcsControlUav(@RequestBody RcsControlUavRequest rcsControlUavRequest, HttpServletRequest request) {
        return null;
    }
}
