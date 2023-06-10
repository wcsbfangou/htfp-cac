package com.htfp.service.cac.router.biz.model.http.response;

import com.htfp.service.cac.router.biz.model.BaseResponse;
import com.htfp.service.cac.router.biz.model.http.response.param.AlarmQueryResultParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class AlarmQueryResponse extends BaseResponse {

    private List<AlarmQueryResultParam> alarmQueryResultParamList;

}
