package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/18
 * @Description 描述
 */
@Data
public class DeleteOperatorInfoRequest implements BaseValidate<ErrorCodeEnum> {

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
