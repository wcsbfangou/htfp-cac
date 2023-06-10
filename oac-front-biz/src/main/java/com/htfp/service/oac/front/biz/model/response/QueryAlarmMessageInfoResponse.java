package com.htfp.service.oac.front.biz.model.response;

import com.htfp.service.oac.front.biz.model.response.param.QueryAlarmMessageInfoResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryAlarmMessageInfoResponse extends BaseResponse {

    List<QueryAlarmMessageInfoResultParam> queryAlarmMessageInfoResultParamList;
}
