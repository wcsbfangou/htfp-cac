package com.htfp.service.oac.client.request;

import com.htfp.service.oac.client.enums.ErrorCodeEnum;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022/12/18
 * @Description 描述
 */
@Data
public class DeletePilotInfoRequest implements BaseValidate<ErrorCodeEnum> {

    private String pilotUniId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(pilotUniId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
