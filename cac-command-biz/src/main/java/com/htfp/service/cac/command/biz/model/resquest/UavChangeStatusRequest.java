package com.htfp.service.cac.command.biz.model.resquest;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 21:00
 */

@Data
public class UavChangeStatusRequest {

    Long uavId;
    Long gcsId;
    Integer uavStatus;

}
