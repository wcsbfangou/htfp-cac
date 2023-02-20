package com.htfp.service.cac.router.biz.model.http.request.param;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class PositionStringParam implements BaseValidate<ErrorCodeEnum> {

    private String lng;
    private String lat;
    private String alt;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(lng)) {
            return ErrorCodeEnum.LACK_OF_LNG;
        } else if (StringUtils.isBlank(lat)) {
            return ErrorCodeEnum.LACK_OF_LAT;
        } else if (StringUtils.isBlank(alt)) {
            return ErrorCodeEnum.LACK_OF_ALT;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
