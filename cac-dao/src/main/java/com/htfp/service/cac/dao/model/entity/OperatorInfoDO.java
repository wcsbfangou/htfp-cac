package com.htfp.service.cac.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/5
 * @Description 运营主体PJ
 */

@Data
public class OperatorInfoDO implements Serializable {

    private Long id;
    /**
     * operatorCode = idCardType+'#'+idCardNumber
     */
    private String operatorCode;
    private String operatorUniId;
    private String operatorName;
    private Integer operatorType;

    private Integer idCardType;
    private String idCardNumber;
    private String idCardPictureAddress;

    private String companyName;
    private String socialCreditCode;

    private Integer gender;
    private String nationality;
    private String city;
    private String address;
    private String phoneNumber;
    private String emailAddress;

    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
