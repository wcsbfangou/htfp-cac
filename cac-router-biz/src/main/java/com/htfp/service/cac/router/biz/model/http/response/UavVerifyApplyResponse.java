package com.htfp.service.cac.router.biz.model.http.response;

import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.param.UavVerifyResultParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class UavVerifyApplyResponse extends BaseResponse {

    private UavVerifyResultParam uavVerifyResultParam;

}
