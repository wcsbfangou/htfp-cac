package com.htfp.service.oac.biz.model.inner.response;

import com.htfp.service.oac.biz.model.inner.param.FlyQueryResultParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlyQueryResponse extends BaseResponse {

    private FlyQueryResultParam flyQueryResultParam;

}