package com.htfp.service.cac.router.biz.service.http;

import com.htfp.service.cac.router.biz.model.http.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.http.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.http.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.http.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.http.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.http.response.UavStatusChangeResponse;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 9:46
 */
public interface IGcsService {

    /**
     * 地面站注册
     * @param signInRequest
     * @return
     */
    SignInResponse gcsSignIn(SignInRequest signInRequest);

    /**
     * 地面站注销
     * @param signOutRequest
     * @return
     */
    SignOutResponse gcsSignOut(SignOutRequest signOutRequest);

    /**
     * 地面站在控无人机变更
     * @param gcsChangeUavRequest
     * @return
     */
    GcsChangeUavResponse gcsChangeUav(GcsChangeUavRequest gcsChangeUavRequest);

    /**
     * 无人机状态变更
     * @param uavStatusChangeRequest
     * @return
     */
    UavStatusChangeResponse uavStatusChange(UavStatusChangeRequest uavStatusChangeRequest);

    /**
     * 地面站指控指令执行
     * @param gcsControlUavRequest
     * @return
     */
    GcsControlUavResponse gcsControlUav(GcsControlUavRequest gcsControlUavRequest);
}
