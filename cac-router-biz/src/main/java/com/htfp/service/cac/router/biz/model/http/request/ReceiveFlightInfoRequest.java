package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.router.biz.model.http.request.param.PositionParam;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class ReceiveFlightInfoRequest {

    private String uavId;
    private String uavReg;
    private Integer atcType;
    private String content;
    private String effectTime;
    private Integer limitTime;
    private PositionParam specificPosition;

}
