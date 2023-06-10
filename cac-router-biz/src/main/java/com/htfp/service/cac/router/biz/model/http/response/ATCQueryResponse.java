package com.htfp.service.cac.router.biz.model.http.response;

import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.param.ATCQueryResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class ATCQueryResponse extends BaseResponse {

    // private List<ATCQueryResultParam> atcQueryResultParamList;
    ATCQueryResultParam atcQueryResultParam;

}