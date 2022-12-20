package com.htfp.service.oac.client.request.param;


import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import com.htfp.service.oac.client.enums.IdCardTypeEnum;
import com.htfp.service.oac.client.enums.OperatorTypeEnum;
import com.htfp.service.oac.client.request.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
@Data
public class OperatorInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String operatorSourceId;
    private String operatorUniId;
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
        if (StringUtils.isBlank(operatorSourceId)) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_ID;
        } else if (IdCardTypeEnum.getFromCode(idCardType) == null) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_TYPE;
        } else if (StringUtils.isBlank(idCardNumber)) {
            return ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER;
        } else if (OperatorTypeEnum.getFromCode(operatorType) == null) {
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
