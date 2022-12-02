package com.htfp.service.cac.dao.model.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:10
 */
@Data
public class UavInfoDO implements Serializable {

    private Long id;
    private Long uavId;
    private Integer typeId;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
