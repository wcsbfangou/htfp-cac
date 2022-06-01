package com.htfp.service.cac.command.biz.model.resquest;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-06-01 18:15
 */

@Data
public class SaveUavControlLogRequest {

    private Long gcsId;
    private Long rcsId;
    private Long uavId;
    private Long pilotId;
    private Integer commandCode;
    private Boolean commandResult;

}
