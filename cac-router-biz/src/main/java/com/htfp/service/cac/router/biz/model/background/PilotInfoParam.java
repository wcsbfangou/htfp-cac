package com.htfp.service.cac.router.biz.model.background;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:46
 */
@Data
public class PilotInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String pilotId;
    private String pilotName;
    private Integer controllableUavType;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(pilotId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else if (StringUtils.isBlank(pilotName)) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else if (controllableUavType == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
