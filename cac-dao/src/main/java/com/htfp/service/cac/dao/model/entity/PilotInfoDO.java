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
    private Long pilotId;
    private String pilotName;
    private Integer controllableUavType;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
