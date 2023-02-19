package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class AlarmIssuedRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private String flyId;
    private Integer infoType;
    private String content;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(flightPlanId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (StringUtils.isBlank(flyId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLY_ID;
        } else if (infoType == null) {
            return ErrorCodeEnum.LACK_OF_ALARM_TYPE;
        } else if (content == null) {
            return ErrorCodeEnum.LACK_OF_ALARM_CONTENT;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
