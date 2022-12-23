package com.htfp.service.cac.router.biz.model.http.request.param;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 18:53
 */
@Data
public class ChangeUavParam implements BaseValidate<ErrorCodeEnum> {

    private Boolean newArrival;
    private String uavId;
    private Integer uavStatus;
    private String masterPilotId;
    private String deputyPilotId;

    @Override
    public ErrorCodeEnum validate() {
        if (newArrival == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(masterPilotId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else if (newArrival && UavStatusEnum.getFromCode(uavStatus) == null) {
            return ErrorCodeEnum.LACK_OF_UAV_STATUS;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
