package com.htfp.service.cac.router.biz.service;

import com.htfp.service.cac.router.biz.model.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;

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
    GcsControlUavResponse rcsControlUav(RcsControlUavRequest rcsControlUavRequest);
}
