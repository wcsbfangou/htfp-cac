package com.htfp.service.cac.router.biz.service.http;

import com.htfp.service.cac.router.biz.model.http.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.response.RcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 9:47
 */
public interface IRcsService {

    /**
     * 远程地面站注册
     * @param signInRequest
     * @return
     */
    SignInResponse rcsSignIn(SignInRequest signInRequest);

    /**
     * 远程地面站注销
     * @param signOutRequest
     * @return
     */
    SignOutResponse rcsSignOut(SignOutRequest signOutRequest);


    /**
     * 远程地面站指控指令执行
     * @param rcsControlUavRequest
     * @return
     */
    RcsControlUavResponse rcsControlUav(RcsControlUavRequest rcsControlUavRequest);
}
