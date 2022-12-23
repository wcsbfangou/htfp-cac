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
public class FinishFlightPlanRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String replyFlightPlanId;
    private Integer totalRoutePoint;
    private Integer currentRoutePointIndex;
    private Boolean isOver;
    private String message;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(replyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (totalRoutePoint == null || currentRoutePointIndex == null || isOver == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
