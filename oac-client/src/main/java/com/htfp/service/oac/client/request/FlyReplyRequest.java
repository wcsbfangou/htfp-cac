package com.htfp.service.oac.client.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlyReplyRequest {

    private String ApplyFlyId;
    private String replyFlyId;
    private Boolean flyPass;
}
