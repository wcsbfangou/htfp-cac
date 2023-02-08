package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import com.htfp.service.cac.router.biz.model.http.request.param.UavDynamicParam;
import com.htfp.service.cac.router.biz.model.http.request.param.UavStaticParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class UavVerifyApplyRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer groundSpeed;
    private Integer relativeHeight;
    private String flightControlSn;
    private String flightControlVersion;
    private UavDynamicParam uavDynamicParam;
    private UavStaticParam uavStaticParam;


    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
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
