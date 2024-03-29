package com.htfp.service.cac.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:21
 */
@Data
public class GcsInfoDO implements Serializable {

    private Long id;
    private String gcsReg;
    private String gcsSn;
    private Integer gcsType;
    private Integer controllableUavType;
    private Integer dataLinkType;
    private String token;
    private Long operatorId;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
