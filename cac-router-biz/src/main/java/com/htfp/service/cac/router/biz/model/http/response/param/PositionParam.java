package com.htfp.service.cac.router.biz.model.http.response.param;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
@Data
public class PositionParam {

    private Integer lng;
    private Integer lat;
    private Integer alt;

}
