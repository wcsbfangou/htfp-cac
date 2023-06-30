package com.htfp.service.cac.router.biz.job;

import com.htfp.service.cac.dao.model.oac.ApplyFlightPlanLogDO;
import com.htfp.service.cac.dao.model.oac.DynamicFlightPlanInfoDO;
import com.htfp.service.cac.dao.service.oac.OacApplyFlightPlanLogDalService;
import com.htfp.service.cac.dao.service.oac.OacDynamicFlightPlanInfoDalService;
import com.htfp.service.oac.common.enums.ApplyStatusEnum;
import com.htfp.service.oac.common.enums.FlightPlanStatusTypeEnum;
import com.htfp.service.oac.common.utils.DateUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2023/6/30
 * @Description 描述
 */
@Slf4j
public class ModifyDynamicInfoFlightPlanStatusJob extends QuartzJobBean {

    @Resource
    private OacDynamicFlightPlanInfoDalService oacDynamicFlightPlanInfoDalService;

    @Resource
    private OacApplyFlightPlanLogDalService oacApplyFlightPlanLogDalService;


    // TODO: 2023/6/30 通知到指控
    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<DynamicFlightPlanInfoDO> queryDynamicFlightPlanInfoDOList = oacDynamicFlightPlanInfoDalService.queryByPlanStatus(FlightPlanStatusTypeEnum.FLIGHT_PLAN_SUBMITTED.getCode());
        if (CollectionUtils.isNotEmpty(queryDynamicFlightPlanInfoDOList)) {
            for (DynamicFlightPlanInfoDO dynamicFlightPlanInfo : queryDynamicFlightPlanInfoDOList) {
                Date flightPlanStartTime = DateUtils.getDateByStr(dynamicFlightPlanInfo.getFlightPlanStartTime(), DateUtils.DATETIME_MSEC_PATTERN);
                Date currentTime = new Date();
                if (currentTime.after(flightPlanStartTime)) {
                    ApplyFlightPlanLogDO queryApplyFlightPlanLog = oacApplyFlightPlanLogDalService.queryApplyFlightPlanLogByReplyFlightPlanId(dynamicFlightPlanInfo.getReplyFlightPlanId());
                    oacApplyFlightPlanLogDalService.updateApplyFlightPlanLogStatus(queryApplyFlightPlanLog, ApplyStatusEnum.OVERDUE.getCode());
                    oacDynamicFlightPlanInfoDalService.updateDynamicFlightPlanInfoPlanStatus(dynamicFlightPlanInfo, FlightPlanStatusTypeEnum.FLIGHT_PLAN_OVERDUE.getCode());
                }
            }
        }
    }

}
