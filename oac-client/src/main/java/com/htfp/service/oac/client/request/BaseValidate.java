package com.htfp.service.oac.client.request;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:31
 */
public interface BaseValidate<T> {
    /**
     * 参数校验
     *
     * @return
     */
    T validate();
}
