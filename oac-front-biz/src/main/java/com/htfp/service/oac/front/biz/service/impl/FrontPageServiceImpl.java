package com.htfp.service.oac.front.biz.service.impl;

import com.htfp.service.cac.dao.model.oac.ATCIssuedLogDO;
import com.htfp.service.cac.dao.model.oac.AlarmIssuedLogDO;
import com.htfp.service.cac.dao.service.oac.OacATCIssuedLogDalService;
import com.htfp.service.cac.dao.service.oac.OacAlarmIssuedLogDalService;
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
import com.htfp.service.oac.common.enums.UavDynamicInfoQueryStatusEnum;
import com.htfp.service.oac.common.utils.DateUtils;
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
import com.htfp.service.oac.front.biz.model.response.param.CoordinateParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryAirportInfoResultParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryAlarmMessageInfoParam;
import com.htfp.service.oac.front.biz.model.response.param.QueryFlightPlanInfoParam;
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
            UavDynamicInfoQueryStatusEnum frontQueryPlanStatusEnum = UavDynamicInfoQueryStatusEnum.getFromCode(queryUavDynamicInfoRequest.getUavPlanStatus());
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
                if (UavDynamicInfoQueryStatusEnum.ALL.equals(frontQueryPlanStatusEnum)) {
                    // TODO: 2023/2/20 待优化
                    List<DynamicUavInfoDO> queryDynamicUavInfoDOList = oacDynamicUavInfoDalService.queryByPlanStatusInterval(FlightPlanStatusTypeEnum.FLY_APPLY_SUBMITTED.getCode(), FlightPlanStatusTypeEnum.COMPLETE_LANDING.getCode());
                    dynamicUavInfoDOList.addAll(queryDynamicUavInfoDOList);
                } else if (UavDynamicInfoQueryStatusEnum.FLIGHT_PLAN_PASS_AND_NOT_OVER.equals(frontQueryPlanStatusEnum)) {
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
                } else if (UavDynamicInfoQueryStatusEnum.ARRIVAL.equals(frontQueryPlanStatusEnum)) {
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

    List<QueryUavDynamicInfoResultParam> buildQueryUavDynamicInfoResultParamList(List<DynamicUavInfoDO> dynamicUavInfoDOList) {
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

    QueryUavRouteInfoResultParam buildQueryUavRouteInfoResultParam(DynamicRouteInfoDO dynamicRouteInfo) {
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

    QueryAirportInfoResultParam buildQueryAirportInfoResultParam(AirportInfoDO airportInfo) {
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
            List<QueryAlarmMessageInfoParam> queryAlarmMessageInfoParamList = new ArrayList<>();
            for (String alarmId : queryAlarmMessageInfoRequest.getAlarmIds()) {
                AlarmIssuedLogDO alarmIssuedLog = oacAlarmIssuedLogDalService.queryAlarmIssuedLog(Long.valueOf(alarmId));
                queryAlarmMessageInfoParamList.add(buildQueryAlarmMessageInfoParam(alarmIssuedLog));
            }
            queryAlarmMessageInfoResponse.setQueryAlarmMessageInfoParamList(queryAlarmMessageInfoParamList);
            queryAlarmMessageInfoResponse.success();
            log.info("[oac]查询告警信息end，queryAlarmMessageInfoRequest={},queryAlarmMessageInfoResponse={}", queryAlarmMessageInfoRequest, JsonUtils.object2Json(queryAlarmMessageInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询告警信息异常，queryAlarmMessageInfoRequest={}", queryAlarmMessageInfoRequest, e);
            queryAlarmMessageInfoResponse.fail(e.getMessage());
        }
        return queryAlarmMessageInfoResponse;
    }

    QueryAlarmMessageInfoParam buildQueryAlarmMessageInfoParam(AlarmIssuedLogDO alarmIssuedLog) {
        QueryAlarmMessageInfoParam queryAlarmMessageInfoParam = new QueryAlarmMessageInfoParam();
        queryAlarmMessageInfoParam.setAlarmId(alarmIssuedLog.getId().toString());
        queryAlarmMessageInfoParam.setFlyId(alarmIssuedLog.getReplyFlyId().toString());
        queryAlarmMessageInfoParam.setCpn(alarmIssuedLog.getCpn());
        queryAlarmMessageInfoParam.setAlarmLevel(alarmIssuedLog.getAlarmLevel());
        queryAlarmMessageInfoParam.setAlarmContent(alarmIssuedLog.getAlarmContent());
        queryAlarmMessageInfoParam.setAlarmEffectTime(alarmIssuedLog.getAlarmEffectTime());
        queryAlarmMessageInfoParam.setAlarmOperator(alarmIssuedLog.getAlarmOperator());
        queryAlarmMessageInfoParam.setAlarmDeliver(alarmIssuedLog.getAlarmDelivered());
        return queryAlarmMessageInfoParam;
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
                Integer queryApplyFlightPlanLogStatus = queryApplyFlightPlanLog.getStatus();
                if (ApplyStatusEnum.PENDING.equals(ApplyStatusEnum.getFromCode(queryApplyFlightPlanLogStatus))) {
                    if (flightPlanIssuedRequest.getPass()) {
                        oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.APPROVED.getCode());
                        // 飞行计划通过,存储动态信息
                        if (!initializeDynamicInfo(queryApplyFlightPlanLog, flightPlanIssuedRequest.getCpn(), FlightPlanStatusTypeEnum.FLIGHT_PLAN_APPROVED.getCode())) {
                            //ROLLBACK
                            oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, queryApplyFlightPlanLogStatus);
                            flightPlanIssuedResponse.fail();
                        } else {
                            flightPlanIssuedResponse.success();
                        }
                    } else {
                        oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.UNAPPROVED.getCode());
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
                flightPlanIssuedResponse.fail("未申请该飞行计划，不允许下发");
            }
        } catch (Exception e) {
            log.error("[oac]飞行计划下发异常，flightPlanIssuedRequest={}", flightPlanIssuedRequest, e);
            flightPlanIssuedResponse.fail(e.getMessage());
        }
        return flightPlanIssuedResponse;
    }

    FlightPlanReplyRequest buildCacFlightPlanReplyRequest(FlightPlanIssuedRequest flightPlanIssuedRequest, String applyFlightPlanId) {
        FlightPlanReplyRequest cacFlightPlanReplyRequest = new FlightPlanReplyRequest();
        cacFlightPlanReplyRequest.setCpn(flightPlanIssuedRequest.getCpn());
        cacFlightPlanReplyRequest.setApplyFlightPlanId(applyFlightPlanId);
        cacFlightPlanReplyRequest.setReplyFlightPlanId(flightPlanIssuedRequest.getFlightPlanId());
        cacFlightPlanReplyRequest.setPass(flightPlanIssuedRequest.getPass());
        return cacFlightPlanReplyRequest;
    }

    FlightPlanIssuedResponse buildFlightPlanIssuedResponse(FlightPlanReplyResponse cacFlightPlanReplyResponse, String cpn) {
        FlightPlanIssuedResponse flightPlanIssuedResponse = new FlightPlanIssuedResponse();
        flightPlanIssuedResponse.setSuccess(cacFlightPlanReplyResponse.getSuccess());
        flightPlanIssuedResponse.setCode(cacFlightPlanReplyResponse.getCode());
        flightPlanIssuedResponse.setMessage(cacFlightPlanReplyResponse.getMessage());
        flightPlanIssuedResponse.setCpn(cpn);
        return flightPlanIssuedResponse;
    }

    Boolean initializeDynamicInfo(ApplyFlightPlanLogDO queryApplyFlightPlanLog, String cpn, Integer flightPlanStatus) {
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

    FlyReplyRequest buildCacFlyReplyRequest(FlyIssuedRequest flyIssuedRequest, String applyFlyId) {
        FlyReplyRequest cacFlyReplyRequest = new FlyReplyRequest();
        cacFlyReplyRequest.setCpn(flyIssuedRequest.getCpn());
        cacFlyReplyRequest.setApplyFlyId(applyFlyId);
        cacFlyReplyRequest.setReplyFlyId(flyIssuedRequest.getFlyId());
        cacFlyReplyRequest.setPass(flyIssuedRequest.getPass());
        return cacFlyReplyRequest;
    }

    FlyIssuedResponse buildFlyIssuedResponse(FlyReplyResponse cacFlyReplyResponse) {
        FlyIssuedResponse flyIssuedResponse = new FlyIssuedResponse();
        flyIssuedResponse.setSuccess(cacFlyReplyResponse.getSuccess());
        flyIssuedResponse.setCode(cacFlyReplyResponse.getCode());
        flyIssuedResponse.setMessage(cacFlyReplyResponse.getMessage());
        return flyIssuedResponse;
    }

    Boolean updateDynamicInfoPlanStatusByReplyFlyId(Long replyFlyId, FlightPlanStatusTypeEnum flightPlanStatusTypeEnum) {
        String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
        DynamicUavInfoDO dynamicUavInfoDO = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlyId(replyFlyId);
        dynamicUavInfoDO.setUpdateTime(currentTime);
        dynamicUavInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        DynamicRouteInfoDO dynamicRouteInfoDO = oacDynamicRouteInfoDalService.queryDynamicRouteInfoByReplyFlyId(replyFlyId);
        dynamicRouteInfoDO.setPlanStatus(flightPlanStatusTypeEnum.getCode());
        // TODO: 2023/2/10 事务
        int updateDynamicUavInfoId = oacDynamicUavInfoDalService.updateDynamicUavInfo(dynamicUavInfoDO);
        int updateDynamicRouteInfoId = oacDynamicRouteInfoDalService.updateDynamicRouteInfo(dynamicRouteInfoDO);
        if (updateDynamicUavInfoId > 0 && updateDynamicRouteInfoId > 0) {
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
            if (queryDynamicUavInfo != null && queryDynamicRouteInfo != null) {
                String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
                Long replyFlightPlanId = Long.valueOf(atcIssuedRequest.getFlightPlanId());
                Long replyFlyId = Long.valueOf(atcIssuedRequest.getFlyId());
                ATCIssuedLogDO atcIssuedLog = oacATCIssuedLogDalService.buildATCIssuedLog(replyFlightPlanId, replyFlyId, atcIssuedRequest.getCpn(), atcIssuedRequest.getAtcType(), null,
                        currentTime, null, "oacSystem", DeliverTypeEnum.DELIVERING.getCode());
                int id = oacATCIssuedLogDalService.insertATCIssuedLog(atcIssuedLog);
                if (id > 0) {
                    ApplyFlyLogDO queryApplyFlyLog = oacApplyFlyLogDalService.queryApplyFlyLogByReplyFlyId(replyFlyId);
                    if (updateDynamicInfoStatus(queryDynamicUavInfo, queryDynamicRouteInfo, atcIssuedRequest.getAtcType())) {
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

    boolean updateDynamicInfoStatus(DynamicUavInfoDO queryDynamicUavInfo, DynamicRouteInfoDO queryDynamicRouteInfo, Integer atcType) {
        boolean result = true;
        FlightPlanStatusTypeEnum flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.getFromCode(queryDynamicUavInfo.getPlanStatus());
        if (AtcTypeEnum.ALLOW_TAKE_OFF.equals(AtcTypeEnum.getFromCode(atcType))) {
            if (FlightPlanStatusTypeEnum.FLY_APPLY_APPROVED.equals(flightPlanStatusTypeEnum)) {
                // TODO: 2023/2/19 事务
                int dynamicUavInfoPlanStatusUpdateId = oacDynamicUavInfoDalService.updateDynamicUavInfoPlanStatus(queryDynamicUavInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                int dynamicRouteInfoPlanStatusUpdateId = oacDynamicRouteInfoDalService.updateDynamicRouteInfoPlanStatus(queryDynamicRouteInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_IMPLEMENT.getCode());
                if (dynamicUavInfoPlanStatusUpdateId <= 0 || dynamicRouteInfoPlanStatusUpdateId <= 0) {
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
                if (dynamicUavInfoPlanStatusUpdateId <= 0 || dynamicRouteInfoPlanStatusUpdateId <= 0) {
                    result = false;
                }
            } else {
                result = false;
            }
        }
        return result;
    }

    ATCSendRequest buildCacATCSendRequest(ATCIssuedRequest atcIssuedRequest, ApplyFlyLogDO queryApplyFlyLog) {
        ATCSendRequest atcSendRequest = new ATCSendRequest();
        atcSendRequest.setApplyFlightPlanId(queryApplyFlyLog.getApplyFlightPlanId());
        atcSendRequest.setApplyFlyId(queryApplyFlyLog.getApplyFlyId());
        atcSendRequest.setReplyFlightPlanId(atcIssuedRequest.getFlightPlanId());
        atcSendRequest.setReplyFlyId(atcIssuedRequest.getFlyId());
        atcSendRequest.setCpn(atcIssuedRequest.getCpn());
        atcSendRequest.setAtcType(atcIssuedRequest.getAtcType());
        return atcSendRequest;
    }

    ATCIssuedResponse buildATCSendResponse(ATCSendResponse atcSendResponse, String cpn) {
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
     * 查询飞行计划
     *
     * @param queryFlightPlanInfoRequest
     * @return
     */
    // TODO: 2023/2/20 待优化,动态信息表,支持多种动态查询,现在查询耗性能
    @Override
    public QueryFlightPlanInfoResponse queryFlightPlanInfo(QueryFlightPlanInfoRequest queryFlightPlanInfoRequest) {
        QueryFlightPlanInfoResponse queryFlightPlanInfoResponse = new QueryFlightPlanInfoResponse();
        queryFlightPlanInfoResponse.fail();
        try {
            //log.info("[oac]查询飞行计划start，queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest);
            List<QueryFlightPlanInfoParam> flightPlanInfoParamList = new ArrayList<>();
            List<ApplyFlightPlanLogDO> queryApplyFlightPlanLogList;
            FlightPlanStatusTypeEnum flightPlanStatusTypeEnum = FlightPlanStatusTypeEnum.getFromCode(queryFlightPlanInfoRequest.getUavPlanStatus());
            if (FlightPlanStatusTypeEnum.FLIGHT_PLAN_SUBMITTED.equals(flightPlanStatusTypeEnum)) {
                queryApplyFlightPlanLogList = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByStatus(ApplyStatusEnum.PENDING.getCode());
                for (ApplyFlightPlanLogDO queryApplyFlightPlanLog : queryApplyFlightPlanLogList) {
                    UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(queryApplyFlightPlanLog.getCpn());
                    flightPlanInfoParamList.add(buildQueryFlightPlanInfoParam(queryApplyFlightPlanLog, queryUavInfo.getUavName(), queryFlightPlanInfoRequest.getUavPlanStatus()));
                }
            } else {
                queryApplyFlightPlanLogList = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByStatus(ApplyStatusEnum.APPROVED.getCode());
                for (ApplyFlightPlanLogDO queryApplyFlightPlanLog : queryApplyFlightPlanLogList) {
                    DynamicUavInfoDO dynamicUavInfo = oacDynamicUavInfoDalService.queryDynamicUavInfoByReplyFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId());
                    if (flightPlanStatusTypeEnum.equals(FlightPlanStatusTypeEnum.getFromCode(dynamicUavInfo.getPlanStatus()))) {
                        flightPlanInfoParamList.add(buildQueryFlightPlanInfoParam(queryApplyFlightPlanLog, dynamicUavInfo.getUavName(), queryFlightPlanInfoRequest.getUavPlanStatus()));
                    }
                }
            }
            queryFlightPlanInfoResponse.setQueryFlightPlanInfoParamList(flightPlanInfoParamList);
            queryFlightPlanInfoResponse.success();
            //log.info("[oac]查询飞行计划end，queryFlightPlanInfoRequest={},queryFlightPlanInfoResponse={}", queryFlightPlanInfoRequest, JsonUtils.object2Json(queryFlightPlanInfoResponse));
        } catch (Exception e) {
            log.error("[oac]查询飞行计划异常，queryFlightPlanInfoRequest={}", queryFlightPlanInfoRequest, e);
            queryFlightPlanInfoResponse.fail(e.getMessage());
        }
        return queryFlightPlanInfoResponse;
    }

    QueryFlightPlanInfoParam buildQueryFlightPlanInfoParam(ApplyFlightPlanLogDO queryApplyFlightPlanLog, String uavName, Integer planStatus) {
        QueryFlightPlanInfoParam queryFlightPlanInfoParam = new QueryFlightPlanInfoParam();
        queryFlightPlanInfoParam.setCpn(queryApplyFlightPlanLog.getCpn());
        queryFlightPlanInfoParam.setShortCpn(queryApplyFlightPlanLog.getCpn().substring(queryApplyFlightPlanLog.getCpn().length() - 4));
        queryFlightPlanInfoParam.setFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId().toString());
        queryFlightPlanInfoParam.setShortFlightPlanId(queryApplyFlightPlanLog.getReplyFlightPlanId().toString().substring(queryApplyFlightPlanLog.getReplyFlightPlanId().toString().length() - 4));
        queryFlightPlanInfoParam.setUavName(uavName);
        queryFlightPlanInfoParam.setRoutePointCoordinates(JsonUtils.json2List(queryApplyFlightPlanLog.getRoutePointCoordinates(), CoordinateParam.class));
        queryFlightPlanInfoParam.setTakeoffAirportId(queryApplyFlightPlanLog.getTakeoffAirportId());
        queryFlightPlanInfoParam.setLandingAirportId(queryApplyFlightPlanLog.getLandingAirportId());
        queryFlightPlanInfoParam.setTakeoffSite(queryApplyFlightPlanLog.getTakeoffSite());
        queryFlightPlanInfoParam.setLandingSite(queryApplyFlightPlanLog.getLandingSite());
        queryFlightPlanInfoParam.setStartTime(queryApplyFlightPlanLog.getStartTime());
        queryFlightPlanInfoParam.setEndTime(queryApplyFlightPlanLog.getEndTime());
        queryFlightPlanInfoParam.setMissionType(queryApplyFlightPlanLog.getMissionType());
        queryFlightPlanInfoParam.setEmergencyProcedure(queryApplyFlightPlanLog.getEmergencyProcedure());
        queryFlightPlanInfoParam.setOperationScenarioType(queryApplyFlightPlanLog.getOperationScenarioType());
        queryFlightPlanInfoParam.setIsEmergency(queryApplyFlightPlanLog.getIsEmergency());
        queryFlightPlanInfoParam.setIsVlos(queryApplyFlightPlanLog.getIsVlos());
        queryFlightPlanInfoParam.setPlanStatus(planStatus);
        return queryFlightPlanInfoParam;
    }
}
