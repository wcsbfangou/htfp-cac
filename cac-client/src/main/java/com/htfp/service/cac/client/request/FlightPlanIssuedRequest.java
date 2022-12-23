package com.htfp.service.cac.client.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
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
    private String applyFlightPlanId;
    private String replyFlightPlanId;
    private Boolean pass;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(replyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (pass == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
