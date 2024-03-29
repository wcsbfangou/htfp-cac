package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryUavDynamicInfoResultParam;
import lombok.Data;

import java.util.List;


/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */

@Data
public class QueryUavDynamicInfoResponse extends BaseResponse {

    private List<QueryUavDynamicInfoResultParam> queryUavDynamicInfoResultParamList;
}
