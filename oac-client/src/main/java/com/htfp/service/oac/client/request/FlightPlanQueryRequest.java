package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlightPlanQueryRequest implements BaseValidate<ErrorCodeEnum> {

    private String replyFlightPlanId;

    @Override
    public ErrorCodeEnum validate() {
        if(StringUtils.isBlank(replyFlightPlanId)){
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
