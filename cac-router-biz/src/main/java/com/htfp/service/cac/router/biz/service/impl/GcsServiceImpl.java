package com.htfp.service.cac.router.biz.service.impl;

import com.htfp.service.cac.router.biz.model.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.service.IGcsService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:11
 */
@Slf4j
@Service
public class GcsServiceImpl implements IGcsService {
    /**
     * 地面站注册
     *
     * @param signInRequest
     * @return
     */
    @Override
    public SignInResponse gcsSignIn(SignInRequest signInRequest) {
        return null;
    }

    /**
     * 地面站注销
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse gcsSignOut(SignOutRequest signOutRequest) {
        return null;
    }

    /**
     * 地面站在控无人机变更
     *
     * @param gcsChangeUavRequest
     * @return
     */
    @Override
    public GcsChangeUavResponse gcsChangeUav(GcsChangeUavRequest gcsChangeUavRequest) {
        return null;
    }

    /**
     * 无人机状态变更
     *
     * @param uavStatusChangeRequest
     * @return
     */
    @Override
    public UavStatusChangeResponse uavStatusChange(UavStatusChangeRequest uavStatusChangeRequest) {
        return null;
    }

    /**
     * 地面站指控指令执行
     *
     * @param gcsControlUavRequest
     * @return
     */
    @Override
    public GcsControlUavResponse gcsControlUav(GcsControlUavRequest gcsControlUavRequest) {
        return null;
    }
}
