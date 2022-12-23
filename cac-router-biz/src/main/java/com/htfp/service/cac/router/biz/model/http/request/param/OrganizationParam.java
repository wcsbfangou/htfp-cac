package com.htfp.service.cac.router.biz.model.http.request.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.OrganizationTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
@Data
public class OrganizationParam implements BaseValidate<ErrorCodeEnum > {

    private Integer orgType;
    private String orgName;
    private String socialCreditCode;
    private String contactName;
    private String contactPhone;
    private String contactEmail;
    private String memo;

    @Override
    public ErrorCodeEnum validate() {
        if (OrganizationTypeEnum.getFromCode(orgType)==null) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_TYPE;
        } else if (StringUtils.isBlank(orgName)) {
            return ErrorCodeEnum.LACK_OF_ORG_NAME;
        } else if (StringUtils.isBlank(socialCreditCode)) {
            return ErrorCodeEnum.LACK_OF_SOCIAL_CREDIT_CODE;
        } else if (StringUtils.isBlank(contactPhone)) {
            return ErrorCodeEnum.LACK_OF_PHONE_NUMBER;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
