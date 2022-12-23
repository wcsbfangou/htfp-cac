package com.htfp.service.cac.router.biz.model.http.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlyReplyRequest {

    private String applyFlyId;
    private String replyFlyId;
    private Boolean flyPass;
}
