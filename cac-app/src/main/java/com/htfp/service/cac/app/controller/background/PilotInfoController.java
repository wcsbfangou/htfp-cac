package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.background.request.CancelPilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.PilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterPilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.CancelPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeletePilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.RegisterPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdatePilotInfoResponse;
import com.htfp.service.cac.router.biz.service.http.IStaticInformationService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 14:45
 */
@Slf4j
@Controller
@RequestMapping("/background/pilot")
public class PilotInfoController {

    @Resource(name = "staticInformationServiceImpl")
    private IStaticInformationService staticInformationService;

    @Resource
    private HttpValidator httpValidator;


    /**
     * 根据pilotId查询pilot信息
     *
     * @param pilotId
     * @return
     */
    @RequestMapping(value = "/queryPilotInfoByPilotId", method = RequestMethod.POST)
    @ResponseBody
    public QueryPilotInfoResponse queryPilotByPilotId(@RequestParam(value = "pilotId") String pilotId) {
        QueryPilotInfoResponse queryPilotInfoResponse = new QueryPilotInfoResponse();
        queryPilotInfoResponse.fail();
        try {
            if (StringUtils.isBlank(pilotId)) {
                queryPilotInfoResponse.fail(ErrorCodeEnum.LACK_OF_PILOT_ID);
            } else {
                queryPilotInfoResponse = staticInformationService.queryPilotInfo(Long.valueOf(pilotId));
            }
        } catch (Exception e) {
            log.error("查询pilot信息异常, pilotId={}", pilotId, e);
            queryPilotInfoResponse.fail("查询pilot信息异常");
        }
        return queryPilotInfoResponse;
    }

    /**
     * 根据idCardType和idCardNumber查询pilot信息
     *
     * @param
     * @param
     * @return
     */
    @RequestMapping(value = "/queryPilotInfoByIdCardInfo", method = RequestMethod.POST)
    @ResponseBody
    public QueryPilotInfoResponse queryPilotByIdCardInfo(@RequestParam(value = "idCardType") Integer idCardType, @RequestParam(value = "idCardNumber") String idCardNumber) {
        QueryPilotInfoResponse queryPilotInfoResponse = new QueryPilotInfoResponse();
        queryPilotInfoResponse.fail();
        try {
            if (idCardType == null) {
                queryPilotInfoResponse.fail(ErrorCodeEnum.LACK_OF_ID_CARD_TYPE);
            }
            if (StringUtils.isBlank(idCardNumber)) {
                queryPilotInfoResponse.fail(ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER);
            }
            queryPilotInfoResponse = staticInformationService.queryPilotInfoByIdCardInfo(idCardType, idCardNumber);
        } catch (Exception e) {
            log.error("查询pilot信息异常, pilotIdCardType={}, pilotIdCardNumber={}", idCardType, idCardNumber, e);
            queryPilotInfoResponse.fail("查询pilot信息异常");
        }
        return queryPilotInfoResponse;
    }

    /**
     * 更新pilot数据
     *
     * @param pilotInfoRequest
     * @return
     */
    @RequestMapping(value = "/updatePilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public UpdatePilotInfoResponse updatePilotInfo(@RequestBody PilotInfoRequest pilotInfoRequest) {
        UpdatePilotInfoResponse updatePilotInfoResponse = new UpdatePilotInfoResponse();
        updatePilotInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(pilotInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updatePilotInfoResponse = staticInformationService.updatePilotInfo(pilotInfoRequest);
            } else {
                updatePilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("更新pilot信息异常, pilotInfoRequest={}", pilotInfoRequest, e);
            updatePilotInfoResponse.fail("更新pilot数据异常");
        }
        return updatePilotInfoResponse;
    }

    /**
     * 插入pilot信息
     *
     * @param pilotInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertPilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public InsertPilotInfoResponse insertPilotInfo(@RequestBody PilotInfoRequest pilotInfoRequest) {
        InsertPilotInfoResponse insertPilotInfoResponse = new InsertPilotInfoResponse();
        insertPilotInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(pilotInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                insertPilotInfoResponse = staticInformationService.typeInPilotInfo(pilotInfoRequest);
            } else {
                insertPilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("插入pilot信息异常, pilotInfoRequest={}", pilotInfoRequest, e);
            insertPilotInfoResponse.fail("插入pilot数据异常");
        }
        return insertPilotInfoResponse;
    }

    /**
     * 根据pilotId删除pilot信息
     *
     * @param pilotId
     * @return
     */
    @RequestMapping(value = "/deletePilotInfoByPilotId", method = RequestMethod.POST)
    @ResponseBody
    public DeletePilotInfoResponse deletePilotInfoByPilotId(@RequestParam(value = "pilotId") String pilotId) {
        DeletePilotInfoResponse deletePilotInfoResponse = new DeletePilotInfoResponse();
        deletePilotInfoResponse.fail();
        try {
            if (!StringUtils.isBlank(pilotId)) {
                deletePilotInfoResponse = staticInformationService.deletePilotInfo(Long.valueOf(pilotId));
            } else {
                deletePilotInfoResponse.fail(ErrorCodeEnum.WRONG_PILOT_ID);
            }
        } catch (Exception e) {
            log.error("删除pilot信息异常, pilotId={}", pilotId, e);
            deletePilotInfoResponse.fail("删除pilot数据失败");
        }
        return deletePilotInfoResponse;
    }

    /**
     * 注册pilot信息
     *
     * @param registerPilotInfoRequest
     * @return
     */
    @RequestMapping(value = "/registerPilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public RegisterPilotInfoResponse registerPilotInfo(@RequestBody RegisterPilotInfoRequest registerPilotInfoRequest) {
        RegisterPilotInfoResponse registerPilotInfoResponse = new RegisterPilotInfoResponse();
        registerPilotInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(registerPilotInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerPilotInfoResponse = staticInformationService.registerPilotInfo(registerPilotInfoRequest);
            } else {
                registerPilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注册pilot信息异常, registerPilotInfoRequest={}", registerPilotInfoRequest, e);
            registerPilotInfoResponse.fail("注册pilot信息异常");
        }
        return registerPilotInfoResponse;
    }

    /**
     * 注销pilot信息
     *
     * @param cancelPilotInfoRequest
     * @return
     */
    @RequestMapping(value = "/cancelPilotInfo", method = RequestMethod.POST)
    @ResponseBody
    public CancelPilotInfoResponse cancelPilotInfo(@RequestBody CancelPilotInfoRequest cancelPilotInfoRequest) {
        CancelPilotInfoResponse cancelPilotInfoResponse = new CancelPilotInfoResponse();
        cancelPilotInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(cancelPilotInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelPilotInfoResponse = staticInformationService.cancelPilotInfo(cancelPilotInfoRequest);
            } else {
                cancelPilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注销pilot信息异常, cancelPilotInfoRequest={}", cancelPilotInfoRequest, e);
            cancelPilotInfoResponse.fail("注销pilot信息异常");
        }
        return cancelPilotInfoResponse;
    }
}
