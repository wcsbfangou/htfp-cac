package com.htfp.service.cac.router.biz.model.background.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.IdCardTypeEnum;
import com.htfp.service.cac.common.enums.OperatorTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
@Data
public class OperatorInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String operatorId;
    /**
     * pilotCode = idCardType+'#'+idCardNumber
     */
    private String operatorCode;
    private String operatorName;
    private Integer operatorType;

    private Integer idCardType;
    private String idCardNumber;
    private String idCardPictureAddress;

    private String companyName;
    private String socialCreditCode;

    private Integer gender;
    private String nationality;
    private String city;
    private String address;
    private String phoneNumber;
    private String emailAddress;

    private Integer status;

    @Override
    public ErrorCodeEnum validate() {
        if (IdCardTypeEnum.getFromCode(idCardType)==null) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_TYPE;
        } else if (StringUtils.isBlank(idCardNumber)) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER;
        } else if (OperatorTypeEnum.getFromCode(operatorType)==null) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_TYPE;
        } else if (StringUtils.isBlank(operatorName)) {
            return ErrorCodeEnum.LACK_OF_REAL_NAME;
        } else if (StringUtils.isBlank(phoneNumber)) {
            return ErrorCodeEnum.LACK_OF_PHONE_NUMBER;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
