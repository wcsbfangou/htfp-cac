package com.htfp.service.cac.router.biz.model.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 14:51
 */
@Data
public class SignOutRequest implements BaseRequest<ErrorCodeEnum>{

    private String gcsId;
    private String gcsIp;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
