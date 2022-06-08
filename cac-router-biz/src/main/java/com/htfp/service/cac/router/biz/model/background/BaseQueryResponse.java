package com.htfp.service.cac.router.biz.model.background;

import com.htfp.service.cac.router.biz.model.BaseResponse;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:40
 */
@Data
public class BaseQueryResponse<T> extends BaseResponse {

    private T data;
}
