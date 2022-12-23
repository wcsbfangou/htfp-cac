package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:04
 */

@Data
public class UavStatusChangeRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private Integer uavStatus;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (UavStatusEnum.getFromCode(uavStatus) == null) {
            return ErrorCodeEnum.LACK_OF_UAV_STATUS;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
