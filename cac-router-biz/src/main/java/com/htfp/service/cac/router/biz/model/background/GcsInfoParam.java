package com.htfp.service.cac.router.biz.model.background;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:47
 */
@Data
public class GcsInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private Integer typeId;
    private Integer controllableUavType;
    private Integer dataLinkType;
    private String token;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (typeId == null || controllableUavType == null || dataLinkType == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }

}
