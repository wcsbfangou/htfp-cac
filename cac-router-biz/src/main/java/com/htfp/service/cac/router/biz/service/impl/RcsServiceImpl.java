package com.htfp.service.cac.router.biz.service.impl;

import com.htfp.service.cac.router.biz.model.request.RcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;
import com.htfp.service.cac.router.biz.service.IRcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:15
 */
@Slf4j
@Service
public class RcsServiceImpl implements IRcsService {
    /**
     * 远程地面站注册
     *
     * @param signInRequest
     * @return
     */
    @Override
    public SignInResponse rcsSignIn(SignInRequest signInRequest) {
        return null;
    }

    /**
     * 远程地面站注销
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse rcsSignOut(SignOutRequest signOutRequest) {
        return null;
    }

    /**
     * 远程地面站指控指令执行
     *
     * @param rcsControlUavRequest
     * @return
     */
    @Override
    public GcsControlUavResponse rcsControlUav(RcsControlUavRequest rcsControlUavRequest) {
        return null;
    }
}
