package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.background.request.CancelUavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterUavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.CancelUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeleteUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.request.UavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.RegisterUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateUavInfoResponse;
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
 * @Date 2022-06-08 9:03
 */
@Slf4j
@Controller
@RequestMapping("/background/uav")
public class UavInfoController {

    @Resource(name = "staticInfoServiceImpl")
    private IStaticInfoService staticInfoService;

    @Resource
    private HttpValidator httpValidator;

    /**
     * 根据uavId查询uav信息
     *
     * @param uavId
     * @return
     */
    @RequestMapping(value = "/queryUavInfoByUavId", method = RequestMethod.POST)
    @ResponseBody
    public QueryUavInfoResponse queryUavByUavId(@RequestParam(value = "uavId") String uavId) {
        QueryUavInfoResponse queryUavInfoResponse = new QueryUavInfoResponse();
        queryUavInfoResponse.fail();
        try {
            if (StringUtils.isBlank(uavId)) {
                queryUavInfoResponse.fail(ErrorCodeEnum.LACK_OF_UAV_ID);
            } else {
                queryUavInfoResponse = staticInfoService.queryUavInfo(Long.valueOf(uavId));
            }
        } catch (Exception e) {
            log.error("查询uav信息异常, uavId={}", uavId, e);
            queryUavInfoResponse.fail("查询uav信息异常");
        }
        return queryUavInfoResponse;
    }

    /**
     * 根据uavReg查询uav信息
     *
     * @param uavReg
     * @return
     */
    @RequestMapping(value = "/queryUavInfoByUavReg", method = RequestMethod.POST)
    @ResponseBody
    public QueryUavInfoResponse queryUavByUavIdByUavReg(@RequestParam(value = "uavReg") String uavReg) {
        QueryUavInfoResponse queryUavInfoResponse = new QueryUavInfoResponse();
        queryUavInfoResponse.fail();
        try {
            if (StringUtils.isBlank(uavReg)) {
                queryUavInfoResponse.fail(ErrorCodeEnum.LACK_OF_UAV_REG);
            } else {
                queryUavInfoResponse = staticInfoService.queryUavInfoByUavReg(uavReg);
            }
        } catch (Exception e) {
            log.error("查询uav信息异常, uavReg={}", uavReg, e);
            queryUavInfoResponse.fail("查询uav信息异常");
        }
        return queryUavInfoResponse;
    }

    /**
     * 更新uav数据
     *
     * @param uavInfoRequest
     * @return
     */
    @RequestMapping(value = "/updateUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public UpdateUavInfoResponse updateUavInfo(@RequestBody UavInfoRequest uavInfoRequest) {
        UpdateUavInfoResponse updateUavInfoResponse = new UpdateUavInfoResponse();
        updateUavInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(uavInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updateUavInfoResponse = staticInfoService.updateUavInfo(uavInfoRequest);
            } else {
                updateUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("更新uav信息异常, uavInfoRequest={}", uavInfoRequest, e);
            updateUavInfoResponse.fail("更新uav数据异常");
        }
        return updateUavInfoResponse;
    }

    /**
     * 插入uav信息
     *
     * @param uavInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public InsertUavInfoResponse insertUavInfo(@RequestBody UavInfoRequest uavInfoRequest) {
        InsertUavInfoResponse insertUavInfoResponse = new InsertUavInfoResponse();
        insertUavInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(uavInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                insertUavInfoResponse = staticInfoService.typeInUavInfo(uavInfoRequest);
            } else {
                insertUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("插入uav信息异常, uavInfoRequest={}", uavInfoRequest, e);
            insertUavInfoResponse.fail("插入uav数据异常");
        }
        return insertUavInfoResponse;
    }

    /**
     * 根据uavId删除uav信息
     *
     * @param uavId
     * @return
     */
    @RequestMapping(value = "/deleteUavInfoByUavId", method = RequestMethod.POST)
    @ResponseBody
    public DeleteUavInfoResponse deleteUavInfoByUavId(@RequestParam(value = "uavId") String uavId) {
        DeleteUavInfoResponse deleteUavInfoResponse = new DeleteUavInfoResponse();
        deleteUavInfoResponse.fail();
        try {
            if (!StringUtils.isBlank(uavId)) {
                deleteUavInfoResponse = staticInfoService.deleteUavInfo(Long.valueOf(uavId));
            } else {
                deleteUavInfoResponse.fail(ErrorCodeEnum.WRONG_UAV_ID);
            }
        } catch (Exception e) {
            log.error("删除uav信息异常, uavId={}", uavId, e);
            deleteUavInfoResponse.fail("删除uav数据失败");
        }
        return deleteUavInfoResponse;
    }

    /**
     * 注册uav信息
     *
     * @param registerUavInfoRequest
     * @return
     */
    @RequestMapping(value = "/registerUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public RegisterUavInfoResponse registerUavInfo(@RequestBody RegisterUavInfoRequest registerUavInfoRequest) {
        RegisterUavInfoResponse registerUavInfoResponse = new RegisterUavInfoResponse();
        registerUavInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(registerUavInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerUavInfoResponse = staticInfoService.registerUavInfo(registerUavInfoRequest);
            } else {
                registerUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注册uav信息异常, registerUavInfoRequest={}", registerUavInfoRequest, e);
            registerUavInfoResponse.fail("注册uav信息异常");
        }
        return registerUavInfoResponse;
    }

    /**
     * 注销uav信息
     *
     * @param cancelUavInfoRequest
     * @return
     */
    @RequestMapping(value = "/cancelUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public CancelUavInfoResponse cancelUavInfo(@RequestBody CancelUavInfoRequest cancelUavInfoRequest) {
        CancelUavInfoResponse cancelUavInfoResponse = new CancelUavInfoResponse();
        cancelUavInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(cancelUavInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelUavInfoResponse = staticInfoService.cancelUavInfo(cancelUavInfoRequest);
            } else {
                cancelUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("注销uav信息异常, cancelUavInfoRequest={}", cancelUavInfoRequest, e);
            cancelUavInfoResponse.fail("注销uav信息异常");
        }
        return cancelUavInfoResponse;
    }
}
