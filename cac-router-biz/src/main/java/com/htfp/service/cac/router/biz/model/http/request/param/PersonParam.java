package com.htfp.service.cac.router.biz.model.http.request.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.IdCardTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
@Data
public class PersonParam implements BaseValidate<ErrorCodeEnum> {

    private String personName;
    private String nationality;
    private Integer idCardType;
    private String idCardNumber;
    private String licenseId;
    private Integer illegalRecordType;
    private String contactPhone;
    private String contactEmail;
    private String memo;

    @Override
    public ErrorCodeEnum validate() {
        if (IdCardTypeEnum.getFromCode(idCardType)==null) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_TYPE;
        } else if (StringUtils.isBlank(idCardNumber)) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER;
        } else if (StringUtils.isBlank(personName)) {
            return ErrorCodeEnum.LACK_OF_REAL_NAME;
        } else if (StringUtils.isBlank(licenseId)) {
            return ErrorCodeEnum.LACK_OF_LICENSE_ID;
        } else if (StringUtils.isBlank(contactPhone)) {
            return ErrorCodeEnum.LACK_OF_PHONE_NUMBER;
        } else if (StringUtils.isBlank(nationality)) {
            return ErrorCodeEnum.LACK_OF_NATIONALITY;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
