package com.htfp.service.cac.router.biz.model.http.request.param;

import com.htfp.service.cac.common.enums.CommandCodeEnum;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 19:44
 */

@Data
public class CommandUavParam implements BaseValidate<ErrorCodeEnum> {

    private String uavId;
    private String pilotId;
    private Integer commandCode;
    private Boolean commandResult;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(pilotId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else if (CommandCodeEnum.getFromCode(commandCode) == null) {
            return ErrorCodeEnum.LACK_OF_COMMAND_CODE;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
