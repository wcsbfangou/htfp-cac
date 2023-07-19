package com.htfp.service.cac.router.biz.model.inner.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/6/28
 * @Description 描述
 */
@Data
public class FlightPlanRevokeRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String applyFlightPlanId;
    private String replyFlightPlanId;
    private String revokeReason;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(replyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(revokeReason)) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
