package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 14:50
 */

@Data
public class SignInRequest implements BaseValidate<ErrorCodeEnum> {

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
