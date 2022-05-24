package com.htfp.service.cac.dao.model.mapping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:30
 */
@Data
public class UavNavigationMappingInfoDO implements Serializable {

    private Long id;
    private Long uavId;
    private Long navigationId;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
