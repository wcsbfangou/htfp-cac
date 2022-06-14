package com.htfp.service.cac.app.controller.background;

import com.htfp.service.cac.app.validator.HttpValidator;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.background.response.QueryUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.UavInfoParam;
import com.htfp.service.cac.router.biz.model.background.request.UavInfoRequest;
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

    @Resource
    private HttpValidator httpValidator;

    @Resource
    private UavDalService uavDalService;

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
            }
            UavInfoDO uavInfo = uavDalService.queryUavInfo(Long.valueOf(uavId));
            if (uavInfo != null) {
                UavInfoParam uavInfoParam = new UavInfoParam();
                uavInfoParam.setUavId(uavInfo.getUavId().toString());
                uavInfoParam.setTypeId(uavInfo.getTypeId());
                queryUavInfoResponse.setData(uavInfoParam);
                queryUavInfoResponse.success();
            }
        } catch (Exception e) {
            log.error("查询无人机信息异常, uavId={}", uavId, e);
        }
        return queryUavInfoResponse;
    }

    /**
     * 更新uav类型
     *
     * @param uavId
     * @param typeId
     * @return
     */
    @RequestMapping(value = "/updateUavInfoType", method = RequestMethod.POST)
    @ResponseBody
    public boolean updateUavInfoType(@RequestParam(value = "uavId") String uavId, @RequestParam(value = "typeId") Integer typeId) {
        boolean result = false;
        try {
            if (!StringUtils.isBlank(uavId) && typeId != null) {
                UavInfoDO uavInfo = uavDalService.queryUavInfo(Long.valueOf(uavId));
                int id = uavDalService.updateUavInfoTypeId(uavInfo, typeId);
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("更新无人机类型异常, uavId={}, typeId={}", uavId, typeId, e);
        }
        return result;
    }

    /**
     * 插入uav信息
     *
     * @param uavInfoRequest
     * @return
     */
    @RequestMapping(value = "/insertUavInfo", method = RequestMethod.POST)
    @ResponseBody
    public boolean insertUavInfo(@RequestBody UavInfoRequest uavInfoRequest) {
        boolean result = false;
        try {
            if (ErrorCodeEnum.SUCCESS.equals(httpValidator.httpRequestValidate(uavInfoRequest))) {
                UavInfoDO uavInfo = uavDalService.buildUavInfoDO(Long.valueOf(uavInfoRequest.getUavId()), uavInfoRequest.getTypeId());
                int id = uavDalService.insertUavInfo(uavInfo);
                if (id > 0) {
                    result = true;
                }
            }
        } catch (Exception e) {
            log.error("插入无人机信息异常, uavInfoRequest={}", uavInfoRequest, e);
        }
        return result;
    }

    /**
     * 根据uavId删除uav信息
     *
     * @param uavId
     * @return
     */
    @RequestMapping(value = "/deleteUavInfoByUavId", method = RequestMethod.POST)
    @ResponseBody
    public boolean deleteUavInfoByUavId(@RequestParam(value = "uavId") String uavId) {
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
}
