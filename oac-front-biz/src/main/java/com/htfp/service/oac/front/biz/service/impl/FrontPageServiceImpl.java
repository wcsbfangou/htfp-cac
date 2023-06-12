package com.htfp.service.oac.front.biz.service.impl;

import com.htfp.service.cac.dao.model.oac.ATCIssuedLogDO;
import com.htfp.service.cac.dao.model.oac.AlarmIssuedLogDO;
import com.htfp.service.cac.dao.model.oac.DynamicFlightPlanInfoDO;
import com.htfp.service.cac.dao.service.oac.OacATCIssuedLogDalService;
import com.htfp.service.cac.dao.service.oac.OacAlarmIssuedLogDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicFlightPlanInfoDalService;
import com.htfp.service.cac.router.biz.service.inner.IOacService;
import com.htfp.service.cac.router.biz.model.inner.request.ATCSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.response.ATCSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlightPlanReplyResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlyReplyResponse;
import com.htfp.service.oac.common.enums.ApplyStatusEnum;
import com.htfp.service.oac.common.enums.AtcTypeEnum;
import com.htfp.service.oac.common.enums.DeliverTypeEnum;
import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.common.enums.UavDynamicFlightPlanInfoQueryStatusEnum;
import com.htfp.service.oac.common.enums.UavDynamicInfoQueryStatusEnum;
import com.htfp.service.oac.common.utils.DateUtils;
import com.htfp.service.oac.common.utils.GpsDistanceUtils;
import com.htfp.service.oac.common.utils.JsonUtils;
import com.htfp.service.cac.dao.model.oac.AirportInfoDO;
import com.htfp.service.cac.dao.model.oac.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.oac.ApplyFlyLogDO;
import com.htfp.service.cac.dao.model.oac.DynamicRouteInfoDO;
import com.htfp.service.cac.dao.model.oac.DynamicUavInfoDO;
import com.htfp.service.cac.dao.model.oac.OperatorInfoDO;
import com.htfp.service.cac.dao.model.oac.UavInfoDO;
import com.htfp.service.cac.dao.service.oac.OacAirportInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.oac.OacApplyFlyLogDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicRouteInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicUavInfoDalService;
import com.htfp.service.cac.dao.service.oac.OacOperatorDalService;
import com.htfp.service.cac.dao.service.oac.OacUavDalService;
import com.htfp.service.oac.front.biz.model.request.ATCIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.AlarmIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlightPlanIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.FlyIssuedRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAirportInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryAlarmMessageInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryFlightPlanInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicFlightPlanRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavDynamicInfoRequest;
import com.htfp.service.oac.front.biz.model.request.QueryUavRouteInfoRequest;
import com.htfp.service.oac.front.biz.model.response.ATCIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.AlarmIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlightPlanIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.FlyIssuedResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAirportInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryAlarmMessageInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryFlightPlanInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicFlightPlanResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavDynamicInfoResponse;
import com.htfp.service.oac.front.biz.model.response.QueryUavRouteInfoResponse;
import com.htfp.service.oac.front.biz.model.response.param.CoordinateParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryAirportInfoResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryAlarmMessageInfoResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryFlightPlanInfoResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryUavDynamicFlightPlanResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryUavDynamicInfoResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryUavRouteInfoResultParam;
import com.htfp.service.oac.front.biz.service.IFrontPageService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Slf4j
@Service("frontPageServiceImpl")
public class FrontPageServiceImpl implements IFrontPageService {

    @Resource
    private OacApplyFlyLogDalService oacApplyFlyLogDalService;

    @Resource
    private OacUavDalService oacUavDalService;

    @Resource
    private OacOperatorDalService oacOperatorDalService;

    @Resource
    private OacAirportInfoDalService oacAirportInfoDalService;

    @Resource
    private OacAlarmIssuedLogDalService oacAlarmIssuedLogDalService;

    @Resource
    private OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;

    @Resource
    private OacDynamicUavInfoDalService oacDynamicUavInfoDalService;

    @Resource
    private OacDynamicFlightPlanInfoDalService oacDynamicFlightPlanInfoDalService;

    @Resource
    private OacDynamicRouteInfoDalService oacDynamicRouteInfoDalService;

    @Resource
    private OacATCIssuedLogDalService oacATCIssuedLogDalService;

    @Resource(name = "oacServiceImpl")
    private IOacService oacService;

