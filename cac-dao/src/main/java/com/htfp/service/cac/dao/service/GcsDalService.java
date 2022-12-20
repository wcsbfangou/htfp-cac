package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.SubscribeDataEnum;
import com.htfp.service.cac.dao.mapper.entity.GcsInfoMapper;
import com.htfp.service.cac.dao.mapper.mapping.GcsIpMappingMapper;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 16:25
 * @Description 地面站dao服务类
 */
@Slf4j
@Service
public class GcsDalService {

    @Resource
    GcsInfoMapper gcsInfoMapper;
    @Resource
    GcsIpMappingMapper gcsIpMappingMapper;

    public boolean validateGcsId(Long gcsId) {
        GcsInfoDO gcsInfoDO = gcsInfoMapper.selectById(gcsId);
        return gcsInfoDO != null;
    }

    public boolean validateGcsType(Long gcsId, GcsTypeEnum gcsTypeEnum) {
        GcsInfoDO gcsInfoDO = gcsInfoMapper.selectById(gcsId);
        if (gcsInfoDO != null) {
            return gcsTypeEnum.equals(GcsTypeEnum.getFromCode(gcsInfoDO.getGcsType()));
        } else {
            return false;
        }
    }

    public boolean validateRcsToken(Long rcsId, String rcsToken) {
        GcsInfoDO gcsInfoDO = gcsInfoMapper.selectById(rcsId);
        if (gcsInfoDO != null) {
            if (gcsInfoDO.getToken().equals(rcsToken) &&
                    GcsTypeEnum.RCS.equals(GcsTypeEnum.getFromCode(gcsInfoDO.getGcsType()))) {
                return true;
            }
        }
        return false;
    }

    public boolean validateGcsToken(Long gcsId, String gcsToken) {
        GcsInfoDO gcsInfoDO = gcsInfoMapper.selectById(gcsId);
        if (gcsInfoDO != null) {
            if (gcsInfoDO.getToken().equals(gcsToken) &&
                    GcsTypeEnum.GCS.equals(GcsTypeEnum.getFromCode(gcsInfoDO.getGcsType()))) {
                return true;
            }
        }
        return false;
    }

    public GcsInfoDO queryGcsInfo(Long gcsId) {
        return gcsInfoMapper.selectById(gcsId);
    }

