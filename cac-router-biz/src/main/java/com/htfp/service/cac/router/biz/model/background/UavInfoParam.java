package com.htfp.service.cac.router.biz.model.background;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.UavStatusEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-08 10:44
 */
@Data
public class UavInfoParam implements BaseValidate<ErrorCodeEnum> {

    private String uavId;
    private Integer typeId;

    @Override
    public ErrorCodeEnum validate() {
        if(StringUtils.isBlank(uavId)){
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (typeId == null){
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
