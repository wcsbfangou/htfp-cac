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
import com.htfp.service.cac.router.biz.model.http.request.PingRequest;
import com.htfp.service.cac.router.biz.model.http.response.PingResponse;
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
 * @Date 2022-06-06 11:30
 * @Description 地面站探活定时任务
 */
@Slf4j
public class GcsPingJob extends QuartzJobBean {

    public static final String GCS_ECHO_TOKEN = "hello,gcs:";

    @Resource
    GcsDalService gcsDalService;

    @Override
    protected void executeInternal(JobExecutionContext jobExecutionContext) throws JobExecutionException {
        List<GcsInfoDO> gcsInfoDOList = gcsDalService.queryGcsInfo(GcsTypeEnum.GCS);
        if (CollectionUtils.isNotEmpty(gcsInfoDOList)) {
            for (GcsInfoDO gcsInfoDO : gcsInfoDOList) {
                GcsIpMappingDO gcsIpMapping = gcsDalService.queryGcsIpMapping(gcsInfoDO.getGcsId(), MappingStatusEnum.VALID);
                if (gcsIpMapping != null) {
                    // TODO: 2022/6/14 报警
                    boolean pingResult = pingGcs(gcsIpMapping.getGcsId(), gcsIpMapping.getGcsIp());
                }
            }
        }
    }

    private boolean pingGcs(Long gcsId, String gcsIp) {
        boolean result = false;
        try {
            String url = getUrl(gcsIp);
            if (StringUtils.isNotEmpty(url)) {
                PingRequest pingRequest = buildPingRequest(gcsId);
                HttpContentWrapper httpContentWrapper = HttpContentWrapper.of()
                        .contentObject(JsonUtils.object2Json(pingRequest, false))
                        .gcsId(gcsId)
                        .contentType(HttpContentTypeConstant.JSON_TYPE)
                        .create();
                Map<String, String> httpHeader = builderRequestHeader();
                CustomHttpConfig customHttpConfig = buildCustomHttpConfig();
                String httpResponse = HttpAsyncClient.getInstance().executePost(url, customHttpConfig, httpContentWrapper, httpHeader);
                PingResponse pingResponse = decodeHttpResponse(httpResponse);
                if (pingResponse.getSuccess()) {
                    result = generateEchoToken(gcsId).equals(pingResponse.getData());
                }
            }
        } catch (Exception e) {
            log.error("[GcsPing]请求地面站发生异常", e);
        }
        return result;
    }

    private String getUrl(String gcsIp) {
        return gcsIp + "/" + HttpUriConstant.GCS_PING;
    }

    private PingRequest buildPingRequest(Long gcsId) {
        PingRequest pingRequest = new PingRequest();
        pingRequest.setGcsId(gcsId.toString());
        pingRequest.setEchoToken(generateEchoToken(gcsId));
        return pingRequest;
    }

    public Map<String, String> builderRequestHeader() {
        Map<String, String> map = Maps.newHashMap();
        map.put("Accept", "application/json");
        map.put("Content-Type", "application/json;charset=UTF-8");
        return map;
    }

    private CustomHttpConfig buildCustomHttpConfig() {
        return new CustomHttpConfig();
    }

    private PingResponse decodeHttpResponse(String response) throws Exception {
        if (response == null || StringUtils.isBlank(response)) {
            return null;
        } else {
            PingResponse pingResponse = JsonUtils.json2ObjectThrowException(response, PingResponse.class);
            pingResponse.setResultStr(response);
            return pingResponse;
        }
    }

    private String generateEchoToken(Long gcsId) {
        return GCS_ECHO_TOKEN + gcsId.toString();
    }
}
