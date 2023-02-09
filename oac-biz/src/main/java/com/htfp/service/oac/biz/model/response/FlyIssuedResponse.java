package com.htfp.service.oac.biz.model.response;

import com.htfp.service.oac.client.response.BaseResponse;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/8
 * @Description 描述
 */
@Data
public class FlyIssuedResponse extends BaseResponse {
    private String cpn;
}
