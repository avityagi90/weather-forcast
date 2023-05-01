package com.weather.weatherforcast.service.helper;

import com.weather.weatherforcast.response.DayWeatherPrediction;
import com.weather.weatherforcast.response.WeatherForcastResponse;
import com.weather.weatherforcast.response.openweather.DayRecord;
import com.weather.weatherforcast.response.openweather.OpenWeatherResponse;
import com.weather.weatherforcast.response.openweather.WeatherData;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.OptionalDouble;
import java.util.Set;
import java.util.TimeZone;
import java.util.stream.Collectors;

@Component
public class DaywiseDataAggregationHelper {

    @Autowired
    private PredictionHelper predictionHelper;

    public Map<Date, List<DayRecord>> getDaywiseAggregatedData(OpenWeatherResponse openWeatherResponse) {
        return openWeatherResponse.getList()
                .stream()
                .collect(Collectors
                        .groupingBy(dayRecord -> getDate(dayRecord.getDt_txt()), Collectors.toList()));
    }

    public Date getDate(String dt_txt){
        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        Date date = null;
        try {
            date = format.parse(dt_txt);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return date;
    }

    public WeatherForcastResponse prepareDayWiseFoprcasting(Map<Date, List<DayRecord>> map) {
        WeatherForcastResponse weatherForcastResponse = new WeatherForcastResponse();
        List<DayWeatherPrediction> dayWeatherPredictionList = new ArrayList<>();
        map.entrySet()
                .forEach(dateListEntry ->
                        dayWeatherPredictionList.add(prepareDayResponse(dateListEntry.getKey(), dateListEntry.getValue())));

        weatherForcastResponse.setWeatherData(dayWeatherPredictionList);
        return weatherForcastResponse;
    }

    private DayWeatherPrediction prepareDayResponse(Date date, List<DayRecord> dayRecordList) {
        DayWeatherPrediction.DayWeatherPredictionBuilder builder = DayWeatherPrediction.builder();
        builder.date(date.toString());

        String minTemp = null;
        minTemp = getLowTemprature(dayRecordList);
        builder.lowTemperature(minTemp);

        String maxTemp = getHighTemprature(dayRecordList);
        builder.highTemperature(maxTemp);

        String maxWind = getMaxWind(dayRecordList);
        builder.highTemperature(maxTemp);


        Set<String> weatherCodes = getCodes(dayRecordList);

        List<String> prediction = predictionHelper.predictWeatherData(maxTemp, maxWind, weatherCodes);

        return builder.prediction(prediction).build();
    }

    private String getMaxWind(List<DayRecord> dayRecordList) {
        String maxSpeed = null;
        OptionalDouble optionalDouble = dayRecordList.stream()
                .map(dayRecord -> dayRecord.getWind())
                .map(windData -> windData.getSpeed())
                .mapToDouble(speed -> Double.parseDouble(speed))
                .max();
        if (optionalDouble.isPresent()){
            maxSpeed = String.valueOf(optionalDouble.getAsDouble());
        }
        return maxSpeed;
    }

    private String getLowTemprature(List<DayRecord> dayRecordList) {
        String minTemp = null;
        OptionalDouble minTempOptional = dayRecordList.stream()
                .map(dayRecord -> dayRecord.getMain().getTemp_min())
                .mapToDouble(minTemprature -> Double.parseDouble(minTemprature))
                .min();

        if (minTempOptional.isPresent())
            minTemp = String.valueOf(minTempOptional.getAsDouble());
        return minTemp;
    }

    private String getHighTemprature(List<DayRecord> dayRecordList) {
        String maxTemp = null;
        OptionalDouble maxTempOptional = dayRecordList.stream()
                .map(dayRecord -> dayRecord.getMain().getTemp_max())
                .mapToDouble(maxTemprature -> Double.parseDouble(maxTemprature))
                .max();
        if (maxTempOptional.isPresent())
            maxTemp = String.valueOf(maxTempOptional.getAsDouble());
        return maxTemp;
    }

    private Set<String> getCodes(List<DayRecord> dayRecordList) {
        return dayRecordList
                .stream()
                .flatMap(dayRecord -> dayRecord.getWeather().stream())
                .map(WeatherData::getMain)
                .map(code -> StringUtils.lowerCase(code))
                .collect(Collectors.toSet());
    }

}
