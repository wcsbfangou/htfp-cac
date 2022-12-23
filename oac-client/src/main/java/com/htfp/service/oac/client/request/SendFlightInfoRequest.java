package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.request.param.PositionParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class SendFlightInfoRequest {

    private String cpn;
    private Integer atcType;
    private String content;
    private String effectTime;
    private Integer limitTime;
    private PositionParam specificPosition;

}
