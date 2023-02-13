package com.htfp.service.oac.biz.model.inner.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/18
 * @Description 描述
 */
@Data
public class DeleteUavInfoRequest implements BaseValidate<ErrorCodeEnum> {

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
