package com.htfp.service.cac.common.utils;

import com.htfp.service.cac.common.constant.HttpHeaderConstant;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;

import javax.servlet.http.HttpServletRequest;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 16:22
 * @Description HTTP工具类
 */
@Slf4j
public class HttpUtils {

    /**
     * 获取http请求的IP地址
     *
     * @param httpServletRequest
     * @return
     */
    public static String getIpAddress(HttpServletRequest httpServletRequest) {
        try {
            String forwardIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_FORWARDED_IP);
            if (StringUtils.isNotEmpty(forwardIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(forwardIp)) {
                // 多次反向代理后会有多个ip值，第一个ip才是真实的ip
                int index = forwardIp.indexOf(",");
                if (index != -1) {
                    return forwardIp.substring(0, index);
                } else {
                    return forwardIp;
                }
            }
            String realIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_REAL_IP);
            if (StringUtils.isNotEmpty(realIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(realIp)) {
                return realIp;
            }
            String proxyClientIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_PROXY_CLIENT);
            if (StringUtils.isNotEmpty(proxyClientIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(proxyClientIp)) {
                return proxyClientIp;
            }
            String wlProxyClientIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_WL_PROXY_CLIENT);
            if (StringUtils.isNotEmpty(wlProxyClientIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(wlProxyClientIp)) {
                return wlProxyClientIp;
            }
            String httpClientIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_HTTP_CLIENT_IP);
            if (StringUtils.isNotEmpty(httpClientIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(httpClientIp)) {
                return httpClientIp;
            }
            String httpXForwardIp = httpServletRequest.getHeader(HttpHeaderConstant.HTTP_HEADER_HTTP_X_FORWARDED_FOR);
            if (StringUtils.isNotEmpty(httpXForwardIp) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(httpXForwardIp)) {
                return httpXForwardIp;
            }
            String remoteAddress = httpServletRequest.getRemoteAddr();
            if (StringUtils.isNotEmpty(remoteAddress) && !HttpHeaderConstant.UNKNOWN.equalsIgnoreCase(remoteAddress)) {
                return remoteAddress;
            }
        } catch (Exception e) {
            log.error("通过http请求头获取IP失败，httpServletRequest={}", httpServletRequest, e);
            return null;
        }
        return null;
    }
}
