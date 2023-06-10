package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryFlightPlanInfoResultParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class QueryFlightPlanInfoResponse extends BaseResponse {

    private QueryFlightPlanInfoResultParam queryFlightPlanInfoResultParam;
}
