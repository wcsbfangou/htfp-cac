package com.htfp.service.oac.client.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class SendAtcRequest {

    private String cpn;
    private Integer infoType;
    private String content;
    private String effectTime;
}
