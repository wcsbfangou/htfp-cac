package com.htfp.service.cac.router.biz.service.netty.handler.dataTransfer;

import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.enums.SubscribeDataEnum;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

import javax.annotation.Resource;

/**
 * @Author sunjipeng
 * @Date 2022-06-11 16:22
 * @Description channel管理
 */
@Slf4j
@Component
public class SubscribeManager {

    @Resource
    private GcsDalService gcsDalService;

    public Boolean subscribe(String gcsId) {
        Boolean result = false;
        GcsIpMappingDO gcsIpMapping = gcsDalService.queryGcsIpMapping(Long.valueOf(gcsId));
        if (MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(gcsIpMapping.getStatus())) &&
                SubscribeDataEnum.UN_SUBSCRIBE.equals(SubscribeDataEnum.getFromCode(gcsIpMapping.getSubscribe()))) {
            gcsDalService.updateGcsIpMappingSubscribe(gcsIpMapping, SubscribeDataEnum.SUBSCRIBE);
            result = true;
        }
        return result;
    }

    public Boolean cancelSubscribe(String gcsId) {
        Boolean result = false;
        GcsIpMappingDO gcsIpMapping = gcsDalService.queryGcsIpMapping(Long.valueOf(gcsId));
        if (MappingStatusEnum.VALID.equals(MappingStatusEnum.getFromCode(gcsIpMapping.getStatus())) &&
                SubscribeDataEnum.SUBSCRIBE.equals(SubscribeDataEnum.getFromCode(gcsIpMapping.getSubscribe()))) {
            gcsDalService.updateGcsIpMappingSubscribe(gcsIpMapping, SubscribeDataEnum.UN_SUBSCRIBE);
            result = true;
        }
        return result;
    }
}
