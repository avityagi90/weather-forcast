package com.weather.weatherforcast.service;

import com.weather.weatherforcast.cache.WeatherCache;
import com.weather.weatherforcast.constants.ApplicationConstants;
import com.weather.weatherforcast.exception.WeatherForcastException;
import com.weather.weatherforcast.http.CallType;
import com.weather.weatherforcast.http.OpenWeatherApiCallHelper;
import com.weather.weatherforcast.response.WeatherForcastResponse;
import com.weather.weatherforcast.response.openweather.DayRecord;
import com.weather.weatherforcast.response.openweather.OpenWeatherResponse;
import com.weather.weatherforcast.service.helper.DaywiseDataAggregationHelper;
import com.weather.weatherforcast.util.CommonUtils;

import org.apache.http.client.methods.CloseableHttpResponse;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Calendar;
import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Objects;
import java.util.stream.Collectors;

@Component
public class WeatherForcastService {

    @Autowired
    private OpenWeatherApiCallHelper openWeatherApiCallHelper;

    @Autowired
    private DaywiseDataAggregationHelper daywiseDataAggregationHelper;

    @Autowired
    private WeatherCache weatherCache;

    public int getNumberOfDays(Map<String, String> httpHeaders) {
        Integer numberOfDaysFromHeader = CommonUtils
                .getIntValueFromHeaders(httpHeaders, ApplicationConstants.HEADER_NAME_DAYS);
        if (Objects.nonNull(numberOfDaysFromHeader)) {
            return numberOfDaysFromHeader;
        }
        return ApplicationConstants.DEFAULT_DAYS;
    }

    public WeatherForcastResponse getCityWeatherForcastForGivenDays(String cityName, int numberOfDays)
            throws IOException, WeatherForcastException {

        //get data from open weather API
        Map<String, Object> params = openWeatherApiCallHelper.buildRequestParams(cityName, numberOfDays, ApplicationConstants.TEMP_UNIT_CELSIUS);
        Map<String, String> headers = new HashMap<>();
        CloseableHttpResponse closeableHttpResponse = null;
        try {
            closeableHttpResponse = openWeatherApiCallHelper
                    .sendGetRequest(params, headers, CallType.FORECAST_CITY);
        } catch (Exception ex){
            //log exception
            return getFromCacheResiliency(cityName);
        }
        //Parse response from the upstream into DTO (Local/provided)
        OpenWeatherResponse openWeatherResponse = openWeatherApiCallHelper.getResponseObject(closeableHttpResponse,
                OpenWeatherResponse.class);

        //Used for testing
        //OpenWeatherResponse openWeatherResponse = CommonUtils.cast(str, OpenWeatherResponse.class);

        Map<Date, List<DayRecord>> map = daywiseDataAggregationHelper
                .getDaywiseAggregatedData(openWeatherResponse);

        Map<Date, List<DayRecord>> filteredMap = getFilteredMap(map, numberOfDays);



        WeatherForcastResponse weatherForcastResponse = daywiseDataAggregationHelper
                .prepareDayWiseFoprcasting(filteredMap);

        //Adding to cache for resiliency
        weatherCache.setCityData(cityName, weatherForcastResponse);

        return weatherForcastResponse;
    }

    private WeatherForcastResponse getFromCacheResiliency(String cityName) {
        return weatherCache.getCityData(cityName);
    }

    private Map<Date, List<DayRecord>> getFilteredMap(Map<Date, List<DayRecord>> map, int numberOfDays) {
        Date currentDate = new Date(System.currentTimeMillis());
        Calendar calendar = Calendar.getInstance();
        Date startDate = calendar.getTime();
        calendar.set(Calendar.DAY_OF_MONTH, calendar.get(Calendar.DAY_OF_MONTH) + numberOfDays);
        Date endDate = calendar.getTime();

        List<Date> dates = map.keySet().stream().sorted()
                .filter(date -> date.after(currentDate) && date.before(endDate))
                .collect(Collectors.toList());

        return map.entrySet().stream()
                .filter(entry -> dates.contains(entry.getKey()))
                .collect(Collectors.toMap(entry -> entry.getKey(), entry -> entry.getValue()));
    }

