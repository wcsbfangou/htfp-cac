package com.htfp.service.cac.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:28
 */
@Data
public class PilotInfoDO implements Serializable {

    private Long id;
    /**
     * pilotCode = idCardType+'#'+idCardNumber
     */
    private String pilotCode;
    private String pilotUniId;
    private String pilotName;
    private Integer pilotType;

    private Integer controllableUavType;

    private Integer licenseType;
    private String licenseId;
    private String licensePictureAddress;

    private Integer idCardType;
    private String idCardNumber;
    private String idCardPictureAddress;
    private Integer gender;
    private String nationality;
    private String phoneNumber;
    private String emailAddress;

    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
