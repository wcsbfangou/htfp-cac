package com.htfp.service.cac.common.utils.http;

import lombok.Builder;
import lombok.Getter;

import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022-06-02 16:44
 */
@Getter
@Builder(builderMethodName = "of", buildMethodName = "create")
public class HttpContentWrapper {

    private Long gcsId;
    private String gcsToken;
    private String authorization;
    private String contentType;
    /**
     * 支持的编码类型，如："gzip,deflate"
     */
    private String acceptEncoding;
    private String contentEncode;
    private Object contentObject;
    /**
     * 调用者的接口名称
     */
    private String interfaceName;
}
