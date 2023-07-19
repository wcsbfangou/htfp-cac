package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryVideoStreamAddressResultParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/13
 * @Description 描述
 */
@Data
public class QueryVideoStreamAddressResponse extends BaseResponse {

    private QueryVideoStreamAddressResultParam queryVideoStreamAddressResultParam;

}
