package com.htfp.service.cac.app.validator;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:52
 * @Description 校验接口
 */
public interface BaseValidator<R, T> {
    /**
     * 参数校验
     *
     * @param data
     * @return
     */
    R validate(T data);
}
