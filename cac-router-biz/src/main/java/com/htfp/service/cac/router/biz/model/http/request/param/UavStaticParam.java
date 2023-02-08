package com.htfp.service.cac.router.biz.model.http.request.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/1/11
 * @Description 描述
 */
@Data
public class UavStaticParam {

    private Integer imsi;
    private Integer imei;
    private String phoneNumber;
    private Integer powerType;
    private Integer horizontalPositionAcc;
    private Integer verticalPositionAcc;
    private Integer totalPositionAcc;

}
