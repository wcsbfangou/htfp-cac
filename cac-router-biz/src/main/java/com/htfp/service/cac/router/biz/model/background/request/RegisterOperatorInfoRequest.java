package com.htfp.service.cac.router.biz.model.background.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
@Data
public class RegisterOperatorInfoRequest implements BaseValidate<ErrorCodeEnum> {

    String operatorId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(operatorId)) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
