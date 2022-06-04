package com.htfp.service.cac.common.utils.http;

import com.google.common.collect.Lists;
import com.htfp.service.cac.common.utils.BasicAuthorizationUtils;
import com.htfp.service.cac.common.utils.JsonUtils;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.collections.MapUtils;
import org.apache.commons.lang3.StringUtils;
import org.apache.http.ConnectionClosedException;
import org.apache.http.Consts;
import org.apache.http.HttpEntity;
import org.apache.http.HttpResponse;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.client.utils.HttpClientUtils;
import org.apache.http.config.ConnectionConfig;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.nio.client.CloseableHttpAsyncClient;
import org.apache.http.impl.nio.client.HttpAsyncClientBuilder;
import org.apache.http.impl.nio.conn.PoolingNHttpClientConnectionManager;
import org.apache.http.impl.nio.reactor.DefaultConnectingIOReactor;
import org.apache.http.impl.nio.reactor.IOReactorConfig;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.nio.reactor.ConnectingIOReactor;
import org.apache.http.util.Asserts;
import org.apache.http.util.EntityUtils;
import org.springframework.stereotype.Component;

import javax.validation.constraints.NotNull;
import java.io.IOException;
import java.net.ConnectException;
import java.net.NoRouteToHostException;
import java.net.SocketTimeoutException;
import java.net.URI;
import java.net.UnknownHostException;
import java.nio.charset.StandardCharsets;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.ExecutionException;
import java.util.concurrent.ThreadFactory;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.TimeoutException;
import java.util.concurrent.atomic.AtomicLong;

/**
 * @Author sunjipeng
 * @Date 2022-06-02 16:25
 */
@Slf4j
@Component
public class HttpClient {

    protected static final int defaultMaxTotal = 4096;
    protected static final int defaultMaxPerRoute = 256;
    protected static final int defaultConnRequestTime = 5 * 1000;
    protected static final int defaultConnectTime = 30 * 1000;
    protected static final int defaultSocketTime = 30 * 1000;
    protected static final String ContentType = "Content-Type";
    protected static final String ACCEPT_ENCODING = "Accept-Encoding";
    protected static final String GcsId = "GcsId";
    protected static final String Authorization = "Authorization";

    private HttpClient() {
    }

    private static class Holder {
        private static HttpClient httpClient = new HttpClient();
    }

    public static HttpClient getInstance() {
        return Holder.httpClient;
    }

    public static Map<Integer, CloseableHttpAsyncClient> httpClientMap = new ConcurrentHashMap<>();

    private static CloseableHttpAsyncClient buildClient() {
        CloseableHttpAsyncClient httpAsyncClient = null;
        try {
            final IOReactorConfig ioReactorConfig = IOReactorConfig.custom()
                    .setSelectInterval(500L)
                    .setIoThreadCount(Runtime.getRuntime()
                            .availableProcessors())
                    .setSoKeepAlive(true)
                    .setSoReuseAddress(true)
                    .setTcpNoDelay(true)
                    .build();
            final ThreadFactory threadFactory = new ThreadFactory() {
                private final AtomicLong COUNT = new AtomicLong(1);

                @Override
                public Thread newThread(@SuppressWarnings("NullableProblems") Runnable r) {
                    return new Thread(r, "HttpClient_I/O_dispatcher" + COUNT.getAndIncrement());
                }
            };
            final ConnectingIOReactor connectingIOReactor = new DefaultConnectingIOReactor(ioReactorConfig,
                    threadFactory);

            final PoolingNHttpClientConnectionManager connManager;

            connManager = new PoolingNHttpClientConnectionManager(connectingIOReactor);

            connManager.setDefaultMaxPerRoute(defaultMaxPerRoute);
            connManager.setMaxTotal(defaultMaxTotal);
            ConnectionConfig connConfig = ConnectionConfig.custom().setCharset(StandardCharsets.UTF_8).build();
            connManager.setDefaultConnectionConfig(connConfig);

            final RequestConfig defaultRequestConfig = RequestConfig.custom()
                    .setConnectionRequestTimeout(defaultConnRequestTime)
                    .setConnectTimeout(defaultConnectTime)
                    .setSocketTimeout(defaultSocketTime).build();

            HttpAsyncClientBuilder builder = HttpAsyncClientBuilder.create()
                    .setDefaultRequestConfig(defaultRequestConfig)
                    .setConnectionManager(connManager);

            httpAsyncClient = builder.build();
            httpAsyncClient.start();
        } catch (Exception e) {
            log.error(e.getMessage(), e);
            throw new RuntimeException(e.getMessage(), e);
        }

        return httpAsyncClient;
    }

