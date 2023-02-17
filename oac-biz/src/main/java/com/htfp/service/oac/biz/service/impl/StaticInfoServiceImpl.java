package com.htfp.service.oac.biz.service.impl;

import com.htfp.service.oac.biz.service.IStaticInfoService;
import com.htfp.service.oac.common.enums.StaticInfoStatusEnum;
import com.htfp.service.oac.common.enums.UavProductSizeTypeEnum;
import com.htfp.service.oac.common.enums.UavProductTypeEnum;
import com.htfp.service.oac.biz.model.inner.request.CancelOperatorInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.CancelPilotInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.CancelUavInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.DeleteOperatorInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.DeletePilotInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.DeleteUavInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.RegisterOperatorInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.RegisterPilotInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.RegisterUavInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.UpdateOperatorInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.UpdatePilotInfoRequest;
import com.htfp.service.oac.biz.model.inner.request.UpdateUavInfoRequest;
import com.htfp.service.oac.biz.model.inner.response.CancelOperatorInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.CancelPilotInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.CancelUavInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.DeleteOperatorInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.DeletePilotInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.DeleteUavInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.RegisterOperatorInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.RegisterPilotInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.RegisterUavInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.UpdateOperatorInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.UpdatePilotInfoResponse;
import com.htfp.service.oac.biz.model.inner.response.UpdateUavInfoResponse;
import com.htfp.service.cac.dao.model.oac.OperatorInfoDO;
import com.htfp.service.cac.dao.model.oac.PilotInfoDO;
import com.htfp.service.cac.dao.model.oac.UavInfoDO;
import com.htfp.service.cac.dao.service.oac.OacOperatorDalService;
import com.htfp.service.cac.dao.service.oac.OacPilotDalService;
import com.htfp.service.cac.dao.service.oac.OacUavDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022/12/18
 * @Description 描述
 */
@Slf4j
@Service("staticInfoServiceImpl")
public class StaticInfoServiceImpl implements IStaticInfoService {

    public final static Integer ONE_THOUSAND = 1000;
    public final static Integer ONE_MILLION = 1000000;

    @Resource
    private OacPilotDalService oacPilotDalService;

    @Resource
    private OacOperatorDalService oacOperatorDalService;

    @Resource
    private OacUavDalService oacUavDalService;

