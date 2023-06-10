package com.htfp.service.cac.router.biz.model.inner.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/2/9
 * @Description 描述
 */
@Data
public class ATCSendRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String applyFlightPlanId;
    private String applyFlyId;
    private String replyFlightPlanId;
    private String replyFlyId;
    private Integer atcType;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(applyFlyId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLY_ID;
        } else if (StringUtils.isBlank(replyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(replyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLY_ID;
        } else if (atcType == null) {
            return ErrorCodeEnum.LACK_OF_ATC_TYPE;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
