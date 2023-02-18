package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryAlarmMessageInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private String flyId;
    private List<String> alarmIds;


    @Override
    public ErrorCodeEnum validate() {
        if (cpn == null) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (flyId == null) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLY_ID;
        } else if (flightPlanId == null) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else if (CollectionUtils.isEmpty(alarmIds)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLIGHT_PLAN_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
