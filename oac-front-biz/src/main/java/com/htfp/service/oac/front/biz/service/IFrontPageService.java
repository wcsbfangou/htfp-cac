package com.htfp.service.oac.front.biz.service;

import com.htfp.service.oac.front.biz.model.request.*;
import com.htfp.service.oac.front.biz.model.response.*;

/**
 * @Author sunjipeng
 * @Date 2023/2/13
 * @Description 描述
 */
public interface IFrontPageService {
    /**
     * 查询无人机动态信息
     * @param queryUavDynamicInfoRequest
     * @return
     */
    QueryUavDynamicInfoResponse queryUavDynamicInfo(QueryUavDynamicInfoRequest queryUavDynamicInfoRequest);

    /**
     * 查询无人机航线信息
     * @param queryUavRouteInfoRequest
     * @return
     */
    QueryUavRouteInfoResponse queryUavRouteInfo(QueryUavRouteInfoRequest queryUavRouteInfoRequest);

    /**
     * 查询无人机机场信息
     * @param queryAirportInfoRequest
     * @return
     */
    QueryAirportInfoResponse queryAirportInfoData(QueryAirportInfoRequest queryAirportInfoRequest);

    /**
     * 查询告警信息
     * @param queryAlarmMessageInfoRequest
     * @return
     */
    QueryAlarmMessageInfoResponse queryAlarmMessageInfoData(QueryAlarmMessageInfoRequest queryAlarmMessageInfoRequest);

    /**
     * 飞行计划下发
     * @param flightPlanIssuedRequest
     * @return
     */
    FlightPlanIssuedResponse flightPlanIssued(FlightPlanIssuedRequest flightPlanIssuedRequest);

    /**
     * 放飞结果下发
     * @param flyIssuedRequest
     * @return
     */
    FlyIssuedResponse flyIssued(FlyIssuedRequest flyIssuedRequest);
}
