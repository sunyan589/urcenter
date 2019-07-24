package com.yks.urc.fw;

import com.yks.common.util.StringCommonUtils;
import com.yks.urc.vo.ResultVO;
import com.yks.urc.vo.helper.PlsVoHelper;
import com.yks.urc.vo.helper.VoHelper;
import org.apache.http.HttpEntity;
import org.apache.http.NameValuePair;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.entity.UrlEncodedFormEntity;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpUriRequest;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.message.BasicNameValuePair;
import org.apache.http.util.EntityUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;

public class HttpUtility2 {

    private static Logger LOGGER = LoggerFactory.getLogger(HttpUtility2.class);

    private final static String UTF8 = "UTF-8";

    public static CloseableHttpClient httpclient = null;

    static {
        // 初始化线程池
        RequestConfig params = RequestConfig.custom().setConnectTimeout(30000).setConnectionRequestTimeout(30000).setSocketTimeout(30000)
                .setExpectContinueEnabled(true).build();

        PoolingHttpClientConnectionManager pccm = new PoolingHttpClientConnectionManager();
        pccm.setMaxTotal(600); // 连接池最大并发连接数
        pccm.setDefaultMaxPerRoute(100); // 单路由最大并发数
        httpclient = HttpClients.custom().setConnectionManager(pccm).setDefaultRequestConfig(params)
                .build();
    }

    private static Integer iTimeout = 30000;


    public static ResultVO<String> postStringResultVO(String url, String paramBody, Map<String, String> headerMap) {
        try {
            ResultVO<String> rslt = VoHelper.getSuccessResult();
            rslt.data = HttpUtility2.postStringWithException(url, paramBody, headerMap);
            return rslt;
        } catch (Exception ex) {
            return PlsVoHelper.getErrorResult(ex.getMessage());
        }
    }

    public static String postForm(String urlToRequest, Map<String, String> paramMap, Map<String, String> headerMap){
        try {
            return postFormWithException(urlToRequest, paramMap, headerMap);
        } catch (Exception ex) {
            LOGGER.error(String.format("%s\r\n%s\r\n%s", urlToRequest, StringCommonUtils.toJSONString(paramMap), StringCommonUtils.toJSONString(headerMap)), ex);
        }
        return StringUtility.Empty;
    }

    public static String postFormWithException(String urlToRequest, Map<String, String> paramMap, Map<String, String> headerMap) throws Exception {
        Long startTs = System.currentTimeMillis();

        try {
//            LOGGER.info("post-req:url:{},param:{}", urlToRequest, JSON.toJSONString(parameters));
            HttpPost post = new HttpPost(urlToRequest);

            {
                RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(iTimeout)
                        .setConnectionRequestTimeout(iTimeout).setSocketTimeout(iTimeout).build();
                post.setConfig(requestConfig);
            }
            // 设置参数
            if (headerMap != null && !headerMap.isEmpty()) {
                for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                    post.setHeader(entry.getKey(), entry.getValue());
                }
            }

            //装填参数
            List<NameValuePair> nvps = new ArrayList<>();
            if (paramMap != null && !paramMap.isEmpty()) {
                for (Map.Entry<String, String> entry : paramMap.entrySet()) {
                    nvps.add(new BasicNameValuePair(entry.getKey(), entry.getValue()));
                }
            }

            //StringEntity se = new StringEntity(paramBody);
            post.setEntity(new UrlEncodedFormEntity(nvps, "utf-8"));

            String result = invoke(post);
            Long endTs = System.currentTimeMillis();
            Long currentMethodCallTime = endTs - startTs;
            LOGGER.info("url:{},call time {} ms", urlToRequest, currentMethodCallTime);
            if (currentMethodCallTime > 5000) {
                LOGGER.info("所有存活线程=" + Thread.getAllStackTraces().size());
            }
//            LOGGER.info("post-rps:{}", result);
            return result;
        } catch (Exception e) {
            LOGGER.error("[HttpClientUtils][post][Unsupported Encoding Exception]", e);
            throw e;
        }
    }

    public static String postString(String url, String paramBody) {
        return postString(url, paramBody, null);
    }

    public static String postString(String urlToRequest, String paramBody, Map<String, String> headerMap) {
        try {
            return postStringWithException(urlToRequest,paramBody,headerMap);
        } catch (Exception ex) {
            LOGGER.error(String.format("%s\r\n%s\r\n%s", urlToRequest, paramBody, StringCommonUtils.toJSONString(headerMap)), ex);
            return StringUtility.Empty;
        }
    }

    public static String postStringWithException(String urlToRequest, String paramBody, Map<String, String> headerMap) throws Exception {
        Long startTs = System.currentTimeMillis();

//            LOGGER.info("post-req:url:{},param:{}", urlToRequest, JSON.toJSONString(parameters));
        HttpPost post = new HttpPost(urlToRequest);

        {
            RequestConfig requestConfig = RequestConfig.custom().setConnectTimeout(iTimeout)
                    .setConnectionRequestTimeout(iTimeout).setSocketTimeout(iTimeout).build();
            post.setConfig(requestConfig);
        }
        // 设置参数
        if (headerMap != null && !headerMap.isEmpty()) {
            for (Map.Entry<String, String> entry : headerMap.entrySet()) {
                post.setHeader(entry.getKey(), entry.getValue());
            }
        }
        StringEntity se = new StringEntity(paramBody, "utf-8");
        post.setEntity(se);
        String result = invoke(post);
        Long endTs = System.currentTimeMillis();
        Long currentMethodCallTime = endTs - startTs;
        LOGGER.info("url:{},call time {} ms", urlToRequest, currentMethodCallTime);
        if (currentMethodCallTime > 5000) {
            LOGGER.info("所有存活线程=" + Thread.getAllStackTraces().size());
        }
//            LOGGER.info("post-rps:{}", result);
        return result;
    }


    private static String invoke(HttpUriRequest request) throws Exception {
        CloseableHttpResponse response = null;
        try {
            response = httpclient.execute(request);
//            if (response.getStatusLine().getStatusCode() == HttpStatus.SC_OK)
            {
                HttpEntity entity = response.getEntity();
                return EntityUtils.toString(entity, UTF8);
            }
        } catch (Exception e) {
            LOGGER.error(
                    "[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is request exception", e);
            throw e;
        } finally {
            if (response != null) {
                try {
                    response.close();
                } catch (IOException e) {
                    LOGGER.error(
                            "[HttpClientUtils][invoke][method:" + request.getMethod() + " URI:" + request.getURI() + "] is closed exception",
                            e);
                }
            }
        }
//        return StringUtility.Empty;
    }
}
