package com.htfp.service.oac.biz.model.inner.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
@Data
public class CancelOperatorInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String operatorUniId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(operatorUniId)) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
