package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.background.request.CancelOperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.OperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterOperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.CancelOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeleteOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.RegisterOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateOperatorInfoResponse;
import com.htfp.service.cac.router.biz.service.http.IStaticInfoService;
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
 * @Date 2022/12/15
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/background/operator")
public class OperatorInfoController {


    @Resource(name = "staticInfoServiceImpl")
    private IStaticInfoService staticInfoService;

    @Resource
    private HttpValidator httpValidator;

    /**
     * 插入operator信息
     *
     * @param operatorInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertOperatorInfo", method = RequestMethod.POST)
    @ResponseBody
    public InsertOperatorInfoResponse insertOperatorInfo(@RequestBody OperatorInfoRequest operatorInfoRequest) {
        InsertOperatorInfoResponse insertOperatorInfoResponse = new InsertOperatorInfoResponse();
        insertOperatorInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(operatorInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                insertOperatorInfoResponse = staticInfoService.typeInOperatorInfo(operatorInfoRequest);
            } else {
                insertOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("插入operator信息异常, operatorInfoRequest={}", operatorInfoRequest, e);
            insertOperatorInfoResponse.fail("插入operator数据异常");
        }
        return insertOperatorInfoResponse;
    }

    /**
     * 更新operator数据
     *
     * @param operatorInfoRequest
     * @return
     */
    @RequestMapping(value = "/updateOperatorInfo", method = RequestMethod.POST)
    @ResponseBody
    public UpdateOperatorInfoResponse updateOperatorInfo(@RequestBody OperatorInfoRequest operatorInfoRequest) {
        UpdateOperatorInfoResponse updateOperatorInfoResponse = new UpdateOperatorInfoResponse();
        updateOperatorInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(operatorInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updateOperatorInfoResponse = staticInfoService.updateOperatorInfo(operatorInfoRequest);
            } else {
                updateOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("更新operator信息异常, operatorInfoRequest={}", operatorInfoRequest, e);
            updateOperatorInfoResponse.fail("更新operator数据异常");
        }
        return updateOperatorInfoResponse;
    }

    /**
     * 根据operatorId查询operator信息
     *
     * @param operatorId
     * @return
     */
    @RequestMapping(value = "/queryOperatorInfoByOperatorId", method = RequestMethod.POST)
    @ResponseBody
    public QueryOperatorInfoResponse queryOperatorByOperatorId(@RequestParam(value = "operatorId") String operatorId) {
        QueryOperatorInfoResponse queryOperatorInfoResponse = new QueryOperatorInfoResponse();
        queryOperatorInfoResponse.fail();
        try {
            if (StringUtils.isBlank(operatorId)) {
                queryOperatorInfoResponse.fail(ErrorCodeEnum.LACK_OF_OPERATOR_ID);
            } else {
                queryOperatorInfoResponse = staticInfoService.queryOperatorInfo(Long.valueOf(operatorId));
            }
        } catch (Exception e) {
            log.error("查询operator信息异常, operatorId={}", operatorId, e);
            queryOperatorInfoResponse.fail("查询operator信息异常");
        }
        return queryOperatorInfoResponse;
    }

    /**
     * 根据idCardType和idCardNumber查询operator信息
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    @RequestMapping(value = "/queryOperatorInfoByIdCardInfo", method = RequestMethod.POST)
    @ResponseBody
    public QueryOperatorInfoResponse queryOperatorByIdCardInfo(@RequestParam(value = "idCardType") Integer idCardType, @RequestParam(value = "idCardNumber") String idCardNumber) {
        QueryOperatorInfoResponse queryOperatorInfoResponse = new QueryOperatorInfoResponse();
        queryOperatorInfoResponse.fail();
        try {
            if (idCardType == null) {
                queryOperatorInfoResponse.fail(ErrorCodeEnum.LACK_OF_ID_CARD_TYPE);
            }
            if (StringUtils.isBlank(idCardNumber)) {
                queryOperatorInfoResponse.fail(ErrorCodeEnum.LACK_OF_ID_CARD_NUMBER);
            }
            queryOperatorInfoResponse = staticInfoService.queryOperatorInfoByIdCardInfo(idCardType, idCardNumber);
        } catch (Exception e) {
            log.error("查询operator信息异常, operatorIdCardType={}, operatorIdCardNumber={}", idCardType, idCardNumber, e);
            queryOperatorInfoResponse.fail("查询operator信息异常");
        }
        return queryOperatorInfoResponse;
    }

    /**
     * 根据operatorId删除operator信息
     *
     * @param operatorId
     * @return
     */
    @RequestMapping(value = "/deleteOperatorInfoByOperatorId", method = RequestMethod.POST)
    @ResponseBody
    public DeleteOperatorInfoResponse deleteOperatorInfoByOperatorId(@RequestParam(value = "operatorId") String operatorId) {
        DeleteOperatorInfoResponse operatorInfoResponse = new DeleteOperatorInfoResponse();
        operatorInfoResponse.fail();
        try {
            if (!StringUtils.isBlank(operatorId)) {
                operatorInfoResponse = staticInfoService.deleteOperatorInfo(Long.valueOf(operatorId));
            } else {
                operatorInfoResponse.fail(ErrorCodeEnum.WRONG_OPERATOR_ID);
            }
        } catch (Exception e) {
            log.error("删除operator信息异常, operatorId={}", operatorId, e);
            operatorInfoResponse.fail("删除operator数据失败");
        }
        return operatorInfoResponse;
    }

    /**
     * 注册operator信息
     *
     * @param registerOperatorInfoRequest
     * @return
     */
    @RequestMapping(value = "/registerOperatorInfo", method = RequestMethod.POST)
    @ResponseBody
    public RegisterOperatorInfoResponse registerOperatorInfo(@RequestBody RegisterOperatorInfoRequest registerOperatorInfoRequest) {
        RegisterOperatorInfoResponse registerOperatorInfoResponse = new RegisterOperatorInfoResponse();
        registerOperatorInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(registerOperatorInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerOperatorInfoResponse = staticInfoService.registerOperatorInfo(registerOperatorInfoRequest);
            } else {
                registerOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注册operator信息异常, registerOperatorInfoRequest={}", registerOperatorInfoRequest, e);
            registerOperatorInfoResponse.fail("注册operator信息异常");
        }
        return registerOperatorInfoResponse;
    }

    /**
     * 注销operator信息
     *
     * @param cancelOperatorInfoRequest
     * @return
     */
    @RequestMapping(value = "/cancelOperatorInfo", method = RequestMethod.POST)
    @ResponseBody
    public CancelOperatorInfoResponse cancelOperatorInfo(@RequestBody CancelOperatorInfoRequest cancelOperatorInfoRequest) {
        CancelOperatorInfoResponse cancelOperatorInfoResponse = new CancelOperatorInfoResponse();
        cancelOperatorInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(cancelOperatorInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelOperatorInfoResponse = staticInfoService.cancelOperatorInfo(cancelOperatorInfoRequest);
            } else {
                cancelOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注销operator信息异常, cancelOperatorInfoRequest={}", cancelOperatorInfoRequest, e);
            cancelOperatorInfoResponse.fail("注销operator信息异常");
        }
        return cancelOperatorInfoResponse;
    }
}
