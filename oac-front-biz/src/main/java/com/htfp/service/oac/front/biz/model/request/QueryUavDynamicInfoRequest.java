package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.UavDynamicInfoQueryStatusEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryUavDynamicInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private Integer queryUavDynamicInfoStatus;
    private Boolean inAlarm;
    private String takeoffAirportId;
    private String landingAirportId;


    @Override
    public ErrorCodeEnum validate() {
        if (UavDynamicInfoQueryStatusEnum.getFromCode(queryUavDynamicInfoStatus) == null) {
            return ErrorCodeEnum.LACK_OF_PLAN_STATUS;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}