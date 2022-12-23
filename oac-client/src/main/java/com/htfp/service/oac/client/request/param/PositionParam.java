package com.htfp.service.oac.client.request.param;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import com.htfp.service.oac.client.request.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
@Data
public class PositionParam implements BaseValidate<ErrorCodeEnum> {

    private Integer lng;
    private Integer lat;
    private Integer alt;

    @Override
    public ErrorCodeEnum validate() {
        if (lng == null) {
            return ErrorCodeEnum.LACK_OF_LNG;
        } else if (lat == null) {
            return ErrorCodeEnum.LACK_OF_LAT;
        } else if (alt == null) {
            return ErrorCodeEnum.LACK_OF_ALT;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
