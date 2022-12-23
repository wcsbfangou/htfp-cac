package com.htfp.service.oac.dao.model;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/23
 * @Description 描述
 */
@Data
public class AirportInfoDO implements Serializable {

    private Long id;

    private String airportId;
    private String airportName;
    private String airportOperatorSubject;
    private String phoneNumber;
    private String emailAddress;
    private String city;
    private String address;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer identificationAreaRadius;
    private Integer alarmAreaRadius;
    private String landingSites;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
