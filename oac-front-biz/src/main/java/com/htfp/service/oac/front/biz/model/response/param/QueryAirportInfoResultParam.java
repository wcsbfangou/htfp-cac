package com.htfp.service.oac.front.biz.model.response.param;

import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/2/17
 * @Description 描述
 */
@Data
public class QueryAirportInfoResultParam {

    private String airportId;
    private String city;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer identificationAreaRadius;
    private Integer alarmAreaRadius;
    private List<String> landingSites;
}
