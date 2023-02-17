package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.CoordinateParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryUavRouteInfoResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryUavRouteInfoResponse extends BaseResponse {

    private List<QueryUavRouteInfoResultParam> queryUavRouteInfoResultParamList;
}
