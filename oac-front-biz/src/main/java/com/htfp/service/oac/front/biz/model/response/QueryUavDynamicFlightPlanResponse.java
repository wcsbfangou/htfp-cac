package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryUavDynamicFlightPlanResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/6/8
 * @Description 描述
 */
@Data
public class QueryUavDynamicFlightPlanResponse extends BaseResponse{
    private List<QueryUavDynamicFlightPlanResultParam> queryUavDynamicFlightPlanResultParamList;
}
