package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/16
 * @Description 描述
 */
@Data
public class QueryUavRouteInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String flightPlanId;
    private String flyId;


    @Override
    public ErrorCodeEnum validate() {
        if (cpn == null) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
