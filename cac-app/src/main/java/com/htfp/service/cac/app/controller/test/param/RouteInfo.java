package com.htfp.service.cac.app.controller.test.param;

import com.htfp.service.cac.router.biz.model.http.request.param.PositionParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/21
 * @Description 描述
 */
@Data
public class RouteInfo {

    private String routeCode;
    private String routeName;
    private List<PositionParam> routePointCoordinates;
    private Integer routeLength;
    private String routeStartTime;
    private String routeEndTime;
    private Integer routeIdentificationRadius;
    private Integer routeAlarmRadius;
    private Integer routeLevel;
    private String routeOperatorId;
    private Integer routeStatus;
}
