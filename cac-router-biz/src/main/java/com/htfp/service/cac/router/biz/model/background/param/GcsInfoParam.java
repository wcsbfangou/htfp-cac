package com.htfp.service.cac.router.biz.model.background.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
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
    private String gcsReg;
    private String gcsSn;
    private Integer gcsType;
    private Integer controllableUavType;
    private Integer dataLinkType;
    private String token;
    private String operatorId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsReg)) {
            return ErrorCodeEnum.LACK_OF_GCS_REG;
        } else if (gcsType == null) {
            return ErrorCodeEnum.LACK_OF_GCS_TYPE;
        } else if (controllableUavType == null) {
            return ErrorCodeEnum.LACK_OF_CONTROLLABLE_UAV_TYPE;
        } else if (dataLinkType == null) {
            return ErrorCodeEnum.LACK_OF_DATA_LINK_TYPE;
        } else if (StringUtils.isBlank(operatorId)) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }

}
