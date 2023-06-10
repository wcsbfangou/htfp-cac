package com.htfp.service.cac.dao.model.mapping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022/12/21
 * @Description 描述
 */
@Data
public class UavOacMappingDO implements Serializable {

    private Long id;
    private Long uavId;
    private String reportCode;
    private Integer status;
    private Integer linkStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