    /**
     * 修改驾驶员信息
     *
     * @param updatePilotInfoRequest
     * @return
     */
    @Override
    public UpdatePilotInfoResponse updatePilotInfo(UpdatePilotInfoRequest updatePilotInfoRequest) {
        UpdatePilotInfoResponse updatePilotInfoResponse = new UpdatePilotInfoResponse();
        updatePilotInfoResponse.fail();
        PilotInfoDO queryPilotInfo = oacPilotDalService.queryPilotInfoByPilotUniId(updatePilotInfoRequest.getPilotUniId());
        if (queryPilotInfo != null) {
            if (StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus()))) {
                String pilotUniId = generateSubjectUniqueId(updatePilotInfoRequest.getIdCardType(), updatePilotInfoRequest.getIdCardNumber());
                if (queryPilotInfo.getPilotUniId().equals(pilotUniId)) {
                    PilotInfoDO pilotInfo = oacPilotDalService.buildPilotInfoDO(updatePilotInfoRequest.getPilotSourceId(), updatePilotInfoRequest.getPilotUniId(), updatePilotInfoRequest.getPilotName(), updatePilotInfoRequest.getPilotType(), updatePilotInfoRequest.getLicenseType(), updatePilotInfoRequest.getLicenseId(), updatePilotInfoRequest.getLicensePictureAddress(), updatePilotInfoRequest.getIdCardType(), updatePilotInfoRequest.getIdCardNumber(), updatePilotInfoRequest.getIdCardPictureAddress(), updatePilotInfoRequest.getGender(), updatePilotInfoRequest.getNationality(), updatePilotInfoRequest.getPhoneNumber(), updatePilotInfoRequest.getEmailAddress(), null);
                    pilotInfo.setId(queryPilotInfo.getId());
                    int id = oacPilotDalService.updatePilotInfo(pilotInfo);
                    if (id > 0) {
                        updatePilotInfoResponse.success();
                    } else {
                        updatePilotInfoResponse.fail("更新pilot数据失败");
                    }
                } else {
                    updatePilotInfoResponse.fail("pilot身份信息不同，建议新增pilot数据");
                }
            } else {
                updatePilotInfoResponse.fail("pilot数据未处于已注册状态，暂停更新");
            }
        } else {
            updatePilotInfoResponse.fail("无此pilot数据");
        }
        return updatePilotInfoResponse;
    }

    /**
     * 修改运营主体信息
     *
     * @param updateOperatorInfoRequest
     * @return
     */
    @Override
    public UpdateOperatorInfoResponse updateOperatorInfo(UpdateOperatorInfoRequest updateOperatorInfoRequest) {
        UpdateOperatorInfoResponse updateOperatorInfoResponse = new UpdateOperatorInfoResponse();
        updateOperatorInfoResponse.fail();
        OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(updateOperatorInfoRequest.getOperatorUniId());
        if (queryOperatorInfo != null) {
            if (StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus()))) {
                String operatorUniId = generateSubjectUniqueId(updateOperatorInfoRequest.getIdCardType(), updateOperatorInfoRequest.getIdCardNumber());
                if (queryOperatorInfo.getOperatorUniId().equals(operatorUniId)) {
                    OperatorInfoDO operatorInfo = oacOperatorDalService.buildOperatorInfoDO(updateOperatorInfoRequest.getOperatorSourceId(), updateOperatorInfoRequest.getOperatorUniId(), updateOperatorInfoRequest.getOperatorName(), updateOperatorInfoRequest.getOperatorType(), updateOperatorInfoRequest.getIdCardType(), updateOperatorInfoRequest.getIdCardNumber(), updateOperatorInfoRequest.getIdCardPictureAddress(), updateOperatorInfoRequest.getCompanyName(), updateOperatorInfoRequest.getSocialCreditCode(), updateOperatorInfoRequest.getGender(), updateOperatorInfoRequest.getNationality(), updateOperatorInfoRequest.getCity(), updateOperatorInfoRequest.getAddress(), updateOperatorInfoRequest.getPhoneNumber(), updateOperatorInfoRequest.getEmailAddress(), null);
                    operatorInfo.setId(queryOperatorInfo.getId());
                    int id = oacOperatorDalService.updateOperatorInfo(operatorInfo);
                    if (id > 0) {
                        updateOperatorInfoResponse.success();
                    } else {
                        updateOperatorInfoResponse.fail("更新operator数据失败");
                    }
                } else {
                    updateOperatorInfoResponse.fail("operator身份信息不同，建议新增operator数据");
                }
            } else {
                updateOperatorInfoResponse.fail("operator数据未处于已注册状态，暂停更新");
            }
        } else {
            updateOperatorInfoResponse.fail("无此operator数据");
        }
        return updateOperatorInfoResponse;
    }

    /**
     * 修改无人机信息
     *
     * @param updateUavInfoRequest
     * @return
     */
    @Override
    public UpdateUavInfoResponse updateUavInfo(UpdateUavInfoRequest updateUavInfoRequest) {
        UpdateUavInfoResponse updateUavInfoResponse = new UpdateUavInfoResponse();
        updateUavInfoResponse.fail();
        UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(updateUavInfoRequest.getCpn());
        if (queryUavInfo != null) {
            if (StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus()))) {
                // 是否需要生成新的CPN
                if (!queryUavInfo.getOperatorUniId().equals(updateUavInfoRequest.getOperatorUniId()) ||
                        !queryUavInfo.getProductSizeType().equals(updateUavInfoRequest.getProductSizeType()) ||
                        !queryUavInfo.getProductType().equals(updateUavInfoRequest.getProductType())) {
                    String newCpn = generateNewCpn(queryUavInfo.getCpn(), updateUavInfoRequest.getOperatorUniId(), updateUavInfoRequest.getProductSizeType(), updateUavInfoRequest.getProductType());
                    updateUavInfoRequest.setCpn(newCpn);
                    updateUavInfoResponse.setCpn(newCpn);
                }
                UavInfoDO uavInfo = oacUavDalService.buildUavInfoDO(updateUavInfoRequest.getUavSourceId(), updateUavInfoRequest.getUavReg(), updateUavInfoRequest.getUavName(), updateUavInfoRequest.getCpn(), updateUavInfoRequest.getVin(), updateUavInfoRequest.getPvin(), updateUavInfoRequest.getSn(), updateUavInfoRequest.getFlightControlSn(), updateUavInfoRequest.getImei(), updateUavInfoRequest.getImsi(), updateUavInfoRequest.getManufacturerName(), updateUavInfoRequest.getProductName(), updateUavInfoRequest.getProductType(), updateUavInfoRequest.getProductSizeType(), updateUavInfoRequest.getMaxFlyTime(), updateUavInfoRequest.getOperationScenarioType(), updateUavInfoRequest.getOperatorUniId(), null);
                uavInfo.setId(queryUavInfo.getId());
                int id = oacUavDalService.updateUavInfo(uavInfo);
                if (id > 0) {
                    updateUavInfoResponse.success();
                } else {
                    updateUavInfoResponse.fail("更新uav数据失败");
                }
            } else {
                updateUavInfoResponse.fail("uav数据未处于已注册状态，暂停更新");
            }
        } else {
            updateUavInfoResponse.fail("无此uav数据");
        }
        return updateUavInfoResponse;
    }

    // TODO: 2022/12/20 待优化
    String generateNewCpn(String oldCpn, String operatorUniId, Integer productSizeType, Integer productType) {
        OperatorInfoDO operatorInfoDO = oacOperatorDalService.queryOperatorInfoByOperatorUniId(operatorUniId);
        String operatorNum = String.format("%03d", operatorInfoDO.getId().intValue() % ONE_THOUSAND);
        String uavNum = oldCpn.substring(oldCpn.length() - 7);
        return operatorNum + UavProductSizeTypeEnum.getFromCode(productSizeType).getType().toString() + UavProductTypeEnum.getFromCode(productType).getType() + "#" + uavNum;
    }

    // TODO: 2022/12/20 待优化
    String generateCpn(String operatorUniId, Long operatorId, Integer productSizeType, Integer productType) {
        String operatorNum = String.format("%03d", operatorId.intValue() % ONE_THOUSAND);
        Long uavCount = oacUavDalService.queryUavCountByOperatorUniIdIncludeDel(operatorUniId);
        String uavNum = String.format("%07d", (uavCount.intValue() + 1) % ONE_MILLION);
        return operatorNum + UavProductSizeTypeEnum.getFromCode(productSizeType).getType().toString() + UavProductTypeEnum.getFromCode(productType).getType() + "#" + uavNum;
    }


    private String generateSubjectUniqueId(Integer idCardType, String idCardNumber) {
        return idCardType.toString() + "#" + idCardNumber;
    }

    /**
     * 删除驾驶员信息
     *
     * @param deletePilotInfoRequest
     * @return
     */
    @Override
    public DeletePilotInfoResponse deletePilotInfo(DeletePilotInfoRequest deletePilotInfoRequest) {
        DeletePilotInfoResponse deletePilotInfoResponse = new DeletePilotInfoResponse();
        deletePilotInfoResponse.fail();
        try {
            PilotInfoDO queryPilotInfo = oacPilotDalService.queryPilotInfoByPilotUniId(deletePilotInfoRequest.getPilotUniId());
            if (queryPilotInfo != null) {
                int id = oacPilotDalService.deletePilotInfoById(queryPilotInfo.getId());
                if (id > 0) {
                    deletePilotInfoResponse.success();
                } else {
                    deletePilotInfoResponse.fail("删除pilot数据失败");
                }
            } else {
                deletePilotInfoResponse.fail("pilot数据已删除无需重复删除");
            }
        } catch (Exception e) {
            log.error("删除pilot信息异常, pilotUniId={}", deletePilotInfoRequest.getPilotUniId(), e);
            deletePilotInfoResponse.fail("删除pilot数据失败");
        }
        return deletePilotInfoResponse;
    }

    /**
     * 删除运营主体信息
     *
     * @param deleteOperatorInfoRequest
     * @return
     */
    @Override
    public DeleteOperatorInfoResponse deleteOperatorInfo(DeleteOperatorInfoRequest deleteOperatorInfoRequest) {
        DeleteOperatorInfoResponse deleteOperatorInfoResponse = new DeleteOperatorInfoResponse();
        deleteOperatorInfoResponse.fail();
        try {
            List<UavInfoDO> queryUavInfoList = oacUavDalService.queryUavInfoByOperatorUniId(deleteOperatorInfoRequest.getOperatorUniId());
            if (queryUavInfoList == null) {
                OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(deleteOperatorInfoRequest.getOperatorUniId());
                if (queryOperatorInfo != null) {
                    int id = oacOperatorDalService.deleteOperatorInfo(queryOperatorInfo.getId());
                    if (id > 0) {
                        deleteOperatorInfoResponse.success();
                    } else {
                        deleteOperatorInfoResponse.fail("删除operator数据失败");
                    }
                } else {
                    deleteOperatorInfoResponse.fail("operator数据已删除,无需重复删除");
                }
            } else {
                deleteOperatorInfoResponse.fail("operator下存在uav，删除失败");
            }
        } catch (Exception e) {
            log.error("删除operator信息异常, operatorUniId={}", deleteOperatorInfoRequest.getOperatorUniId(), e);
            deleteOperatorInfoResponse.fail("删除operator数据失败");
        }
        return deleteOperatorInfoResponse;
    }

    /**
     * 删除无人机信息
     *
     * @param deleteUavInfoRequest
     * @return
     */
    @Override
    public DeleteUavInfoResponse deleteUavInfo(DeleteUavInfoRequest deleteUavInfoRequest) {
        DeleteUavInfoResponse deleteUavInfoResponse = new DeleteUavInfoResponse();
        deleteUavInfoResponse.fail();
        try {
            UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(deleteUavInfoRequest.getCpn());
            if (queryUavInfo != null) {
                int id = oacUavDalService.deleteUavInfoById(queryUavInfo.getId());
                if (id > 0) {
                    deleteUavInfoResponse.success();
                } else {
                    deleteUavInfoResponse.fail("删除uav数据失败");
                }
            } else {
                deleteUavInfoResponse.fail("uav数据已删除无需重复删除");
            }
        } catch (Exception e) {
            log.error("删除uav信息异常, cpn={}", deleteUavInfoRequest.getCpn(), e);
            deleteUavInfoResponse.fail("删除uav数据失败");
        }
        return deleteUavInfoResponse;
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
            String pilotUniId = generateSubjectUniqueId(registerPilotInfoRequest.getIdCardType(), registerPilotInfoRequest.getIdCardNumber());
            PilotInfoDO queryPilotInfo = oacPilotDalService.queryPilotInfoByPilotUniId(pilotUniId);
            if (queryPilotInfo == null) {
                // 默认注册成功，外部接口确定后，增加请求外部接口流程
                PilotInfoDO pilotInfo = oacPilotDalService.buildPilotInfoDO(registerPilotInfoRequest.getPilotSourceId(), pilotUniId, registerPilotInfoRequest.getPilotName(), registerPilotInfoRequest.getPilotType(), registerPilotInfoRequest.getLicenseType(), registerPilotInfoRequest.getLicenseId(), registerPilotInfoRequest.getLicensePictureAddress(), registerPilotInfoRequest.getIdCardType(), registerPilotInfoRequest.getIdCardNumber(), registerPilotInfoRequest.getIdCardPictureAddress(), registerPilotInfoRequest.getGender(), registerPilotInfoRequest.getNationality(), registerPilotInfoRequest.getPhoneNumber(), registerPilotInfoRequest.getEmailAddress(), StaticInfoStatusEnum.REGISTERED.getCode());
                int id = oacPilotDalService.insertPilotInfo(pilotInfo);
                if (id > 0) {
                    registerPilotInfoResponse.setPilotUniId(pilotUniId);
                    registerPilotInfoResponse.success();
                } else {
                    registerPilotInfoResponse.fail("pilot注册失败");
                }
            } else {
                // 已注销的可以重新注册
                if (StaticInfoStatusEnum.CANCELED.equals(StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus()))) {
                    int id = oacPilotDalService.updatePilotInfoStatus(queryPilotInfo, StaticInfoStatusEnum.REGISTERED.getCode());
                    if (id > 0) {
                        registerPilotInfoResponse.setPilotUniId(pilotUniId);
                        registerPilotInfoResponse.success();
                    } else {
                        registerPilotInfoResponse.fail("pilot注册失败");
                    }
                } else {
                    registerPilotInfoResponse.fail("pilot注册失败，数据已存在且未处于注销状态");
                }
            }
        } catch (Exception e) {
            log.error("注册pilot异常, registerPilotInfoRequest={}", registerPilotInfoRequest, e);
            registerPilotInfoResponse.fail("注册pilot异常");
        }
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
            String operatorUniId = generateSubjectUniqueId(registerOperatorInfoRequest.getIdCardType(), registerOperatorInfoRequest.getIdCardNumber());
            OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(operatorUniId);
            if (queryOperatorInfo == null) {
                // 默认注册成功，外部接口确定后，增加请求外部接口流程
                OperatorInfoDO operatorInfo = oacOperatorDalService.buildOperatorInfoDO(registerOperatorInfoRequest.getOperatorSourceId(), operatorUniId, registerOperatorInfoRequest.getOperatorName(), registerOperatorInfoRequest.getOperatorType(), registerOperatorInfoRequest.getIdCardType(), registerOperatorInfoRequest.getIdCardNumber(), registerOperatorInfoRequest.getIdCardPictureAddress(), registerOperatorInfoRequest.getCompanyName(), registerOperatorInfoRequest.getSocialCreditCode(), registerOperatorInfoRequest.getGender(), registerOperatorInfoRequest.getNationality(), registerOperatorInfoRequest.getCity(), registerOperatorInfoRequest.getAddress(), registerOperatorInfoRequest.getPhoneNumber(), registerOperatorInfoRequest.getEmailAddress(), StaticInfoStatusEnum.REGISTERED.getCode());
                int id = oacOperatorDalService.insertOperatorInfo(operatorInfo);
                if (id > 0) {
                    registerOperatorInfoResponse.setOperatorUniId(operatorUniId);
                    registerOperatorInfoResponse.success();
                } else {
                    registerOperatorInfoResponse.fail("operator注册失败");
                }
            } else {
                // 已注销的可以重新注册
                if (StaticInfoStatusEnum.CANCELED.equals(StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus()))) {
                    int id = oacOperatorDalService.updateOperatorInfoStatus(queryOperatorInfo, StaticInfoStatusEnum.REGISTERED.getCode());
                    if (id > 0) {
                        registerOperatorInfoResponse.setOperatorUniId(operatorUniId);
                        registerOperatorInfoResponse.success();
                    } else {
                        registerOperatorInfoResponse.fail("operator注册失败");
                    }
                } else {
                    registerOperatorInfoResponse.fail("operator注册失败，数据已存在且未处于注销状态");
                }
            }
        } catch (Exception e) {
            log.error("注册operator异常, registerOperatorInfoRequest={}", registerOperatorInfoRequest, e);
            registerOperatorInfoResponse.fail("注册operator异常");
        }
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
            OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(registerUavInfoRequest.getOperatorUniId());
            if (queryOperatorInfo != null && StaticInfoStatusEnum.REGISTERED.equals(StaticInfoStatusEnum.getFromCode(queryOperatorInfo.getStatus()))) {
                UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByUavReg(registerUavInfoRequest.getUavReg());
                if (queryUavInfo == null) {
                    String cpn = generateCpn(queryOperatorInfo.getOperatorUniId(), queryOperatorInfo.getId(), registerUavInfoRequest.getProductSizeType(), registerUavInfoRequest.getProductType());
                    // 默认注册成功，外部接口确定后，增加请求外部接口流程
                    UavInfoDO uavInfo = oacUavDalService.buildUavInfoDO(registerUavInfoRequest.getUavSourceId(), registerUavInfoRequest.getUavReg(), registerUavInfoRequest.getUavName(), cpn, registerUavInfoRequest.getVin(), registerUavInfoRequest.getPvin(), registerUavInfoRequest.getSn(), registerUavInfoRequest.getFlightControlSn(), registerUavInfoRequest.getImei(), registerUavInfoRequest.getImsi(), registerUavInfoRequest.getManufacturerName(), registerUavInfoRequest.getProductName(), registerUavInfoRequest.getProductType(), registerUavInfoRequest.getProductSizeType(), registerUavInfoRequest.getMaxFlyTime(), registerUavInfoRequest.getOperationScenarioType(), registerUavInfoRequest.getOperatorUniId(), StaticInfoStatusEnum.REGISTERED.getCode());
                    int id = oacUavDalService.insertUavInfo(uavInfo);
                    if (id > 0) {
                        registerUavInfoResponse.setCpn(cpn);
                        registerUavInfoResponse.success();
                    } else {
                        registerUavInfoResponse.fail("uav注册失败");
                    }
                } else {
                    // 已注销的可以重新注册
                    if (StaticInfoStatusEnum.CANCELED.equals(StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus()))) {
                        int id = oacUavDalService.updateUavInfoStatus(queryUavInfo, StaticInfoStatusEnum.REGISTERED.getCode());
                        if (id > 0) {
                            registerUavInfoResponse.setCpn(queryUavInfo.getCpn());
                            registerUavInfoResponse.success();
                        } else {
                            registerUavInfoResponse.fail("uav注册失败");
                        }
                    } else {
                        registerUavInfoResponse.fail("uav注册失败，数据已存在且未处于注销状态");
                    }
                }
            } else {
                registerUavInfoResponse.fail("运营主体未完成注册，uav注册失败");
            }
        } catch (Exception e) {
            log.error("注册uav异常, registerUavInfoRequest={}", registerUavInfoRequest, e);
            registerUavInfoResponse.fail("注册uav异常");
        }
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
            PilotInfoDO queryPilotInfo = oacPilotDalService.queryPilotInfoByPilotUniId(cancelPilotInfoRequest.getPilotUniId());
            if (queryPilotInfo != null) {
                StaticInfoStatusEnum pilotStatus = StaticInfoStatusEnum.getFromCode(queryPilotInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(pilotStatus)) {
                    // 默认注销成功，外部接口确定后，增加请求外部接口流程
                    int id = oacPilotDalService.updatePilotInfoStatus(queryPilotInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    if (id > 0) {
                        cancelPilotInfoResponse.success();
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

    /**
     * 注销运营人信息
     *
     * @param cancelOperatorInfoRequest
     * @return
     */
    @Override
    public CancelOperatorInfoResponse cancelOperatorInfo(CancelOperatorInfoRequest cancelOperatorInfoRequest) {
        CancelOperatorInfoResponse cancelOperatorInfoResponse = new CancelOperatorInfoResponse();
        cancelOperatorInfoResponse.fail();
        try {
            OperatorInfoDO queryOperatorInfo = oacOperatorDalService.queryOperatorInfoByOperatorUniId(cancelOperatorInfoRequest.getOperatorUniId());
            if (queryOperatorInfo != null) {
                List<UavInfoDO> queryUavInfoList = oacUavDalService.queryUavInfoByOperatorUniId(cancelOperatorInfoRequest.getOperatorUniId());
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
                    // 默认注销成功，外部接口确定后，增加请求外部接口流程
                    int id = oacOperatorDalService.updateOperatorInfoStatus(queryOperatorInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    if (id > 0) {
                        cancelOperatorInfoResponse.success();
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
            UavInfoDO queryUavInfo = oacUavDalService.queryUavInfoByCpn(cancelUavInfoRequest.getCpn());
            if (queryUavInfo != null) {
                StaticInfoStatusEnum uavStatus = StaticInfoStatusEnum.getFromCode(queryUavInfo.getStatus());
                if (StaticInfoStatusEnum.REGISTERED.equals(uavStatus)) {
                    // 默认注销成功，外部接口确定后，增加请求外部接口流程
                    int id = oacUavDalService.updateUavInfoStatus(queryUavInfo, StaticInfoStatusEnum.CANCELED.getCode());
                    if (id > 0) {
                        cancelUavInfoResponse.success();
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
}
