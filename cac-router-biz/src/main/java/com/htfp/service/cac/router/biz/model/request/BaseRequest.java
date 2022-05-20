package com.htfp.service.cac.router.biz.model.request;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:31
 */
public interface BaseRequest<T> {
    /**
     * 参数校验
     * @return
     */
    T validate();
}
