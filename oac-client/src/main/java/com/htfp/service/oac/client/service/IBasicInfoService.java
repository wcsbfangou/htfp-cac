package com.htfp.service.oac.client.service;

import com.htfp.service.oac.client.request.CancelOperatorInfoRequest;
import com.htfp.service.oac.client.request.CancelPilotInfoRequest;
import com.htfp.service.oac.client.request.CancelUavInfoRequest;
import com.htfp.service.oac.client.request.DeleteOperatorInfoRequest;
import com.htfp.service.oac.client.request.DeletePilotInfoRequest;
import com.htfp.service.oac.client.request.DeleteUavInfoRequest;
import com.htfp.service.oac.client.request.RegisterOperatorInfoRequest;
import com.htfp.service.oac.client.request.RegisterPilotInfoRequest;
import com.htfp.service.oac.client.request.RegisterUavInfoRequest;
import com.htfp.service.oac.client.request.UpdateOperatorInfoRequest;
import com.htfp.service.oac.client.request.UpdatePilotInfoRequest;
import com.htfp.service.oac.client.request.UpdateUavInfoRequest;
import com.htfp.service.oac.client.response.CancelOperatorInfoResponse;
import com.htfp.service.oac.client.response.CancelPilotInfoResponse;
import com.htfp.service.oac.client.response.CancelUavInfoResponse;
import com.htfp.service.oac.client.response.DeleteOperatorInfoResponse;
import com.htfp.service.oac.client.response.DeletePilotInfoResponse;
import com.htfp.service.oac.client.response.DeleteUavInfoResponse;
import com.htfp.service.oac.client.response.RegisterOperatorInfoResponse;
import com.htfp.service.oac.client.response.RegisterPilotInfoResponse;
import com.htfp.service.oac.client.response.RegisterUavInfoResponse;
import com.htfp.service.oac.client.response.UpdateOperatorInfoResponse;
import com.htfp.service.oac.client.response.UpdatePilotInfoResponse;
import com.htfp.service.oac.client.response.UpdateUavInfoResponse;

/**
 * @Author sunjipeng
 * @Date 2022/12/15
 * @Description 描述
 */
public interface IBasicInfoService {

    /**
     * 修改驾驶员信息
     * @param updatePilotInfoRequest
     * @return
     */
    UpdatePilotInfoResponse updatePilotInfo(UpdatePilotInfoRequest updatePilotInfoRequest);

    /**
     * 修改运营主体信息
     * @param updateOperatorInfoRequest
     * @return
     */
    UpdateOperatorInfoResponse updateOperatorInfo(UpdateOperatorInfoRequest updateOperatorInfoRequest);

    /**
     * 修改无人机信息
     * @param updateUavInfoRequest
     * @return
     */
    UpdateUavInfoResponse updateUavInfo(UpdateUavInfoRequest updateUavInfoRequest);

    /**
     * 删除驾驶员信息
     * @param deletePilotInfoRequest
     * @return
     */
    DeletePilotInfoResponse deletePilotInfo(DeletePilotInfoRequest deletePilotInfoRequest);

    /**
     * 删除运营主体信息
     * @param deleteOperatorInfoRequest
     * @return
     */
    DeleteOperatorInfoResponse deleteOperatorInfo(DeleteOperatorInfoRequest deleteOperatorInfoRequest);

    /**
     * 删除无人机信息
     * @param deleteUavInfoRequest
     * @return
     */
    DeleteUavInfoResponse deleteUavInfo(DeleteUavInfoRequest deleteUavInfoRequest);

    /**
     * 注册驾驶员信息
     * @param registerPilotInfoRequest
     * @return
     */
    RegisterPilotInfoResponse registerPilotInfo(RegisterPilotInfoRequest registerPilotInfoRequest);

    /**
     * 注册运营主体信息
     * @param registerOperatorInfoRequest
     * @return
     */
    RegisterOperatorInfoResponse registerOperatorInfo(RegisterOperatorInfoRequest registerOperatorInfoRequest);

    /**
     * 注册无人机信息
     * @param registerUavInfoRequest
     * @return
     */
    RegisterUavInfoResponse registerUavInfo(RegisterUavInfoRequest registerUavInfoRequest);

    /**
     * 注销驾驶员信息
     * @param cancelPilotInfoRequest
     * @return
     */
    CancelPilotInfoResponse cancelPilotInfo(CancelPilotInfoRequest cancelPilotInfoRequest);

    /**
     * 注销运营人信息
     * @param cancelOperatorInfoRequest
     * @return
     */
    CancelOperatorInfoResponse cancelOperatorInfo(CancelOperatorInfoRequest cancelOperatorInfoRequest);

    /**
     * 注销无人机信息
     * @param cancelUavInfoRequest
     * @return
     */
    CancelUavInfoResponse cancelUavInfo(CancelUavInfoRequest cancelUavInfoRequest);
}
