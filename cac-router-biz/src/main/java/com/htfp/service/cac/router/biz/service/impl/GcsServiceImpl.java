package com.htfp.service.cac.router.biz.service.impl;

import com.htfp.service.cac.common.enums.ErrorCodeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnums;
import com.htfp.service.cac.dao.mapper.entity.GcsInfoMapper;
import com.htfp.service.cac.dao.mapper.entity.PilotInfoMapper;
import com.htfp.service.cac.dao.mapper.entity.UavInfoMapper;
import com.htfp.service.cac.dao.mapper.log.CommandAndControlLogMapper;
import com.htfp.service.cac.dao.mapper.log.NavigationLogMapper;
import com.htfp.service.cac.dao.mapper.log.UavStatusLogMapper;
import com.htfp.service.cac.dao.mapper.mapping.GcsIpMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavGcsMappingMapper;
import com.htfp.service.cac.dao.mapper.mapping.UavNavigationMappingMapper;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavGcsMappingDO;
import com.htfp.service.cac.router.biz.model.request.GcsChangeUavRequest;
import com.htfp.service.cac.router.biz.model.request.GcsControlUavRequest;
import com.htfp.service.cac.router.biz.model.request.SignInRequest;
import com.htfp.service.cac.router.biz.model.request.SignOutRequest;
import com.htfp.service.cac.router.biz.model.request.UavStatusChangeRequest;
import com.htfp.service.cac.router.biz.model.response.GcsChangeUavResponse;
import com.htfp.service.cac.router.biz.model.response.GcsControlUavResponse;
import com.htfp.service.cac.router.biz.model.response.SignInResponse;
import com.htfp.service.cac.router.biz.model.response.SignOutResponse;
import com.htfp.service.cac.router.biz.model.response.UavStatusChangeResponse;
import com.htfp.service.cac.router.biz.service.IGcsService;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-20 15:11
 */
@Slf4j
@Service
public class GcsServiceImpl implements IGcsService {

    @Resource
    GcsInfoMapper gcsInfoMapper;
    @Resource
    PilotInfoMapper pilotInfoMapper;
    @Resource
    UavInfoMapper uavInfoMapper;
    @Resource
    GcsIpMappingMapper gcsIpMappingMapper;
    @Resource
    UavGcsMappingMapper uavGcsMappingMapper;
    @Resource
    UavNavigationMappingMapper uavNavigationMappingMapper;
    @Resource
    CommandAndControlLogMapper commandAndControlLogMapper;
    @Resource
    NavigationLogMapper navigationLogMapper;
    @Resource
    UavStatusLogMapper uavStatusLogMapper;

    /**
     * 地面站注册
     *
     * @param signInRequest
     * @return
     */
    @Override
    public SignInResponse gcsSignIn(SignInRequest signInRequest) {
        SignInResponse signInResponse = new SignInResponse();
        signInResponse.fail();
        try {
            final Long gcsId = Long.getLong(signInRequest.getGcsId());
            List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsId(gcsId);
            if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
                List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
                if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
                    GcsIpMappingDO gcsIpMappingDO = gcsIpMappingDOList.get(0);
                    gcsIpMappingDO.setGcsIp(signInRequest.getGcsIp());
                    gcsIpMappingDO.setGmtModify(new Date());
                    gcsIpMappingMapper.updateByGcsIpMapping(gcsIpMappingDO);
                } else {
                    GcsIpMappingDO gcsIpMappingDO = buildGcsIpMappingDO(gcsId, signInRequest.getGcsIp());
                    gcsIpMappingMapper.insertGcsIpMapping(gcsIpMappingDO);
                }
                signInResponse.success();
            } else {
                signInResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
        } catch (Exception e) {
            log.error("地面站注册异常，signRequest={}", signInRequest, e);
            signInResponse.fail(e.getMessage());
        }
        return signInResponse;
    }

    /**
     * 地面站注销
     *
     * @param signOutRequest
     * @return
     */
    @Override
    public SignOutResponse gcsSignOut(SignOutRequest signOutRequest) {
        SignOutResponse signOutResponse = new SignOutResponse();
        signOutResponse.fail();
        try {
            final Long gcsId = Long.getLong(signOutRequest.getGcsId());
            List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsId(gcsId);
            if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
                List<UavGcsMappingDO> uavGcsMappingDOList = uavGcsMappingMapper.selectByGcsIdAndStatus(gcsId, MappingStatusEnums.EFFECTIVE.getCode());
                if (CollectionUtils.isNotEmpty(uavGcsMappingDOList)) {
                    signOutResponse.setMessage("地面站绑定的无人机还未完成航行，不可下线");
                } else {
                    List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
                    if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
                        GcsIpMappingDO gcsIpMappingDO = gcsIpMappingDOList.get(0);
                        if (!gcsIpMappingDO.getGcsIp().equals(signOutRequest.getGcsIp())) {
                            signOutResponse.setMessage("地面站注销时IP与注册时IP不一致，不可下线");
                        } else {
                            gcsIpMappingDO.setStatus(MappingStatusEnums.INVALID.getCode());
                            gcsIpMappingDO.setGmtModify(new Date());
                            gcsIpMappingMapper.updateByGcsIpMapping(gcsIpMappingDO);
                            signOutResponse.success();
                        }
                    } else {
                        signOutResponse.setMessage("地面站未注册过，不可下线");
                    }
                }
            } else {
                signOutResponse.fail(ErrorCodeEnum.WRONG_GCS_ID);
            }
        } catch (Exception e) {
            log.error("地面站注销异常，signOutRequest={}", signOutRequest, e);
            signOutResponse.fail(e.getMessage());
        }
        return signOutResponse;
    }

    /**
     * 地面站在控无人机变更
     *
     * @param gcsChangeUavRequest
     * @return
     */
    @Override
    public GcsChangeUavResponse gcsChangeUav(GcsChangeUavRequest gcsChangeUavRequest) {
        return null;
    }

    /**
     * 无人机状态变更
     *
     * @param uavStatusChangeRequest
     * @return
     */
    @Override
    public UavStatusChangeResponse uavStatusChange(UavStatusChangeRequest uavStatusChangeRequest) {
        return null;
    }

    /**
     * 地面站指控指令执行
     *
     * @param gcsControlUavRequest
     * @return
     */
    @Override
    public GcsControlUavResponse gcsControlUav(GcsControlUavRequest gcsControlUavRequest) {
        return null;
    }

    private GcsIpMappingDO buildGcsIpMappingDO(Long gcsId, String gcsIp) {
        GcsIpMappingDO gcsIpMappingDO = new GcsIpMappingDO();
        gcsIpMappingDO.setGcsId(gcsId);
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setStatus(MappingStatusEnums.EFFECTIVE.getCode());
        gcsIpMappingDO.setGmtCreate(new Date());
        gcsIpMappingDO.setGmtModify(new Date());
        return gcsIpMappingDO;
    }
}
