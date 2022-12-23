package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.client.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;


/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:03
 */

@Data
public class GcsChangeUavRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private Boolean newArrival;
    private Integer uavStatus;
    private String masterPilotId;
    private String deputyPilotId;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (newArrival == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else if (newArrival && UavStatusEnum.getFromCode(uavStatus) == null) {
            return ErrorCodeEnum.LACK_OF_UAV_STATUS;
        } else if (StringUtils.isBlank(masterPilotId)) {
            return ErrorCodeEnum.LACK_OF_PILOT_ID;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
