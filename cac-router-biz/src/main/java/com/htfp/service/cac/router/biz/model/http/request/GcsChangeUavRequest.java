package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-18 15:03
 */

@Data
public class GcsChangeUavRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private List<ChangeUavParam> uavList;

    @Override
    public ErrorCodeEnum validate() {
        if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
        } else if (CollectionUtils.isEmpty(uavList)) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            for (ChangeUavParam changeUavParam : uavList) {
                ErrorCodeEnum changeUavParamValidateResult = changeUavParam.validate();
                if (!ErrorCodeEnum.SUCCESS.equals(changeUavParamValidateResult)) {
                    return changeUavParamValidateResult;
                }
            }
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
