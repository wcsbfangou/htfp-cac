package com.htfp.service.cac.app.controller.test;

import com.htfp.service.cac.dao.model.oac.AirportInfoDO;
import com.htfp.service.cac.dao.service.oac.OacAirportInfoDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Slf4j
@Controller
@RequestMapping("/background/airport")
public class AirportInfoController {

    @Resource
    OacAirportInfoDalService oacAirportInfoDalService;

    /**
     * 插入gcs信息
     *
     * @param airportInfo
     * @return
     */
    @RequestMapping(value = "/insertAirportInfo", method = RequestMethod.POST)
    @ResponseBody
    public int insertAirportInfo(@RequestBody AirportInfo airportInfo) {

        AirportInfoDO airportInfoDO = oacAirportInfoDalService.buildAirportInfoDO(airportInfo.getAirportId(), airportInfo.getAirportName(), airportInfo.getAirportOperatorSubject(),
                airportInfo.getPhoneNumber(), airportInfo.getEmailAddress(), airportInfo.getCity(), airportInfo.getAddress(),
                airportInfo.getLng(), airportInfo.getLat(), airportInfo.getAlt(), airportInfo.getIdentificationAreaRadius(),
                airportInfo.getAlarmAreaRadius(), airportInfo.getLandingSites(), airportInfo.getStatus());
        int id=oacAirportInfoDalService.insertAirportInfo(airportInfoDO);
        return id;
    }
}
