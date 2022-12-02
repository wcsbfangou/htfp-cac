package com.htfp.service.cac.command.biz.model.resquest;

import lombok.Data;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 21:08
 * @Description 地面站更改在控无人机请求体
 */
@Data
public class GcsChangeControlUavRequest {

    Long gcsId;
    Long uavId;
    private Boolean newArrival;
    private Integer uavStatus;
    private Long masterPilotId;
    private Long deputyPilotId;

}
