package com.htfp.service.cac.dao.service.oac;

import com.htfp.service.cac.dao.mapper.oac.OacAirportInfoMapper;
import com.htfp.service.cac.dao.model.oac.AirportInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Slf4j
@Service
public class OacAirportInfoDalService {

    @Resource
    private OacAirportInfoMapper oacAirportInfoMapper;

    public AirportInfoDO queryAirportInfo(Long id) {
        return oacAirportInfoMapper.selectById(id);
    }

    public AirportInfoDO queryAirportInfoByAirportId(String airportId) {
        return oacAirportInfoMapper.selectByAirportId(airportId);
    }

    public List<AirportInfoDO> queryAllAirportInfo() {
        return oacAirportInfoMapper.selectAllAirports();
    }

    public int insertAirportInfo(AirportInfoDO airportInfoDO) {
        return oacAirportInfoMapper.insertAirportInfoLog(airportInfoDO);
    }

    public int updateAirportInfo(AirportInfoDO airportInfoDO) {
        return oacAirportInfoMapper.updateByApplyFlightPlanLog(airportInfoDO);
    }

    public int updateAirportInfoStatus(AirportInfoDO airportInfo, Integer status) {
        airportInfo.setStatus(status);
        airportInfo.setGmtModify(new Date());
        return updateAirportInfo(airportInfo);
    }


    public int deleteAirportInfoByAirportId(String airportId) {
        return oacAirportInfoMapper.deleteByAirportId(airportId);
    }


    public int deleteAirportInfoById(Long id) {
        return oacAirportInfoMapper.deleteById(id);
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
