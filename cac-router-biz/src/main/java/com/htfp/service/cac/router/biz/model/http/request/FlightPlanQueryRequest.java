package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlightPlanQueryRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String applyFlightPlanId;

    @Override
    public ErrorCodeEnum validate() {
        if(StringUtils.isBlank(applyFlightPlanId)){
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
