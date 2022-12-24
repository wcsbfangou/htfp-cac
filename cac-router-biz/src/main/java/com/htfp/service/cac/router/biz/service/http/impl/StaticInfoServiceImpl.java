package com.htfp.service.cac.router.biz.service.http.impl;

import com.htfp.service.cac.common.enums.StaticInfoStatusEnum;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.OperatorInfoDO;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.dao.service.OperatorDalService;
import com.htfp.service.cac.dao.service.PilotDalService;
import com.htfp.service.cac.dao.service.UavDalService;
import com.htfp.service.cac.router.biz.model.background.param.GcsInfoParam;
import com.htfp.service.cac.router.biz.model.background.param.OperatorInfoParam;
import com.htfp.service.cac.router.biz.model.background.param.PilotInfoParam;
import com.htfp.service.cac.router.biz.model.background.param.UavInfoParam;
import com.htfp.service.cac.router.biz.model.background.request.CancelOperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.CancelPilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.CancelUavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.GcsInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.OperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.PilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterOperatorInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterPilotInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.RegisterUavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.request.UavInfoRequest;
import com.htfp.service.cac.router.biz.model.background.response.CancelOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.CancelPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.CancelUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeleteGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeleteOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeletePilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.DeleteUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.InsertUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.QueryUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.RegisterOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.RegisterPilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.RegisterUavInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateGcsInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateOperatorInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdatePilotInfoResponse;
import com.htfp.service.cac.router.biz.model.background.response.UpdateUavInfoResponse;
import com.htfp.service.cac.router.biz.service.http.IStaticInfoService;
import com.htfp.service.oac.client.request.UpdateOperatorInfoRequest;
import com.htfp.service.oac.client.request.UpdatePilotInfoRequest;
import com.htfp.service.oac.client.request.UpdateUavInfoRequest;
import com.htfp.service.oac.client.service.IBasicInfoService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.io.UnsupportedEncodingException;
import java.util.Base64;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/15
 * @Description 描述
 */
@Slf4j
@Service("staticInfoServiceImpl")
public class StaticInfoServiceImpl implements IStaticInfoService {

    @Resource
    private GcsDalService gcsDalService;

    @Resource
    private OperatorDalService operatorDalService;

    @Resource
    private PilotDalService pilotDalService;

    @Resource
    private UavDalService uavDalService;

    @Resource
    private IBasicInfoService basicInfoService;

    /**
     * 录入驾驶员信息
     *
     * @param pilotInfoRequest
     * @return
     */
    @Override
    public InsertPilotInfoResponse typeInPilotInfo(PilotInfoRequest pilotInfoRequest) {
        InsertPilotInfoResponse insertPilotInfoResponse = new InsertPilotInfoResponse();
        insertPilotInfoResponse.fail();
        try {
            String pilotCode = buildUniqueSubjectCode(pilotInfoRequest.getIdCardType(), pilotInfoRequest.getIdCardNumber());
            PilotInfoDO queryPilotInfo = pilotDalService.queryPilotInfo(pilotCode);
            if (queryPilotInfo == null) {
                PilotInfoDO pilotInfo = pilotDalService.buildPilotInfoDO(pilotCode, null, pilotInfoRequest.getPilotName(), pilotInfoRequest.getPilotType(), pilotInfoRequest.getControllableUavType(), pilotInfoRequest.getLicenseType(), pilotInfoRequest.getLicenseId(), pilotInfoRequest.getLicensePictureAddress(), pilotInfoRequest.getIdCardType(), pilotInfoRequest.getIdCardNumber(), pilotInfoRequest.getIdCardPictureAddress(), pilotInfoRequest.getGender(), pilotInfoRequest.getNationality(), pilotInfoRequest.getPhoneNumber(), pilotInfoRequest.getEmailAddress(), StaticInfoStatusEnum.TYPE_IN.getCode());
                int id = pilotDalService.insertPilotInfo(pilotInfo);
                if (id > 0) {
                    insertPilotInfoResponse.setData(pilotInfo.getId().toString());
                    insertPilotInfoResponse.success();
                } else {
                    insertPilotInfoResponse.fail("插入pilot数据失败");
                }
            } else {
                insertPilotInfoResponse.fail("pilot数据已存在，无需重新录入");
            }
        } catch (Exception e) {
            log.error("插入pilot信息异常, pilotInfoRequest={}", pilotInfoRequest, e);
            insertPilotInfoResponse.fail("插入pilot数据异常");
        }
        return insertPilotInfoResponse;
    }

    private String buildUniqueSubjectCode(Integer idCardType, String idCardNumber) {
        return idCardType.toString() + "#" + idCardNumber;
    }

    /**
     * 录入运营主体信息
     *
     * @param operatorInfoRequest
     * @return
     */
    @Override
    public InsertOperatorInfoResponse typeInOperatorInfo(OperatorInfoRequest operatorInfoRequest) {
        InsertOperatorInfoResponse insertOperatorInfoResponse = new InsertOperatorInfoResponse();
        insertOperatorInfoResponse.fail();
        try {
            String operatorCode = buildUniqueSubjectCode(operatorInfoRequest.getIdCardType(), operatorInfoRequest.getIdCardNumber());
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(operatorCode);
            if (queryOperatorInfo == null) {
                OperatorInfoDO operatorInfo = operatorDalService.buildOperatorInfoDO(operatorCode, null, operatorInfoRequest.getOperatorName(), operatorInfoRequest.getOperatorType(), operatorInfoRequest.getIdCardType(), operatorInfoRequest.getIdCardNumber(), operatorInfoRequest.getIdCardPictureAddress(), operatorInfoRequest.getCompanyName(), operatorInfoRequest.getSocialCreditCode(), operatorInfoRequest.getGender(), operatorInfoRequest.getNationality(), operatorInfoRequest.getCity(), operatorInfoRequest.getAddress(), operatorInfoRequest.getPhoneNumber(), operatorInfoRequest.getEmailAddress(), StaticInfoStatusEnum.TYPE_IN.getCode());
                int id = operatorDalService.insertOperatorInfo(operatorInfo);
                if (id > 0) {
                    insertOperatorInfoResponse.setData(operatorInfo.getId().toString());
                    insertOperatorInfoResponse.success();
                } else {
                    insertOperatorInfoResponse.fail("插入operator数据失败");
                }
            } else {
                insertOperatorInfoResponse.fail("operator数据已存在，无需重新录入");
            }
        } catch (Exception e) {
            log.error("插入operator信息异常, operatorInfoRequest={}", operatorInfoRequest, e);
            insertOperatorInfoResponse.fail("插入operator数据异常");
        }
        return insertOperatorInfoResponse;
    }

