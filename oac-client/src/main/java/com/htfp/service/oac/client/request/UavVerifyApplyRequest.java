package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class UavVerifyApplyRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String applyVerifyTimeStamp;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer groundSpeed;
    private Integer absoluteSpeed;
    private Integer relativeHeight;
    private Integer trueCourse;
    private Integer pitchAngle;
    private Integer rollAngle;
    private Integer voltage;
    private Integer fuel;
    private Integer battery;
    private Integer flyMode;
    private Boolean cameraOn;
    private Boolean engineOn;
    private Boolean airOn;
    private Integer imsi;
    private Integer imei;
    private String phoneNumber;
    private Integer powerType;
    private Integer ambientTemperature;
    private String flightControlSn;
    private String flightControlVersion;
    private Integer horizontalPositionAcc;
    private Integer verticalPositionAcc;
    private Integer totalPositionAcc;
    private Integer currentFaultCode;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(applyVerifyTimeStamp)) {
            return ErrorCodeEnum.LACK_OF_TIME;
        } else if (alt == null) {
            return ErrorCodeEnum.LACK_OF_ALT;
        } else if (lng == null) {
            return ErrorCodeEnum.LACK_OF_LNG;
        } else if (lat == null) {
            return ErrorCodeEnum.LACK_OF_LAT;
        } else if (groundSpeed == null) {
            return ErrorCodeEnum.LACK_OF_GROUND_SPEED;
        } else if (relativeHeight == null) {
            return ErrorCodeEnum.LACK_OF_RELATIVE_HEIGHT;
        } else if (StringUtils.isBlank(flightControlSn)) {
            return ErrorCodeEnum.LACK_OF_UAV_FLIGHT_CONTROL_SN;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
