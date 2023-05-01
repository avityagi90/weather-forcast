package com.weather.weatherforcast.http;

import com.weather.weatherforcast.exception.ErrorCode;
import com.weather.weatherforcast.exception.WeatherForcastException;
import com.weather.weatherforcast.util.CommonUtils;

import org.apache.commons.io.IOUtils;
import org.apache.http.HttpRequest;
import org.apache.http.client.config.RequestConfig;
import org.apache.http.client.methods.CloseableHttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.client.methods.HttpPost;
import org.apache.http.client.methods.HttpRequestBase;
import org.apache.http.entity.StringEntity;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.impl.conn.PoolingHttpClientConnectionManager;
import org.apache.http.util.EntityUtils;

import org.springframework.web.util.UriComponentsBuilder;

import java.io.IOException;
import java.net.URI;
import java.nio.charset.Charset;
import java.util.List;
import java.util.Map;

public abstract class HttpUtil {

    private CloseableHttpClient client = null;

    protected abstract String getServiceUrl();

    protected abstract String getAppId();

    protected abstract int getConnectionTimeout();

    protected abstract int getMaxConnections();

    protected abstract Map<String, Object> buildRequestHeaders(String cityName, Integer count);

    protected abstract Map<String, Object> buildRequestParams(String cityName, Integer count, String unit);

    protected CloseableHttpClient getClient() {
        if (client == null) {
            PoolingHttpClientConnectionManager poolingHttpClientConnectionManager = new PoolingHttpClientConnectionManager();
            poolingHttpClientConnectionManager.setMaxTotal(getMaxConnections());
            poolingHttpClientConnectionManager.setDefaultMaxPerRoute(getMaxConnections());

            RequestConfig requestConfig = RequestConfig.custom().
                    setConnectionRequestTimeout(getConnectionTimeout() * 1000)
                    .setConnectTimeout(getConnectionTimeout() * 1000).
                    setSocketTimeout(getConnectionTimeout() * 1000).setStaleConnectionCheckEnabled(true).build();

            client = HttpClients.custom()
                    .setConnectionManager(poolingHttpClientConnectionManager)
                    .setDefaultRequestConfig(requestConfig)
                    .build();
        }
        return client;
    }

    protected void close(CloseableHttpResponse response) {
        if (response != null && response.getEntity() != null) {
            EntityUtils.consumeQuietly(response.getEntity());
            try {
                response.close();
            } catch (IOException e) {
                //log error
            }
        }
    }

    public void setCommonRequestHeaders(HttpRequestBase httpReq) {
        httpReq.setHeader("Content-Type", "application/json");
        httpReq.setHeader("WM_CONSUMER.ID", getAppId());
        httpReq.setHeader("Accept", "application/json");
    }

    public CloseableHttpResponse sendGetRequest(Map<String, Object> params,
                                                Map<String, String> headers, CallType callType)
            throws IOException {

        UriComponentsBuilder uriComponentsBuilder = UriComponentsBuilder.fromHttpUrl(getServiceUrl());
        params.entrySet().stream().forEach(e -> uriComponentsBuilder.queryParam(e.getKey(), e.getValue()));
        URI uri = uriComponentsBuilder.build().toUri();

        HttpGet httpReq = new HttpGet(uri);
        setHeaders(httpReq, headers);
        CloseableHttpClient client = getClient();
        //For the call type we can push metrics/timers from this common place
        //To keep track of the upstream call in our dashboards
        CloseableHttpResponse response = client.execute(httpReq);
        return response;
    }

    public CloseableHttpResponse sendPostRequest(String url, String jsonBody, Map<String, String> headers,
                                                 CallType callType) throws IOException {
        URI uri = UriComponentsBuilder.fromHttpUrl(url).build().toUri();
        HttpPost httpReq = new HttpPost(uri);
        setHeaders(httpReq, headers);
        StringEntity entity = new StringEntity(jsonBody);
        httpReq.setEntity(entity);
        CloseableHttpClient client = getClient();
        CloseableHttpResponse response = client.execute(httpReq);
        return response;
    }

    private void setHeaders(HttpRequest httpReq, Map<String, String> headers) {
        for (Map.Entry<String, String> entry : headers.entrySet()) {
            httpReq.setHeader(entry.getKey(), entry.getValue());
        }
    }

    public <T> List<T> getResponseObjectList(CloseableHttpResponse response, Class<T> classType) throws IOException, WeatherForcastException {
        int statusCode = response.getStatusLine().getStatusCode();
        Family statusCodeFamily = familyOf(statusCode);
        if (!statusCodeFamily.equals(Family.SUCCESSFUL)) {
            String errorMessage = "HttpRequest was not successful, Hence not processing it further.";
            throw new WeatherForcastException(ErrorCode.HTTP_CALL_FAIL_EXCEPTION.name(), errorMessage);
        }
        return CommonUtils.castList(IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()), classType);
    }

    public <T> T getResponseObject(CloseableHttpResponse response, Class<T> classType) throws IOException, WeatherForcastException {
        int statusCode = response.getStatusLine().getStatusCode();
        Family statusCodeFamily = familyOf(statusCode);
        if (!statusCodeFamily.equals(Family.SUCCESSFUL)) {
            String errorMessage = "HttpRequest was not successful, Hence not processing it further.";
            throw new WeatherForcastException(ErrorCode.HTTP_CALL_FAIL_EXCEPTION.name(), errorMessage);
        }
        return CommonUtils.cast(IOUtils.toString(response.getEntity().getContent(), Charset.defaultCharset()), classType);
    }

    public Family familyOf(int statusCode) {
        switch (statusCode / 100) {
            case 1:
                return Family.INFORMATIONAL;
            case 2:
                return Family.SUCCESSFUL;
            case 3:
                return Family.REDIRECTION;
            case 4:
                return Family.CLIENT_ERROR;
            case 5:
                return Family.SERVER_ERROR;
            default:
                return Family.OTHER;
        }
    }

    enum Family {
        INFORMATIONAL,
        SUCCESSFUL,
        REDIRECTION,
        CLIENT_ERROR,
        SERVER_ERROR,
        OTHER;

        private Family() {
        }
    }


}
