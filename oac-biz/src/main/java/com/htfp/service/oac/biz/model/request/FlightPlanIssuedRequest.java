package com.htfp.service.oac.biz.model.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import com.htfp.service.oac.client.request.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class FlightPlanIssuedRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private Boolean pass;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(flightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (pass == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
