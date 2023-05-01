package com.weather.weatherforcast.http;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

@Component
public class OpenWeatherApiCallHelper extends HttpUtil {

    public static final String CITY_NAME = "q";
    public static final String APP_ID = "appid";
    public static final String COUNT = "cnt";
    public static final String UNITS = "units";

    @Value("${open.weather.url.forecast}")
    private String OPEN_WEATHER_API_FORCAST_URL;

    @Value("${open.weather.url.forcast.appid}")
    private String OPEN_WEATHER_API_FORCAST_APP_ID;

    @Value("${open.weather.url.forcast.connectiontimeout}")
    private int OPEN_WEATHER_API_FORCAST_CONNECTION_TIMEOUT;

    @Value("${open.weather.url.forcast.maxconnections}")
    private int OPEN_WEATHER_API_FORCAST_MAX_CONNECTIONS;

    @Override
    protected String getServiceUrl() {
        return OPEN_WEATHER_API_FORCAST_URL;
    }

    @Override
    protected String getAppId() {
        return OPEN_WEATHER_API_FORCAST_APP_ID;
    }

    @Override
    protected int getConnectionTimeout() {
        return OPEN_WEATHER_API_FORCAST_CONNECTION_TIMEOUT;
    }

    @Override
    protected int getMaxConnections() {
        return OPEN_WEATHER_API_FORCAST_MAX_CONNECTIONS;
    }

    @Override
    protected Map<String, Object> buildRequestHeaders(String cityName, Integer count) {
        return null;
    }

    @Override
    public Map<String, Object> buildRequestParams(String cityName, Integer count, String unit) {
        Map<String, Object> params = new HashMap<>();
        params.put(CITY_NAME, cityName);
        params.put(APP_ID, getAppId());
        params.put(COUNT, (count + 1) * 8);
        params.put(UNITS, unit);
        return params;
    }

}
