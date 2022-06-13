package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.dao.mapper.entity.GcsInfoMapper;
import com.htfp.service.cac.dao.mapper.mapping.GcsIpMappingMapper;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.model.mapping.UavNavigationMappingDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-26 16:25
 */
@Slf4j
@Service
public class GcsDalService {

    @Resource
    GcsInfoMapper gcsInfoMapper;
    @Resource
    GcsIpMappingMapper gcsIpMappingMapper;

    public boolean validateGcsId(Long gcsId){
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsId(gcsId);
        return CollectionUtils.isNotEmpty(gcsInfoDOList);
    }

    public boolean validateRcsToken(Long gcsId, String gcsToken){
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsId(gcsId);
        if(CollectionUtils.isNotEmpty(gcsInfoDOList)){
            if(gcsInfoDOList.get(0).getToken().equals(gcsToken) &&
                    GcsTypeEnum.RCS.equals(GcsTypeEnum.getFromCode(gcsInfoDOList.get(0).getTypeId()))){
                return true;
            }
        }
        return false;
    }

    public GcsInfoDO queryGcsInfo(Long gcsId){
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByGcsId(gcsId);
        if(CollectionUtils.isNotEmpty(gcsInfoDOList)){
            return gcsInfoDOList.get(0);
        } else {
            return null;
        }
    }

    public List<GcsInfoDO> queryGcsInfo(GcsTypeEnum gcsTypeEnum){
        List<GcsInfoDO> gcsInfoDOList = gcsInfoMapper.selectByTypeId(gcsTypeEnum.getCode());
        if(CollectionUtils.isNotEmpty(gcsInfoDOList)){
            return gcsInfoDOList;
        } else {
            return null;
        }
    }

    public int insertGcsInfo(GcsInfoDO gcsInfoDO){
        return gcsInfoMapper.insertGcsInfo(gcsInfoDO);
    }

    public int updateGcsInfo(GcsInfoDO gcsInfoDO){
        return gcsInfoMapper.updateByGcsInfo(gcsInfoDO);
    }

    public int deleteGcsInfoByGcsId(Long gcsId){
        return gcsInfoMapper.deleteByGcsId(gcsId);
    }

    public int deleteGcsInfoById(Long id){
        return gcsInfoMapper.deleteById(id);
    }

    public int updateGcsInfoControllableUavType(GcsInfoDO gcsInfoDO, Integer controllableUavType){
        gcsInfoDO.setControllableUavType(controllableUavType);
        gcsInfoDO.setGmtModify(new Date());
        return updateGcsInfo(gcsInfoDO);
    }

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId){
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return gcsIpMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId, MappingStatusEnum mappingStatusEnum){
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsIdAndStatus(gcsId, mappingStatusEnum.getCode());
        if (CollectionUtils.isNotEmpty(gcsIpMappingDOList)) {
            return gcsIpMappingDOList.get(0);
        } else {
            return null;
        }
    }

    public int updateGcsIpMapping (GcsIpMappingDO gcsIpMappingDO){
        return gcsIpMappingMapper.updateByGcsIpMapping(gcsIpMappingDO);
    }

    public int insertGcsIpMapping (GcsIpMappingDO gcsIpMappingDO){
        return gcsIpMappingMapper.insertGcsIpMapping(gcsIpMappingDO);
    }

    public void updateGcsIpMappingIp(GcsIpMappingDO gcsIpMappingDO, String gcsIp){
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setGmtModify(new Date());
        updateGcsIpMapping(gcsIpMappingDO);
    }

    public GcsIpMappingDO buildGcsIpMappingDO(Long gcsId, String gcsIp) {
        GcsIpMappingDO gcsIpMappingDO = new GcsIpMappingDO();
        gcsIpMappingDO.setGcsId(gcsId);
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setStatus(MappingStatusEnum.VALID.getCode());
        gcsIpMappingDO.setGmtCreate(new Date());
        gcsIpMappingDO.setGmtModify(new Date());
        return gcsIpMappingDO;
    }

    public GcsInfoDO buildGcsInfoDO(Long gcsId, Integer typeId, Integer controllableUavType, Integer dataLinkType, String token) {
        GcsInfoDO gcsInfoDO = new GcsInfoDO();
        gcsInfoDO.setGcsId(gcsId);
        gcsInfoDO.setTypeId(typeId);
        gcsInfoDO.setControllableUavType(controllableUavType);
        gcsInfoDO.setDataLinkType(dataLinkType);
        gcsInfoDO.setToken(token);
        gcsInfoDO.setGmtCreate(new Date());
        gcsInfoDO.setGmtModify(new Date());
        return gcsInfoDO;
    }
}
