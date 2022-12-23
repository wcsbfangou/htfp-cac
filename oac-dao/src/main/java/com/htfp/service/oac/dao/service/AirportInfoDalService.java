package com.htfp.service.oac.dao.service;

import com.htfp.service.oac.dao.mapper.AirportInfoMapper;
import com.htfp.service.oac.dao.model.AirportInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Service
public class AirportInfoDalService {

    @Resource
    AirportInfoMapper airportInfoMapper;

    public AirportInfoDO queryAirportInfo(Long id) {
        return airportInfoMapper.selectById(id);
    }

    public AirportInfoDO queryAirportInfoByUavReg(String airportId) {
        return airportInfoMapper.selectByAirportId(airportId);
    }

    public int insertAirportInfo(AirportInfoDO airportInfoDO) {
        return airportInfoMapper.insertAirportInfoLog(airportInfoDO);
    }

    public int updateAirportInfo(AirportInfoDO airportInfoDO) {
        return airportInfoMapper.updateByApplyFlightPlanLog(airportInfoDO);
    }

    public int updateAirportInfoStatus(AirportInfoDO airportInfo, Integer status) {
        airportInfo.setStatus(status);
        airportInfo.setGmtModify(new Date());
        return updateAirportInfo(airportInfo);
    }


    public int deleteAirportInfoByAirportId(String airportId) {
        return airportInfoMapper.deleteByAirportId(airportId);
    }


    public int deleteAirportInfoById(Long id) {
        return airportInfoMapper.deleteById(id);
    }

    public AirportInfoDO buildAirportInfoDO(String airportId, String airportName, String airportOperatorSubject, String phoneNumber, String emailAddress,  String city, String address, Integer lng, Integer lat ,Integer alt, Integer identificationAreaRadius, Integer alarmAreaRadius, String landingSites, Integer status) {
        AirportInfoDO airportInfoDO = new AirportInfoDO();
        airportInfoDO.setAirportId(airportId);
        airportInfoDO.setAirportName(airportName);
        airportInfoDO.setAirportOperatorSubject(airportOperatorSubject);
        airportInfoDO.setPhoneNumber(phoneNumber);
        airportInfoDO.setEmailAddress(emailAddress);
        airportInfoDO.setCity(city);
        airportInfoDO.setAddress(address);
        airportInfoDO.setLng(lng);
        airportInfoDO.setLat(lat);
        airportInfoDO.setAlt(alt);
        airportInfoDO.setIdentificationAreaRadius(identificationAreaRadius);
        airportInfoDO.setAlarmAreaRadius(alarmAreaRadius);
        airportInfoDO.setLandingSites(landingSites);
        airportInfoDO.setStatus(status);
        airportInfoDO.setGmtCreate(new Date());
        airportInfoDO.setGmtModify(new Date());
        return airportInfoDO;
    }
}