    public GcsInfoDO queryGcsInfo(String gcsReg) {
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsReg(gcsReg);
        if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
            return gcsInfoDOList.get(0);
        } else {
            return null;
        }
    }


    public List<GcsInfoDO> queryGcsInfo(GcsTypeEnum gcsTypeEnum) {
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsType(gcsTypeEnum.getCode());
        if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
            return gcsInfoDOList;
        } else {
            return null;
        }
    }

    public List<GcsInfoDO> queryGcsInfoByOperatorId(Long operatorId) {
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByOperatorId(operatorId);
        if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
            return gcsInfoDOList;
        } else {
            return null;
        }
    }

    public int insertGcsInfo(GcsInfoDO gcsInfoDO) {
        return gcsInfoMapper.insertGcsInfo(gcsInfoDO);
    }

    public int updateGcsInfo(GcsInfoDO gcsInfoDO) {
        return gcsInfoMapper.updateByGcsInfo(gcsInfoDO);
    }

    public int deleteGcsInfoByGcsId(Long gcsId) {
        return gcsInfoMapper.deleteById(gcsId);
    }

    public int updateGcsInfoControllableUavType(GcsInfoDO gcsInfoDO, Integer controllableUavType) {
        gcsInfoDO.setControllableUavType(controllableUavType);
        gcsInfoDO.setGmtModify(new Date());
        return updateGcsInfo(gcsInfoDO);
    }

    public boolean validateGcsStatus(Long gcsId) {
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(gcsIpMappingDOList.get(0).getStatus()));
        } else {
            return false;
        }
    }

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId) {
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return gcsIpMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId, MappingStatusEnum mappingStatusEnum) {
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsIdAndStatus(gcsId, mappingStatusEnum.getCode());
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return gcsIpMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public List<GcsIpMappingDO> queryGcsIpMapping(SubscribeDataEnum subscribeDataEnum) {
        return gcsIpMappingMapper.selectBySubscribe(subscribeDataEnum.getCode());
    }

    public List<GcsIpMappingDO> queryGcsIpMapping(MappingStatusEnum statusEnum, SubscribeDataEnum subscribeDataEnum) {
        return gcsIpMappingMapper.selectByStatusAndSubscribe(statusEnum.getCode(), subscribeDataEnum.getCode());
    }

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId, SubscribeDataEnum subscribeDataEnum) {
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsIdAndSubscribe(gcsId, subscribeDataEnum.getCode());
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return gcsIpMappingDOList.get(0);
        } else {
            return null;
        }
    }


    public int updateGcsIpMapping(GcsIpMappingDO gcsIpMappingDO) {
        return gcsIpMappingMapper.updateByGcsIpMapping(gcsIpMappingDO);
    }

    public int insertGcsIpMapping(GcsIpMappingDO gcsIpMappingDO) {
        return gcsIpMappingMapper.insertGcsIpMapping(gcsIpMappingDO);
    }

    public int deleteGcsIpMapping(Long gcsId) {
        return gcsIpMappingMapper.deleteByGcsId(gcsId);
    }

    public int updateGcsIpMappingIp(GcsIpMappingDO gcsIpMappingDO, String gcsIp) {
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setStatus(MappingStatusEnum.VALID.getCode());
        gcsIpMappingDO.setGmtModify(new Date());
        return updateGcsIpMapping(gcsIpMappingDO);
    }

    public int updateGcsIpMappingStatus(GcsIpMappingDO gcsIpMappingDO, MappingStatusEnum statusEnum) {
        gcsIpMappingDO.setStatus(statusEnum.getCode());
        gcsIpMappingDO.setGmtModify(new Date());
        return updateGcsIpMapping(gcsIpMappingDO);
    }

    public int updateGcsIpMappingStatusAndSubscribe(GcsIpMappingDO gcsIpMappingDO, MappingStatusEnum statusEnum, SubscribeDataEnum subscribeDataEnum) {
        gcsIpMappingDO.setStatus(statusEnum.getCode());
        gcsIpMappingDO.setSubscribe(subscribeDataEnum.getCode());
        gcsIpMappingDO.setGmtModify(new Date());
        return updateGcsIpMapping(gcsIpMappingDO);
    }

    public int updateGcsIpMappingSubscribe(GcsIpMappingDO gcsIpMappingDO, SubscribeDataEnum subscribeDataEnum) {
        gcsIpMappingDO.setSubscribe(subscribeDataEnum.getCode());
        gcsIpMappingDO.setGmtModify(new Date());
        return updateGcsIpMapping(gcsIpMappingDO);
    }

    public GcsIpMappingDO buildGcsIpMappingDO(Long gcsId, String gcsIp) {
        GcsIpMappingDO gcsIpMappingDO = new GcsIpMappingDO();
        gcsIpMappingDO.setGcsId(gcsId);
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setStatus(MappingStatusEnum.VALID.getCode());
        gcsIpMappingDO.setSubscribe(SubscribeDataEnum.UN_SUBSCRIBE.getCode());
        gcsIpMappingDO.setGmtCreate(new Date());
        gcsIpMappingDO.setGmtModify(new Date());
        return gcsIpMappingDO;
    }

    public GcsInfoDO buildGcsInfoDO(String gcsReg, String gcsSn, Integer gcsType, Integer controllableUavType, Integer dataLinkType, String token, Long operatorId) {
        GcsInfoDO gcsInfoDO = new GcsInfoDO();
        gcsInfoDO.setGcsReg(gcsReg);
        gcsInfoDO.setGcsSn(gcsSn);
        gcsInfoDO.setGcsType(gcsType);
        gcsInfoDO.setControllableUavType(controllableUavType);
        gcsInfoDO.setDataLinkType(dataLinkType);
        gcsInfoDO.setToken(token);
        gcsInfoDO.setOperatorId(operatorId);
        gcsInfoDO.setGmtCreate(new Date());
        gcsInfoDO.setGmtModify(new Date());
        return gcsInfoDO;
    }
}
