package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.router.biz.model.http.request.param.CommandUavParam;
import lombok.Data;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-06-01 22:56
 */

@Data
public class RouterControlUavRequest {

    private String gcsId;
    private List<CommandUavParam> uavList;
}
