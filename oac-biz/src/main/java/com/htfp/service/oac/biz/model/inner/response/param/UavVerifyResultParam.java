package com.htfp.service.oac.biz.model.inner.response.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Data
public class UavVerifyResultParam {

    private String applyUavVerifyId;
    private String replyUavVerifyId;
    private Boolean uavVerifyPass;

}
