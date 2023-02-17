package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryAirportInfoResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class QueryAirportInfoResponse extends BaseResponse {

    private List<QueryAirportInfoResultParam> queryAirportInfoResultParamList;
}
