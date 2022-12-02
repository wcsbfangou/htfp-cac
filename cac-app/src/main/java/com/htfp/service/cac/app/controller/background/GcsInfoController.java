package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.model.background.GcsInfoParam;
import com.htfp.service.cac.router.biz.model.background.request.GcsInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.QueryGcsInfoResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Base64;

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

    @Resource
    private GcsDalService gcsDalService;

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
                queryGcsInfoResponse.fail(ErrorCodeEnum.LACK_OF_GCS_ID);
            }
            GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(Long.valueOf(gcsId));
            if (gcsInfo != null) {
                GcsInfoParam gcsInfoParam = new GcsInfoParam();
                gcsInfoParam.setGcsId(gcsInfo.getGcsId().toString());
                gcsInfoParam.setToken(gcsInfo.getToken());
                gcsInfoParam.setTypeId(gcsInfo.getTypeId());
                gcsInfoParam.setControllableUavType(gcsInfo.getControllableUavType());
                gcsInfoParam.setDataLinkType(gcsInfo.getDataLinkType());
                queryGcsInfoResponse.setData(gcsInfoParam);
                queryGcsInfoResponse.success();
            } else {
                queryGcsInfoResponse.fail("无此gcs数据");
            }
        } catch (Exception e) {
            log.error("查询地面站信息异常, gcsId={}", gcsId, e);
        }
        return queryGcsInfoResponse;
    }

    /**
     * 更新gcs可控无人机类型
     *
     * @param gcsId
     * @param controllableUavType
     * @return
     */
    @RequestMapping(value = "/updateGcsInfoControllableUavType", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateGcsInfoControllableUavType(@RequestParam(value = "gcsId") String gcsId, @RequestParam(value = "controllableUavType") Integer controllableUavType) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(gcsId) && controllableUavType != null) {
                GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(Long.valueOf(gcsId));
                if (gcsInfo != null) {
                    int id = gcsDalService.updateGcsInfoControllableUavType(gcsInfo, controllableUavType);
                    if (id > 0) {
                        result = true;
                    }
                }
            }
        } catch (Exception e) {
            log.error("更新地面站可控无人机类型异常, gcsId={}, controllableUavType={}", gcsId, controllableUavType, e);
        }
        return result;
    }

    /**
     * 插入gcs信息
     *
     * @param gcsInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertGcsInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean insertGcsInfo(@RequestBody GcsInfoRequest gcsInfoRequest) {
        boolean result = false;
        try {
            if (ErrorCodeEnum.SUCCESS.equals(httpValidator.httpRequestValidate(gcsInfoRequest))) {
                generateGcsToken(gcsInfoRequest);
                GcsInfoDO gcsInfo = gcsDalService.buildGcsInfoDO(Long.valueOf(gcsInfoRequest.getGcsId()), gcsInfoRequest.getTypeId(), gcsInfoRequest.getControllableUavType(), gcsInfoRequest.getDataLinkType(), gcsInfoRequest.getToken());
                int id = gcsDalService.insertGcsInfo(gcsInfo);
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("插入地面站信息异常, gcsInfoRequest={}", gcsInfoRequest, e);
        }
        return result;
    }

    /**
     * 根据gcsId删除gcs信息
     *
     * @param gcsId
     * @return
     */
    @RequestMapping(value = "/deleteGcsInfoByGcsId", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteGcsInfoByGcsId(@RequestParam(value = "gcsId") String gcsId) {
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

    /**
     * 根据gcsId使用base64编码生成对应的token
     *
     * @param gcsInfoRequest
     * @throws UnsupportedEncodingException
     */
    private void generateGcsToken(GcsInfoRequest gcsInfoRequest) throws UnsupportedEncodingException {
        String gcsToken = Base64.getEncoder().encodeToString(gcsInfoRequest.getGcsId().getBytes("UTF-8"));
        gcsInfoRequest.setToken(gcsToken);
    }
}
