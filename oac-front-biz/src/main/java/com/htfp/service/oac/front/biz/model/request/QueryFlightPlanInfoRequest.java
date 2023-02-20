package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class QueryFlightPlanInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private Integer uavPlanStatus;


    @Override
    public ErrorCodeEnum validate() {
        if (FlightPlanStatusTypeEnum.getFromCode(uavPlanStatus) != null) {
            return ErrorCodeEnum.LACK_OF_PLAN_STATUS;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
