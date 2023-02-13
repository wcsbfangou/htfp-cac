package com.htfp.service.oac.front.biz.model;

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
