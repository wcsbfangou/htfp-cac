package com.htfp.service.cac.app.controller.test.param;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class AirportInfo {

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
}
