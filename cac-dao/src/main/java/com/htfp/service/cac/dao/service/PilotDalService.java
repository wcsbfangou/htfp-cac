package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.entity.PilotInfoMapper;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import com.htfp.service.cac.dao.model.entity.UavInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.List;

/**
 * @Author sunjipeng
 * @Date 2022-05-31 22:09
 */

@Slf4j
@Service
public class PilotDalService {

    @Resource
    PilotInfoMapper pilotInfoMapper;

    public PilotInfoDO queryPilotInfo(Long pilotId){
        List<PilotInfoDO> pilotInfoDOList = pilotInfoMapper.selectByPilotId(pilotId);
        if(CollectionUtils.isNotEmpty(pilotInfoDOList)){
            return pilotInfoDOList.get(0);
        } else {
            return null;
        }
    }
}
