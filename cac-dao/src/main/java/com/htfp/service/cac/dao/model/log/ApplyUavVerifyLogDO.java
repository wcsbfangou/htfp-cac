package com.htfp.service.cac.dao.model.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2023/1/11
 * @Description 描述
 */
@Data
public class ApplyUavVerifyLogDO implements Serializable {

    private Long id;
    private Long applyUavVerifyId;
    private String replyUavVerifyId;
    private String gcsId;
    private String uavId;
    private String uavReg;
    private String cpn;
    private Integer lng;
    private Integer lat;
    private Integer alt;
    private Integer groundSpeed;
    private Integer relativeHeight;
    private String flightControlSn;
    private String flightControlVersion;
    private String uavDynamicParam;
    private String uavStaticParam;
    private Integer status;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;
}