    /**
     * 录入无人机信息
     *
     * @param uavInfoRequest
     * @return
     */
    @Override
    public InsertUavInfoResponse typeInUavInfo(UavInfoRequest uavInfoRequest) {
        InsertUavInfoResponse insertUavInfoResponse = new InsertUavInfoResponse();
        insertUavInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(Long.valueOf(uavInfoRequest.getOperatorId()));
            if (queryOperatorInfo != null) {
                UavInfoDO queryUavInfo = uavDalService.queryUavInfoByUavReg(uavInfoRequest.getUavReg());
                if (queryUavInfo == null) {
                    UavInfoDO uavInfo = uavDalService.buildUavInfoDO(uavInfoRequest.getUavReg(), uavInfoRequest.getUavName(), uavInfoRequest.getUavType(), null, uavInfoRequest.getVin(), uavInfoRequest.getPvin(), uavInfoRequest.getSn(), uavInfoRequest.getFlightControlSn(), uavInfoRequest.getImei(), uavInfoRequest.getImsi(), uavInfoRequest.getManufacturerName(), uavInfoRequest.getProductName(), uavInfoRequest.getProductType(), uavInfoRequest.getProductSizeType(), uavInfoRequest.getMaxFlyTime(), uavInfoRequest.getOperationScenarioType(), Long.valueOf(uavInfoRequest.getOperatorId()), StaticInfoStatusEnum.TYPE_IN.getCode());
                    int id = uavDalService.insertUavInfo(uavInfo);
                    if (id > 0) {
                        insertUavInfoResponse.setData(uavInfo.getId().toString());
                        insertUavInfoResponse.success();
                    } else {
                        insertUavInfoResponse.fail("插入uav数据失败");
                    }
                } else {
                    insertUavInfoResponse.fail("uav数据已存在，无需重新录入");
                }
            } else {
                insertUavInfoResponse.fail("无此运营主体数据，请先录入运营主体数据");
            }
        } catch (Exception e) {
            log.error("插入uav信息异常, uavInfoRequest={}", uavInfoRequest, e);
            insertUavInfoResponse.fail("插入uav数据异常");
        }
        return insertUavInfoResponse;
    }

    /**
     * 录入地面站信息
     *
     * @param gcsInfoRequest
     * @return
     */
    @Override
    public InsertGcsInfoResponse typeInGcsInfo(GcsInfoRequest gcsInfoRequest) {
        InsertGcsInfoResponse insertGcsInfoResponse = new InsertGcsInfoResponse();
        insertGcsInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(Long.valueOf(gcsInfoRequest.getOperatorId()));
            if (queryOperatorInfo != null) {
                GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsInfoRequest.getGcsReg());
                if (queryGcsInfo == null) {
                    generateGcsToken(gcsInfoRequest);
                    GcsInfoDO gcsInfo = gcsDalService.buildGcsInfoDO(gcsInfoRequest.getGcsReg(), gcsInfoRequest.getGcsSn(), gcsInfoRequest.getGcsType(), gcsInfoRequest.getControllableUavType(), gcsInfoRequest.getDataLinkType(), gcsInfoRequest.getToken(), Long.valueOf(gcsInfoRequest.getOperatorId()));
                    int id = gcsDalService.insertGcsInfo(gcsInfo);
                    if (id > 0) {
                        insertGcsInfoResponse.setData(gcsInfo.getId().toString());
                        insertGcsInfoResponse.success();
                    } else {
                        insertGcsInfoResponse.fail("插入gcs数据失败");
                    }
                } else {
                    insertGcsInfoResponse.fail("gcs数据已存在，无需重新录入");
                }
            } else {
                insertGcsInfoResponse.fail("无此运营主体数据，请先录入运营主体数据");
            }
        } catch (Exception e) {
            log.error("插入gcs信息异常, gcsInfoRequest={}", gcsInfoRequest, e);
            insertGcsInfoResponse.fail("插入gcs数据异常");
        }
        return insertGcsInfoResponse;
    }

    /**
     * 根据gcsReg使用base64编码生成对应的token
     *
     * @param gcsInfoRequest
     * @throws UnsupportedEncodingException
     */
    private void generateGcsToken(GcsInfoRequest gcsInfoRequest) throws UnsupportedEncodingException {
        String gcsToken = Base64.getEncoder().encodeToString(gcsInfoRequest.getGcsReg().getBytes("UTF-8"));
        gcsInfoRequest.setToken(gcsToken);
    }

    /**
     * 修改驾驶员信息
     *
     * @param pilotInfoRequest
     * @return
     */
    @Override
    public UpdatePilotInfoResponse updatePilotInfo(PilotInfoRequest pilotInfoRequest) {
        UpdatePilotInfoResponse updatePilotInfoResponse = new UpdatePilotInfoResponse();
        updatePilotInfoResponse.fail();
        try {
            PilotInfoDO queryPilotInfo = pilotDalService.queryPilotInfo(Long.valueOf(pilotInfoRequest.getPilotId()));
            String pilotCode = buildUniqueSubjectCode(pilotInfoRequest.getIdCardType(), pilotInfoRequest.getIdCardNumber());
            if (queryPilotInfo.getPilotCode().equals(pilotCode)) {
                StaticInfoStatusEnum pilotStatus = StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus());
                if (!StaticInfoStatusEnum.REGISTERING.equals(pilotStatus)) {
                    PilotInfoDO pilotInfo = pilotDalService.buildPilotInfoDO(pilotCode, null, pilotInfoRequest.getPilotName(), pilotInfoRequest.getPilotType(), pilotInfoRequest.getControllableUavType(), pilotInfoRequest.getLicenseType(), pilotInfoRequest.getLicenseId(), pilotInfoRequest.getLicensePictureAddress(), pilotInfoRequest.getIdCardType(), pilotInfoRequest.getIdCardNumber(), pilotInfoRequest.getIdCardPictureAddress(), pilotInfoRequest.getGender(), pilotInfoRequest.getNationality(), pilotInfoRequest.getPhoneNumber(), pilotInfoRequest.getEmailAddress(), null);
                    pilotInfo.setId(Long.valueOf(pilotInfoRequest.getPilotId()));
                    int id = pilotDalService.updatePilotInfo(pilotInfo);
                    if (id > 0) {
                        if (StaticInfoStatusEnum.REGISTERED.equals(pilotStatus)) {
                            UpdatePilotInfoRequest oacUpdatePilotInfoRequest = buildOacUpdatePilotRequest(pilotInfoRequest, queryPilotInfo.getPilotUniId());
                            com.htfp.service.oac.client.response.UpdatePilotInfoResponse oacUpdatePilotInfoResponse = basicInfoService.updatePilotInfo(oacUpdatePilotInfoRequest);
                            updatePilotInfoResponse = buildUpdatePilotInfoResponse(oacUpdatePilotInfoResponse);
                        } else {
                            updatePilotInfoResponse.success();
                        }
                    } else {
                        updatePilotInfoResponse.fail("更新pilot数据失败");
                    }
                } else {
                    updatePilotInfoResponse.fail("pilot信息处于注册中状态，请暂缓更新");
                }
            } else {
                updatePilotInfoResponse.fail("pilot的idCardInfo信息不同，更新操作失败，建议录入新数据");
            }
        } catch (Exception e) {
            log.error("更新pilot信息异常, pilotInfoRequest={}", pilotInfoRequest, e);
            updatePilotInfoResponse.fail("更新pilot数据异常");
        }
        return updatePilotInfoResponse;
    }

    UpdatePilotInfoRequest buildOacUpdatePilotRequest(PilotInfoRequest pilotInfoRequest, String pilotUniId) {
        UpdatePilotInfoRequest updatePilotInfoRequest = new UpdatePilotInfoRequest();
        updatePilotInfoRequest.setPilotSourceId(pilotInfoRequest.getPilotId());
        updatePilotInfoRequest.setPilotUniId(pilotUniId);
        updatePilotInfoRequest.setPilotName(pilotInfoRequest.getPilotName());
        updatePilotInfoRequest.setPilotType(pilotInfoRequest.getPilotType());
        updatePilotInfoRequest.setLicenseType(pilotInfoRequest.getLicenseType());
        updatePilotInfoRequest.setLicenseId(pilotInfoRequest.getLicenseId());
        updatePilotInfoRequest.setLicensePictureAddress(pilotInfoRequest.getLicensePictureAddress());
        updatePilotInfoRequest.setIdCardType(pilotInfoRequest.getIdCardType());
        updatePilotInfoRequest.setIdCardNumber(pilotInfoRequest.getIdCardNumber());
        updatePilotInfoRequest.setIdCardPictureAddress(pilotInfoRequest.getIdCardPictureAddress());
        updatePilotInfoRequest.setGender(pilotInfoRequest.getGender());
        updatePilotInfoRequest.setNationality(pilotInfoRequest.getNationality());
        updatePilotInfoRequest.setPhoneNumber(pilotInfoRequest.getPhoneNumber());
        updatePilotInfoRequest.setEmailAddress(pilotInfoRequest.getEmailAddress());
        return updatePilotInfoRequest;
    }

    UpdatePilotInfoResponse buildUpdatePilotInfoResponse(com.htfp.service.oac.client.response.UpdatePilotInfoResponse oavUpdatePilotInfoResponse) {
        UpdatePilotInfoResponse updatePilotInfoResponse = new UpdatePilotInfoResponse();
        updatePilotInfoResponse.setSuccess(oavUpdatePilotInfoResponse.getSuccess());
        updatePilotInfoResponse.setCode(oavUpdatePilotInfoResponse.getCode());
        updatePilotInfoResponse.setMessage(oavUpdatePilotInfoResponse.getMessage());
        return updatePilotInfoResponse;
    }

    /**
     * 修改运营主体信息
     *
     * @param operatorInfoRequest
     * @return
     */
    @Override
    public UpdateOperatorInfoResponse updateOperatorInfo(OperatorInfoRequest operatorInfoRequest) {
        UpdateOperatorInfoResponse updateOperatorInfoResponse = new UpdateOperatorInfoResponse();
        updateOperatorInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(Long.valueOf(operatorInfoRequest.getOperatorId()));
            String operatorCode = buildUniqueSubjectCode(operatorInfoRequest.getIdCardType(), operatorInfoRequest.getIdCardNumber());
            if (queryOperatorInfo.getOperatorCode().equals(operatorCode)) {
                StaticInfoStatusEnum operatorStatus = StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus());
                if (!StaticInfoStatusEnum.REGISTERING.equals(operatorStatus)) {
                    OperatorInfoDO operatorInfo = operatorDalService.buildOperatorInfoDO(operatorCode, null, operatorInfoRequest.getOperatorName(), operatorInfoRequest.getOperatorType(), operatorInfoRequest.getIdCardType(), operatorInfoRequest.getIdCardNumber(), operatorInfoRequest.getIdCardPictureAddress(), operatorInfoRequest.getCompanyName(), operatorInfoRequest.getSocialCreditCode(), operatorInfoRequest.getGender(), operatorInfoRequest.getNationality(), operatorInfoRequest.getCity(), operatorInfoRequest.getAddress(), operatorInfoRequest.getPhoneNumber(), operatorInfoRequest.getEmailAddress(), null);
                    operatorInfo.setId(Long.valueOf(operatorInfoRequest.getOperatorId()));
                    int id = operatorDalService.updateOperatorInfo(operatorInfo);
                    if (id > 0) {
                        if (StaticInfoStatusEnum.REGISTERED.equals(operatorStatus)) {
                            UpdateOperatorInfoRequest oacUpdateOperatorInfoRequest = buildOacUpdateOperatorRequest(operatorInfoRequest, queryOperatorInfo.getOperatorUniId());
                            com.htfp.service.oac.client.response.UpdateOperatorInfoResponse oacUpdateOperatorInfoResponse = basicInfoService.updateOperatorInfo(oacUpdateOperatorInfoRequest);
                            updateOperatorInfoResponse = buildUpdateOperatorInfoResponse(oacUpdateOperatorInfoResponse);
                        } else {
                            updateOperatorInfoResponse.success();
                        }
                    } else {
                        updateOperatorInfoResponse.fail("更新operator数据失败");
                    }
                } else {
                    updateOperatorInfoResponse.fail("operator信息处于注册中状态，请暂缓更新");
                }
            } else {
                updateOperatorInfoResponse.fail("operator的idCardInfo信息不同，更新操作失败，建议插入新数据");
            }
        } catch (Exception e) {
            log.error("更新operator信息异常, operatorInfoRequest={}", operatorInfoRequest, e);
            updateOperatorInfoResponse.fail("更新operator数据异常");
        }
        return updateOperatorInfoResponse;
    }

    UpdateOperatorInfoRequest buildOacUpdateOperatorRequest(OperatorInfoRequest operatorInfoRequest, String operatorUniId) {
        UpdateOperatorInfoRequest updateOperatorInfoRequest = new UpdateOperatorInfoRequest();
        updateOperatorInfoRequest.setOperatorSourceId(operatorInfoRequest.getOperatorId());
        updateOperatorInfoRequest.setOperatorUniId(operatorUniId);
        updateOperatorInfoRequest.setOperatorName(operatorInfoRequest.getOperatorName());
        updateOperatorInfoRequest.setOperatorType(operatorInfoRequest.getOperatorType());
        updateOperatorInfoRequest.setIdCardType(operatorInfoRequest.getIdCardType());
        updateOperatorInfoRequest.setIdCardNumber(operatorInfoRequest.getIdCardNumber());
        updateOperatorInfoRequest.setIdCardPictureAddress(operatorInfoRequest.getIdCardPictureAddress());
        updateOperatorInfoRequest.setCompanyName(operatorInfoRequest.getCompanyName());
        updateOperatorInfoRequest.setSocialCreditCode(operatorInfoRequest.getSocialCreditCode());
        updateOperatorInfoRequest.setGender(operatorInfoRequest.getGender());
        updateOperatorInfoRequest.setNationality(operatorInfoRequest.getNationality());
        updateOperatorInfoRequest.setCity(operatorInfoRequest.getCity());
        updateOperatorInfoRequest.setAddress(operatorInfoRequest.getAddress());
        updateOperatorInfoRequest.setPhoneNumber(operatorInfoRequest.getPhoneNumber());
        updateOperatorInfoRequest.setEmailAddress(operatorInfoRequest.getEmailAddress());
        return updateOperatorInfoRequest;
    }

    UpdateOperatorInfoResponse buildUpdateOperatorInfoResponse(com.htfp.service.oac.client.response.UpdateOperatorInfoResponse oacUpdateOperatorInfoResponse) {
        UpdateOperatorInfoResponse updateOperatorInfoResponse = new UpdateOperatorInfoResponse();
        updateOperatorInfoResponse.setSuccess(oacUpdateOperatorInfoResponse.getSuccess());
        updateOperatorInfoResponse.setCode(oacUpdateOperatorInfoResponse.getCode());
        updateOperatorInfoResponse.setMessage(oacUpdateOperatorInfoResponse.getMessage());
        return updateOperatorInfoResponse;
    }

    /**
     * 修改无人机信息
     *
     * @param uavInfoRequest
     * @return
     */
    @Override
    public UpdateUavInfoResponse updateUavInfo(UavInfoRequest uavInfoRequest) {
        UpdateUavInfoResponse updateUavInfoResponse = new UpdateUavInfoResponse();
        updateUavInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(Long.valueOf(uavInfoRequest.getOperatorId()));
            if (queryOperatorInfo != null) {
                UavInfoDO queryUavInfo = uavDalService.queryUavInfo(Long.valueOf(uavInfoRequest.getUavId()));
                if (queryUavInfo.getUavReg().equals(uavInfoRequest.getUavReg())) {
                    StaticInfoStatusEnum uavStatus = StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus());
                    if (!StaticInfoStatusEnum.REGISTERING.equals(uavStatus)) {
                        UavInfoDO uavInfo = uavDalService.buildUavInfoDO(uavInfoRequest.getUavReg(), uavInfoRequest.getUavName(), uavInfoRequest.getUavType(), null, uavInfoRequest.getVin(), uavInfoRequest.getPvin(), uavInfoRequest.getSn(), uavInfoRequest.getFlightControlSn(), uavInfoRequest.getImei(), uavInfoRequest.getImsi(), uavInfoRequest.getManufacturerName(), uavInfoRequest.getProductName(), uavInfoRequest.getProductType(), uavInfoRequest.getProductSizeType(), uavInfoRequest.getMaxFlyTime(), uavInfoRequest.getOperationScenarioType(), Long.valueOf(uavInfoRequest.getOperatorId()), null);
                        uavInfo.setId(Long.valueOf(uavInfoRequest.getUavId()));
                        int id = uavDalService.updateUavInfo(uavInfo);
                        if (id > 0) {
                            if (StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus()))) {
                                if (StaticInfoStatusEnum.REGISTERED.equals(uavStatus)) {
                                    UpdateUavInfoRequest oacUpdateUavInfoRequest = buildOacUpdateUavRequest(uavInfoRequest, queryUavInfo.getCpn(), queryOperatorInfo.getOperatorUniId());
                                    com.htfp.service.oac.client.response.UpdateUavInfoResponse oacUpdateUavInfoResponse = basicInfoService.updateUavInfo(oacUpdateUavInfoRequest);
                                    updateUavInfoResponse = buildUpdateUavInfoResponse(oacUpdateUavInfoResponse);
                                    if (updateUavInfoResponse.getSuccess() && StringUtils.isNotBlank(updateUavInfoResponse.getCpn())) {
                                        if (uavDalService.updateUavInfoCpn(uavInfo, updateUavInfoResponse.getCpn()) <= 0) {
                                            updateUavInfoResponse.fail("更新cpn失败");
                                        }
                                    }
                                } else {
                                    updateUavInfoResponse.success();
                                }
                            } else {
                                updateUavInfoResponse.fail("运营主体在oac未完成注册，修改oac无人机信息失败");
                            }
                        } else {
                            updateUavInfoResponse.fail("cac更新uav数据失败");
                        }
                    } else {
                        updateUavInfoResponse.fail("uav信息处于注册中状态，请暂缓更新");
                    }
                } else {
                    updateUavInfoResponse.fail("uav的reg编码不同，更新操作失败，建议插入新数据");
                }
            } else {
                updateUavInfoResponse.fail("无人机运营主体未录入，修改无人机信息失败");
            }
        } catch (Exception e) {
            log.error("更新uav信息异常, uavInfoRequest={}", uavInfoRequest, e);
            updateUavInfoResponse.fail("更新uav数据异常");
        }
        return updateUavInfoResponse;
    }

    UpdateUavInfoRequest buildOacUpdateUavRequest(UavInfoRequest uavInfoRequest, String cpn, String operatorUniId) {
        UpdateUavInfoRequest updateUavInfoRequest = new UpdateUavInfoRequest();
        updateUavInfoRequest.setUavSourceId(uavInfoRequest.getUavId());
        updateUavInfoRequest.setUavReg(uavInfoRequest.getUavReg());
        updateUavInfoRequest.setUavName(uavInfoRequest.getUavName());
        updateUavInfoRequest.setCpn(cpn);
        updateUavInfoRequest.setVin(uavInfoRequest.getVin());
        updateUavInfoRequest.setPvin(uavInfoRequest.getPvin());
        updateUavInfoRequest.setSn(uavInfoRequest.getSn());
        updateUavInfoRequest.setFlightControlSn(uavInfoRequest.getFlightControlSn());
        updateUavInfoRequest.setImei(uavInfoRequest.getImei());
        updateUavInfoRequest.setImsi(uavInfoRequest.getImsi());
        updateUavInfoRequest.setManufacturerName(uavInfoRequest.getManufacturerName());
        updateUavInfoRequest.setProductName(uavInfoRequest.getProductName());
        updateUavInfoRequest.setProductType(uavInfoRequest.getProductType());
        updateUavInfoRequest.setProductSizeType(uavInfoRequest.getProductSizeType());
        updateUavInfoRequest.setMaxFlyTime(uavInfoRequest.getMaxFlyTime());
        updateUavInfoRequest.setOperationScenarioType(uavInfoRequest.getOperationScenarioType());
        updateUavInfoRequest.setOperatorUniId(operatorUniId);
        return updateUavInfoRequest;
    }

    UpdateUavInfoResponse buildUpdateUavInfoResponse(com.htfp.service.oac.client.response.UpdateUavInfoResponse oacUpdateUavInfoResponse) {
        UpdateUavInfoResponse updateUavInfoResponse = new UpdateUavInfoResponse();
        updateUavInfoResponse.setCpn(oacUpdateUavInfoResponse.getCpn());
        updateUavInfoResponse.setSuccess(oacUpdateUavInfoResponse.getSuccess());
        updateUavInfoResponse.setCode(oacUpdateUavInfoResponse.getCode());
        updateUavInfoResponse.setMessage(oacUpdateUavInfoResponse.getMessage());
        return updateUavInfoResponse;
    }

    /**
     * 修改地面站信息
     *
     * @param gcsInfoRequest
     * @return
     */
    @Override
    public UpdateGcsInfoResponse updateGcsInfo(GcsInfoRequest gcsInfoRequest) {
        UpdateGcsInfoResponse updateGcsInfoResponse = new UpdateGcsInfoResponse();
        updateGcsInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(Long.valueOf(gcsInfoRequest.getOperatorId()));
            if (queryOperatorInfo != null) {
                GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(Long.valueOf(gcsInfoRequest.getGcsId()));
                if (queryGcsInfo.getGcsReg().equals(gcsInfoRequest.getGcsReg())) {
                    generateGcsToken(gcsInfoRequest);
                    GcsInfoDO gcsInfo = gcsDalService.buildGcsInfoDO(gcsInfoRequest.getGcsReg(), gcsInfoRequest.getGcsReg(), gcsInfoRequest.getGcsType(), gcsInfoRequest.getControllableUavType(), gcsInfoRequest.getDataLinkType(), gcsInfoRequest.getToken(), Long.valueOf(gcsInfoRequest.getOperatorId()));
                    gcsInfo.setId(Long.valueOf(gcsInfoRequest.getGcsId()));
                    int id = gcsDalService.updateGcsInfo(gcsInfo);
                    if (id > 0) {
                        updateGcsInfoResponse.success();
                    } else {
                        updateGcsInfoResponse.fail("更新gcs数据失败");
                    }
                } else {
                    updateGcsInfoResponse.fail("gcs数据reg不同，更新操作失败，建议插入新数据");
                }
            } else {
                updateGcsInfoResponse.setMessage("地面站运营主体未录入，修改地面站信息失败");
            }
        } catch (Exception e) {
            log.error("更新gcs信息异常, gcsInfoRequest={}", gcsInfoRequest, e);
            updateGcsInfoResponse.fail("更新gcs数据异常");
        }
        return updateGcsInfoResponse;
    }

    /**
     * 查询驾驶员信息
     *
     * @param pilotId
     * @return
     */
    @Override
    public QueryPilotInfoResponse queryPilotInfo(Long pilotId) {
        QueryPilotInfoResponse queryPilotInfoResponse = new QueryPilotInfoResponse();
        queryPilotInfoResponse.fail();
        try {
            PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(pilotId);
            if (pilotInfo != null) {
                PilotInfoParam pilotInfoParam = buildPilotInfoParam(pilotInfo);
                queryPilotInfoResponse.setData(pilotInfoParam);
                queryPilotInfoResponse.success();
            } else {
                queryPilotInfoResponse.fail("无此pilot数据");
            }
        } catch (Exception e) {
            log.error("查询pilot信息异常, pilotId={}", pilotId, e);
            queryPilotInfoResponse.fail("查询pilot信息异常");
        }
        return queryPilotInfoResponse;
    }

    private PilotInfoParam buildPilotInfoParam(PilotInfoDO pilotInfoDO) {
        PilotInfoParam pilotInfoParam = new PilotInfoParam();
        pilotInfoParam.setPilotId(pilotInfoDO.getId().toString());
        pilotInfoParam.setPilotName(pilotInfoDO.getPilotName());
        pilotInfoParam.setPilotType(pilotInfoDO.getPilotType());
        pilotInfoParam.setControllableUavType(pilotInfoDO.getControllableUavType());
        pilotInfoParam.setLicenseType(pilotInfoDO.getLicenseType());
        pilotInfoParam.setLicenseId(pilotInfoDO.getLicenseId());
        pilotInfoParam.setLicensePictureAddress(pilotInfoDO.getLicensePictureAddress());
        pilotInfoParam.setIdCardType(pilotInfoDO.getIdCardType());
        pilotInfoParam.setIdCardNumber(pilotInfoDO.getIdCardNumber());
        pilotInfoParam.setIdCardPictureAddress(pilotInfoDO.getIdCardPictureAddress());
        pilotInfoParam.setGender(pilotInfoDO.getGender());
        pilotInfoParam.setNationality(pilotInfoDO.getNationality());
        pilotInfoParam.setPhoneNumber(pilotInfoDO.getPhoneNumber());
        pilotInfoParam.setEmailAddress(pilotInfoDO.getEmailAddress());
        pilotInfoParam.setStatus(pilotInfoDO.getStatus());
        return pilotInfoParam;
    }

    /**
     * 查询运营主体信息
     *
     * @param operatorId
     * @return
     */
    @Override
    public QueryOperatorInfoResponse queryOperatorInfo(Long operatorId) {
        QueryOperatorInfoResponse queryOperatorInfoResponse = new QueryOperatorInfoResponse();
        queryOperatorInfoResponse.fail();
        try {
            OperatorInfoDO operatorInfo = operatorDalService.queryOperatorInfo(operatorId);
            if (operatorInfo != null) {
                OperatorInfoParam operatorInfoParam = buildOperatorInfoParam(operatorInfo);
                queryOperatorInfoResponse.setData(operatorInfoParam);
                queryOperatorInfoResponse.success();
            } else {
                queryOperatorInfoResponse.fail("无此operator数据");
            }
        } catch (Exception e) {
            log.error("查询operator信息异常, operatorId={}", operatorId, e);
            queryOperatorInfoResponse.fail("查询operator信息异常");
        }
        return queryOperatorInfoResponse;
    }

    OperatorInfoParam buildOperatorInfoParam(OperatorInfoDO operatorInfo) {
        OperatorInfoParam operatorInfoParam = new OperatorInfoParam();
        operatorInfoParam.setOperatorId(operatorInfo.getId().toString());
        operatorInfoParam.setOperatorName(operatorInfo.getOperatorName());
        operatorInfoParam.setOperatorType(operatorInfo.getOperatorType());
        operatorInfoParam.setIdCardType(operatorInfo.getIdCardType());
        operatorInfoParam.setIdCardNumber(operatorInfo.getIdCardNumber());
        operatorInfoParam.setIdCardPictureAddress(operatorInfo.getIdCardPictureAddress());
        operatorInfoParam.setCompanyName(operatorInfo.getCompanyName());
        operatorInfoParam.setSocialCreditCode(operatorInfo.getSocialCreditCode());
        operatorInfoParam.setGender(operatorInfo.getGender());
        operatorInfoParam.setNationality(operatorInfo.getNationality());
        operatorInfoParam.setCity(operatorInfo.getCity());
        operatorInfoParam.setAddress(operatorInfo.getAddress());
        operatorInfoParam.setPhoneNumber(operatorInfo.getPhoneNumber());
        operatorInfoParam.setEmailAddress(operatorInfo.getEmailAddress());
        operatorInfoParam.setStatus(operatorInfo.getStatus());
        return operatorInfoParam;
    }

    /**
     * 查询无人机信息
     *
     * @param uavId
     * @return
     */
    @Override
    public QueryUavInfoResponse queryUavInfo(Long uavId) {
        QueryUavInfoResponse queryUavInfoResponse = new QueryUavInfoResponse();
        queryUavInfoResponse.fail();
        try {
            UavInfoDO uavInfo = uavDalService.queryUavInfo(uavId);
            if (uavInfo != null) {
                UavInfoParam uavInfoParam = buildUavInfoParam(uavInfo);
                queryUavInfoResponse.setData(uavInfoParam);
                queryUavInfoResponse.success();
            } else {
                queryUavInfoResponse.fail("无此uav数据");
            }
        } catch (Exception e) {
            log.error("查询uav信息异常, uavId={}", uavId, e);
            queryUavInfoResponse.fail("查询uav信息异常");
        }
        return queryUavInfoResponse;
    }

    UavInfoParam buildUavInfoParam(UavInfoDO uavInfo) {
        UavInfoParam uavInfoParam = new UavInfoParam();
        uavInfoParam.setUavId(uavInfo.getId().toString());
        uavInfoParam.setUavReg(uavInfo.getUavReg());
        uavInfoParam.setUavName(uavInfo.getUavName());
        uavInfoParam.setUavType(uavInfo.getUavType());
        uavInfoParam.setVin(uavInfo.getVin());
        uavInfoParam.setPvin(uavInfo.getPvin());
        uavInfoParam.setSn(uavInfo.getSn());
        uavInfoParam.setFlightControlSn(uavInfo.getFlightControlSn());
        uavInfoParam.setImei(uavInfo.getImei());
        uavInfoParam.setImsi(uavInfo.getImsi());
        uavInfoParam.setManufacturerName(uavInfo.getManufacturerName());
        uavInfoParam.setProductName(uavInfo.getProductName());
        uavInfoParam.setProductType(uavInfo.getProductType());
        uavInfoParam.setProductSizeType(uavInfo.getProductSizeType());
        uavInfoParam.setMaxFlyTime(uavInfo.getMaxFlyTime());
        uavInfoParam.setOperationScenarioType(uavInfo.getOperationScenarioType());
        uavInfoParam.setOperatorId(uavInfo.getOperatorId().toString());
        uavInfoParam.setStatus(uavInfo.getStatus());
        return uavInfoParam;
    }

    /**
     * 查询地面站信息
     *
     * @param gcsId
     * @return
     */
    @Override
    public QueryGcsInfoResponse queryGcsInfo(Long gcsId) {
        QueryGcsInfoResponse queryGcsInfoResponse = new QueryGcsInfoResponse();
        queryGcsInfoResponse.fail();
        try {
            GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(gcsId);
            if (gcsInfo != null) {
                GcsInfoParam gcsInfoParam = buildGcsInfoParam(gcsInfo);
                queryGcsInfoResponse.setData(gcsInfoParam);
                queryGcsInfoResponse.success();
            } else {
                queryGcsInfoResponse.fail("无此gcs数据");
            }
        } catch (Exception e) {
            log.error("查询gcs信息异常, gcsId={}", gcsId, e);
            queryGcsInfoResponse.fail("查询gcs信息异常");
        }
        return queryGcsInfoResponse;
    }

    /**
     * 查询驾驶员信息
     *
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    @Override
    public QueryPilotInfoResponse queryPilotInfoByIdCardInfo(Integer idCardType, String idCardNumber) {
        QueryPilotInfoResponse queryPilotInfoResponse = new QueryPilotInfoResponse();
        queryPilotInfoResponse.fail();
        try {
            String pilotCode = buildUniqueSubjectCode(idCardType, idCardNumber);
            PilotInfoDO pilotInfo = pilotDalService.queryPilotInfo(pilotCode);
            if (pilotInfo != null) {
                PilotInfoParam pilotInfoParam = buildPilotInfoParam(pilotInfo);
                queryPilotInfoResponse.setData(pilotInfoParam);
                queryPilotInfoResponse.success();
            } else {
                queryPilotInfoResponse.fail("无此pilot数据");
            }
        } catch (Exception e) {
            log.error("查询pilot信息异常, pilotIdCardType={}, pilotIdCardNumber={}", idCardType, idCardNumber, e);
            queryPilotInfoResponse.fail("查询pilot信息异常");
        }
        return queryPilotInfoResponse;
    }

    /**
     * 查询运营主体信息
     *
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    @Override
    public QueryOperatorInfoResponse queryOperatorInfoByIdCardInfo(Integer idCardType, String idCardNumber) {
        QueryOperatorInfoResponse queryOperatorInfoResponse = new QueryOperatorInfoResponse();
        queryOperatorInfoResponse.fail();
        try {
            String operatorCode = buildUniqueSubjectCode(idCardType, idCardNumber);
            OperatorInfoDO operatorInfo = operatorDalService.queryOperatorInfo(operatorCode);
            if (operatorInfo != null) {
                OperatorInfoParam operatorInfoParam = buildOperatorInfoParam(operatorInfo);
                queryOperatorInfoResponse.setData(operatorInfoParam);
                queryOperatorInfoResponse.success();
            } else {
                queryOperatorInfoResponse.fail("无此operator数据");
            }
        } catch (Exception e) {
            log.error("查询operator信息异常, operatorIdCardType={}, operatorIdCardNumber={}", idCardType, idCardNumber, e);
            queryOperatorInfoResponse.fail("查询operator信息异常");
        }
        return queryOperatorInfoResponse;
    }

    /**
     * 查询无人机信息
     *
     * @param uavReg
     * @return
     */
    @Override
    public QueryUavInfoResponse queryUavInfoByUavReg(String uavReg) {
        QueryUavInfoResponse queryUavInfoResponse = new QueryUavInfoResponse();
        queryUavInfoResponse.fail();
        try {
            UavInfoDO uavInfo = uavDalService.queryUavInfoByUavReg(uavReg);
            if (uavInfo != null) {
                UavInfoParam uavInfoParam = buildUavInfoParam(uavInfo);
                queryUavInfoResponse.setData(uavInfoParam);
                queryUavInfoResponse.success();
            } else {
                queryUavInfoResponse.fail("无此uav数据");
            }
        } catch (Exception e) {
            log.error("查询uav信息异常, uavReg={}", uavReg, e);
            queryUavInfoResponse.fail("查询uav信息异常");
        }
        return queryUavInfoResponse;
    }

    /**
     * 查询地面站信息
     *
     * @param gcsReg
     * @return
     */
    @Override
    public QueryGcsInfoResponse queryGcsInfoByGcsReg(String gcsReg) {
        QueryGcsInfoResponse queryGcsInfoResponse = new QueryGcsInfoResponse();
        queryGcsInfoResponse.fail();
        try {
            GcsInfoDO gcsInfo = gcsDalService.queryGcsInfo(gcsReg);
            if (gcsInfo != null) {
                GcsInfoParam gcsInfoParam = buildGcsInfoParam(gcsInfo);
                queryGcsInfoResponse.setData(gcsInfoParam);
                queryGcsInfoResponse.success();
            } else {
                queryGcsInfoResponse.fail("无此gcs数据");
            }
        } catch (Exception e) {
            log.error("查询gcs信息异常, gcsReg={}", gcsReg, e);
            queryGcsInfoResponse.fail("查询gcs信息异常");
        }
        return queryGcsInfoResponse;
    }

    GcsInfoParam buildGcsInfoParam(GcsInfoDO gcsInfo) {
        GcsInfoParam gcsInfoParam = new GcsInfoParam();
        gcsInfoParam.setGcsId(gcsInfo.getId().toString());
        gcsInfoParam.setGcsReg(gcsInfo.getGcsReg());
        gcsInfoParam.setGcsSn(gcsInfo.getGcsSn());
        gcsInfoParam.setGcsType(gcsInfo.getGcsType());
        gcsInfoParam.setControllableUavType(gcsInfo.getControllableUavType());
        gcsInfoParam.setDataLinkType(gcsInfo.getDataLinkType());
        gcsInfoParam.setToken(gcsInfo.getToken());
        gcsInfoParam.setOperatorId(gcsInfo.getOperatorId().toString());
        return gcsInfoParam;
    }

    /**
     * 删除驾驶员信息
     *
     * @param pilotId
     * @return
     */
    @Override
    public DeletePilotInfoResponse deletePilotInfo(Long pilotId) {
        DeletePilotInfoResponse deletePilotInfoResponse = new DeletePilotInfoResponse();
        deletePilotInfoResponse.fail();
        try {
            PilotInfoDO queryPilotInfo = pilotDalService.queryPilotInfo(pilotId);
            if (queryPilotInfo != null) {
                int id = pilotDalService.deletePilotInfoByPilotId(pilotId);
                if (id > 0) {
                    if (StaticInfoStatusEnum.TYPE_IN != StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus())) {
                        com.htfp.service.oac.client.request.DeletePilotInfoRequest oacDeletePilotInfoRequest = buildOacDeletePilotInfoRequest(queryPilotInfo);
                        com.htfp.service.oac.client.response.DeletePilotInfoResponse oacDeletePilotInfoResponse = basicInfoService.deletePilotInfo(oacDeletePilotInfoRequest);
                        deletePilotInfoResponse = buildDeletePilotInfoResponse(oacDeletePilotInfoResponse);
                    } else {
                        deletePilotInfoResponse.success();
                    }
                } else {
                    deletePilotInfoResponse.fail("删除pilot数据失败");
                }
            } else {
                deletePilotInfoResponse.fail("pilot数据已删除无需重复删除");
            }
        } catch (Exception e) {
            log.error("删除pilot信息异常, pilotId={}", pilotId, e);
            deletePilotInfoResponse.fail("删除pilot数据失败");
        }
        return deletePilotInfoResponse;
    }

    com.htfp.service.oac.client.request.DeletePilotInfoRequest buildOacDeletePilotInfoRequest(PilotInfoDO queryPilotInfo) {
        com.htfp.service.oac.client.request.DeletePilotInfoRequest deletePilotInfoRequest = new com.htfp.service.oac.client.request.DeletePilotInfoRequest();
        deletePilotInfoRequest.setPilotUniId(queryPilotInfo.getPilotUniId());
        return deletePilotInfoRequest;
    }

    DeletePilotInfoResponse buildDeletePilotInfoResponse(com.htfp.service.oac.client.response.DeletePilotInfoResponse oacDeletePilotInfoResponse) {
        DeletePilotInfoResponse deletePilotInfoResponse = new DeletePilotInfoResponse();
        deletePilotInfoResponse.setSuccess(oacDeletePilotInfoResponse.getSuccess());
        deletePilotInfoResponse.setCode(oacDeletePilotInfoResponse.getCode());
        deletePilotInfoResponse.setMessage(oacDeletePilotInfoResponse.getMessage());
        return deletePilotInfoResponse;
    }

    /**
     * 删除运营主体信息
     *
     * @param operatorId
     * @return
     */
    @Override
    public DeleteOperatorInfoResponse deleteOperatorInfo(Long operatorId) {
        DeleteOperatorInfoResponse deleteOperatorInfoResponse = new DeleteOperatorInfoResponse();
        deleteOperatorInfoResponse.fail();
        try {
            List<UavInfoDO> queryUavInfoList = uavDalService.queryUavInfoByOperatorId(operatorId);
            List<GcsInfoDO> queryGcsInfoList = gcsDalService.queryGcsInfoByOperatorId(operatorId);
            if (queryUavInfoList == null && queryGcsInfoList == null) {
                OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(operatorId);
                if (queryOperatorInfo != null) {
                    int id = operatorDalService.deleteOperatorInfoByOperatorId(operatorId);
                    if (id > 0) {
                        if (StaticInfoStatusEnum.TYPE_IN != StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus())) {
                            com.htfp.service.oac.client.request.DeleteOperatorInfoRequest deleteOperatorInfoRequest = buildOacDeleteOperatorInfoRequest(queryOperatorInfo);
                            com.htfp.service.oac.client.response.DeleteOperatorInfoResponse oacDeleteOperatorInfoResponse = basicInfoService.deleteOperatorInfo(deleteOperatorInfoRequest);
                            deleteOperatorInfoResponse = buildDeleteOperatorInfoResponse(oacDeleteOperatorInfoResponse);
                        } else {
                            deleteOperatorInfoResponse.success();
                        }
                    } else {
                        deleteOperatorInfoResponse.fail("删除operator数据失败");
                    }
                } else {
                    deleteOperatorInfoResponse.fail("operator数据已删除,无需重复删除");
                }
            } else {
                deleteOperatorInfoResponse.fail("operator下存在uav或gcs，删除失败");
            }
        } catch (Exception e) {
            log.error("删除operator信息异常, operatorId={}", operatorId, e);
            deleteOperatorInfoResponse.fail("删除operator数据失败");
        }
        return deleteOperatorInfoResponse;
    }

    com.htfp.service.oac.client.request.DeleteOperatorInfoRequest buildOacDeleteOperatorInfoRequest(OperatorInfoDO queryOperatorInfo) {
        com.htfp.service.oac.client.request.DeleteOperatorInfoRequest deleteOperatorInfoRequest = new com.htfp.service.oac.client.request.DeleteOperatorInfoRequest();
        deleteOperatorInfoRequest.setOperatorUniId(queryOperatorInfo.getOperatorUniId());
        return deleteOperatorInfoRequest;
    }

    DeleteOperatorInfoResponse buildDeleteOperatorInfoResponse(com.htfp.service.oac.client.response.DeleteOperatorInfoResponse oacDeleteOperatorInfoResponse) {
        DeleteOperatorInfoResponse deleteOperatorInfoResponse = new DeleteOperatorInfoResponse();
        deleteOperatorInfoResponse.setSuccess(oacDeleteOperatorInfoResponse.getSuccess());
        deleteOperatorInfoResponse.setCode(oacDeleteOperatorInfoResponse.getCode());
        deleteOperatorInfoResponse.setMessage(oacDeleteOperatorInfoResponse.getMessage());
        return deleteOperatorInfoResponse;
    }

    /**
     * 删除无人机信息
     *
     * @param uavId
     * @return
     */
    @Override
    public DeleteUavInfoResponse deleteUavInfo(Long uavId) {
        DeleteUavInfoResponse deleteUavInfoResponse = new DeleteUavInfoResponse();
        deleteUavInfoResponse.fail();
        try {
            UavInfoDO queryUavInfo = uavDalService.queryUavInfo(uavId);
            if (queryUavInfo != null) {
                int id = uavDalService.deleteUavInfoById(uavId);
                if (id > 0) {
                    if (StaticInfoStatusEnum.TYPE_IN != StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus())) {
                        com.htfp.service.oac.client.request.DeleteUavInfoRequest deleteUavInfoRequest = buildOacDeleteUavInfoRequest(queryUavInfo);
                        com.htfp.service.oac.client.response.DeleteUavInfoResponse oacDeleteUavInfoResponse = basicInfoService.deleteUavInfo(deleteUavInfoRequest);
                        deleteUavInfoResponse = buildDeleteUavInfoResponse(oacDeleteUavInfoResponse);
                    } else {
                        deleteUavInfoResponse.success();
                    }
                } else {
                    deleteUavInfoResponse.fail("删除uav数据失败");
                }
            } else {
                deleteUavInfoResponse.fail("uav数据已删除无需重复删除");
            }

        } catch (Exception e) {
            log.error("删除uav信息异常, uavId={}", uavId, e);
            deleteUavInfoResponse.fail("删除uav数据失败");
        }
        return deleteUavInfoResponse;
    }

    com.htfp.service.oac.client.request.DeleteUavInfoRequest buildOacDeleteUavInfoRequest(UavInfoDO queryUavInfo) {
        com.htfp.service.oac.client.request.DeleteUavInfoRequest deleteUavInfoRequest = new com.htfp.service.oac.client.request.DeleteUavInfoRequest();
        deleteUavInfoRequest.setCpn(queryUavInfo.getCpn());
        return deleteUavInfoRequest;
    }

    DeleteUavInfoResponse buildDeleteUavInfoResponse(com.htfp.service.oac.client.response.DeleteUavInfoResponse oacDeleteUavInfoResponse) {
        DeleteUavInfoResponse deleteUavInfoResponse = new DeleteUavInfoResponse();
        deleteUavInfoResponse.setSuccess(oacDeleteUavInfoResponse.getSuccess());
        deleteUavInfoResponse.setCode(oacDeleteUavInfoResponse.getCode());
        deleteUavInfoResponse.setMessage(oacDeleteUavInfoResponse.getMessage());
        return deleteUavInfoResponse;
    }

    /**
     * 删除地面站信息
     *
     * @param gcsId
     * @return
     */
    @Override
    public DeleteGcsInfoResponse deleteGcsInfo(Long gcsId) {
        DeleteGcsInfoResponse deleteGcsInfoResponse = new DeleteGcsInfoResponse();
        deleteGcsInfoResponse.fail();
        try {
            GcsInfoDO queryGcsInfo = gcsDalService.queryGcsInfo(gcsId);
            if (queryGcsInfo != null) {
                int id = gcsDalService.deleteGcsInfoByGcsId(gcsId);
                if (id > 0) {
                    deleteGcsInfoResponse.success();
                } else {
                    deleteGcsInfoResponse.fail("删除gcs数据失败");
                }
            } else {
                deleteGcsInfoResponse.fail("gcs数据已删除，无需重复删除");
            }
        } catch (Exception e) {
            log.error("删除gcs信息异常, gcsId={}", gcsId, e);
            deleteGcsInfoResponse.fail("删除gcs数据失败");
        }
        return deleteGcsInfoResponse;
    }

    /**
     * 注册驾驶员信息
     *
     * @param registerPilotInfoRequest
     * @return
     */
    @Override
    public RegisterPilotInfoResponse registerPilotInfo(RegisterPilotInfoRequest registerPilotInfoRequest) {
        RegisterPilotInfoResponse registerPilotInfoResponse = new RegisterPilotInfoResponse();
        registerPilotInfoResponse.fail();
        try {
            Long pilotId = Long.valueOf(registerPilotInfoRequest.getPilotId());
            PilotInfoDO queryPilotInfo = pilotDalService.queryPilotInfo(pilotId);
            if (queryPilotInfo != null) {
                StaticInfoStatusEnum pilotStatus = StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus());
                if (StaticInfoStatusEnum.TYPE_IN.equals(pilotStatus) || StaticInfoStatusEnum.CANCELED.equals(pilotStatus)) {
                    pilotDalService.updatePilotInfoStatus(queryPilotInfo, StaticInfoStatusEnum.REGISTERING.getCode());
                    com.htfp.service.oac.client.request.RegisterPilotInfoRequest oacRegisterPilotInfoRequest = buildOacRegisterPilotInfoRequest(queryPilotInfo);
                    com.htfp.service.oac.client.response.RegisterPilotInfoResponse oacRegisterPilotInfoResponse = basicInfoService.registerPilotInfo(oacRegisterPilotInfoRequest);
                    registerPilotInfoResponse = buildOacRegisterPilotInfoResponse(oacRegisterPilotInfoResponse);
                    if (registerPilotInfoResponse.getSuccess() && StringUtils.isNotBlank(registerPilotInfoResponse.getPilotUniId())) {
                        pilotDalService.updatePilotInfoUniIdAndStatus(queryPilotInfo, registerPilotInfoResponse.getPilotUniId(), StaticInfoStatusEnum.REGISTERED.getCode());
                    }
                } else {
                    registerPilotInfoResponse.fail("pilot已注册或正在注册/注销");
                }
            } else {
                registerPilotInfoResponse.fail("无此pilot数据");
            }
        } catch (Exception e) {
            log.error("注册pilot异常, registerPilotInfoRequest={}", registerPilotInfoRequest, e);
            registerPilotInfoResponse.fail("注册pilot异常");
        }
        return registerPilotInfoResponse;
    }

    com.htfp.service.oac.client.request.RegisterPilotInfoRequest buildOacRegisterPilotInfoRequest(PilotInfoDO pilotInfo) {
        com.htfp.service.oac.client.request.RegisterPilotInfoRequest oacRegisterPilotInfoRequest = new com.htfp.service.oac.client.request.RegisterPilotInfoRequest();
        oacRegisterPilotInfoRequest.setPilotSourceId(pilotInfo.getId().toString());
        oacRegisterPilotInfoRequest.setPilotName(pilotInfo.getPilotName());
        oacRegisterPilotInfoRequest.setPilotType(pilotInfo.getPilotType());
        oacRegisterPilotInfoRequest.setLicenseType(pilotInfo.getLicenseType());
        oacRegisterPilotInfoRequest.setLicenseId(pilotInfo.getLicenseId());
        oacRegisterPilotInfoRequest.setLicensePictureAddress(pilotInfo.getLicensePictureAddress());
        oacRegisterPilotInfoRequest.setIdCardType(pilotInfo.getIdCardType());
        oacRegisterPilotInfoRequest.setIdCardNumber(pilotInfo.getIdCardNumber());
        oacRegisterPilotInfoRequest.setIdCardPictureAddress(pilotInfo.getIdCardPictureAddress());
        oacRegisterPilotInfoRequest.setGender(pilotInfo.getGender());
        oacRegisterPilotInfoRequest.setNationality(pilotInfo.getNationality());
        oacRegisterPilotInfoRequest.setPhoneNumber(pilotInfo.getPhoneNumber());
        oacRegisterPilotInfoRequest.setEmailAddress(pilotInfo.getEmailAddress());
        return oacRegisterPilotInfoRequest;
    }

    RegisterPilotInfoResponse buildOacRegisterPilotInfoResponse(com.htfp.service.oac.client.response.RegisterPilotInfoResponse oacRegisterPilotInfoResponse) {
        RegisterPilotInfoResponse registerPilotInfoResponse = new RegisterPilotInfoResponse();
        registerPilotInfoResponse.setSuccess(oacRegisterPilotInfoResponse.getSuccess());
        registerPilotInfoResponse.setCode(oacRegisterPilotInfoResponse.getCode());
        registerPilotInfoResponse.setMessage(oacRegisterPilotInfoResponse.getMessage());
        registerPilotInfoResponse.setPilotUniId(oacRegisterPilotInfoResponse.getPilotUniId());
        return registerPilotInfoResponse;
    }

    /**
     * 注册运营主体信息
     *
     * @param registerOperatorInfoRequest
     * @return
     */
    @Override
    public RegisterOperatorInfoResponse registerOperatorInfo(RegisterOperatorInfoRequest registerOperatorInfoRequest) {
        RegisterOperatorInfoResponse registerOperatorInfoResponse = new RegisterOperatorInfoResponse();
        registerOperatorInfoResponse.fail();
        try {
            Long operatorId = Long.valueOf(registerOperatorInfoRequest.getOperatorId());
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(operatorId);
            if (queryOperatorInfo != null) {
                StaticInfoStatusEnum operatorStatus = StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus());
                if (StaticInfoStatusEnum.TYPE_IN.equals(operatorStatus) || StaticInfoStatusEnum.CANCELED.equals(operatorStatus)) {
                    operatorDalService.updateOperatorInfoStatus(queryOperatorInfo, StaticInfoStatusEnum.REGISTERING.getCode());
                    com.htfp.service.oac.client.request.RegisterOperatorInfoRequest oacRegisterOperatorInfoRequest = buildOacRegisterOperatorInfoRequest(queryOperatorInfo);
                    com.htfp.service.oac.client.response.RegisterOperatorInfoResponse oacRegisterOperatorInfoResponse = basicInfoService.registerOperatorInfo(oacRegisterOperatorInfoRequest);
                    registerOperatorInfoResponse = buildOacRegisterOperatorInfoResponse(oacRegisterOperatorInfoResponse);
                    if (registerOperatorInfoResponse.getSuccess() && StringUtils.isNotBlank(registerOperatorInfoResponse.getOperatorUniId())) {
                        operatorDalService.updateOperatorInfoUniIdAndStatus(queryOperatorInfo, registerOperatorInfoResponse.getOperatorUniId(), StaticInfoStatusEnum.REGISTERED.getCode());
                    }
                } else {
                    registerOperatorInfoResponse.fail("operator已注册或正在注册/注销");
                }
            } else {
                registerOperatorInfoResponse.fail("无此operator数据");
            }
        } catch (Exception e) {
            log.error("注册operator异常, registerOperatorInfoRequest={}", registerOperatorInfoRequest, e);
            registerOperatorInfoResponse.fail("注册operator异常");
        }
        return registerOperatorInfoResponse;
    }

    com.htfp.service.oac.client.request.RegisterOperatorInfoRequest buildOacRegisterOperatorInfoRequest(OperatorInfoDO operatorInfo) {
        com.htfp.service.oac.client.request.RegisterOperatorInfoRequest oacRegisterPilotInfoRequest = new com.htfp.service.oac.client.request.RegisterOperatorInfoRequest();
        oacRegisterPilotInfoRequest.setOperatorSourceId(operatorInfo.getId().toString());
        oacRegisterPilotInfoRequest.setOperatorName(operatorInfo.getOperatorName());
        oacRegisterPilotInfoRequest.setOperatorType(operatorInfo.getOperatorType());
        oacRegisterPilotInfoRequest.setIdCardType(operatorInfo.getIdCardType());
        oacRegisterPilotInfoRequest.setIdCardNumber(operatorInfo.getIdCardNumber());
        oacRegisterPilotInfoRequest.setIdCardPictureAddress(operatorInfo.getIdCardPictureAddress());
        oacRegisterPilotInfoRequest.setCompanyName(operatorInfo.getCompanyName());
        oacRegisterPilotInfoRequest.setSocialCreditCode(operatorInfo.getSocialCreditCode());
        oacRegisterPilotInfoRequest.setGender(operatorInfo.getGender());
        oacRegisterPilotInfoRequest.setNationality(operatorInfo.getNationality());
        oacRegisterPilotInfoRequest.setCity(operatorInfo.getCity());
        oacRegisterPilotInfoRequest.setAddress(operatorInfo.getAddress());
        oacRegisterPilotInfoRequest.setPhoneNumber(operatorInfo.getPhoneNumber());
        oacRegisterPilotInfoRequest.setEmailAddress(operatorInfo.getEmailAddress());
        return oacRegisterPilotInfoRequest;
    }

    RegisterOperatorInfoResponse buildOacRegisterOperatorInfoResponse(com.htfp.service.oac.client.response.RegisterOperatorInfoResponse oacRegisterOperatorInfoResponse) {
        RegisterOperatorInfoResponse registerOperatorInfoResponse = new RegisterOperatorInfoResponse();
        registerOperatorInfoResponse.setSuccess(oacRegisterOperatorInfoResponse.getSuccess());
        registerOperatorInfoResponse.setCode(oacRegisterOperatorInfoResponse.getCode());
        registerOperatorInfoResponse.setMessage(oacRegisterOperatorInfoResponse.getMessage());
        registerOperatorInfoResponse.setOperatorUniId(oacRegisterOperatorInfoResponse.getOperatorUniId());
        return registerOperatorInfoResponse;
    }

    /**
     * 注册无人机信息
     *
     * @param registerUavInfoRequest
     * @return
     */
    @Override
    public RegisterUavInfoResponse registerUavInfo(RegisterUavInfoRequest registerUavInfoRequest) {
        RegisterUavInfoResponse registerUavInfoResponse = new RegisterUavInfoResponse();
        registerUavInfoResponse.fail();
        try {
            Long uavId = Long.valueOf(registerUavInfoRequest.getUavId());
            UavInfoDO queryUavInfo = uavDalService.queryUavInfo(uavId);
            if (queryUavInfo != null) {
                OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(queryUavInfo.getOperatorId());
                StaticInfoStatusEnum operatorStatus = StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(operatorStatus)) {
                    StaticInfoStatusEnum uavStatus = StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus());
                    if (StaticInfoStatusEnum.TYPE_IN.equals(uavStatus) || StaticInfoStatusEnum.CANCELED.equals(uavStatus)) {
                        uavDalService.updateUavInfoStatus(queryUavInfo, StaticInfoStatusEnum.REGISTERING.getCode());
                        com.htfp.service.oac.client.request.RegisterUavInfoRequest oacRegisterUavInfoRequest = buildOacRegisterUavInfoRequest(queryUavInfo, queryOperatorInfo.getOperatorUniId());
                        com.htfp.service.oac.client.response.RegisterUavInfoResponse oacRegisterUavInfoResponse = basicInfoService.registerUavInfo(oacRegisterUavInfoRequest);
                        registerUavInfoResponse = buildOacRegisterUavInfoResponse(oacRegisterUavInfoResponse);
                        // 注册成功更新cpn编号以及状态
                        if (registerUavInfoResponse.getSuccess() && StringUtils.isNotBlank(registerUavInfoResponse.getCpn())) {
                            uavDalService.updateUavInfoCpnAndStatus(queryUavInfo, registerUavInfoResponse.getCpn(), StaticInfoStatusEnum.REGISTERED.getCode());
                        }
                    } else {
                        registerUavInfoResponse.fail("uav已注册或正在注册/正在注销，注册失败");
                    }
                } else {
                    registerUavInfoResponse.fail("运营主体未完成注册，uav注册失败");
                }
            } else {
                registerUavInfoResponse.fail("无此uav数据");
            }
        } catch (Exception e) {
            log.error("注册uav异常, registerUavInfoRequest={}", registerUavInfoRequest, e);
            registerUavInfoResponse.fail("注册uav异常");
        }
        return registerUavInfoResponse;
    }

    com.htfp.service.oac.client.request.RegisterUavInfoRequest buildOacRegisterUavInfoRequest(UavInfoDO uavInfo, String operatorUniId) {
        com.htfp.service.oac.client.request.RegisterUavInfoRequest oacRegisterUavInfoRequest = new com.htfp.service.oac.client.request.RegisterUavInfoRequest();
        oacRegisterUavInfoRequest.setUavSourceId(uavInfo.getId().toString());
        oacRegisterUavInfoRequest.setUavReg(uavInfo.getUavReg());
        oacRegisterUavInfoRequest.setUavName(uavInfo.getUavName());
        oacRegisterUavInfoRequest.setVin(uavInfo.getVin());
        oacRegisterUavInfoRequest.setPvin(uavInfo.getPvin());
        oacRegisterUavInfoRequest.setSn(uavInfo.getSn());
        oacRegisterUavInfoRequest.setFlightControlSn(uavInfo.getFlightControlSn());
        oacRegisterUavInfoRequest.setImei(uavInfo.getImei());
        oacRegisterUavInfoRequest.setImsi(uavInfo.getImsi());
        oacRegisterUavInfoRequest.setManufacturerName(uavInfo.getManufacturerName());
        oacRegisterUavInfoRequest.setProductName(uavInfo.getProductName());
        oacRegisterUavInfoRequest.setProductType(uavInfo.getProductType());
        oacRegisterUavInfoRequest.setProductSizeType(uavInfo.getProductSizeType());
        oacRegisterUavInfoRequest.setMaxFlyTime(uavInfo.getMaxFlyTime());
        oacRegisterUavInfoRequest.setOperationScenarioType(uavInfo.getOperationScenarioType());
        oacRegisterUavInfoRequest.setOperatorUniId(operatorUniId);
        return oacRegisterUavInfoRequest;
    }

    RegisterUavInfoResponse buildOacRegisterUavInfoResponse(com.htfp.service.oac.client.response.RegisterUavInfoResponse oacRegisterUavInfoResponse) {
        RegisterUavInfoResponse registerUavInfoResponse = new RegisterUavInfoResponse();
        registerUavInfoResponse.setSuccess(oacRegisterUavInfoResponse.getSuccess());
        registerUavInfoResponse.setCode(oacRegisterUavInfoResponse.getCode());
        registerUavInfoResponse.setMessage(oacRegisterUavInfoResponse.getMessage());
        registerUavInfoResponse.setCpn(oacRegisterUavInfoResponse.getCpn());
        return registerUavInfoResponse;
    }

    /**
     * 注销驾驶员信息
     *
     * @param cancelPilotInfoRequest
     * @return
     */
    @Override
    public CancelPilotInfoResponse cancelPilotInfo(CancelPilotInfoRequest cancelPilotInfoRequest) {
        CancelPilotInfoResponse cancelPilotInfoResponse = new CancelPilotInfoResponse();
        cancelPilotInfoResponse.fail();
        try {
            Long pilotId = Long.valueOf(cancelPilotInfoRequest.getPilotId());
            PilotInfoDO queryPilotInfo = pilotDalService.queryPilotInfo(pilotId);
            if (queryPilotInfo != null) {
                StaticInfoStatusEnum pilotStatus = StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(pilotStatus)) {
                    pilotDalService.updatePilotInfoStatus(queryPilotInfo, StaticInfoStatusEnum.CANCELING.getCode());
                    com.htfp.service.oac.client.request.CancelPilotInfoRequest oacCancelPilotInfoRequest = buildOacCancelPilotInfoRequest(queryPilotInfo);
                    com.htfp.service.oac.client.response.CancelPilotInfoResponse oacCancelPilotInfoResponse = basicInfoService.cancelPilotInfo(oacCancelPilotInfoRequest);
                    cancelPilotInfoResponse = buildOacCancelPilotInfoResponse(oacCancelPilotInfoResponse);
                    if (cancelPilotInfoResponse.getSuccess()) {
                        pilotDalService.updatePilotInfoStatus(queryPilotInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    }
                } else {
                    cancelPilotInfoResponse.fail("pilot未完成注册");
                }
            } else {
                cancelPilotInfoResponse.fail("无此pilot数据");
            }
        } catch (Exception e) {
            log.error("注销pilot异常, cancelPilotInfoRequest={}", cancelPilotInfoRequest, e);
            cancelPilotInfoResponse.fail("注销pilot异常");
        }
        return cancelPilotInfoResponse;
    }

    com.htfp.service.oac.client.request.CancelPilotInfoRequest buildOacCancelPilotInfoRequest(PilotInfoDO pilotInfo) {
        com.htfp.service.oac.client.request.CancelPilotInfoRequest oacCancelPilotInfoRequest = new com.htfp.service.oac.client.request.CancelPilotInfoRequest();
        oacCancelPilotInfoRequest.setPilotUniId(pilotInfo.getPilotUniId());
        return oacCancelPilotInfoRequest;
    }

    CancelPilotInfoResponse buildOacCancelPilotInfoResponse(com.htfp.service.oac.client.response.CancelPilotInfoResponse oacCancelPilotInfoResponse) {
        CancelPilotInfoResponse cancelPilotInfoResponse = new CancelPilotInfoResponse();
        cancelPilotInfoResponse.setSuccess(oacCancelPilotInfoResponse.getSuccess());
        cancelPilotInfoResponse.setCode(oacCancelPilotInfoResponse.getCode());
        cancelPilotInfoResponse.setMessage(oacCancelPilotInfoResponse.getMessage());
        return cancelPilotInfoResponse;
    }

    /**
     * 注销运营主体信息
     *
     * @param cancelOperatorInfoRequest
     * @return
     */
    @Override
    public CancelOperatorInfoResponse cancelOperatorInfo(CancelOperatorInfoRequest cancelOperatorInfoRequest) {
        CancelOperatorInfoResponse cancelOperatorInfoResponse = new CancelOperatorInfoResponse();
        cancelOperatorInfoResponse.fail();
        try {
            Long operatorId = Long.valueOf(cancelOperatorInfoRequest.getOperatorId());
            OperatorInfoDO queryOperatorInfo = operatorDalService.queryOperatorInfo(operatorId);
            if (queryOperatorInfo != null) {
                List<UavInfoDO> queryUavInfoList = uavDalService.queryUavInfoByOperatorId(operatorId);
                if (queryUavInfoList != null) {
                    for (UavInfoDO uavInfoDO : queryUavInfoList) {
                        if (StaticInfoStatusEnum.REGISTERING.equals(StaticInfoStatusEnum.getFromCode(uavInfoDO.getStatus())) ||
                                StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(uavInfoDO.getStatus())) ||
                                StaticInfoStatusEnum.CANCELING.equals(StaticInfoStatusEnum.getFromCode(uavInfoDO.getStatus()))) {
                            cancelOperatorInfoResponse.fail("运营主体下还有uav处于注册中/已注册/注销中的状态，注销operator失败");
                            return cancelOperatorInfoResponse;
                        }
                    }
                }
                StaticInfoStatusEnum operatorStatus = StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(operatorStatus)) {
                    operatorDalService.updateOperatorInfoStatus(queryOperatorInfo, StaticInfoStatusEnum.CANCELING.getCode());
                    com.htfp.service.oac.client.request.CancelOperatorInfoRequest oacCancelOperatorInfoRequest = buildOacCancelOperatorInfoRequest(queryOperatorInfo);
                    com.htfp.service.oac.client.response.CancelOperatorInfoResponse oacCancelOperatorInfoResponse = basicInfoService.cancelOperatorInfo(oacCancelOperatorInfoRequest);
                    cancelOperatorInfoResponse = buildOacCancelOperatorInfoResponse(oacCancelOperatorInfoResponse);
                    if (cancelOperatorInfoResponse.getSuccess()) {
                        operatorDalService.updateOperatorInfoStatus(queryOperatorInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    }
                } else {
                    cancelOperatorInfoResponse.fail("operator未完成注册");
                }
            } else {
                cancelOperatorInfoResponse.fail("无此operator数据");
            }
        } catch (Exception e) {
            log.error("注销operator异常, cancelOperatorInfoRequest={}", cancelOperatorInfoRequest, e);
            cancelOperatorInfoResponse.fail("注销operator异常");
        }
        return cancelOperatorInfoResponse;
    }

    com.htfp.service.oac.client.request.CancelOperatorInfoRequest buildOacCancelOperatorInfoRequest(OperatorInfoDO queryOperatorInfo) {
        com.htfp.service.oac.client.request.CancelOperatorInfoRequest oacCancelOperatorInfoRequest = new com.htfp.service.oac.client.request.CancelOperatorInfoRequest();
        oacCancelOperatorInfoRequest.setOperatorUniId(queryOperatorInfo.getOperatorUniId());
        return oacCancelOperatorInfoRequest;
    }

    CancelOperatorInfoResponse buildOacCancelOperatorInfoResponse(com.htfp.service.oac.client.response.CancelOperatorInfoResponse oacCancelOperatorInfoResponse) {
        CancelOperatorInfoResponse cancelOperatorInfoResponse = new CancelOperatorInfoResponse();
        cancelOperatorInfoResponse.setSuccess(oacCancelOperatorInfoResponse.getSuccess());
        cancelOperatorInfoResponse.setCode(oacCancelOperatorInfoResponse.getCode());
        cancelOperatorInfoResponse.setMessage(oacCancelOperatorInfoResponse.getMessage());
        return cancelOperatorInfoResponse;
    }

    /**
     * 注销无人机信息
     *
     * @param cancelUavInfoRequest
     * @return
     */
    @Override
    public CancelUavInfoResponse cancelUavInfo(CancelUavInfoRequest cancelUavInfoRequest) {
        CancelUavInfoResponse cancelUavInfoResponse = new CancelUavInfoResponse();
        cancelUavInfoResponse.fail();
        try {
            Long uavId = Long.valueOf(cancelUavInfoRequest.getUavId());
            UavInfoDO queryUavInfo = uavDalService.queryUavInfo(uavId);
            if (queryUavInfo != null) {
                StaticInfoStatusEnum uavStatus = StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(uavStatus)) {
                    uavDalService.updateUavInfoStatus(queryUavInfo, StaticInfoStatusEnum.CANCELING.getCode());
                    com.htfp.service.oac.client.request.CancelUavInfoRequest oacCancelUavInfoRequest = buildOacCancelUavInfoRequest(queryUavInfo);
                    com.htfp.service.oac.client.response.CancelUavInfoResponse oacCancelUavInfoResponse = basicInfoService.cancelUavInfo(oacCancelUavInfoRequest);
                    cancelUavInfoResponse = buildOacCancelUavInfoResponse(oacCancelUavInfoResponse);
                    if (cancelUavInfoResponse.getSuccess()) {
                        uavDalService.updateUavInfoStatus(queryUavInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    }
                } else {
                    cancelUavInfoResponse.fail("uav未完成注册");
                }
            } else {
                cancelUavInfoResponse.fail("无此uav数据");
            }
        } catch (Exception e) {
            log.error("注销uav异常, cancelUavInfoRequest={}", cancelUavInfoRequest, e);
            cancelUavInfoResponse.fail("注销uav异常");
        }
        return cancelUavInfoResponse;
    }

    com.htfp.service.oac.client.request.CancelUavInfoRequest buildOacCancelUavInfoRequest(UavInfoDO queryUavInfo) {
        com.htfp.service.oac.client.request.CancelUavInfoRequest oacCancelUavInfoRequest = new com.htfp.service.oac.client.request.CancelUavInfoRequest();
        oacCancelUavInfoRequest.setCpn(queryUavInfo.getCpn());
        return oacCancelUavInfoRequest;
    }

    CancelUavInfoResponse buildOacCancelUavInfoResponse(com.htfp.service.oac.client.response.CancelUavInfoResponse oacCancelUavInfoResponse) {
        CancelUavInfoResponse cancelUavInfoResponse = new CancelUavInfoResponse();
        cancelUavInfoResponse.setSuccess(oacCancelUavInfoResponse.getSuccess());
        cancelUavInfoResponse.setCode(oacCancelUavInfoResponse.getCode());
        cancelUavInfoResponse.setMessage(oacCancelUavInfoResponse.getMessage());
        return cancelUavInfoResponse;
    }
}
