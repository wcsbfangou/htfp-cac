package com.htfp.service.cac.router.biz.model.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:07
 */
@Data
public class RcsControlUavRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private List<CommandUavParam> uavList;


    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (CollectionUtils.isEmpty(uavList)) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            for (CommandUavParam commandUavParam : uavList) {
                ErrorCodeEnum commandUavParamValidateResult = commandUavParam.validate();
                if (!ErrorCodeEnum.SUCCESS.equals(commandUavParamValidateResult)) {
                    return commandUavParamValidateResult;
                }
            }
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
