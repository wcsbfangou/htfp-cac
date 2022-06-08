package com.htfp.service.cac.dao.service;

import com.htfp.service.cac.dao.mapper.entity.PilotInfoMapper;
import com.htfp.service.cac.dao.model.entity.PilotInfoDO;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.springframework.stereotype.Service;

import javax.annotation.Resource;
import java.util.Date;
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

    public List<PilotInfoDO> queryPilotInfo(Integer controllableUavType){
        return pilotInfoMapper.selectByControllableUavType(controllableUavType);
    }

    public int insertPilotInfo(PilotInfoDO pilotInfoDO){
        return pilotInfoMapper.insertPilotInfo(pilotInfoDO);
    }

    public int updatePilotInfo(PilotInfoDO pilotInfoDO){
        return pilotInfoMapper.updateByPilotInfo(pilotInfoDO);
    }

    public int deletePilotInfoByPilotId(Long pilotId){
        return pilotInfoMapper.deleteByPilotId(pilotId);
    }

    public int deletePilotInfoById(Long id){
        return pilotInfoMapper.deleteById(id);
    }

    public int updatePilotInfoControllableUavType(PilotInfoDO pilotInfoDO, Integer controllableUavType){
        pilotInfoDO.setControllableUavType(controllableUavType);
        pilotInfoDO.setGmtModify(new Date());
        return updatePilotInfo(pilotInfoDO);
    }

    public PilotInfoDO buildPilotInfoDO(Long pilotId, String pilotName, Integer controllableUavType) {
        PilotInfoDO pilotInfoDO = new PilotInfoDO();
        pilotInfoDO.setPilotId(pilotId);
        pilotInfoDO.setPilotName(pilotName);
        pilotInfoDO.setControllableUavType(controllableUavType);
        pilotInfoDO.setGmtCreate(new Date());
        pilotInfoDO.setGmtModify(new Date());
        return pilotInfoDO;
    }
}
