package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlyQueryRequest implements BaseValidate<ErrorCodeEnum> {

    private String replyFlyId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(replyFlyId)) {
            return ErrorCodeEnum.LACK_OF_REPLY_FLY_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}