package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryFlightPlanInfoParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class QueryFlightPlanInfoResponse extends BaseResponse {

    List<QueryFlightPlanInfoParam> queryFlightPlanInfoParamList;
}
