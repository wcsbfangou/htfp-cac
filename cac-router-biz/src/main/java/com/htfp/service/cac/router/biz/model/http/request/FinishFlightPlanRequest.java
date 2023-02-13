package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FinishFlightPlanRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private String applyFlightPlanId;
    private Integer totalRoutePoint;
    private Integer currentRoutePointIndex;
    private Boolean isOver;
    private String message;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (StringUtils.isBlank(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (totalRoutePoint == null || currentRoutePointIndex == null || isOver == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
