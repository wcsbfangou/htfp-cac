package com.htfp.service.cac.router.biz.service.http.impl;

import com.google.common.collect.Maps;
import com.htfp.service.cac.common.enums.DeliverTypeEnum;
import com.htfp.service.cac.common.enums.dataFrame.DataFrameVersionEnum;
import com.htfp.service.cac.common.enums.dataFrame.GcsTcpTypeEnum;
import com.htfp.service.cac.common.enums.dataFrame.MagicCodeEnum;
import com.htfp.service.cac.common.enums.dataFrame.SerializationAlgorithmEnum;
import com.htfp.service.cac.common.utils.DateUtils;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.log.ATCIssuedLogDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import com.htfp.service.cac.dao.service.ATCIssuedLogDalService;
import com.htfp.service.cac.router.biz.model.inner.request.ATCSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.AlarmSendRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.request.FlyReplyRequest;
import com.htfp.service.cac.router.biz.model.inner.response.ATCSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.AlarmSendResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlightPlanReplyResponse;
import com.htfp.service.cac.router.biz.model.inner.response.FlyReplyResponse;
import com.htfp.service.cac.common.constant.HttpContentTypeConstant;
import com.htfp.service.cac.common.enums.ApplyStatusEnum;
import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.http.CustomHttpConfig;
import com.htfp.service.cac.common.utils.http.HttpAsyncClient;
import com.htfp.service.cac.common.utils.http.HttpContentWrapper;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.log.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.log.ApplyFlyLogDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import com.htfp.service.cac.dao.service.ApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.ApplyFlyLogDalService;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.service.http.IRouteToGcsService;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import com.htfp.service.cac.router.biz.service.tcp.message.request.TcpFlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.service.tcp.server.TcpNettyChannelManager;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.nio.ByteBuffer;
import java.util.Date;
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
    private UavDalService uavDalService;

    @Resource
    private GcsDalService gcsDalService;

    @Resource
    private ApplyFlightPlanLogDalService applyFlightPlanLogDalService;

    @Resource
    private ApplyFlyLogDalService applyFlyLogDalService;

    @Resource
    private ATCIssuedLogDalService atcIssuedLogDalService;

    @Resource
    private TcpNettyChannelManager tcpNettyChannelManager;

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
                        queryUavInfo.getId().equals(queryApplyFlightPlanLog.getUavId())) {
                    int id = applyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, flightPlanReplyRequest.getPass() ? ApplyStatusEnum.APPROVED.getCode() : ApplyStatusEnum.UNAPPROVED.getCode());
                    if (id > 0) {
                        // 查询uav与gcs的mapping关系
                        UavGcsMappingDO queryUavGcsMapping = uavDalService.queryUavGcsMapping(queryUavInfo.getId());
                        if (queryUavGcsMapping != null &&
                                queryUavGcsMapping.getGcsId() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryUavGcsMapping.getStatus()))) {
                            flightPlanReplyResponse.success();
                            tcpFlightPlanReplyToGcs(flightPlanReplyRequest, queryApplyFlightPlanLog.getUavId(), queryUavGcsMapping.getGcsId());
                        } else {
                            flightPlanReplyResponse.fail("无人机系统未连接，管制信息下发失败");
                        }
                        /* //查询gcs与ip的mapping关系
                        GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(queryApplyFlightPlanLog.getGcsId());
                        if (queryGcsIpMapping != null &&
                                queryGcsIpMapping.getGcsIp() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus())) &&
                                LinkStatusEnum.ONLINE.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                            // TODO: 2023/2/17 请求地面站，记得修改回来
                            flightPlanReplyResponse.success();
                            tcpFlightPlanReplyToGcs(flightPlanReplyRequest, queryApplyFlightPlanLog.getUavId(), queryApplyFlightPlanLog.getGcsId());
                            //flightPlanReplyResponse = flightPlanReplyToGcs(flightPlanReplyRequest, queryApplyFlightPlanLog.getUavId(), queryApplyFlightPlanLog.getGcsId(), queryGcsIpMapping.getGcsIp() + "/" + HttpUriConstant.FLIGHT_PLAN_REPLY);
                        } else {
                            flightPlanReplyResponse.fail("无人机系统未连接，飞行计划回复失败");
                        }*/
                    } else {
                        flightPlanReplyResponse.fail("写入飞行计划状态异常，飞行计划回复失败");
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

    private void tcpFlightPlanReplyToGcs(FlightPlanReplyRequest flightPlanReplyRequest, Long uavId, Long gcsId) {
        GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsId);
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = buildGcsTcpBaseDataFrame(uavId.toString(), gcsId.toString(), queryGcsInfo.getToken());
        gcsTcpBaseDataFrame.setType(GcsTcpTypeEnum.FLIGHT_PLAN_REPLY_REQUEST.getType());
        byte[] applyFlightPlanIdBytes = flightPlanReplyRequest.getApplyFlightPlanId().getBytes();
        byte[] replyFlightPlanIdBytes = flightPlanReplyRequest.getReplyFlightPlanId().getBytes();
        int readableDataBytesLength = 3 + applyFlightPlanIdBytes.length + replyFlightPlanIdBytes.length;
        ByteBuffer readableDataBytesByteBuffer = ByteBuffer.allocate(readableDataBytesLength);
        byte[] readableDataBytes = new byte[readableDataBytesLength];
        readableDataBytesByteBuffer.put((byte) applyFlightPlanIdBytes.length);
        readableDataBytesByteBuffer.put(applyFlightPlanIdBytes);
        readableDataBytesByteBuffer.put((byte) replyFlightPlanIdBytes.length);
        readableDataBytesByteBuffer.put(replyFlightPlanIdBytes);
        readableDataBytesByteBuffer.put(flightPlanReplyRequest.getPass() ? (byte) 1 : (byte) 0);
        readableDataBytesByteBuffer.flip();
        readableDataBytesByteBuffer.get(readableDataBytes);
        gcsTcpBaseDataFrame.setReadableDataBytesLength(readableDataBytesLength);
        gcsTcpBaseDataFrame.setReadableDataBytes(readableDataBytes);
        tcpNettyChannelManager.send(gcsId.toString(), gcsTcpBaseDataFrame);
    }

    private GcsTcpBaseDataFrame buildGcsTcpBaseDataFrame(String uavId, String gcsId, String gcsToken) {
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = new GcsTcpBaseDataFrame();
        gcsTcpBaseDataFrame.setMagicCode(MagicCodeEnum.DATA_TRANSFER.getCode());
        gcsTcpBaseDataFrame.setVersion(DataFrameVersionEnum.VERSION_0.getType());
        gcsTcpBaseDataFrame.setSerializationAlgorithm(SerializationAlgorithmEnum.NO_ALGORITHM.getType());
        gcsTcpBaseDataFrame.setGcsIdLength((byte) gcsId.getBytes().length);
        gcsTcpBaseDataFrame.setGcsId(gcsId);
        gcsTcpBaseDataFrame.setGcsTokenLength((byte) gcsToken.getBytes().length);
        gcsTcpBaseDataFrame.setGcsToken(gcsToken);
        gcsTcpBaseDataFrame.setUavIdLength((byte) uavId.getBytes().length);
        gcsTcpBaseDataFrame.setUavId(uavId);
        return gcsTcpBaseDataFrame;
    }

    public FlightPlanReplyResponse flightPlanReplyToGcs(FlightPlanReplyRequest flightPlanReplyRequest, Long uavId, Long gcsId, String url) {
        FlightPlanReplyResponse flightPlanReplyResponse = new FlightPlanReplyResponse();
        flightPlanReplyResponse.fail();
        try {
            com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest gcsFlightPlanReplyRequest = buildGcsFlightPlanReplyRequest(flightPlanReplyRequest, uavId);
            HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                    .contentObject(JsonUtils.object2Json(gcsFlightPlanReplyRequest, false))
                    .gcsId(gcsId)
                    .contentType(HttpContentTypeConstant.JSON_TYPE)
                    .create();
            Map<String, String> httpHeader = builderRequestHeader();
            CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
            String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
            com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse = decodeHttpResponseToFlightPlanReplyResponse(httpResponse);
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

    private com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest buildGcsFlightPlanReplyRequest(FlightPlanReplyRequest flightPlanReplyRequest, Long uavId) {
        com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest gcsFlightPlanReplyRequest = new com.htfp.service.cac.router.biz.model.http.request.FlightPlanReplyRequest();
        gcsFlightPlanReplyRequest.setFlightPlanPass(flightPlanReplyRequest.getPass());
        gcsFlightPlanReplyRequest.setUavId(uavId.toString());
        gcsFlightPlanReplyRequest.setApplyFlightPlanId(flightPlanReplyRequest.getApplyFlightPlanId());
        gcsFlightPlanReplyRequest.setReplyFlightPlanId(flightPlanReplyRequest.getReplyFlightPlanId());
        return gcsFlightPlanReplyRequest;
    }

    private com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse decodeHttpResponseToFlightPlanReplyResponse(String response) throws Exception {
        if (response == null || StringUtils.isBlank(response)) {
            return null;
        } else {
            com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse = JsonUtils.json2ObjectThrowException(response, com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse.class);
            gcsFlightPlanReplyResponse.setResultStr(response);
            return gcsFlightPlanReplyResponse;
        }
    }

    private FlightPlanReplyResponse buildFlightPlanReplyResponse(com.htfp.service.cac.router.biz.model.http.response.FlightPlanReplyResponse gcsFlightPlanReplyResponse) {
        FlightPlanReplyResponse flightPlanReplyResponse = new FlightPlanReplyResponse();
        flightPlanReplyResponse.setSuccess(gcsFlightPlanReplyResponse.getSuccess());
        flightPlanReplyResponse.setCode(gcsFlightPlanReplyResponse.getCode());
        flightPlanReplyResponse.setMessage(gcsFlightPlanReplyResponse.getMessage());
        return flightPlanReplyResponse;
    }

    /**
     * 放飞申请回复
     *
     * @param flyReplyRequest
     * @return
     */
    @Override
    public FlyReplyResponse flyReply(FlyReplyRequest flyReplyRequest) {
        FlyReplyResponse flyReplyResponse = new FlyReplyResponse();
        flyReplyResponse.fail();
        try {
            UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(flyReplyRequest.getCpn());
            ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByApplyFlyId(Long.valueOf(flyReplyRequest.getApplyFlyId()));
            if (queryApplyFlyLog != null &&
                    queryApplyFlyLog.getReplyFlyId() != null &&
                    queryApplyFlyLog.getReplyFlyId().equals(flyReplyRequest.getReplyFlyId())) {
                if (queryUavInfo != null &&
                        queryApplyFlyLog.getUavId() != null &&
                        queryUavInfo.getId().equals(queryApplyFlyLog.getUavId())) {
                    // 修改状态和上报编码
                    int id = applyFlyLogDalService.updateApplyFlyLogStatus(queryApplyFlyLog, flyReplyRequest.getPass() ? ApplyStatusEnum.APPROVED.getCode() : ApplyStatusEnum.UNAPPROVED.getCode());
                    boolean updateReportCodeResult = updateUavOacMappingReportCode(queryUavInfo.getId(), queryApplyFlyLog.getReplyFlyId(), flyReplyRequest.getPass());
                    if (id > 0 && updateReportCodeResult) {
                        // 查询uav与gcs的mapping关系
                        UavGcsMappingDO queryUavGcsMapping = uavDalService.queryUavGcsMapping(queryUavInfo.getId());
                        if (queryUavGcsMapping != null &&
                                queryUavGcsMapping.getGcsId() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryUavGcsMapping.getStatus()))) {
                            flyReplyResponse.success();
                            tcpFlyReplyToGcs(flyReplyRequest, queryApplyFlyLog.getUavId(), queryUavGcsMapping.getGcsId());
                        } else {
                            flyReplyResponse.fail("无人机系统未连接，管制信息下发失败");
                        }
                        /*// 查询无人机信息
                        GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(queryApplyFlyLog.getGcsId());
                        if (queryGcsIpMapping != null &&
                                queryGcsIpMapping.getGcsIp() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus())) &&
                                LinkStatusEnum.ONLINE.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                            // TODO: 2023/2/17 请求地面站记得修改回来
                            flyReplyResponse.success();
                            tcpFlyReplyToGcs(flyReplyRequest, queryApplyFlyLog.getUavId(), queryApplyFlyLog.getGcsId());
                            // flyReplyResponse = flyReplyToGcs(flyReplyRequest, queryApplyFlyLog.getUavId(), queryApplyFlyLog.getGcsId(), queryGcsIpMapping.getGcsIp() + "/" + HttpUriConstant.FLY_REPLY);
                        } else {
                            flyReplyResponse.fail("无人机系统未连接，放飞申请回复失败");
                        }*/
                    } else {
                        flyReplyResponse.fail("写入放飞申请状态和上报编码异常，放飞申请回复失败");
                    }
                } else {
                    flyReplyResponse.fail("无人机信息异常，放飞申请回复失败");
                }
            } else {
                flyReplyResponse.fail("放飞申请信息异常，放飞申请回复失败");
            }
        } catch (Exception e) {
            log.error("放飞申请回复异常, flyReplyRequest={}", flyReplyRequest, e);
            flyReplyResponse.fail("放飞申请回复异常");
        }
        return flyReplyResponse;
    }

    public boolean updateUavOacMappingReportCode(Long uavId, String reportCode, boolean pass) {
        boolean result = false;
        if (pass) {
            UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(uavId, MappingStatusEnum.VALID, LinkStatusEnum.ONLINE);
            if (uavOacMappingDO != null) {
                int id = uavDalService.updateUavOacMappingReportCode(uavOacMappingDO, reportCode);
                if (id > 0) {
                    result = true;
                }
            }
        } else {
            result = true;
        }
        return result;
    }

    private void tcpFlyReplyToGcs(FlyReplyRequest flyReplyRequest, Long uavId, Long gcsId) {
        GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsId);
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = buildGcsTcpBaseDataFrame(uavId.toString(), gcsId.toString(), queryGcsInfo.getToken());
        gcsTcpBaseDataFrame.setType(GcsTcpTypeEnum.FLY_REPLY_REQUEST.getType());
        byte[] applyFlyIdBytes = flyReplyRequest.getApplyFlyId().getBytes();
        byte[] replyFlyIdBytes = flyReplyRequest.getReplyFlyId().getBytes();
        int readableDataBytesLength = 3 + applyFlyIdBytes.length + replyFlyIdBytes.length;
        ByteBuffer readableDataBytesByteBuffer = ByteBuffer.allocate(readableDataBytesLength);
        byte[] readableDataBytes = new byte[readableDataBytesLength];
        readableDataBytesByteBuffer.put((byte) applyFlyIdBytes.length);
        readableDataBytesByteBuffer.put(applyFlyIdBytes);
        readableDataBytesByteBuffer.put((byte) replyFlyIdBytes.length);
        readableDataBytesByteBuffer.put(replyFlyIdBytes);
        readableDataBytesByteBuffer.put(flyReplyRequest.getPass() ? (byte) 1 : (byte) 0);
        readableDataBytesByteBuffer.flip();
        readableDataBytesByteBuffer.get(readableDataBytes);
        gcsTcpBaseDataFrame.setReadableDataBytesLength(readableDataBytesLength);
        gcsTcpBaseDataFrame.setReadableDataBytes(readableDataBytes);
        tcpNettyChannelManager.send(gcsId.toString(), gcsTcpBaseDataFrame);
    }

    public FlyReplyResponse flyReplyToGcs(FlyReplyRequest flyReplyRequest, Long uavId, Long gcsId, String url) {
        FlyReplyResponse flyReplyResponse = new FlyReplyResponse();
        flyReplyResponse.fail();
        try {
            com.htfp.service.cac.router.biz.model.http.request.FlyReplyRequest gcsFlyReplyRequest = buildGcsFlyReplyRequest(flyReplyRequest, uavId);
            HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                    .contentObject(JsonUtils.object2Json(gcsFlyReplyRequest, false))
                    .gcsId(gcsId)
                    .contentType(HttpContentTypeConstant.JSON_TYPE)
                    .create();
            Map<String, String> httpHeader = builderRequestHeader();
            CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
            String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
            com.htfp.service.cac.router.biz.model.http.response.FlyReplyResponse gcsFlyReplyResponse = decodeHttpResponseToFlyReplyResponse(httpResponse);
            flyReplyResponse = buildFlyReplyResponse(gcsFlyReplyResponse);
        } catch (Exception e) {
            log.error("[router]放飞申请回复请求地面站，发生异常", e);
        }
        return flyReplyResponse;
    }

    private com.htfp.service.cac.router.biz.model.http.request.FlyReplyRequest buildGcsFlyReplyRequest(FlyReplyRequest flyReplyRequest, Long uavId) {
        com.htfp.service.cac.router.biz.model.http.request.FlyReplyRequest gcsFlyReplyRequest = new com.htfp.service.cac.router.biz.model.http.request.FlyReplyRequest();
        gcsFlyReplyRequest.setFlyPass(flyReplyRequest.getPass());
        gcsFlyReplyRequest.setUavId(uavId.toString());
        gcsFlyReplyRequest.setApplyFlyId(flyReplyRequest.getApplyFlyId());
        gcsFlyReplyRequest.setReplyFlyId(flyReplyRequest.getReplyFlyId());
        return gcsFlyReplyRequest;
    }

    private com.htfp.service.cac.router.biz.model.http.response.FlyReplyResponse decodeHttpResponseToFlyReplyResponse(String response) throws Exception {
        if (response == null || StringUtils.isBlank(response)) {
            return null;
        } else {
            com.htfp.service.cac.router.biz.model.http.response.FlyReplyResponse gcsFlyReplyResponse = JsonUtils.json2ObjectThrowException(response, com.htfp.service.cac.router.biz.model.http.response.FlyReplyResponse.class);
            gcsFlyReplyResponse.setResultStr(response);
            return gcsFlyReplyResponse;
        }
    }

    private FlyReplyResponse buildFlyReplyResponse(com.htfp.service.cac.router.biz.model.http.response.FlyReplyResponse gcsFlyReplyResponse) {
        FlyReplyResponse flyReplyResponse = new FlyReplyResponse();
        flyReplyResponse.setSuccess(gcsFlyReplyResponse.getSuccess());
        flyReplyResponse.setCode(gcsFlyReplyResponse.getCode());
        flyReplyResponse.setMessage(gcsFlyReplyResponse.getMessage());
        return flyReplyResponse;
    }

    /**
     * 管制信息下发
     *
     * @param atcSendRequest
     * @return
     */
    @Override
    public ATCSendResponse atcSend(ATCSendRequest atcSendRequest) {
        ATCSendResponse atcSendResponse = new ATCSendResponse();
        atcSendResponse.fail();
        try {
            String currentTime = DateUtils.getDateFormatString(new Date(), DateUtils.DATETIME_MSEC_PATTERN);
            Long applyFlightPlanId = Long.valueOf(atcSendRequest.getApplyFlightPlanId());
            Long applyFlyId = Long.valueOf(atcSendRequest.getApplyFlyId());
            UavInfoDO queryUavInfo = uavDalService.queryUavInfoByCpn(atcSendRequest.getCpn());
            ApplyFlyLogDO queryApplyFlyLog = applyFlyLogDalService.queryApplyFlyLogByApplyFlyId(Long.valueOf(atcSendRequest.getApplyFlyId()));
            if (queryApplyFlyLog != null &&
                    queryApplyFlyLog.getReplyFlyId() != null &&
                    queryApplyFlyLog.getReplyFlyId().equals(atcSendRequest.getReplyFlyId()) &&
                    ApplyStatusEnum.APPROVED.equals(ApplyStatusEnum.getFromCode(queryApplyFlyLog.getStatus()))) {
                if (queryUavInfo != null &&
                        queryApplyFlyLog.getUavId() != null &&
                        queryUavInfo.getId().equals(queryApplyFlyLog.getUavId())) {
                    UavNavigationMappingDO queryUavNavigationMapping = uavDalService.queryUavNavigationMapping(queryUavInfo.getId());
                    ATCIssuedLogDO atcIssuedLog = atcIssuedLogDalService.buildATCIssuedLog(applyFlightPlanId, applyFlyId, queryApplyFlyLog.getReplyFlightPlanId(), queryApplyFlyLog.getReplyFlyId(), queryUavNavigationMapping.getNavigationId(), queryUavInfo.getId(), queryUavInfo.getUavReg(), queryUavInfo.getCpn(), atcSendRequest.getAtcType(), null,
                            currentTime, null, "oacSystem", DeliverTypeEnum.DELIVERING.getCode());
                    int id = atcIssuedLogDalService.insertATCIssuedLog(atcIssuedLog);
                    if (id > 0) {
                        // 查询uav与gcs的mapping关系
                        UavGcsMappingDO queryUavGcsMapping = uavDalService.queryUavGcsMapping(queryUavInfo.getId());
                        if (queryUavGcsMapping != null &&
                                queryUavGcsMapping.getGcsId() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryUavGcsMapping.getStatus()))) {
                            atcSendResponse.success();
                            tcpAtcSendToGcs(atcSendRequest, queryUavInfo.getId(), queryUavGcsMapping.getGcsId());
                        } else {
                            atcSendResponse.fail("无人机系统未连接，管制信息下发失败");
                        }
                        /* //查询gcs与ip的mapping关系
                        GcsIpMappingDO queryGcsIpMapping = gcsDalService.queryGcsIpMapping(queryUavGcsMapping.getGcsId());
                        if (queryGcsIpMapping != null &&
                                queryGcsIpMapping.getGcsIp() != null &&
                                MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(queryGcsIpMapping.getStatus())) &&
                                LinkStatusEnum.ONLINE.equals(LinkStatusEnum.getFromCode(queryGcsIpMapping.getLinkStatus()))) {
                            // TODO: 2023/2/17 请求地面站
                            atcSendResponse.success();
                            tcpAtcSendToGcs(atcSendRequest, queryUavInfo.getId(), queryGcsIpMapping.getGcsId());
                        } else {
                            atcSendResponse.fail("无人机系统未连接，管制信息下发失败");
                        }*/
                    } else {
                        atcSendResponse.fail("写入放飞申请状态和上报编码异常，管制信息下发失败");
                    }
                } else {
                    atcSendResponse.fail("无人机信息异常，管制信息下发失败");
                }
            } else {
                atcSendResponse.fail("放飞申请信息异常，管制信息下发失败");
            }
        } catch (Exception e) {
            log.error("管制信息下发异常, atcSendRequest={}", atcSendRequest, e);
            atcSendResponse.fail("管制信息下发异常");
        }
        return atcSendResponse;
    }

    private void tcpAtcSendToGcs(ATCSendRequest atcSendRequest, Long uavId, Long gcsId) {
        GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsId);
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = buildGcsTcpBaseDataFrame(uavId.toString(), gcsId.toString(), queryGcsInfo.getToken());
        gcsTcpBaseDataFrame.setType(GcsTcpTypeEnum.RECEIVE_ATC_REQUEST.getType());
        int readableDataBytesLength = Integer.SIZE / Byte.SIZE + Long.SIZE / Byte.SIZE;
        ByteBuffer readableDataBytesByteBuffer = ByteBuffer.allocate(readableDataBytesLength);
        byte[] readableDataBytes = new byte[readableDataBytesLength];
        readableDataBytesByteBuffer.putInt(atcSendRequest.getAtcType());
        readableDataBytesByteBuffer.putLong(System.currentTimeMillis());
        readableDataBytesByteBuffer.flip();
        readableDataBytesByteBuffer.get(readableDataBytes);
        gcsTcpBaseDataFrame.setReadableDataBytesLength(readableDataBytesLength);
        gcsTcpBaseDataFrame.setReadableDataBytes(readableDataBytes);
        tcpNettyChannelManager.send(gcsId.toString(), gcsTcpBaseDataFrame);
    }

    /**
     * 告警信息下发
     *
     * @param alarmSendRequest
     * @return
     */
    @Override
    public AlarmSendResponse alarmSend(AlarmSendRequest alarmSendRequest) {
        return null;
    }

}
