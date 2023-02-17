package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class QueryUavRouteInfoResultParam {

    private Long flightPlanId;
    private Long flyId;
    private String uavName;
    private String cpn;
    private List<CoordinateParam> routePointCoordinates;
    private CoordinateParam currentLegStartPoint;
    private CoordinateParam currentLegEndPoint;
    private String takeoffSite;
    private String landingSite;

}
