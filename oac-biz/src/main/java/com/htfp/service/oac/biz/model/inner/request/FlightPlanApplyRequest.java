package com.htfp.service.oac.biz.model.inner.request;

import com.htfp.service.oac.common.enums.ApplicantTypeEnum;
import com.htfp.service.oac.common.enums.ErrorCodeEnum;
import com.htfp.service.oac.common.enums.FlightMissionTypeEnum;
import com.htfp.service.oac.biz.model.inner.request.param.OrganizationParam;
import com.htfp.service.oac.biz.model.inner.request.param.PersonParam;
import com.htfp.service.oac.biz.model.inner.request.param.PositionParam;
import lombok.Data;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/22
 * @Description 描述
 */
@Data
public class FlightPlanApplyRequest implements BaseValidate<ErrorCodeEnum> {

    private String cpn;
    private String applyFlightPlanId;
    private Integer applicantType;
    private OrganizationParam applicantOrganizationParam;
    private PersonParam applicantPersonParam;
    private List<PersonParam> pilots;
    private List<String> airspaceNumbers;
    private List<PositionParam> routePointCoordinates;
    private String takeoffAirportId;
    private String landingAirportId;
    private String takeoffSite;
    private String landingSite;
    private Integer missionType;
    private String startTime;
    private String endTime;
    private String emergencyProcedure;
    private String operationScenarioType;
    private Boolean isEmergency;
    private Boolean isVlos;

    @Override
    public ErrorCodeEnum validate() {
        if (ApplicantTypeEnum.getFromCode(applicantType) == null) {
            return ErrorCodeEnum.LACK_OF_APPLICANT_TYPE;
        } else if (StringUtils.isEmpty(applyFlightPlanId)) {
            return ErrorCodeEnum.LACK_OF_APPLY_FLIGHT_PLAN_ID;
        } else if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(applicantType)) && applicantOrganizationParam == null) {
            return ErrorCodeEnum.LACK_OF_ORGANIZATION;
        } else if (ApplicantTypeEnum.PERSON.equals(ApplicantTypeEnum.getFromCode(applicantType)) && applicantPersonParam == null) {
            return ErrorCodeEnum.LACK_OF_PERSON;
        } else if (CollectionUtils.isEmpty(pilots)) {
            return ErrorCodeEnum.LACK_OF_PILOT_INFO;
        } else if (CollectionUtils.isEmpty(airspaceNumbers)) {
            return ErrorCodeEnum.LACK_OF_AIRSPACE_NUM;
        } else if (CollectionUtils.isEmpty(routePointCoordinates)) {
            return ErrorCodeEnum.LACK_OF_ROUTE_POINT;
        } else if (StringUtils.isBlank(takeoffAirportId)) {
            return ErrorCodeEnum.LACK_OF_TAKE_OFF_AIRPORT;
        } else if (StringUtils.isBlank(landingAirportId)) {
            return ErrorCodeEnum.LACK_OF_LANDING_AIRPORT;
        } else if (StringUtils.isBlank(takeoffSite)) {
            return ErrorCodeEnum.LACK_OF_TAKE_OFF_SITE;
        } else if (StringUtils.isBlank(landingSite)) {
            return ErrorCodeEnum.LACK_OF_LANDING_SITE;
        } else if (FlightMissionTypeEnum.getFromCode(missionType) == null) {
            return ErrorCodeEnum.LACK_OF_MISSION_TYPE;
        } else if (StringUtils.isBlank(cpn)) {
            return ErrorCodeEnum.LACK_OF_UAV_CPN;
        } else if (StringUtils.isBlank(startTime)) {
            return ErrorCodeEnum.LACK_OF_START_TIME;
        } else if (StringUtils.isBlank(endTime)) {
            return ErrorCodeEnum.LACK_OF_END_TIME;
        } else if (isEmergency == null || isVlos == null) {
            return ErrorCodeEnum.LACK_OF_OTHER_FILED;
        } else {
            return ErrorCodeEnum.SUCCESS;
        }
    }
}
