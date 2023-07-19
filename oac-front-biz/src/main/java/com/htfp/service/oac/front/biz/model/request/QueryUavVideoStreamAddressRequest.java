package com.htfp.service.oac.front.biz.model.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.UavDynamicInfoQueryStatusEnum;
import com.htfp.service.oac.front.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/6/13
 * @Description 描述
 */
@Data
public class QueryUavVideoStreamAddressRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
