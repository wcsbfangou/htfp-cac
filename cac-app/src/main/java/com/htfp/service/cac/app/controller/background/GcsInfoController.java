package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.background.request.GcsInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.DeleteGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateGcsInfoResponse;
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
 * @Date 2022-06-08 13:41
 */
@Slf4j
@Controller
@RequestMapping("/background/gcs")
public class GcsInfoController {

    @Resource
    private HttpValidator httpValidator;

    @Resource(name = "staticInfoServiceImpl")
    private IStaticInfoService staticInfoService;

    /**
     * 根据gcsId查询gcs信息
     *
     * @param gcsId
     * @return
     */
    @RequestMapping(value = "/queryGcsInfoByGcsId", method = RequestMethod.POST)
    @ResponseBody
    public QueryGcsInfoResponse queryGcsByGcsId(@RequestParam(value = "gcsId") String gcsId) {
        QueryGcsInfoResponse queryGcsInfoResponse = new QueryGcsInfoResponse();
        queryGcsInfoResponse.fail();
        try {
            if (StringUtils.isBlank(gcsId)) {
                queryGcsInfoResponse = staticInfoService.queryGcsInfo(Long.valueOf(gcsId));
            } else {
                queryGcsInfoResponse.fail(ErrorCodeEnum.LACK_OF_GCS_ID);
            }
        } catch (Exception e) {
            log.error("查询gcs信息异常, gcsId={}", gcsId, e);
            queryGcsInfoResponse.fail("查询gcs信息异常");
        }
        return queryGcsInfoResponse;
    }

    /**
     * 根据gcsReg查询gcs信息
     *
     * @param gcsReg
     * @return
     */
    @RequestMapping(value = "/queryGcsInfoByGcsReg", method = RequestMethod.POST)
    @ResponseBody
    public QueryGcsInfoResponse queryGcsByGcsReg(@RequestParam(value = "gcsReg") String gcsReg) {
        QueryGcsInfoResponse queryGcsInfoResponse = new QueryGcsInfoResponse();
        queryGcsInfoResponse.fail();
        try {
            if (!StringUtils.isBlank(gcsReg)) {
                staticInfoService.queryGcsInfoByGcsReg(gcsReg);
            } else{
                queryGcsInfoResponse.fail(ErrorCodeEnum.LACK_OF_GCS_REG);
            }
        } catch (Exception e) {
            log.error("查询地面站信息异常, gcsReg={}", gcsReg, e);
            queryGcsInfoResponse.fail("查询gcs信息异常");
        }
        return queryGcsInfoResponse;
    }

    /**
     * 更新gcs数据
     * @param gcsInfoRequest
     * @return
     */
    @RequestMapping(value = "/updateGcsInfo", method = RequestMethod.POST)
    @ResponseBody
    public UpdateGcsInfoResponse updateGcsInfo(@RequestBody GcsInfoRequest gcsInfoRequest) {
        UpdateGcsInfoResponse updateGcsInfoResponse = new UpdateGcsInfoResponse();
        updateGcsInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(gcsInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updateGcsInfoResponse = staticInfoService.updateGcsInfo(gcsInfoRequest);
            } else {
                updateGcsInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("更新地面站信息异常, gcsInfoRequest={}", gcsInfoRequest, e);
            updateGcsInfoResponse.fail("更新gcs数据异常");
        }
        return updateGcsInfoResponse;
    }

    /**
     * 插入gcs信息
     *
     * @param gcsInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertGcsInfo", method = RequestMethod.POST)
    @ResponseBody
    public InsertGcsInfoResponse insertGcsInfo(@RequestBody GcsInfoRequest gcsInfoRequest) {
        InsertGcsInfoResponse insertGcsInfoResponse = new InsertGcsInfoResponse();
        insertGcsInfoResponse.fail();
        try {
            ErrorCodeEnum errorCodeEnum = httpValidator.httpRequestValidate(gcsInfoRequest);
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                insertGcsInfoResponse = staticInfoService.typeInGcsInfo(gcsInfoRequest);
            } else {
                insertGcsInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e) {
            log.error("插入gcs信息异常, gcsInfoRequest={}", gcsInfoRequest, e);
            insertGcsInfoResponse.fail("插入gcs数据异常");
        }
        return insertGcsInfoResponse;
    }

    /**
     * 根据gcsId删除gcs信息
     *
     * @param gcsId
     * @return
     */
    @RequestMapping(value = "/deleteGcsInfoByGcsId", method = RequestMethod.POST)
    @ResponseBody
    public DeleteGcsInfoResponse deleteGcsInfoByGcsId(@RequestParam(value = "gcsId") String gcsId) {
        DeleteGcsInfoResponse deleteGcsInfoResponse = new DeleteGcsInfoResponse();
        deleteGcsInfoResponse.fail();
        try {
            if (!StringUtils.isBlank(gcsId)) {
                staticInfoService.deleteGcsInfo(Long.valueOf(gcsId));
            } else {
                deleteGcsInfoResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }

        } catch (Exception e) {
            log.error("删除地面站信息异常, gcsId={}", gcsId, e);
            deleteGcsInfoResponse.fail("删除gcs数据失败");
        }
        return deleteGcsInfoResponse;
    }
}
