package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class FlyApplyRequest implements BaseValidate<ErrorCodeEnum> {

    private String applyFlightPlanId;
    private String gcsId;
    private String uavId;
    private List<String> airspaceNumbers;
    private String operationScenarioType;
    private Integer flyLng;
    private Integer flyLat;
    private Integer flyAlt;
    private String vin;
    private String pvin;
    private String flightControlSn;
    private String imei;

    @Override
    public ErrorCodeEnum validate() {
        if(StringUtils.isBlank(applyFlightPlanId)){
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if(StringUtils.isBlank(uavId)){
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if(CollectionUtils.isEmpty(airspaceNumbers)){
            return ErrorCodeEnum.LACK_OF_AIRSPACE_NUM;
        } else if(flyAlt == null){
            return ErrorCodeEnum.LACK_OF_ALT;
        } else if(flyLng == null){
            return ErrorCodeEnum.LACK_OF_LNG;
        } else if(flyLat == null){
            return ErrorCodeEnum.LACK_OF_LAT;
        } else if(StringUtils.isBlank(flightControlSn)){
            return ErrorCodeEnum.LACK_OF_UAV_FLIGHT_CONTROL_SN;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
