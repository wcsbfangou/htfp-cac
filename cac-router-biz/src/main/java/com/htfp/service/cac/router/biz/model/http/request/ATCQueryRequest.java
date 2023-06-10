package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/2/20
 * @Description 描述
 */
@Data
public class ATCQueryRequest implements BaseValidate<ErrorCodeEnum> {

    private String uavId;
    private String gcsId;
    private String applyFlightPlanId;
    private String applyFlyId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (StringUtils.isBlank(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(applyFlyId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLY_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
