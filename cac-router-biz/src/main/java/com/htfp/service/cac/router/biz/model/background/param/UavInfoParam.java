package com.htfp.service.cac.router.biz.model.background.param;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.UavProductSizeTypeEnum;
import com.htfp.service.cac.common.enums.UavProductTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:44
 */
@Data
public class UavInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String uavId;
    private String uavReg;
    private String uavName;
    private Integer uavType;

    private String vin;
    private String pvin;
    private String sn;
    private String flightControlSn;
    private String imei;
    private String imsi;

    private String manufacturerName;
    private String productName;
    private Integer productType;
    private Integer productSizeType;
    private Integer maxFlyTime;
    private String operationScenarioType;
    private String operatorId;
    private Integer status;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavReg)) {
            return ErrorCodeEnum.LACK_OF_UAV_REG;
        } else if (StringUtils.isBlank(uavName)) {
            return ErrorCodeEnum.LACK_OF_UAV_NAME;
        } else if (uavType == null) {
            return ErrorCodeEnum.LACK_OF_UAV_TYPE;
        } else if (UavProductTypeEnum.getFromCode(productType) == null) {
            return ErrorCodeEnum.LACK_OF_UAV_PRODUCT_TYPE;
        } else if (UavProductSizeTypeEnum.getFromCode(productSizeType) == null) {
            return ErrorCodeEnum.LACK_OF_UAV_PRODUCT_SIZE_TYPE;
        } else if (StringUtils.isBlank(vin)) {
            return ErrorCodeEnum.LACK_OF_UAV_VIN;
        } else if (StringUtils.isBlank(pvin)) {
            return ErrorCodeEnum.LACK_OF_UAV_PVIN;
        } else if (StringUtils.isBlank(sn)) {
            return ErrorCodeEnum.LACK_OF_UAV_SN;
        } else if (StringUtils.isBlank(flightControlSn)) {
            return ErrorCodeEnum.LACK_OF_UAV_FLIGHT_CONTROL_SN;
        } else if (StringUtils.isBlank(imei)) {
            return ErrorCodeEnum.LACK_OF_UAV_IMEI;
        } else if (StringUtils.isBlank(imsi)) {
            return ErrorCodeEnum.LACK_OF_UAV_IMSI;
        } else if (StringUtils.isBlank(manufacturerName)) {
            return ErrorCodeEnum.LACK_OF_UAV_MANUFACTURER_NAME;
        } else if (StringUtils.isBlank(productName)) {
            return ErrorCodeEnum.LACK_OF_UAV_PRODUCT_NAME;
        } else if (StringUtils.isBlank(operatorId)) {
            return ErrorCodeEnum.LACK_OF_OPERATOR_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