    //Used for testing
    //static String str = "{\"cod\":\"200\",\"message\":0,\"cnt\":12,\"list\":[{\"dt\":1682920800,\"main\":{\"temp\":283.68,\"feels_like\":283.06,\"temp_min\":283.18,\"temp_max\":283.68,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1013,\"humidity\":87,\"temp_kf\":0.5},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":72},\"wind\":{\"speed\":2.21,\"deg\":260,\"gust\":4.94},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 06:00:00\"},{\"dt\":1682931600,\"main\":{\"temp\":286.19,\"feels_like\":285.48,\"temp_min\":286.19,\"temp_max\":287.32,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1013,\"humidity\":74,\"temp_kf\":-1.13},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":72},\"wind\":{\"speed\":2.93,\"deg\":288,\"gust\":4.02},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 09:00:00\"},{\"dt\":1682942400,\"main\":{\"temp\":288.41,\"feels_like\":287.71,\"temp_min\":288.41,\"temp_max\":288.41,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1014,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":4.04,\"deg\":305,\"gust\":5.39},\"visibility\":10000,\"pop\":0.48,\"rain\":{\"3h\":0.57},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 12:00:00\"},{\"dt\":1682953200,\"main\":{\"temp\":289.72,\"feels_like\":288.92,\"temp_min\":289.72,\"temp_max\":289.72,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1014,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":93},\"wind\":{\"speed\":4.39,\"deg\":327,\"gust\":6.49},\"visibility\":10000,\"pop\":0.46,\"rain\":{\"3h\":0.15},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 15:00:00\"},{\"dt\":1682964000,\"main\":{\"temp\":288.7,\"feels_like\":288.09,\"temp_min\":288.7,\"temp_max\":288.7,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":1015,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":3.84,\"deg\":343,\"gust\":6.57},\"visibility\":10000,\"pop\":0.46,\"rain\":{\"3h\":0.13},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 18:00:00\"},{\"dt\":1682974800,\"main\":{\"temp\":285,\"feels_like\":284.54,\"temp_min\":285,\"temp_max\":285,\"pressure\":1021,\"sea_level\":1021,\"grnd_level\":1018,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":18},\"wind\":{\"speed\":3.26,\"deg\":7,\"gust\":7.61},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-01 21:00:00\"},{\"dt\":1682985600,\"main\":{\"temp\":281.97,\"feels_like\":280.03,\"temp_min\":281.97,\"temp_max\":281.97,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1020,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":40},\"wind\":{\"speed\":3.37,\"deg\":28,\"gust\":6.18},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-02 00:00:00\"},{\"dt\":1682996400,\"main\":{\"temp\":280.91,\"feels_like\":279.1,\"temp_min\":280.91,\"temp_max\":280.91,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1021,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":85},\"wind\":{\"speed\":2.81,\"deg\":29,\"gust\":6.24},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-02 03:00:00\"},{\"dt\":1683007200,\"main\":{\"temp\":280.59,\"feels_like\":278.59,\"temp_min\":280.59,\"temp_max\":280.59,\"pressure\":1026,\"sea_level\":1026,\"grnd_level\":1023,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":58},\"wind\":{\"speed\":3,\"deg\":44,\"gust\":6.01},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 06:00:00\"},{\"dt\":1683018000,\"main\":{\"temp\":283.61,\"feels_like\":282.36,\"temp_min\":283.61,\"temp_max\":283.61,\"pressure\":1027,\"sea_level\":1027,\"grnd_level\":1024,\"humidity\":63,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":3.41,\"deg\":63,\"gust\":4.26},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 09:00:00\"},{\"dt\":1683028800,\"main\":{\"temp\":286.2,\"feels_like\":285,\"temp_min\":286.2,\"temp_max\":286.2,\"pressure\":1027,\"sea_level\":1027,\"grnd_level\":1024,\"humidity\":55,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":90},\"wind\":{\"speed\":2.77,\"deg\":68,\"gust\":2.75},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 12:00:00\"},{\"dt\":1683039600,\"main\":{\"temp\":284.71,\"feels_like\":283.54,\"temp_min\":284.71,\"temp_max\":284.71,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":1025,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":3.35,\"deg\":84,\"gust\":3.09},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 15:00:00\"}],\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lat\":51.5085,\"lon\":-0.1257},\"country\":\"GB\",\"population\":1000000,\"timezone\":3600,\"sunrise\":1682915578,\"sunset\":1682968927}}";
}
