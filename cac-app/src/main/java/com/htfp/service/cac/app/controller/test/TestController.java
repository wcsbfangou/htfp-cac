package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.common.enums.dataFrame.DataFrameVersionEnum;
import com.htfp.service.cac.common.enums.dataFrame.GcsTcpTypeEnum;
import com.htfp.service.cac.common.enums.dataFrame.MagicCodeEnum;
import com.htfp.service.cac.common.enums.dataFrame.SerializationAlgorithmEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.NavigationDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.inner.request.FlightPlanReplyRequest;
import com.htfp.service.cac.router.biz.service.tcp.codec.GcsTcpBaseDataFrame;
import com.htfp.service.cac.router.biz.service.tcp.server.TcpNettyChannelManager;
import com.htfp.service.oac.biz.model.inner.request.UavDataTransferRequest;
import com.htfp.service.oac.biz.model.inner.response.UavDataTransferResponse;
import com.htfp.service.oac.biz.service.IFlightManagementService;
import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.nio.ByteBuffer;

/**
 * @Author sunjipeng
 * @Date 2022-06-20 10:09
 */
@Slf4j
@Controller
@RequestMapping("/test")
public class TestController {

    @Resource
    private GcsDalService gcsDalService;

    @Resource
    private UavDalService uavDalService;

    @Resource
    private PilotDalService pilotDalService;

    @Resource
    private NavigationDalService navigationDalService;

    @Resource(name="flightManagementServiceImpl")
    private IFlightManagementService flightManagementService;

    @Resource
    private TcpNettyChannelManager tcpNettyChannelManager;


    @RequestMapping(value = "/deleteGcsInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteGcsInfo(@RequestParam(value = "gcsId") String gcsId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(gcsId)) {
                int id = gcsDalService.deleteGcsInfoByGcsId(Long.valueOf(gcsId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除地面站信息异常, gcsId={}", gcsId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUavInfo(@RequestParam(value = "uavId") String uavId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(uavId)) {
                int id = uavDalService.deleteUavInfoByUavId(Long.valueOf(uavId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除无人机信息异常, uavId={}", uavId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deletePilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deletePilotIdInfo(@RequestParam(value = "pilotId") String pilotId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(pilotId)) {
                int id = pilotDalService.deletePilotInfoByPilotId(Long.valueOf(pilotId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除驾驶员信息异常, pilotId={}", pilotId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteGcsIpMappingInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteGcsIpMappingInfo(@RequestParam(value = "gcsId") String gcsId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(gcsId)) {
                int id = gcsDalService.deleteGcsIpMapping(Long.valueOf(gcsId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除地面站与Ip的Mapping关系信息异常, gcsId={}", gcsId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteUavGcsMappingInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUavGcsMappingInfo(@RequestParam(value = "uavId") String uavId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(uavId)) {
                int id = uavDalService.deleteUavGcsMappingByUavId(Long.valueOf(uavId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除uav与gcs的Mapping关系信息异常, uavId={}", uavId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteUavNavigationMappingInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUavNavigationMappingInfo(@RequestParam(value = "uavId") String uavId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(uavId)) {
                int id = uavDalService.deleteUavNavigationMappingByUavId(Long.valueOf(uavId));
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除uav与navigation的Mapping关系信息异常, uavId={}", uavId, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteNavigationLogById", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteNavigationLogById(@RequestParam(value = "id") String id) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(id)) {
                int res = navigationDalService.deleteNavigationLogById(Long.valueOf(id));
                if (res > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除navigation的Log信息异常, id={}", id, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteUavStatusLogById", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUavStatusLogById(@RequestParam(value = "id") String id) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(id)) {
                int res = uavDalService.deleteUavStatusById(Long.valueOf(id));
                if (res > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除uavStatus的Log信息异常, id={}", id, e);
        }
        return result;
    }

    @RequestMapping(value = "/deleteCommandAndControlLogById", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteCommandAndControlLogById(@RequestParam(value = "id") String id) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(id)) {
                int res = uavDalService.deleteCommandAndControlLogById(Long.valueOf(id));
                if (res > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("删除CommandAndControl的Log信息异常, id={}", id, e);
        }
        return result;
    }

    @RequestMapping(value = "/uavDataTransferTest", method = RequestMethod.POST)
    @ResponseBody
    public UavDataTransferResponse uavDataTransfer(@RequestBody UavDataTransferRequest uavDataTransferRequest) {
        UavDataTransferResponse uavDataTransferResponse = new UavDataTransferResponse();
        uavDataTransferResponse.fail();
        try{
            ErrorCodeEnum errorCodeEnum = uavDataTransferRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                uavDataTransferResponse = flightManagementService.uavDataTransfer(uavDataTransferRequest);
            } else {
                uavDataTransferResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("无人机遥测数据校验异常, uavDataTransferRequest={}", uavDataTransferRequest, e);
            uavDataTransferResponse.fail("无人机遥测数据校验异常");
        }
        return uavDataTransferResponse;
    }

    @RequestMapping(value = "/replyFlightPlanToGcsTest", method = RequestMethod.POST)
    @ResponseBody
    public String uavDataTransfer(@RequestBody FlightPlanReplyRequest flightPlanReplyRequest, @RequestParam(value = "uavId") Long uavId, @RequestParam(value = "gcsId") Long gcsId) {
        GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsId);
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = buildGcsTcpBaseDataFrame(uavId.toString(), gcsId.toString(), queryGcsInfo.getToken());
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
        return "success";
    }

    private GcsTcpBaseDataFrame buildGcsTcpBaseDataFrame(String uavId, String gcsId, String gcsToken) {
        GcsTcpBaseDataFrame gcsTcpBaseDataFrame = new GcsTcpBaseDataFrame();
        gcsTcpBaseDataFrame.setMagicCode(MagicCodeEnum.DATA_TRANSFER.getCode());
        gcsTcpBaseDataFrame.setVersion(DataFrameVersionEnum.VERSION_1.getType());
        gcsTcpBaseDataFrame.setSerializationAlgorithm(SerializationAlgorithmEnum.NO_ALGORITHM.getType());
        gcsTcpBaseDataFrame.setType(GcsTcpTypeEnum.FLIGHT_PLAN_REPLY_REQUEST.getType());
        gcsTcpBaseDataFrame.setGcsIdLength((byte) gcsId.getBytes().length);
        gcsTcpBaseDataFrame.setGcsId(gcsId);
        gcsTcpBaseDataFrame.setGcsTokenLength((byte) gcsToken.getBytes().length);
        gcsTcpBaseDataFrame.setGcsToken(gcsToken);
        gcsTcpBaseDataFrame.setUavIdLength((byte) uavId.getBytes().length);
        gcsTcpBaseDataFrame.setUavId(uavId);
        return gcsTcpBaseDataFrame;
    }


}
