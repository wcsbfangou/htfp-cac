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
public class CancelUavInfoRequest implements BaseValidate<ErrorCodeEnum> {

    String uavId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
