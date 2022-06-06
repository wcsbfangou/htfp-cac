package com.htfp.service.cac.router.biz.job;

import com.google.common.collect.Maps;
import com.htfp.service.cac.common.constant.HttpContentTypeConstant;
import com.htfp.service.cac.common.constant.HttpUriConstant;
import com.htfp.service.cac.common.enums.GcsTypeEnum;
import com.htfp.service.cac.common.enums.MappingStatusEnum;
import com.htfp.service.cac.common.utils.JsonUtils;
import com.htfp.service.cac.common.utils.http.CustomHttpConfig;
import com.htfp.service.cac.common.utils.http.HttpAsyncClient;
import com.htfp.service.cac.common.utils.http.HttpContentWrapper;
import com.htfp.service.cac.dao.model.entity.GcsInfoDO;
import com.htfp.service.cac.dao.model.mapping.GcsIpMappingDO;
import com.htfp.service.cac.dao.service.GcsDalService;
import com.htfp.service.cac.router.biz.model.request.PingRequest;
import com.htfp.service.cac.router.biz.model.response.PingResponse;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.lang3.StringUtils;
import org.quartz.JobExecutionContext;
import org.quartz.JobExecutionException;
import org.springframework.scheduling.quartz.QuartzJobBean;

import javax.annotation.Resource;
import java.util.List;
import java.util.Map;

/**
 * @Author sunjipeng
 * @Date 2022-06-06 11:31
 */
@Slf4j
public class RcsPingJob extends QuartzJobBean {

    public static final String RCS_ECHO_TOKEN = "hello,rcs:";

    @Resource
    GcsDalService gcsDalService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<GcsInfoDO> rcsInfoDOList = gcsDalService.queryGcsInfo(GcsTypeEnum.RCS);
        if(CollectionUtils.isNotEmpty(rcsInfoDOList)){
            for (GcsInfoDO rcsInfoDO : rcsInfoDOList) {
                GcsIpMappingDO rcsIpMapping = gcsDalService.queryGcsIpMapping(rcsInfoDO.getGcsId(), MappingStatusEnum.VALID);
                if(rcsIpMapping!=null){
                    boolean pingResult = pingRcs(rcsIpMapping.getGcsId(), rcsIpMapping.getGcsIp());
                }
            }
        }
    }

    private boolean pingRcs(Long rcsId, String rcsIp){
        boolean result = false;
        try{
            String url = getUrl(rcsIp);
            if(StringUtils.isNotEmpty(url)){
                PingRequest pingRequest = buildPingRequest(rcsId);
                HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                        .contentObject(JsonUtils.object2Json(pingRequest, false))
                        .gcsId(rcsId)
                        .contentType(HttpContentTypeConstant.JSON_TYPE)
                        .create();
                Map<String, String> httpHeader = builderRequestHeader();
                CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
                String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
                PingResponse pingResponse = decodeHttpResponse(httpResponse);
                if(pingResponse.getSuccess()){
                    result = generateEchoToken(rcsId).equals(pingResponse.getData());
                }
            }
        } catch (Exception e){
            log.error("请求地面站发生异常", e);
        }
        return result;
    }

    private String getUrl(String rcsIp){
        return rcsIp + "/" + HttpUriConstant.RCS_PING;
    }

    private PingRequest buildPingRequest(Long rcsId){
        PingRequest pingRequest = new PingRequest();
        pingRequest.setGcsId(rcsId.toString());
        pingRequest.setEchoToken(generateEchoToken(rcsId));
        return pingRequest;
    }

    public Map<String, String> builderRequestHeader() {
        Map<String, String> map = Maps.newHashMap();
        map.put("Accept","application/json");
        map.put("Content-Type", "application/json;charset=UTF-8");
        return map;
    }

    private CustomHttpConfig buildCustomHttpConfig(){
        return new CustomHttpConfig();
    }

    private PingResponse decodeHttpResponse(String response) throws Exception {
        if (response==null ||StringUtils.isBlank(response)) {
            return null;
        } else {
            PingResponse pingResponse = JsonUtils.json2ObjectThrowException(response, PingResponse.class);
            pingResponse.setResultStr(response);
            return pingResponse;
        }
    }

    private String generateEchoToken(Long rcsId){
        return RCS_ECHO_TOKEN + rcsId.toString();
    }
}
