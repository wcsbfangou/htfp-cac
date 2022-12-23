package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.CommandCodeEnum;
import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:06
 */

@Data
public class GcsControlUavRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private String pilotId;
    private Integer commandCode;
    private Boolean commandResult;


    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(pilotId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else if (CommandCodeEnum.getFromCode(commandCode) == null) {
            return ErrorCodeEnum.LACK_OF_COMMAND_CODE;
        } else if (commandResult == null) {
            return ErrorCodeEnum.LACK_OF_COMMAND_RESULT;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
