package com.htfp.service.oac.front.biz.service;

import com.htfp.service.oac.front.biz.model.request.ATCIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.AlarmIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAlarmMessageInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryFlightPlanInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.response.ATCIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.AlarmIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAlarmMessageInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryFlightPlanInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavRouteInfoResponse;

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

    /**
     * 管制信息下发
     * @param atcIssuedRequest
     * @return
     */
    ATCIssuedResponse atcIssued(ATCIssuedRequest atcIssuedRequest);

    /**
     * 告警信息下发
     * @param alarmIssuedRequest
     * @return
     */
    AlarmIssuedResponse alarmIssued(AlarmIssuedRequest alarmIssuedRequest);

    /**
     * 查询飞行计划
     * @param queryFlightPlanInfoRequest
     * @return
     */
    QueryFlightPlanInfoResponse queryFlightPlanInfo(QueryFlightPlanInfoRequest queryFlightPlanInfoRequest);
}
