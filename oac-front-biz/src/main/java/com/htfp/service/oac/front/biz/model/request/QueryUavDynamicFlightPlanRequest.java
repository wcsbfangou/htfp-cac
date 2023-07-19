package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.UavDynamicFlightPlanInfoQueryStatusEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/6/8
 * @Description 描述
 */
@Data
public class QueryUavDynamicFlightPlanRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private Integer queryUavDynamicFlightPlanStatus;
    private String takeoffAirportId;
    private String landingAirportId;
    private Boolean isEmergency;


    @Override
    public ErrorCodeEnum validate() {
        if (UavDynamicFlightPlanInfoQueryStatusEnum.getFromCode(queryUavDynamicFlightPlanStatus) == null) {
            return ErrorCodeEnum.LACK_OF_PLAN_STATUS;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
