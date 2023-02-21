package com.htfp.service.cac.router.biz.model.http.request;

import com.htfp.service.cac.common.enums.ApplicantTypeEnum;
import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.FlightMissionTypeEnum;
import com.htfp.service.cac.router.biz.model.BaseValidate;
import com.htfp.service.cac.router.biz.model.http.request.param.OrganizationParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PersonParam;
import com.htfp.service.cac.router.biz.model.http.request.param.PositionParam;
import lombok.Data;
import org.apache.commons.lang3.StringUtils;

import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/20
 * @Description 描述
 */
@Data
public class FlightPlanApplyRequest implements BaseValidate<ErrorCodeEnum> {

    private String gcsId;
    private String uavId;
    private Integer applicantType;
    private OrganizationParam applicantOrganization;
    private PersonParam applicantPerson;
    private List<PersonParam> pilots;
    private List<String> airspaceNumbers;
    // TODO: 2023/2/20 地面站转换麻烦,暂时用positionString替代一下,需要改回
    private List<PositionParam> routePointCoordinates;
    private String routeId;
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
        }
        // TODO: 2023/2/21 适配地面站无法发送结构体
        /* else if (ApplicantTypeEnum.ORGANIZATION.equals(ApplicantTypeEnum.getFromCode(applicantType)) && applicantOrganization == null) {
            return ErrorCodeEnum.LACK_OF_ORGANIZATION;
        } else if (ApplicantTypeEnum.PERSON.equals(ApplicantTypeEnum.getFromCode(applicantType)) && applicantPerson == null) {
            return ErrorCodeEnum.LACK_OF_PERSON;
        } else if (CollectionUtils.isEmpty(pilots)) {
            return ErrorCodeEnum.LACK_OF_PILOT_INFO;
        } else if (CollectionUtils.isEmpty(airspaceNumbers)) {
            return ErrorCodeEnum.LACK_OF_AIRSPACE_NUM;
        } else if (CollectionUtils.isEmpty(routePointCoordinates)) {
            return ErrorCodeEnum.LACK_OF_ROUTE_POINT;
        } */else if (StringUtils.isBlank(takeoffAirportId)) {
            return ErrorCodeEnum.LACK_OF_TAKE_OFF_AIRPORT;
        } else if (StringUtils.isBlank(landingAirportId)) {
            return ErrorCodeEnum.LACK_OF_LANDING_AIRPORT;
        } else if (StringUtils.isBlank(takeoffSite)) {
            return ErrorCodeEnum.LACK_OF_TAKE_OFF_SITE;
        } else if (StringUtils.isBlank(landingSite)) {
            return ErrorCodeEnum.LACK_OF_LANDING_SITE;
        } else if (FlightMissionTypeEnum.getFromCode(missionType) == null) {
            return ErrorCodeEnum.LACK_OF_MISSION_TYPE;
        } else if (StringUtils.isBlank(uavId)) {
            return ErrorCodeEnum.LACK_OF_UAV_ID;
        } else if (StringUtils.isBlank(gcsId)) {
            return ErrorCodeEnum.LACK_OF_GCS_ID;
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
