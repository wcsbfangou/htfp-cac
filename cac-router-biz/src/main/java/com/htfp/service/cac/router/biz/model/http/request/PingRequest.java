package com.htfp.service.cac.router.biz.model.http.request;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-06-01 22:57
 */

@Data
public class PingRequest {

    String gcsId;
    String echoToken;
}
