package com.htfp.service.cac.command.biz.service;

import com.htfp.service.cac.command.biz.model.response.UavChangeStatusResponse;
import com.htfp.service.cac.command.biz.model.resquest.UavChangeStatusRequest;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 21:03
 */
public interface IUavService {

    /**
     * 无人机状态变更
     * @param uavChangeStatusRequest
     * @return
     */
    UavChangeStatusResponse uavChangeStatus(UavChangeStatusRequest uavChangeStatusRequest);
}