    private static CloseableHttpAsyncClient getClient(CustomHttpConfig config) {
        if (!httpClientMap.containsKey(config.hashCode())) {
            CloseableHttpAsyncClient client = buildClient();
            httpClientMap.put(config.hashCode(), client);
            return client;
        } else {
            return httpClientMap.get(config.hashCode());
        }
    }

    /**
     * Post方法执行
     *
     * @param requestUrl     requestUrl
     * @param customHttpConfig 自定义http一次请求过程中的各种时间配置
     * @param contentWrapper 请求参数
     * @param httpHeader     使用map存储httpHeader
     * @return 请求需要获得的数据:默认没数据返回null
     * @throws Exception http请求的所有异常
     */
    public String executePost (@NotNull String requestUrl, CustomHttpConfig customHttpConfig,
                               HttpContentWrapper contentWrapper, Map<String, String> httpHeader) throws Exception {

        Asserts.notBlank(requestUrl, "request url ");
        Asserts.notNull(contentWrapper, "content wrapper can't be null!");
        Asserts.notBlank(contentWrapper.getInterfaceName(), "调用接口名称 ");
        Asserts.check(contentWrapper.getGcsId() != 0L, "gcsId can't equal 0");
        try {
            HttpPost post = buildHttpPostWithHttpHeader(requestUrl, customHttpConfig, contentWrapper, httpHeader);
            return doRequestWithLog(post, customHttpConfig, contentWrapper);
        } catch (Exception e) {
            log.warn(e.getMessage(), e);
            throw e;
        }
    }

    private HttpPost buildHttpPostWithHttpHeader (String requestUrl, CustomHttpConfig customHttpConfig,
                                                  HttpContentWrapper contentWrapper, Map<String, String> httpHeader) throws Exception {

        HttpPost httpPost = buildHttpPost(requestUrl, customHttpConfig, contentWrapper);
        httpHeader.forEach(httpPost::setHeader);
        return httpPost;
    }

