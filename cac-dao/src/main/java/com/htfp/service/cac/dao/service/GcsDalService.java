package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.common.enums.MappingStatusEnums;
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

    public GcsIpMappingDO queryGcsIpMapping(Long gcsId){
        List<GcsIpMappingDO> gcsIpMappingDOList = gcsIpMappingMapper.selectByGcsId(gcsId);
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

    public GcsIpMappingDO buildNewGcsIpMappingDO(Long gcsId, String gcsIp) {
        GcsIpMappingDO gcsIpMappingDO = new GcsIpMappingDO();
        gcsIpMappingDO.setGcsId(gcsId);
        gcsIpMappingDO.setGcsIp(gcsIp);
        gcsIpMappingDO.setStatus(MappingStatusEnums.VALID.getCode());
        gcsIpMappingDO.setGmtCreate(new Date());
        gcsIpMappingDO.setGmtModify(new Date());
        return gcsIpMappingDO;
    }
}
