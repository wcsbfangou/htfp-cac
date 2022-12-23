package com.htfp.service.oac.client.response;

import com.htfp.service.oac.client.response.param.FlyQueryResultParam;
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