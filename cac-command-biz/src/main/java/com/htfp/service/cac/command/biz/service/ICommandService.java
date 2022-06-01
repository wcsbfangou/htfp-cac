package com.htfp.service.cac.command.biz.service;

import com.htfp.service.cac.command.biz.model.response.GcsChangeControlUavResponse;
import com.htfp.service.cac.command.biz.model.resquest.GcsChangeControlUavRequest;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 21:16
 */
public interface ICommandService {

    /**
     * 地面站在控无人机变更
     * @param gcsChangeControlUavRequest
     * @return
     */
    GcsChangeControlUavResponse gcsChangeUav(GcsChangeControlUavRequest gcsChangeControlUavRequest);
}
