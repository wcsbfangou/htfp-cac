package com.htfp.service.oac.biz.model.response;

import com.htfp.service.oac.client.response.BaseResponse;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class FlightPlanIssuedResponse extends BaseResponse {

    private String cpn;

}
