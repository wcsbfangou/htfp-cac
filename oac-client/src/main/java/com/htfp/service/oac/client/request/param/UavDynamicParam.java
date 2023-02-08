package com.htfp.service.oac.client.request.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/1/11
 * @Description 描述
 */
@Data
public class UavDynamicParam {

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
}
