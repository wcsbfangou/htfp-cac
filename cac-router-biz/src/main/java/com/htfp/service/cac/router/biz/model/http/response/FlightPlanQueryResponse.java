package com.htfp.service.cac.router.biz.model.http.response;

import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.param.FlightPlanQueryResultParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlightPlanQueryResponse extends BaseResponse {

    private FlightPlanQueryResultParam flightPlanQueryResultParam;

}
