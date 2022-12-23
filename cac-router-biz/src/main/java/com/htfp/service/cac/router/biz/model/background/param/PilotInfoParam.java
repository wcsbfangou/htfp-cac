package com.htfp.service.cac.router.biz.model.background.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.IdCardTypeEnum;
import com.htfp.service.cac.common.enums.PilotLicenseTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:46
 */
@Data
public class PilotInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String pilotId;
    /**
     * pilotCode = idCardType+'#'+idCardNumber
     */
    private String pilotCode;
    private String pilotName;
    private Integer pilotType;

    private Integer controllableUavType;

    private Integer licenseType;
    private String licenseId;
    private String licensePictureAddress;

    private Integer idCardType;
    private String idCardNumber;
    private String idCardPictureAddress;
    private Integer gender;
    private String nationality;
    private String phoneNumber;
    private String emailAddress;
    private Integer status;

    @Override
    public ErrorCodeEnum validate() {
        if (IdCardTypeEnum.getFromCode(idCardType)==null) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_TYPE;
        } else if (StringUtils.isBlank(idCardNumber)) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER;
        } else if (StringUtils.isBlank(pilotName)) {
            return ErrorCodeEnum.LACK_OF_REAL_NAME;
        } else if (PilotLicenseTypeEnum.getFromCode(licenseType)==null) {
            return ErrorCodeEnum.LACK_OF_LICENSE_TYPE;
        } else if (StringUtils.isBlank(licenseId)) {
            return ErrorCodeEnum.LACK_OF_LICENSE_ID;
        } else if (StringUtils.isBlank(phoneNumber)) {
            return ErrorCodeEnum.LACK_OF_PHONE_NUMBER;
        } else if (controllableUavType == null) {
            return ErrorCodeEnum.LACK_OF_CONTROLLABLE_UAV_TYPE;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
