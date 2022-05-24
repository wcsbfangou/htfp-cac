package com.htfp.service.cac.dao.model.mapping;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:37
 */
@Data
public class GcsIpMappingDO implements Serializable {

    private Long id;
    private Long gcsId;
    private String gcsIp;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