    public HttpPost buildHttpPost (@NotNull String requestUrl, CustomHttpConfig customHttpConfig,
                                   HttpContentWrapper contentWrapper) throws Exception {
        HttpPost httpPost = new HttpPost(requestUrl);
        if (customHttpConfig != null) {
            httpPost.setConfig(getRequestConfig(customHttpConfig));
        }
        if (contentWrapper == null) {
            return httpPost;
        }

        // 构造和设置entry
        if (null != contentWrapper.getContentObject()) {
            StringEntity entity = null;
            String encode = contentWrapper.getContentEncode();
            encode = (encode == null ? Consts.UTF_8.name() : encode);
            if (contentWrapper.getContentObject() instanceof Map) {
                //noinspection unchecked
                entity = buildEntityByParams((Map<String, Object>) contentWrapper.getContentObject(), encode);
            } else if (contentWrapper.getContentObject() instanceof String) {
                String entityString = (String) contentWrapper.getContentObject();
                if (StringUtils.isNotBlank(entityString)) {
                    entity = new StringEntity(entityString, encode);
                }
            }
            if (null != entity) {
                httpPost.setEntity(entity);
            }
        }
        // 是否加gcsId这个header
        if (contentWrapper.getGcsId()!=null && contentWrapper.getGcsId() > 0) {
            httpPost.setHeader(GcsId, contentWrapper.getGcsId() + "");
        }
        // 是否做BA验证
        if (contentWrapper.getGcsId() != null && StringUtils.isNotBlank(contentWrapper.getGcsToken())) {
            BasicAuthorizationUtils.generateAuthAndDateHeader(httpPost, contentWrapper.getGcsId(), contentWrapper.getGcsToken());
        }
        // 是否特别制定content-Type
        if (StringUtils.isNotBlank(contentWrapper.getContentType())) {
            httpPost.setHeader(ContentType, contentWrapper.getContentType());
        }
        // 是否特别制定Accept-Encoding
        if (StringUtils.isNotBlank(contentWrapper.getAcceptEncoding())) {
            httpPost.setHeader(ACCEPT_ENCODING, contentWrapper.getAcceptEncoding());
        }
        // 是否特别制定Authorization
        if (StringUtils.isNotBlank(contentWrapper.getAuthorization())) {
            httpPost.setHeader(Authorization, contentWrapper.getAuthorization());
        }
        // 是否加额外的header
        Map<String, String> headerMap = contentWrapper.getHeaderMap();
        if (MapUtils.isNotEmpty(headerMap)) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                httpPost.setHeader(entry.getKey(), entry.getValue());
            }
        }
        return httpPost;
    }

    /**
     * 获取http time config
     *
     * @param customHttpConfig 自定义http time
     * @return
     */
    public RequestConfig getRequestConfig (CustomHttpConfig customHttpConfig) {
        return RequestConfig.custom()
                .setConnectTimeout(customHttpConfig.getConnectTime())
                .setSocketTimeout(customHttpConfig.getSocketTime())
                .setConnectionRequestTimeout(customHttpConfig.getConnectionRequestTime())
                .build();
    }

    private StringEntity buildEntityByParams (Map<String, Object> paramMap, String encode) throws Exception {
        if (MapUtils.isEmpty(paramMap)) {
            return null;
        }

        List<NameValuePair> nameValuePairs = Lists.newArrayList();
        for (String k : paramMap.keySet()) {
            String destValue;
            if (paramMap.get(k) instanceof String) {
                destValue = (String) paramMap.get(k);
            } else {
                destValue = JsonUtils.object2Json(paramMap.get(k), true);
            }
            nameValuePairs.add(new BasicNameValuePair(k, destValue));
        }

        return new UrlEncodedFormEntity(nameValuePairs, encode);
    }

    public String doRequestWithLog (@NotNull HttpRequestBase httpRequestBase, CustomHttpConfig customHttpConfig,
                                    HttpContentWrapper contentWrapper)
            throws Exception {
        long beginTime = System.currentTimeMillis();
        String responseString = null;
        try {
            log.info("HttpClient doRequestWithLog HttpRequestBase:{}", JsonUtils.object2Json(httpRequestBase));
            HttpResponse response = getHttpResponse(httpRequestBase, customHttpConfig);
            log.info("HttpClient doRequestWithLog HttpResponse:{}", JsonUtils.object2Json(response));
            final String encode = contentWrapper.getContentEncode();
            try {
                HttpEntity entity = response.getEntity();
                if (entity != null) {
                    responseString = EntityUtils.toString(entity, encode);
                }
            } catch (Exception e) {
                log.warn(String.format("HttpClient.doRequest get response error, url:%s",
                        httpRequestBase.getURI()), e);
            } finally {
                HttpClientUtils.closeQuietly(response);
            }
        } catch (SocketTimeoutException e) {
            log.warn(String.format("HttpClient.doRequest SocketTimeoutException, url:%s, cost time:%s",
                    httpRequestBase.getURI(), System.currentTimeMillis() - beginTime), e);
            throw e;
        } catch (Exception e) {
            log.warn(String.format("HttpClient.doRequest Exception, url:%s, cost time:%s",
                    httpRequestBase.getURI(), System.currentTimeMillis() - beginTime), e);
            if (e.getCause() instanceof ConnectException) {
                throw (Exception) e.getCause();
            }
            throw e;
        } finally {
            httpRequestBase.releaseConnection();
        }
        return responseString;
    }

    protected HttpResponse getHttpResponse (HttpRequestBase httpRequestBase, CustomHttpConfig customHttpConfig) throws Exception{
        try {
            long curTime = System.currentTimeMillis();
            CloseableHttpAsyncClient httpAsyncClient = getClient(customHttpConfig);
            HttpResponse httpResponse;
            int futureSocketTimeout = customHttpConfig == null ? defaultSocketTime : customHttpConfig.getSocketTime();
            httpResponse = httpAsyncClient.execute(httpRequestBase, null).get(futureSocketTimeout, TimeUnit.MILLISECONDS);
            if (httpRequestBase != null) {
                URI uri = httpRequestBase.getURI();
                final String key = uri.getHost() + uri.getPath();
                final long take = System.currentTimeMillis() - curTime;
                log.info("http request time cost:{},{} ms", key, take);
            }
            if (httpResponse != null) {
                log.debug(" response code:{} ,header :{}", httpResponse.getStatusLine().getStatusCode(), JsonUtils.object2Json(httpRequestBase.getAllHeaders()));
            }
            return httpResponse;
        } catch (TimeoutException e) {
            log.warn("TimeoutException: " + e.getMessage(), e);
            throw new SocketTimeoutException("Read timed out");
        } catch (ExecutionException e) {
            Throwable cause = e.getCause();
            if (cause != null && (cause instanceof IOException) && StringUtils.isNotBlank(cause.getMessage()) && cause.getMessage().contains("Connection reset")) {
                throw new SocketTimeoutException("Connection reset " + cause.getMessage());
            }
            if (cause != null && (cause instanceof ConnectionClosedException)) {
                throw new ConnectionClosedException("Connection closed");
            }
            if (cause != null && (cause instanceof UnknownHostException) && StringUtils.isNotBlank(cause.getMessage())) {
                throw (UnknownHostException) cause;
            }
            if (cause != null && (cause instanceof NoRouteToHostException)) {
                throw new NoRouteToHostException("No route to host");
            }
            throw e;
        }
    }

}
