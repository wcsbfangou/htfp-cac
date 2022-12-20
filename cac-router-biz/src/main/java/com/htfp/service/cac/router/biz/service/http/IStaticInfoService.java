package com.htfp.service.cac.router.biz.service.http;


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

/**
 * @Author sunjipeng
 * @Date 2022/12/8
 * @Description 描述
 */
public interface IStaticInfoService {

    /**
     * 录入驾驶员信息
     * @param pilotInfoRequest
     * @return
     */
    InsertPilotInfoResponse typeInPilotInfo(PilotInfoRequest pilotInfoRequest);

    /**
     * 录入运营主体信息
     * @param operatorInfoRequest
     * @return
     */
    InsertOperatorInfoResponse typeInOperatorInfo(OperatorInfoRequest operatorInfoRequest);

    /**
     * 录入无人机信息
     * @param uavInfoRequest
     * @return
     */
    InsertUavInfoResponse typeInUavInfo(UavInfoRequest uavInfoRequest);

    /**
     * 录入地面站信息
     * @param gcsInfoRequest
     * @return
     */
    InsertGcsInfoResponse typeInGcsInfo(GcsInfoRequest gcsInfoRequest);

    /**
     * 修改驾驶员信息
     * @param pilotInfoRequest
     * @return
     */
    UpdatePilotInfoResponse updatePilotInfo(PilotInfoRequest pilotInfoRequest);

    /**
     * 修改运营主体信息
     * @param operatorInfoRequest
     * @return
     */
    UpdateOperatorInfoResponse updateOperatorInfo(OperatorInfoRequest operatorInfoRequest);

    /**
     * 修改无人机信息
     * @param uavInfoRequest
     * @return
     */
    UpdateUavInfoResponse updateUavInfo(UavInfoRequest uavInfoRequest);

    /**
     * 修改地面站信息
     * @param gcsInfoRequest
     * @return
     */
    UpdateGcsInfoResponse updateGcsInfo(GcsInfoRequest gcsInfoRequest);

    /**
     * 查询驾驶员信息
     * @param pilotId
     * @return
     */
    QueryPilotInfoResponse queryPilotInfo(Long pilotId);

    /**
     * 查询运营主体信息
     * @param operatorId
     * @return
     */
    QueryOperatorInfoResponse queryOperatorInfo(Long operatorId);

    /**
     * 查询无人机信息
     * @param uavId
     * @return
     */
    QueryUavInfoResponse queryUavInfo(Long uavId);

    /**
     * 查询地面站信息
     * @param gcsId
     * @return
     */
    QueryGcsInfoResponse queryGcsInfo(Long gcsId);

    /**
     * 查询驾驶员信息
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    QueryPilotInfoResponse queryPilotInfoByIdCardInfo(Integer idCardType, String idCardNumber);

    /**
     * 查询运营主体信息
     * @param idCardType
     * @param idCardNumber
     * @return
     */
    QueryOperatorInfoResponse queryOperatorInfoByIdCardInfo(Integer idCardType, String idCardNumber);

    /**
     * 查询无人机信息
     * @param uavReg
     * @return
     */
    QueryUavInfoResponse queryUavInfoByUavReg(String uavReg);

    /**
     * 查询地面站信息
     * @param gcsReg
     * @return
     */
    QueryGcsInfoResponse queryGcsInfoByGcsReg(String gcsReg);

    /**
     * 删除驾驶员信息
     * @param pilotId
     * @return
     */
    DeletePilotInfoResponse deletePilotInfo(Long pilotId);

    /**
     * 删除运营主体信息
     * @param operatorId
     * @return
     */
    DeleteOperatorInfoResponse deleteOperatorInfo(Long operatorId);

    /**
     * 删除无人机信息
     * @param uavId
     * @return
     */
    DeleteUavInfoResponse deleteUavInfo(Long uavId);

    /**
     * 删除地面站信息
     * @param gcsId
     * @return
     */
    DeleteGcsInfoResponse deleteGcsInfo(Long gcsId);

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
