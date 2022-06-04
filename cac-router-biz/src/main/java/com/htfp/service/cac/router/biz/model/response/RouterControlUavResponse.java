package com.htfp.service.cac.router.biz.model.response;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

/**
 * @Author sunjipeng
 * @Date 2022-06-01 22:58
 */
@Data
public class RouterControlUavResponse extends BaseResponse implements BaseValidate<ErrorCodeEnum> {

    private String data;

    /**
     * 参数校验
     *
     * @return
     */
    @Override
    public ErrorCodeEnum validate() {
        if(StringUtils.isBlank(data)){
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        }else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
