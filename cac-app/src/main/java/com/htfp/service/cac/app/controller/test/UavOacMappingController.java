package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.common.enums.LinkStatusEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.model.mapping.UavOacMappingDO;
import com.htfp.service.cac.dao.service.UavDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2023/2/23
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/background/uavOacMapping")
public class UavOacMappingController {

    @Resource
    UavDalService uavDalService;

    @RequestMapping(value = "/queryUavOacMappingByUavId", method = RequestMethod.POST)
    @ResponseBody
    public UavOacMappingDO queryAirPortInfoByAirportId(@RequestParam(value = "uavId") String uavId) {
        UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(Long.valueOf(uavId));
        return uavOacMappingDO;
    }

    @RequestMapping(value = "/updateUavOacMappingLinkStatusByUavId", method = RequestMethod.POST)
    @ResponseBody
    public int updateUavOacMappingLinkStatusByUavId(@RequestParam(value = "uavId") String uavId, @RequestParam(value = "linkStatus") Integer linkStatus) {
        int result = 0;
        UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(Long.valueOf(uavId));
        LinkStatusEnum linkStatusEnum = LinkStatusEnum.getFromCode(linkStatus);
        if(linkStatusEnum!=null){
            result = uavDalService.updateUavOacMappingLinkStatus(uavOacMappingDO, linkStatusEnum);
        }
        return result;
    }

    @RequestMapping(value = "/updateUavOacMappingStatusByUavId", method = RequestMethod.POST)
    @ResponseBody
    public int updateUavOacMappingStatusByUavId(@RequestParam(value = "uavId") String uavId, @RequestParam(value = "mappingStatus") Integer mappingStatus) {
        int result = 0;
        UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(Long.valueOf(uavId));
        MappingStatusEnum mappingStatusEnum = MappingStatusEnum.getFromCode(mappingStatus);
        if(mappingStatusEnum!=null){
            result = uavDalService.updateUavOacMappingStatus(uavOacMappingDO, mappingStatusEnum);
        }
        return result;
    }

    @RequestMapping(value = "/updateUavOacReportCodeByUavId", method = RequestMethod.POST)
    @ResponseBody
    public int updateUavOacMappingStatusByUavId(@RequestParam(value = "uavId") String uavId, @RequestParam(value = "reportCode") String reportCode) {
        int result = 0;
        UavOacMappingDO uavOacMappingDO = uavDalService.queryUavOacMapping(Long.valueOf(uavId));
        if(reportCode!=null){
            result = uavDalService.updateUavOacMappingReportCode(uavOacMappingDO, reportCode);
        }
        return result;
    }
}
