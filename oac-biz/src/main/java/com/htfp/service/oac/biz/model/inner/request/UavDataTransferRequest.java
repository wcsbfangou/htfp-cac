package com.htfp.service.oac.biz.model.inner.request;

import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2023/2/14
 * @Description 描述
 */
@Data
public class UavDataTransferRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String reportCode;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer groundSpeed;
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
    private Integer absoluteSpeed;
    private Integer ambientTemperature;
    private Integer currentFaultCode;
    private Integer uavStatus;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        }else if (StringUtils.isBlank(reportCode)) {
            return ErrorCodeEnum.LACK_OF_UAV_REPORT_CODE;
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
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
