package com.htfp.service.cac.router.biz.service.http.impl;

import com.google.common.collect.Maps;
import com.htfp.service.cac.client.request.FlightPlanReplyRequest;
import com.htfp.service.cac.client.response.FlightPlanReplyResponse;
import com.htfp.service.cac.common.constant.HttpContentTypeConstant;
import com.htfp.service.cac.common.constant.HttpUriConstant;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.http.CustomHttpConfig;
import com.htfp.service.cac.common.utils.http.HttpAsyncClient;
import com.htfp.service.cac.common.utils.http.HttpContentWrapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.service.http.IRouteToGcsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Service("routeToGcsServiceImpl")
public class RouteToGcsServiceImpl implements IRouteToGcsService {

    @Resource
    UavDalService uavDalService;

    @Resource
    GcsDalService gcsDalService;

    @Resource
    ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    /**
     * 飞行计划回复
     *
     * @param flightPlanReplyRequest
     * @return
     */
    @Override
    public FlightPlanReplyResponse flightPlanReply(FlightPlanReplyRequest flightPlanReplyRequest) {
        FlightPlanReplyResponse flightPlanReplyResponse = new FlightPlanReplyResponse();
        flightPlanReplyResponse.fail();
        try {
            UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(flightPlanReplyRequest.getCpn());
            ApplyFlightPlanLogDO queryApplyFlightPlanLog = applyFlightPlanLogDalService.queryApplyFlightPlanLogByApplyFlightPlanId(Long.valueOf(flightPlanReplyRequest.getApplyFlightPlanId()));
            if (queryApplyFlightPlanLog != null &&
                    queryApplyFlightPlanLog.getReplyFlightPlanId() != null &&
                    queryApplyFlightPlanLog.getReplyFlightPlanId().equals(flightPlanReplyRequest.getReplyFlightPlanId())) {
                if (queryUavInfo != null &&
                        queryApplyFlightPlanLog.getUavId() != null &&
                        queryUavInfo.getId().toString().equals(queryApplyFlightPlanLog.getUavId())) {
                    GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(Long.valueOf(queryApplyFlightPlanLog.getGcsId()));
                    if (queryGcsIpMapping != null &&
                            queryGcsIpMapping.getGcsIp() != null &&
                            MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus())) &&
                            LinkStatusEnum.ONLINE.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                        flightPlanReplyResponse = flightPlanReplyToGcs(flightPlanReplyRequest, queryApplyFlightPlanLog.getUavId(), queryApplyFlightPlanLog.getGcsId(), queryGcsIpMapping.getGcsIp() + "/" + HttpUriConstant.FLIGHT_PLAN_REPLY);
                    } else {
                        flightPlanReplyResponse.fail("无人机系统未连接，飞行计划回复失败");
                    }
                } else {
                    flightPlanReplyResponse.fail("无人机信息异常，飞行计划回复失败");
                }
            } else {
                flightPlanReplyResponse.fail("飞行计划信息异常，飞行计划回复失败");
            }
        } catch (Exception e) {
            log.error("飞行计划回复异常, flightPlanReplyRequest={}", flightPlanReplyRequest, e);
            flightPlanReplyResponse.fail("飞行计划回复异常");
        }
        return flightPlanReplyResponse;
    }

    public FlightPlanReplyResponse flightPlanReplyToGcs(FlightPlanReplyRequest flightPlanReplyRequest, String uavId, String gcsId, String url) {
        FlightPlanReplyResponse flightPlanReplyResponse = null;
        try {
            com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest gcsFlightPlanReplyRequest = buildGcsFlightPlanReplyRequest(flightPlanReplyRequest, uavId);
            HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                    .contentObject(JsonUtils.object2Json(gcsFlightPlanReplyRequest, false))
                    .gcsId(Long.valueOf(gcsId))
                    .contentType(HttpContentTypeConstant.JSON_TYPE)
                    .create();
            Map<String, String> httpHeader = builderRequestHeader();
            CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
            String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
            com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse = decodeHttpResponse(httpResponse);
            flightPlanReplyResponse = buildFlightPlanReplyResponse(gcsFlightPlanReplyResponse);
        } catch (Exception e) {
            log.error("[router]飞行计划回复请求地面站，发生异常", e);
        }
        return flightPlanReplyResponse;
    }

    public Map<String, String> builderRequestHeader() {
        Map<String, String> map = Maps.newHashMap();
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json;charset=UTF-8");
        return map;
    }

    private CustomHttpConfig buildCustomHttpConfig() {
        return new CustomHttpConfig();
    }

    com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest buildGcsFlightPlanReplyRequest(FlightPlanReplyRequest flightPlanReplyRequest, String uavId) {
        com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest gcsFlightPlanReplyRequest = new com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest();
        gcsFlightPlanReplyRequest.setFlightPlanPass(flightPlanReplyRequest.getPass());
        gcsFlightPlanReplyRequest.setUavId(uavId);
        gcsFlightPlanReplyRequest.setApplyFlightPlanId(flightPlanReplyRequest.getApplyFlightPlanId());
        gcsFlightPlanReplyRequest.setReplyFlightPlanId(flightPlanReplyRequest.getReplyFlightPlanId());
        return gcsFlightPlanReplyRequest;
    }

    private com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse decodeHttpResponse(String response) throws Exception {
        if (response == null || StringUtils.isBlank(response)) {
            return null;
        } else {
            com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse = JsonUtils.json2ObjectThrowException(response, com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse.class);
            gcsFlightPlanReplyResponse.setResultStr(response);
            return gcsFlightPlanReplyResponse;
        }
    }

    FlightPlanReplyResponse buildFlightPlanReplyResponse(com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse) {
        FlightPlanReplyResponse flightPlanReplyResponse = new FlightPlanReplyResponse();
        flightPlanReplyResponse.setSuccess(gcsFlightPlanReplyResponse.getSuccess());
        flightPlanReplyResponse.setCode(gcsFlightPlanReplyResponse.getCode());
        flightPlanReplyResponse.setMessage(gcsFlightPlanReplyResponse.getMessage());
        return flightPlanReplyResponse;
    }
}
