package com.htfp.service.cac.dao.model.log;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * @Author sunjipeng
 * @Date 2022-05-24 14:45
 */
@Data
public class CommandAndControlLogDO implements Serializable {

    private Long id;
    private Long uavId;
    private Long navigationId;
    private Long gcsId;
    private Long rcsId;
    private Long pilotId;
    private Integer commandCode;
    private Integer commandResult;
    private Integer isDel;
    private Date gmtCreate;
    private Date gmtModify;

}
