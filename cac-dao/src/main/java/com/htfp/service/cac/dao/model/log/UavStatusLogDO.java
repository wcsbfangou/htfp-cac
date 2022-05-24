package com.htfp.service.cac.dao.model.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:43
 */
@Data
public class UavStatusLogDO implements Serializable {

    private Long id;
    private Long uavId;
    private Long navigationId;
    private Integer uavStatus;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
