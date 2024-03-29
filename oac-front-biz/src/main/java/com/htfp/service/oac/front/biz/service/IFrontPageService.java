package com.htfp.service.oac.front.biz.service;

import com.htfp.service.oac.front.biz.model.request.ATCIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.AlarmIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanRevokeRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAlarmMessageInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryFlightPlanInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicFlightPlanRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavVideoStreamAddressRequest;
import com.htfp.service.oac.front.biz.model.response.ATCIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.AlarmIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanRevokeResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAlarmMessageInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryFlightPlanInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicFlightPlanResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavRouteInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryVideoStreamAddressResponse;

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
     * 查询无人机视频拉流地址
     * @param queryUavVideoStreamAddressRequest
     * @return
     */
    QueryVideoStreamAddressResponse queryUavVideoStreamAddress(QueryUavVideoStreamAddressRequest queryUavVideoStreamAddressRequest);

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
     * 飞行计划撤销
     * @param flightPlanRevokeRequest
     * @return
     */
    FlightPlanRevokeResponse flightPlanRevoke(FlightPlanRevokeRequest flightPlanRevokeRequest);

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
     * 查询动态飞行计划
     * @param queryUavDynamicFlightPlanRequest
     * @return
     */
    QueryUavDynamicFlightPlanResponse queryUavDynamicFlightPlanInfo(QueryUavDynamicFlightPlanRequest queryUavDynamicFlightPlanRequest);

    /**
     * 查询飞行计划详细信息
     * @param queryFlightPlanInfoRequest
     * @return
     */
    QueryFlightPlanInfoResponse queryUavFlightPlanInfo(QueryFlightPlanInfoRequest queryFlightPlanInfoRequest);
}