    /**
     * 查询无人机动态信息
     *
     * @param queryUavDynamicInfoRequest
     * @return
     */
    @Override
    public QueryUavDynamicInfoResponse queryUavDynamicInfo(QueryUavDynamicInfoRequest queryUavDynamicInfoRequest) {
        QueryUavDynamicInfoResponse queryUavDynamicInfoResponse = new QueryUavDynamicInfoResponse();
        queryUavDynamicInfoResponse.success();
        try {
            UavDynamicInfoQueryStatusEnum uavDynamicInfoQueryStatus = UavDynamicInfoQueryStatusEnum.getFromCode(queryUavDynamicInfoRequest.getQueryUavDynamicInfoStatus());
            List<DynamicUavInfoDO> dynamicUavInfoDOList = new ArrayList<>();
            if (queryUavDynamicInfoRequest.getCpn() != null) {
                List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryDynamicUavInfoByCpn(queryUavDynamicInfoRequest.getCpn());
                for (DynamicUavInfoDO dynamicUavInfo : queryDynamicUavInfoDOList) {
                    FlightPlanStatusTypeEnum flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.getFromCode(dynamicUavInfo.getPlanStatus());
                    if (FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.equals(flightPlanStatusTypeEnum) ||
                            FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.equals(flightPlanStatusTypeEnum) ||
                            FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.equals(flightPlanStatusTypeEnum) ||
                            FlightPlanStatusTypeEnum.PREPARE_LANDING.equals(flightPlanStatusTypeEnum) ||
                            FlightPlanStatusTypeEnum.COMPLETE_LANDING.equals(flightPlanStatusTypeEnum)) {
                        dynamicUavInfoDOList.add(dynamicUavInfo);
                    }
                }
            } else {
                if (UavDynamicInfoQueryStatusEnum.ALL.equals(uavDynamicInfoQueryStatus)) {
                    // TODO: 2023/2/20 待优化
                    List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                    dynamicUavInfoDOList.addAll(queryDynamicUavInfoDOList);
                } else if (UavDynamicInfoQueryStatusEnum.FLIGHT_PLAN_PASS_AND_NOT_OVER.equals(uavDynamicInfoQueryStatus)) {
                    List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                    if (CollectionUtils.isNotEmpty(queryDynamicUavInfoDOList) && queryUavDynamicInfoRequest.getInAlarm() != null && queryUavDynamicInfoRequest.getInAlarm()) {
                        for (DynamicUavInfoDO dynamicUavInfo : queryDynamicUavInfoDOList) {
                            if (dynamicUavInfo.getInAlarm()) {
                                dynamicUavInfoDOList.add(dynamicUavInfo);
                            }
                        }
                    } else {
                        dynamicUavInfoDOList.addAll(queryDynamicUavInfoDOList);
                    }
                } else if (UavDynamicInfoQueryStatusEnum.ARRIVAL.equals(uavDynamicInfoQueryStatus)) {
                    if (StringUtils.isNotBlank(queryUavDynamicInfoRequest.getLandingAirportId())) {
                        List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED.getCode(), FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED.getCode());
                        for (DynamicUavInfoDO dynamicUavInfo : queryDynamicUavInfoDOList) {
                            if (queryUavDynamicInfoRequest.getLandingAirportId().equals(dynamicUavInfo.getLandingAirportId())) {
                                dynamicUavInfoDOList.add(dynamicUavInfo);
                            }
                        }
                    } else {
                        queryUavDynamicInfoResponse.fail(ErrorCodeEnum.LACK_OF_LANDING_AIRPORT);
                    }
                } else {
                    if (StringUtils.isNotBlank(queryUavDynamicInfoRequest.getTakeoffAirportId())) {
                        List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                        for (DynamicUavInfoDO dynamicUavInfo : queryDynamicUavInfoDOList) {
                            if (queryUavDynamicInfoRequest.getTakeoffAirportId().equals(dynamicUavInfo.getTakeoffAirportId())) {
                                dynamicUavInfoDOList.add(dynamicUavInfo);
                            }
                        }
                    } else {
                        queryUavDynamicInfoResponse.fail(ErrorCodeEnum.LACK_OF_TAKE_OFF_AIRPORT);
                    }
                }
            }
            if (queryUavDynamicInfoResponse.getSuccess() && CollectionUtils.isNotEmpty(dynamicUavInfoDOList)) {
                List<QueryUavDynamicInfoResultParam> queryUavDynamicInfoResultParamList = buildQueryUavDynamicInfoResultParamList(dynamicUavInfoDOList);
                queryUavDynamicInfoResponse.setQueryUavDynamicInfoResultParamList(queryUavDynamicInfoResultParamList);
            }
        } catch (Exception e) {
            log.error("[oac]无人机动态信息查询异常，queryUavDynamicInfoRequest={}", queryUavDynamicInfoRequest, e);
            queryUavDynamicInfoResponse.fail(e.getMessage());
        }
        return queryUavDynamicInfoResponse;
    }

    private List<QueryUavDynamicInfoResultParam> buildQueryUavDynamicInfoResultParamList(List<DynamicUavInfoDO> dynamicUavInfoDOList) {
        List<QueryUavDynamicInfoResultParam> queryUavDynamicInfoResultParamList = new ArrayList<>();
        for (DynamicUavInfoDO dynamicUavInfo : dynamicUavInfoDOList) {
            QueryUavDynamicInfoResultParam queryUavDynamicInfoResultParam = new QueryUavDynamicInfoResultParam();
            queryUavDynamicInfoResultParam.setShortCpn(dynamicUavInfo.getCpn().substring(dynamicUavInfo.getCpn().length() - 4));
            queryUavDynamicInfoResultParam.setShortFlightPlanId(dynamicUavInfo.getReplyFlightPlanId().toString().substring(dynamicUavInfo.getReplyFlightPlanId().toString().length() - 4));
            queryUavDynamicInfoResultParam.setCpn(dynamicUavInfo.getCpn());
            queryUavDynamicInfoResultParam.setFlightPlanId(dynamicUavInfo.getReplyFlightPlanId().toString());
            queryUavDynamicInfoResultParam.setFlyId(dynamicUavInfo.getReplyFlyId().toString());
            queryUavDynamicInfoResultParam.setUavName(dynamicUavInfo.getUavName());
            queryUavDynamicInfoResultParam.setUavOperatorName(dynamicUavInfo.getUavOperatorName());
            queryUavDynamicInfoResultParam.setLng(dynamicUavInfo.getLng());
            queryUavDynamicInfoResultParam.setLat(dynamicUavInfo.getLat());
            queryUavDynamicInfoResultParam.setAlt(dynamicUavInfo.getAlt());
            queryUavDynamicInfoResultParam.setSpeed(dynamicUavInfo.getSpeed());
            queryUavDynamicInfoResultParam.setCourse(dynamicUavInfo.getCourse());
            queryUavDynamicInfoResultParam.setFuel(dynamicUavInfo.getFuel() == null ? 85 : dynamicUavInfo.getFuel());
            queryUavDynamicInfoResultParam.setBattery(dynamicUavInfo.getBattery() == null ? 85 : dynamicUavInfo.getBattery());
            queryUavDynamicInfoResultParam.setSignal(dynamicUavInfo.getSignalStrength() == null ? 5 : dynamicUavInfo.getSignalStrength());
            queryUavDynamicInfoResultParam.setUpdateTime(dynamicUavInfo.getUpdateTime());
            queryUavDynamicInfoResultParam.setFlightPlanStartTime(dynamicUavInfo.getFlightPlanStartTime());
            queryUavDynamicInfoResultParam.setFlightPlanEndTime(dynamicUavInfo.getFlightPlanEndTime());
            queryUavDynamicInfoResultParam.setStartFlyTime(dynamicUavInfo.getStartFlyTime());
            queryUavDynamicInfoResultParam.setTakeoffAirportId(dynamicUavInfo.getTakeoffAirportId());
            queryUavDynamicInfoResultParam.setLandingAirportId(dynamicUavInfo.getLandingAirportId());
            queryUavDynamicInfoResultParam.setTakeoffSite(dynamicUavInfo.getTakeoffSite());
            queryUavDynamicInfoResultParam.setLandingSite(dynamicUavInfo.getLandingSite());
            queryUavDynamicInfoResultParam.setDistanceToLandingPoint(dynamicUavInfo.getDistanceToLandingPoint());
            queryUavDynamicInfoResultParam.setInAlarm(dynamicUavInfo.getInAlarm());
            queryUavDynamicInfoResultParam.setAlarmIds(dynamicUavInfo.getAlarmIds());
            queryUavDynamicInfoResultParam.setUavPlanStatus(dynamicUavInfo.getPlanStatus());
            queryUavDynamicInfoResultParam.setUavStatus(dynamicUavInfo.getUavStatus());
            queryUavDynamicInfoResultParamList.add(queryUavDynamicInfoResultParam);
        }
        return queryUavDynamicInfoResultParamList;
    }

    /**
     * 查询无人机航线信息
     *
     * @param queryUavRouteInfoRequest
     * @return
     */
    @Override
    public QueryUavRouteInfoResponse queryUavRouteInfo(QueryUavRouteInfoRequest queryUavRouteInfoRequest) {
        QueryUavRouteInfoResponse queryUavRouteInfoResponse = new QueryUavRouteInfoResponse();
        queryUavRouteInfoResponse.fail();
        try {
            log.info("[oac]查询无人机航线信息start，queryUavRouteInfoRequest={}", queryUavRouteInfoRequest);
            List<QueryUavRouteInfoResultParam> queryUavRouteInfoResultParamList = new ArrayList<>();
            DynamicRouteInfoDO dynamicRouteInfo = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(queryUavRouteInfoRequest.getFlightPlanId()));
            if (dynamicRouteInfo != null) {
                queryUavRouteInfoResultParamList.add(buildQueryUavRouteInfoResultParam(dynamicRouteInfo));
            }
            queryUavRouteInfoResponse.setQueryUavRouteInfoResultParamList(queryUavRouteInfoResultParamList);
            queryUavRouteInfoResponse.success();
            log.info("[oac]查询无人机航线信息end，queryUavRouteInfoRequest={},queryUavRouteInfoResponse={}", queryUavRouteInfoRequest, JsonUtils.object2Json(queryUavRouteInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询无人机航线信息异常，queryUavRouteInfoRequest={}", queryUavRouteInfoRequest, e);
            queryUavRouteInfoResponse.fail(e.getMessage());
        }
        return queryUavRouteInfoResponse;
    }

    private QueryUavRouteInfoResultParam buildQueryUavRouteInfoResultParam(DynamicRouteInfoDO dynamicRouteInfo) {
        CoordinateParam currentLegStartPoint = new CoordinateParam();
        CoordinateParam currentLegEndPoint = new CoordinateParam();
        currentLegStartPoint.setLng(dynamicRouteInfo.getCurrentLegStartLng());
        currentLegStartPoint.setLat(dynamicRouteInfo.getCurrentLegStartLat());
        currentLegStartPoint.setAlt(dynamicRouteInfo.getCurrentLegStartAlt());
        currentLegEndPoint.setLng(dynamicRouteInfo.getCurrentLegEndLng());
        currentLegEndPoint.setLat(dynamicRouteInfo.getCurrentLegEndLat());
        currentLegEndPoint.setAlt(dynamicRouteInfo.getCurrentLegEndAlt());
        QueryUavRouteInfoResultParam queryUavRouteInfoResultParam = new QueryUavRouteInfoResultParam();
        queryUavRouteInfoResultParam.setFlightPlanId(dynamicRouteInfo.getReplyFlightPlanId());
        queryUavRouteInfoResultParam.setFlyId(dynamicRouteInfo.getReplyFlyId());
        queryUavRouteInfoResultParam.setUavName(dynamicRouteInfo.getUavName());
        queryUavRouteInfoResultParam.setCpn(dynamicRouteInfo.getCpn());
        queryUavRouteInfoResultParam.setRoutePointCoordinates(JsonUtils.json2List(dynamicRouteInfo.getRoutePointCoordinates(), CoordinateParam.class));
        queryUavRouteInfoResultParam.setCurrentLegStartPoint(currentLegStartPoint);
        queryUavRouteInfoResultParam.setCurrentLegEndPoint(currentLegEndPoint);
        queryUavRouteInfoResultParam.setTakeoffSite(dynamicRouteInfo.getTakeoffSite());
        queryUavRouteInfoResultParam.setLandingSite(dynamicRouteInfo.getLandingSite());
        return queryUavRouteInfoResultParam;
    }

    /**
     * 查询无人机机场信息
     *
     * @param queryAirportInfoRequest
     * @return
     */
    @Override
    public QueryAirportInfoResponse queryAirportInfoData(QueryAirportInfoRequest queryAirportInfoRequest) {
        QueryAirportInfoResponse queryAirportInfoResponse = new QueryAirportInfoResponse();
        queryAirportInfoResponse.fail();
        try {
            log.info("[oac]查询机场信息start，queryAirportInfoRequest={}", queryAirportInfoRequest);
            List<QueryAirportInfoResultParam> queryQueryAirportInfoResultParamList = new ArrayList<>();
            if (StringUtils.isNotBlank(queryAirportInfoRequest.getAirportId())) {
                AirportInfoDO airportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(queryAirportInfoRequest.getAirportId());
                queryQueryAirportInfoResultParamList.add(buildQueryAirportInfoResultParam(airportInfo));
            } else {
                List<AirportInfoDO> airportInfoList = oacAirportInfoDalService.queryAllAirportInfo();
                if (CollectionUtils.isNotEmpty(airportInfoList)) {
                    for (AirportInfoDO airportInfo : airportInfoList) {
                        queryQueryAirportInfoResultParamList.add(buildQueryAirportInfoResultParam(airportInfo));
                    }
                }
            }
            queryAirportInfoResponse.setQueryAirportInfoResultParamList(queryQueryAirportInfoResultParamList);
            queryAirportInfoResponse.success();
            log.info("[oac]查询机场信息end，queryAirportInfoRequest={},queryAirportInfoResponse={}", queryAirportInfoRequest, JsonUtils.object2Json(queryAirportInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询机场信息异常，queryAirportInfoRequest={}", queryAirportInfoRequest, e);
            queryAirportInfoResponse.fail(e.getMessage());
        }
        return queryAirportInfoResponse;
    }

    private QueryAirportInfoResultParam buildQueryAirportInfoResultParam(AirportInfoDO airportInfo) {
        QueryAirportInfoResultParam queryAirportInfoResultParam = new QueryAirportInfoResultParam();
        queryAirportInfoResultParam.setAirportId(airportInfo.getAirportId());
        queryAirportInfoResultParam.setCity(airportInfo.getCity());
        queryAirportInfoResultParam.setLng(airportInfo.getLng());
        queryAirportInfoResultParam.setLat(airportInfo.getLat());
        queryAirportInfoResultParam.setAlt(airportInfo.getAlt());
        queryAirportInfoResultParam.setIdentificationAreaRadius(airportInfo.getIdentificationAreaRadius());
        queryAirportInfoResultParam.setAlarmAreaRadius(airportInfo.getAlarmAreaRadius());
        queryAirportInfoResultParam.setLandingSites(JsonUtils.json2List(airportInfo.getLandingSites(), String.class));
        return queryAirportInfoResultParam;
    }

    /**
     * 查询告警信息
     *
     * @param queryAlarmMessageInfoRequest
     * @return
     */
    @Override
    public QueryAlarmMessageInfoResponse queryAlarmMessageInfoData(QueryAlarmMessageInfoRequest queryAlarmMessageInfoRequest) {
        QueryAlarmMessageInfoResponse queryAlarmMessageInfoResponse = new QueryAlarmMessageInfoResponse();
        queryAlarmMessageInfoResponse.fail();
        try {
            log.info("[oac]查询告警信息start，queryAlarmMessageInfoRequest={}", queryAlarmMessageInfoRequest);
            List<QueryAlarmMessageInfoResultParam> queryAlarmMessageInfoResultParamList = new ArrayList<>();
            for (String alarmId : queryAlarmMessageInfoRequest.getAlarmIds()) {
                AlarmIssuedLogDO alarmIssuedLog = oacAlarmIssuedLogDalService.queryAlarmIssuedLog(Long.valueOf(alarmId));
                queryAlarmMessageInfoResultParamList.add(buildQueryAlarmMessageInfoParam(alarmIssuedLog));
            }
            queryAlarmMessageInfoResponse.setQueryAlarmMessageInfoResultParamList(queryAlarmMessageInfoResultParamList);
            queryAlarmMessageInfoResponse.success();
            log.info("[oac]查询告警信息end，queryAlarmMessageInfoRequest={},queryAlarmMessageInfoResponse={}", queryAlarmMessageInfoRequest, JsonUtils.object2Json(queryAlarmMessageInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询告警信息异常，queryAlarmMessageInfoRequest={}", queryAlarmMessageInfoRequest, e);
            queryAlarmMessageInfoResponse.fail(e.getMessage());
        }
        return queryAlarmMessageInfoResponse;
    }

    private QueryAlarmMessageInfoResultParam buildQueryAlarmMessageInfoParam(AlarmIssuedLogDO alarmIssuedLog) {
        QueryAlarmMessageInfoResultParam queryAlarmMessageInfoResultParam = new QueryAlarmMessageInfoResultParam();
        queryAlarmMessageInfoResultParam.setAlarmId(alarmIssuedLog.getId().toString());
        queryAlarmMessageInfoResultParam.setFlyId(alarmIssuedLog.getReplyFlyId().toString());
        queryAlarmMessageInfoResultParam.setCpn(alarmIssuedLog.getCpn());
        queryAlarmMessageInfoResultParam.setAlarmLevel(alarmIssuedLog.getAlarmLevel());
        queryAlarmMessageInfoResultParam.setAlarmContent(alarmIssuedLog.getAlarmContent());
        queryAlarmMessageInfoResultParam.setAlarmEffectTime(alarmIssuedLog.getAlarmEffectTime());
        queryAlarmMessageInfoResultParam.setAlarmOperator(alarmIssuedLog.getAlarmOperator());
        queryAlarmMessageInfoResultParam.setAlarmDeliver(alarmIssuedLog.getAlarmDelivered());
        return queryAlarmMessageInfoResultParam;
    }

    /**
     * 飞行计划下发
     *
     * @param flightPlanIssuedRequest
     * @return
     */
    @Override
    public FlightPlanIssuedResponse flightPlanIssued(FlightPlanIssuedRequest flightPlanIssuedRequest) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.fail();
        try {
            log.info("[oac]飞行计划下发start，flightPlanIssuedRequest={}", flightPlanIssuedRequest);
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(flightPlanIssuedRequest.getFlightPlanId()));
            if (queryApplyFlightPlanLog != null) {
                DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId());
                if (queryDynamicFlightPlanInfoDO != null) {
                    Integer queryPlansStatus = queryDynamicFlightPlanInfoDO.getPlanStatus();
                    Integer queryApplyFlightPlanLogStatus = queryApplyFlightPlanLog.getStatus();
                    if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLogStatus))) {
                        if (flightPlanIssuedRequest.getPass()) {
                            // TODO: 2023/6/9 事务
                            oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.APPROVED.getCode());
                            oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode());
                            // 飞行计划通过,存储动态信息
                            if (!initializeDynamicInfo(queryApplyFlightPlanLog, flightPlanIssuedRequest.getCpn(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode())) {
                                //ROLLBACK
                                oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, queryApplyFlightPlanLogStatus);
                                oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, queryPlansStatus);
                                flightPlanIssuedResponse.fail();
                            } else {
                                flightPlanIssuedResponse.success();
                            }
                        } else {
                            // TODO: 2023/6/9 事务
                            oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.UNAPPROVED.getCode());
                            oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_UNAPPROVED.getCode());
                            flightPlanIssuedResponse.success();
                        }
                        if (flightPlanIssuedResponse.getSuccess()) {
                            FlightPlanReplyRequest cacFlightPlanReplyRequest = buildCacFlightPlanReplyRequest(flightPlanIssuedRequest, queryApplyFlightPlanLog.getApplyFlightPlanId());
                            FlightPlanReplyResponse cacFlightPlanReplyResponse = oacService.flightPlanReply(cacFlightPlanReplyRequest);
                            flightPlanIssuedResponse = buildFlightPlanIssuedResponse(cacFlightPlanReplyResponse, flightPlanIssuedRequest.getCpn());
                        }
                        log.info("[oac]飞行计划下发end，flightPlanIssuedRequest={},flightPlanIssuedResponse={}", flightPlanIssuedRequest, JsonUtils.object2Json(flightPlanIssuedResponse));
                    } else {
                        flightPlanIssuedResponse.fail("飞行计划已下发，不允许重复下发");
                    }
                } else {
                    flightPlanIssuedResponse.fail("无飞行计划动态信息，不允许下发");
                }
            } else {
                flightPlanIssuedResponse.fail("未申请该飞行计划，不允许下发");
            }
        } catch (Exception e) {
            log.error("[oac]飞行计划下发异常，flightPlanIssuedRequest={}", flightPlanIssuedRequest, e);
            flightPlanIssuedResponse.fail(e.getMessage());
        }
        return flightPlanIssuedResponse;
    }

    private FlightPlanReplyRequest buildCacFlightPlanReplyRequest(FlightPlanIssuedRequest flightPlanIssuedRequest, String applyFlightPlanId) {
        FlightPlanReplyRequest cacFlightPlanReplyRequest = new FlightPlanReplyRequest();
        cacFlightPlanReplyRequest.setCpn(flightPlanIssuedRequest.getCpn());
        cacFlightPlanReplyRequest.setApplyFlightPlanId(applyFlightPlanId);
        cacFlightPlanReplyRequest.setReplyFlightPlanId(flightPlanIssuedRequest.getFlightPlanId());
        cacFlightPlanReplyRequest.setPass(flightPlanIssuedRequest.getPass());
        return cacFlightPlanReplyRequest;
    }

    private FlightPlanIssuedResponse buildFlightPlanIssuedResponse(FlightPlanReplyResponse cacFlightPlanReplyResponse, String cpn) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.setSuccess(cacFlightPlanReplyResponse.getSuccess());
        flightPlanIssuedResponse.setCode(cacFlightPlanReplyResponse.getCode());
        flightPlanIssuedResponse.setMessage(cacFlightPlanReplyResponse.getMessage());
        flightPlanIssuedResponse.setCpn(cpn);
        return flightPlanIssuedResponse;
    }

    private Boolean initializeDynamicInfo(ApplyFlightPlanLogDO queryApplyFlightPlanLog, String cpn, Integer flightPlanStatus) {
        Boolean result = false;
        UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(cpn);
        if (queryUavInfo != null) {
            OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(queryUavInfo.getOperatorUniId());
            AirportInfoDO queryAirportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(queryApplyFlightPlanLog.getLandingAirportId());
            if (queryOperatorInfo != null && queryAirportInfo != null) {
                String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
                DynamicUavInfoDO dynamicUavInfo = oacDynamicUavInfoDalService.buildDynamicUavInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn, queryOperatorInfo.getOperatorName(),
                        null, null, null, null, null, null, null, null, currentTime, queryApplyFlightPlanLog.getStartTime(), queryApplyFlightPlanLog.getEndTime(),
                        null, queryApplyFlightPlanLog.getTakeoffAirportId(), queryApplyFlightPlanLog.getLandingAirportId(), queryApplyFlightPlanLog.getTakeoffSite(), queryApplyFlightPlanLog.getLandingSite(),
                        queryAirportInfo.getIdentificationAreaRadius(), queryAirportInfo.getAlarmAreaRadius(), queryAirportInfo.getLng(), queryAirportInfo.getLat(), queryAirportInfo.getAlt(),
                        null, false, null, false, flightPlanStatus, null);
                DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.buildDynamicRouteInfoDO(queryApplyFlightPlanLog.getReplyFlightPlanId(), null, queryUavInfo.getUavName(), cpn,
                        queryApplyFlightPlanLog.getRoutePointCoordinates(), null, null, null, null, null, null,
                        queryApplyFlightPlanLog.getTakeoffSite(), queryAirportInfo.getLandingSites(), flightPlanStatus);
                // TODO: 2022/12/23 事务
                int insertDynamicUavInfoResult = oacDynamicUavInfoDalService.insertDynamicUavInfo(dynamicUavInfo);
                int insertDynamicRouteInfoResult = oacDynamicRouteInfoDalService.insertDynamicRouteInfo(dynamicRouteInfoDO);
                if (insertDynamicUavInfoResult > 0 && insertDynamicRouteInfoResult > 0) {
                    result = true;
                }
            }
        }
        return result;
    }

    /**
     * 放飞结果下发
     *
     * @param flyIssuedRequest
     * @return
     */
    @Override
    public FlyIssuedResponse flyIssued(FlyIssuedRequest flyIssuedRequest) {
        FlyIssuedResponse flyIssuedResponse = new FlyIssuedResponse();
        flyIssuedResponse.fail();
        try {
            Long replyFlyId = Long.valueOf(flyIssuedRequest.getFlyId());
            log.info("[oac]放飞申请结果下发start，flyIssuedRequest={}", flyIssuedRequest);
            ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
            if (queryApplyFlyLog != null) {
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus()))) {
                    FlightPlanStatusTypeEnum flightPlanStatusTypeEnum;
                    if (flyIssuedRequest.getPass()) {
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.APPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED;
                    } else {
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, ApplyStatusEnum.UNAPPROVED.getCode());
                        flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.FLY_APPLY_UNAPPROVED;
                    }
                    if (!updateDynamicInfoPlanStatusByReplyFlyId(replyFlyId, flightPlanStatusTypeEnum)) {
                        //ROLLBACK
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, queryApplyFlyLog.getStatus());
                    }
                    FlyReplyRequest cacFlyReplyRequest = buildCacFlyReplyRequest(flyIssuedRequest, queryApplyFlyLog.getApplyFlyId());
                    FlyReplyResponse cacFlyReplyResponse = oacService.flyReply(cacFlyReplyRequest);
                    flyIssuedResponse = buildFlyIssuedResponse(cacFlyReplyResponse);
                    if (flyIssuedResponse.getSuccess()) {
                        flyIssuedResponse.setCpn(flyIssuedRequest.getCpn());
                    } else {
                        //ROLLBACK
                        oacApplyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, queryApplyFlyLog.getStatus());
                    }
                    log.info("[oac]放飞申请结果下发end，flyIssuedRequest={}, flyIssuedResponse={}", flyIssuedRequest, JsonUtils.object2Json(flyIssuedResponse));
                } else {
                    flyIssuedResponse.fail("放飞申请结果已下发，不允许重复下发");
                }
            } else {
                flyIssuedResponse.fail("未进行放飞申请，不允许下发");
            }
        } catch (Exception e) {
            log.error("[oac]放飞申请结果下发异常，flyIssuedRequest={}", flyIssuedRequest, e);
            flyIssuedResponse.fail(e.getMessage());
        }
        return flyIssuedResponse;
    }

    private FlyReplyRequest buildCacFlyReplyRequest(FlyIssuedRequest flyIssuedRequest, String applyFlyId) {
        FlyReplyRequest cacFlyReplyRequest = new FlyReplyRequest();
        cacFlyReplyRequest.setCpn(flyIssuedRequest.getCpn());
        cacFlyReplyRequest.setApplyFlyId(applyFlyId);
        cacFlyReplyRequest.setReplyFlyId(flyIssuedRequest.getFlyId());
        cacFlyReplyRequest.setPass(flyIssuedRequest.getPass());
        return cacFlyReplyRequest;
    }

    private FlyIssuedResponse buildFlyIssuedResponse(FlyReplyResponse cacFlyReplyResponse) {
        FlyIssuedResponse flyIssuedResponse = new FlyIssuedResponse();
        flyIssuedResponse.setSuccess(cacFlyReplyResponse.getSuccess());
        flyIssuedResponse.setCode(cacFlyReplyResponse.getCode());
        flyIssuedResponse.setMessage(cacFlyReplyResponse.getMessage());
        return flyIssuedResponse;
    }

    private Boolean updateDynamicInfoPlanStatusByReplyFlyId(Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicFlightPlanInfoDO dynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlyId(replyFlyId);
        dynamicFlightPlanInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = oacDynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        int updateDynamicFlightPlanInfoId = oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfo(dynamicFlightPlanInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0 && updateDynamicFlightPlanInfoId > 0) {
            return true;
        } else {
            return false;
        }
    }

    /**
     * 管制信息下发
     *
     * @param atcIssuedRequest
     * @return
     */
    @Override
    public ATCIssuedResponse atcIssued(ATCIssuedRequest atcIssuedRequest) {
        ATCIssuedResponse atcIssuedResponse = new ATCIssuedResponse();
        atcIssuedResponse.fail();
        try {
            log.info("[oac]管制信息下发start，atcIssuedRequest={}", atcIssuedRequest);
            DynamicRouteInfoDO queryDynamicRouteInfo = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlightPlanId(Long.valueOf(atcIssuedRequest.getFlightPlanId()));
            DynamicUavInfoDO queryDynamicUavInfo = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(Long.valueOf(atcIssuedRequest.getFlightPlanId()));
            DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoDO = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(Long.valueOf(atcIssuedRequest.getFlightPlanId()));
            if (queryDynamicUavInfo != null && queryDynamicRouteInfo != null && queryDynamicFlightPlanInfoDO != null) {
                String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
                Long replyFlightPlanId = Long.valueOf(atcIssuedRequest.getFlightPlanId());
                Long replyFlyId = Long.valueOf(atcIssuedRequest.getFlyId());
                ATCIssuedLogDO atcIssuedLog = oacATCIssuedLogDalService.buildATCIssuedLog(replyFlightPlanId, replyFlyId, atcIssuedRequest.getCpn(), atcIssuedRequest.getAtcType(), null,
                        currentTime, null, "oacSystem", DeliverTypeEnum.DELIVERING.getCode());
                int id = oacATCIssuedLogDalService.insertATCIssuedLog(atcIssuedLog);
                if (id > 0) {
                    ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
                    if (updateDynamicInfoStatus(queryDynamicUavInfo, queryDynamicRouteInfo, queryDynamicFlightPlanInfoDO, atcIssuedRequest.getAtcType())) {
                        ATCSendRequest cacATCSendRequest = buildCacATCSendRequest(atcIssuedRequest, queryApplyFlyLog);
                        ATCSendResponse cacATCSendResponse = oacService.atcSend(cacATCSendRequest);
                        atcIssuedResponse = buildATCSendResponse(cacATCSendResponse, atcIssuedRequest.getCpn());
                        if (atcIssuedResponse.getSuccess()) {
                            oacATCIssuedLogDalService.updateATCIssuedLogDelivered(atcIssuedLog, DeliverTypeEnum.DELIVERED.getCode());
                        }
                    } else {
                        atcIssuedResponse.fail("更新动态信息状态失败或管制信息与状态信息不匹配，下发失败");
                    }
                } else {
                    atcIssuedResponse.fail("管制信息插入数据异常，下发失败");
                }
            } else {
                atcIssuedResponse.fail("不存在该飞行计划，不允许下发");
            }
            log.info("[oac]管制信息下发end，atcIssuedRequest={},atcIssuedResponse={}", atcIssuedRequest, JsonUtils.object2Json(atcIssuedResponse));
        } catch (Exception e) {
            log.error("[oac]管制信息发异常，atcIssuedRequest={}", atcIssuedRequest, e);
            atcIssuedResponse.fail(e.getMessage());
        }
        return atcIssuedResponse;
    }

    private boolean updateDynamicInfoStatus(DynamicUavInfoDO queryDynamicUavInfo, DynamicRouteInfoDO queryDynamicRouteInfo, DynamicFlightPlanInfoDO queryDynamicFlightPlanInfoDO, Integer atcType) {
        boolean result = true;
        FlightPlanStatusTypeEnum flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.getFromCode(queryDynamicUavInfo.getPlanStatus());
        if (AtcTypeEnum.ALLOW_TAKE_OFF.equals(AtcTypeEnum.getFromCode(atcType))) {
            if (FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED.equals(flightPlanStatusTypeEnum)) {
                // TODO: 2023/2/19 事务
                int dynamicUavInfoPlanStatusUpdateId = oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                int dynamicRouteInfoPlanStatusUpdateId = oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                int dynamicFlightPlanInfoPlanStatusUpdateId = oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                if (dynamicUavInfoPlanStatusUpdateId <= 0 || dynamicRouteInfoPlanStatusUpdateId <= 0 || dynamicFlightPlanInfoPlanStatusUpdateId <= 0) {
                    result = false;
                }
            } else {
                result = false;
            }
        } else if (AtcTypeEnum.ALLOW_LANDING.equals(AtcTypeEnum.getFromCode(atcType))) {
            if (FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.equals(flightPlanStatusTypeEnum)) {
                // TODO: 2023/2/19 事务
                int dynamicUavInfoPlanStatusUpdateId = oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.getCode());
                int dynamicRouteInfoPlanStatusUpdateId = oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfo, FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.getCode());
                int dynamicFlightPlanInfoPlanStatusUpdateId = oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(queryDynamicFlightPlanInfoDO, FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.getCode());
                if (dynamicUavInfoPlanStatusUpdateId <= 0 || dynamicRouteInfoPlanStatusUpdateId <= 0 || dynamicFlightPlanInfoPlanStatusUpdateId <= 0) {
                    result = false;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    private ATCSendRequest buildCacATCSendRequest(ATCIssuedRequest atcIssuedRequest, ApplyFlyLogDO queryApplyFlyLog) {
        ATCSendRequest atcSendRequest = new ATCSendRequest();
        atcSendRequest.setApplyFlightPlanId(queryApplyFlyLog.getApplyFlightPlanId());
        atcSendRequest.setApplyFlyId(queryApplyFlyLog.getApplyFlyId());
        atcSendRequest.setReplyFlightPlanId(atcIssuedRequest.getFlightPlanId());
        atcSendRequest.setReplyFlyId(atcIssuedRequest.getFlyId());
        atcSendRequest.setCpn(atcIssuedRequest.getCpn());
        atcSendRequest.setAtcType(atcIssuedRequest.getAtcType());
        return atcSendRequest;
    }

    private ATCIssuedResponse buildATCSendResponse(ATCSendResponse atcSendResponse, String cpn) {
        ATCIssuedResponse atcIssuedResponse = new ATCIssuedResponse();
        atcIssuedResponse.setSuccess(atcSendResponse.getSuccess());
        atcIssuedResponse.setCode(atcSendResponse.getCode());
        atcIssuedResponse.setMessage(atcSendResponse.getMessage());
        atcIssuedResponse.setCpn(cpn);
        return atcIssuedResponse;
    }

    /**
     * 告警信息下发
     *
     * @param alarmIssuedRequest
     * @return
     */
    @Override
    public AlarmIssuedResponse alarmIssued(AlarmIssuedRequest alarmIssuedRequest) {
        return null;
    }

    /**
     * 查询动态飞行计划
     *
     * @param queryUavDynamicFlightPlanRequest
     * @return
     */
    @Override
    public QueryUavDynamicFlightPlanResponse queryUavDynamicFlightPlanInfo(QueryUavDynamicFlightPlanRequest queryUavDynamicFlightPlanRequest) {
        QueryUavDynamicFlightPlanResponse queryUavDynamicFlightPlanResponse = new QueryUavDynamicFlightPlanResponse();
        queryUavDynamicFlightPlanResponse.success();
        try {
            //log.info("[oac]查询动态飞行计划start，queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest);
            UavDynamicFlightPlanInfoQueryStatusEnum uavDynamicFlightPlanInfoQueryStatus = UavDynamicFlightPlanInfoQueryStatusEnum.getFromCode(queryUavDynamicFlightPlanRequest.getQueryUavDynamicFlightPlanStatus());
            List<DynamicFlightPlanInfoDO> dynamicFlightPlanInfoList = new ArrayList<>();
            // TODO: 2023/6/10 待优化
            if (UavDynamicFlightPlanInfoQueryStatusEnum.ALL.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLIGHT_PLAN_FORMULATED.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else if (UavDynamicFlightPlanInfoQueryStatusEnum.FLIGHT_PLAN_SUBMITTED.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLIGHT_PLAN_FORMULATED.getCode(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_SUBMITTED.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else if (UavDynamicFlightPlanInfoQueryStatusEnum.FLIGHT_PLAN_APPROVED.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatus(FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else if (UavDynamicFlightPlanInfoQueryStatusEnum.PLANNED_DEPARTURE.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED.getCode(), FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else if (UavDynamicFlightPlanInfoQueryStatusEnum.DEPARTURE.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatus(FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else if (UavDynamicFlightPlanInfoQueryStatusEnum.PLANNED_ARRIVAL.equals(uavDynamicFlightPlanInfoQueryStatus)) {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatus(FlightPlanStatusTypeEnum.ENTER_IDENTIFICATION_AREA.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            } else {
                List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoList = oacDynamicFlightPlanInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.LANDING_APPLY_APPROVED.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                dynamicFlightPlanInfoList.addAll(queryDynamicFlightPlanInfoList);
            }
            if (queryUavDynamicFlightPlanResponse.getSuccess() && CollectionUtils.isNotEmpty(dynamicFlightPlanInfoList)) {
                List<QueryUavDynamicFlightPlanResultParam> queryUavDynamicFlightPlanResultParamList = buildQueryUavDynamicFlightPlanInfoResultParamList(dynamicFlightPlanInfoList);
                queryUavDynamicFlightPlanResponse.setQueryUavDynamicFlightPlanResultParamList(queryUavDynamicFlightPlanResultParamList);
            }
            //log.info("[oac]查询动态飞行计划end，queryUavDynamicFlightPlanRequest={},queryUavDynamicFlightPlanResponse={}", queryUavDynamicFlightPlanRequest, JsonUtils.object2Json(queryUavDynamicFlightPlanResponse));
        } catch (Exception e) {
            log.error("[oac]查询动态飞行计划异常，queryUavDynamicFlightPlanRequest={}", queryUavDynamicFlightPlanRequest, e);
            queryUavDynamicFlightPlanResponse.fail(e.getMessage());
        }
        return queryUavDynamicFlightPlanResponse;
    }

    private List<QueryUavDynamicFlightPlanResultParam> buildQueryUavDynamicFlightPlanInfoResultParamList(List<DynamicFlightPlanInfoDO> dynamicFlightPlanInfoList) {
        List<QueryUavDynamicFlightPlanResultParam> queryUavDynamicFlightPlanResultParamList = new ArrayList<>();
        for (DynamicFlightPlanInfoDO dynamicFlightPlanInfo : dynamicFlightPlanInfoList) {
            QueryUavDynamicFlightPlanResultParam queryUavDynamicFlightPlanResultParam = new QueryUavDynamicFlightPlanResultParam();
            queryUavDynamicFlightPlanResultParam.setShortCpn(dynamicFlightPlanInfo.getCpn().substring(dynamicFlightPlanInfo.getCpn().length() - 4));
            queryUavDynamicFlightPlanResultParam.setShortFlightPlanId(dynamicFlightPlanInfo.getReplyFlightPlanId().toString().substring(dynamicFlightPlanInfo.getReplyFlightPlanId().toString().length() - 4));
            queryUavDynamicFlightPlanResultParam.setCpn(dynamicFlightPlanInfo.getCpn());
            queryUavDynamicFlightPlanResultParam.setFlightPlanId(dynamicFlightPlanInfo.getReplyFlightPlanId().toString());
            queryUavDynamicFlightPlanResultParam.setUavName(dynamicFlightPlanInfo.getUavName());
            queryUavDynamicFlightPlanResultParam.setPilotName(dynamicFlightPlanInfo.getPilotName());
            queryUavDynamicFlightPlanResultParam.setOperatorName(dynamicFlightPlanInfo.getOperatorName());
            queryUavDynamicFlightPlanResultParam.setFlightPlanStartTime(dynamicFlightPlanInfo.getFlightPlanStartTime());
            queryUavDynamicFlightPlanResultParam.setFlightPlanEndTime(dynamicFlightPlanInfo.getFlightPlanEndTime());
            queryUavDynamicFlightPlanResultParam.setTakeoffAirportId(dynamicFlightPlanInfo.getTakeoffAirportId());
            queryUavDynamicFlightPlanResultParam.setLandingAirportId(dynamicFlightPlanInfo.getLandingAirportId());
            queryUavDynamicFlightPlanResultParam.setIsEmergency(dynamicFlightPlanInfo.getIsEmergency());
            queryUavDynamicFlightPlanResultParam.setMissionType(dynamicFlightPlanInfo.getMissionType());
            queryUavDynamicFlightPlanResultParam.setUavPlanStatus(dynamicFlightPlanInfo.getPlanStatus());
            queryUavDynamicFlightPlanResultParam.setUavFlowStatus(dynamicFlightPlanInfo.getFlowStatus());
            queryUavDynamicFlightPlanResultParamList.add(queryUavDynamicFlightPlanResultParam);
        }
        return queryUavDynamicFlightPlanResultParamList;
    }

    /**
     * 查询飞行计划详细信息
     *
     * @param queryFlightPlanInfoRequest
     * @return
     */
    @Override
    public QueryFlightPlanInfoResponse queryUavFlightPlanInfo(QueryFlightPlanInfoRequest queryFlightPlanInfoRequest) {
        QueryFlightPlanInfoResponse queryFlightPlanInfoResponse = new QueryFlightPlanInfoResponse();
        queryFlightPlanInfoResponse.fail();
        try {
            log.info("[oac]查询飞行计划详细信息start，queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest);
            QueryFlightPlanInfoResultParam flightPlanInfoResultParam = new QueryFlightPlanInfoResultParam();
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(Long.valueOf(queryFlightPlanInfoRequest.getFlightPlanId()));
            DynamicFlightPlanInfoDO queryDynamicFlightPlanInfo = oacDynamicFlightPlanInfoDalService.queryDynamicFlightPlanInfoByReplyFlightPlanId(Long.valueOf(queryFlightPlanInfoRequest.getFlightPlanId()));
            if (queryApplyFlightPlanLog != null && queryDynamicFlightPlanInfo != null) {
                UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(queryFlightPlanInfoRequest.getCpn());
                if (queryUavInfo != null && queryFlightPlanInfoRequest.getCpn().equals(queryApplyFlightPlanLog.getCpn())) {
                    List<CoordinateParam> coordinateParamList = JsonUtils.json2List(queryApplyFlightPlanLog.getRoutePointCoordinates(), CoordinateParam.class);
                    flightPlanInfoResultParam.setShortCpn(queryApplyFlightPlanLog.getCpn().substring(queryApplyFlightPlanLog.getCpn().length() - 4));
                    flightPlanInfoResultParam.setShortFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId().toString().substring(queryApplyFlightPlanLog.getReplyFlightPlanId().toString().length() - 4));
                    flightPlanInfoResultParam.setCpn(queryApplyFlightPlanLog.getCpn());
                    flightPlanInfoResultParam.setFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId().toString());
                    flightPlanInfoResultParam.setUavName(queryUavInfo.getUavName());
                    flightPlanInfoResultParam.setRoutePointName(buildRoutePointName(queryApplyFlightPlanLog.getTakeoffAirportId(), queryApplyFlightPlanLog.getLandingAirportId()));
                    flightPlanInfoResultParam.setRoutePointLength(calculateRouteLength(coordinateParamList));
                    flightPlanInfoResultParam.setRoutePointCoordinates(coordinateParamList);
                    flightPlanInfoResultParam.setFlightPlanStartTime(queryApplyFlightPlanLog.getStartTime());
                    flightPlanInfoResultParam.setFlightPlanEndTime(queryApplyFlightPlanLog.getEndTime());
                    flightPlanInfoResultParam.setFlightPlanApplyTime(DateUtils.date2Str(queryApplyFlightPlanLog.getGmtCreate(), DateUtils.DATETIME_MSEC_PATTERN));
                    flightPlanInfoResultParam.setTakeoffAirportId(queryApplyFlightPlanLog.getTakeoffAirportId());
                    flightPlanInfoResultParam.setLandingAirportId(queryApplyFlightPlanLog.getLandingAirportId());
                    flightPlanInfoResultParam.setTakeoffSite(queryApplyFlightPlanLog.getTakeoffSite());
                    flightPlanInfoResultParam.setLandingSite(queryApplyFlightPlanLog.getLandingSite());
                    flightPlanInfoResultParam.setIsEmergency(queryApplyFlightPlanLog.getIsEmergency());
                    flightPlanInfoResultParam.setIsVlos(queryApplyFlightPlanLog.getIsVlos());
                    flightPlanInfoResultParam.setMissionType(queryApplyFlightPlanLog.getMissionType());
                    flightPlanInfoResultParam.setEmergencyProcedure(queryApplyFlightPlanLog.getEmergencyProcedure());
                    flightPlanInfoResultParam.setOperationScenarioType(queryApplyFlightPlanLog.getOperationScenarioType());
                    flightPlanInfoResultParam.setUavPlanStatus(queryDynamicFlightPlanInfo.getPlanStatus());
                    flightPlanInfoResultParam.setPilotName(queryDynamicFlightPlanInfo.getPilotName());
                    flightPlanInfoResultParam.setOperatorName(queryDynamicFlightPlanInfo.getOperatorName());
                    queryFlightPlanInfoResponse.setQueryFlightPlanInfoResultParam(flightPlanInfoResultParam);
                    queryFlightPlanInfoResponse.success();
                } else {
                    queryFlightPlanInfoResponse.fail(ErrorCodeEnum.WRONG_CPN);
                }
            } else {
                queryFlightPlanInfoResponse.fail(ErrorCodeEnum.WRONG_REPLY_FLIGHT_PLAN_ID);
            }
            log.info("[oac]查询飞行计划详细信息end，queryFlightPlanInfoRequest={},queryFlightPlanInfoResponse={}", queryFlightPlanInfoRequest, JsonUtils.object2Json(queryFlightPlanInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询飞行计划详细信息异常，queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest, e);
            queryFlightPlanInfoResponse.fail(e.getMessage());
        }
        return queryFlightPlanInfoResponse;
    }

    private Integer calculateRouteLength(List<CoordinateParam> coordinateParamList) {
        int routeLength = 0;
        if (CollectionUtils.isNotEmpty(coordinateParamList)) {
            for (int index = 0; index < coordinateParamList.size() - 1; index++) {
                Integer distance = GpsDistanceUtils.getDistance(coordinateParamList.get(index).getLng(), coordinateParamList.get(index).getLat(), coordinateParamList.get(index + 1).getLng(), coordinateParamList.get(index + 1).getLat());
                routeLength += distance;
            }
        }
        return routeLength;
    }

    private String buildRoutePointName(String takeoffAirportId, String landingAirportId) {
        String routePointName = "测试航线";
        AirportInfoDO takeoffAirportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(takeoffAirportId);
        AirportInfoDO landingAirportInfo = oacAirportInfoDalService.queryAirportInfoByAirportId(landingAirportId);
        if (takeoffAirportInfo != null && StringUtils.isNotBlank(takeoffAirportInfo.getAirportName()) && landingAirportId != null && StringUtils.isNotBlank(landingAirportInfo.getAirportName())) {
            routePointName = takeoffAirportInfo.getAirportName() + "-" + landingAirportInfo.getAirportName();
        }
        return routePointName;
    }
}
