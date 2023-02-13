package com.htfp.service.oac.app.service.impl;

import com.htfp.service.oac.app.service.IBasicInfoService;
import com.htfp.service.oac.biz.service.IStaticInfoService;
import com.htfp.service.oac.common.enums.ErrorCodeEnum;
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
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022/12/17
 * @Description 描述
 */
@Slf4j
@Service("basicInfoServiceImpl")
public class BasicInfoServiceImpl implements IBasicInfoService {

    @Resource(name="staticInfoServiceImpl")
    IStaticInfoService staticInfoService;

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
        try{
            ErrorCodeEnum errorCodeEnum = updatePilotInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updatePilotInfoResponse = staticInfoService.updatePilotInfo(updatePilotInfoRequest);
            } else {
                updatePilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("修改pilot信息异常, updatePilotInfoRequest={}", updatePilotInfoRequest, e);
            updatePilotInfoResponse.fail("修改pilot数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = updateOperatorInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updateOperatorInfoResponse = staticInfoService.updateOperatorInfo(updateOperatorInfoRequest);
            } else {
                updateOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("修改operator信息异常, updateOperatorInfoRequest={}", updateOperatorInfoRequest, e);
            updateOperatorInfoResponse.fail("修改operator数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = updateUavInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                updateUavInfoResponse = staticInfoService.updateUavInfo(updateUavInfoRequest);
            } else {
                updateUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("修改uav信息异常, updateUavInfoRequest={}", updateUavInfoRequest, e);
            updateUavInfoResponse.fail("修改uav数据异常");
        }
        return updateUavInfoResponse;
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
        try{
            ErrorCodeEnum errorCodeEnum = deletePilotInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                deletePilotInfoResponse = staticInfoService.deletePilotInfo(deletePilotInfoRequest);
            } else {
                deletePilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("删除pilot信息异常, deletePilotInfoRequest={}", deletePilotInfoRequest, e);
            deletePilotInfoResponse.fail("删除pilot数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = deleteOperatorInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                deleteOperatorInfoResponse = staticInfoService.deleteOperatorInfo(deleteOperatorInfoRequest);
            } else {
                deleteOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("删除operator信息异常, deleteOperatorInfoRequest={}", deleteOperatorInfoRequest, e);
            deleteOperatorInfoResponse.fail("删除operator数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = deleteUavInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                deleteUavInfoResponse = staticInfoService.deleteUavInfo(deleteUavInfoRequest);
            } else {
                deleteUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("删除uav信息异常, updateUavInfoRequest={}", deleteUavInfoRequest, e);
            deleteUavInfoResponse.fail("删除uav数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = registerPilotInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerPilotInfoResponse = staticInfoService.registerPilotInfo(registerPilotInfoRequest);
            } else {
                registerPilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注册pilot信息异常, registerPilotInfoRequest={}", registerPilotInfoRequest, e);
            registerPilotInfoResponse.fail("注册pilot数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = registerOperatorInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerOperatorInfoResponse = staticInfoService.registerOperatorInfo(registerOperatorInfoRequest);
            } else {
                registerOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注册operator信息异常, registerOperatorInfoRequest={}", registerOperatorInfoRequest, e);
            registerOperatorInfoResponse.fail("注册operator数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = registerUavInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                registerUavInfoResponse = staticInfoService.registerUavInfo(registerUavInfoRequest);
            } else {
                registerUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注册uav信息异常, registerUavInfoRequest={}", registerUavInfoRequest, e);
            registerUavInfoResponse.fail("注册uav数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = cancelPilotInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelPilotInfoResponse = staticInfoService.cancelPilotInfo(cancelPilotInfoRequest);
            } else {
                cancelPilotInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注销pilot信息异常, cancelPilotInfoRequest={}", cancelPilotInfoRequest, e);
            cancelPilotInfoResponse.fail("注销pilot数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = cancelOperatorInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelOperatorInfoResponse = staticInfoService.cancelOperatorInfo(cancelOperatorInfoRequest);
            } else {
                cancelOperatorInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注销operator信息异常, cancelOperatorInfoRequest={}", cancelOperatorInfoRequest, e);
            cancelOperatorInfoResponse.fail("注销operator数据异常");
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
        try{
            ErrorCodeEnum errorCodeEnum = cancelUavInfoRequest.validate();
            if (ErrorCodeEnum.SUCCESS.equals(errorCodeEnum)) {
                cancelUavInfoResponse = staticInfoService.cancelUavInfo(cancelUavInfoRequest);
            } else {
                cancelUavInfoResponse.fail(errorCodeEnum);
            }
        } catch (Exception e){
            log.error("注销uav信息异常, registerUavInfoRequest={}", cancelUavInfoRequest, e);
            cancelUavInfoResponse.fail("注销uav数据异常");
        }
        return cancelUavInfoResponse;
    }
}
