package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class QueryAirportInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String airportId;

    @Override
    public ErrorCodeEnum validate() {
        if (airportId == null) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
