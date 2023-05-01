package com.weather.weatherforcast.service.helper;

import org.springframework.stereotype.Component;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Set;

import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_SPEED_WIND;
import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_TEMPRATURE;
import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_TEMPRATURE_MESSAGE;
import static com.weather.weatherforcast.constants.ApplicationConstants.WEATHER_CODE_CLOUDS;
import static com.weather.weatherforcast.constants.ApplicationConstants.WEATHER_CODE_RAIN;
import static com.weather.weatherforcast.constants.ApplicationConstants.WEATHER_CODE_THUNDERSTORM;

@Component
public class PredictionHelper {

    public List<String> predictWeatherData(String maxTemp, String maxWind, Set<String> codes) {
        List<String> prediction = new ArrayList<>();

        if(Objects.nonNull(codes) && !codes.isEmpty()) {
            if (codes.contains(WEATHER_CODE_THUNDERSTORM)) {
                prediction.add("Don’t step out! A Storm is brewing!");
            }
            if (codes.contains(WEATHER_CODE_RAIN)) {
                prediction.add("Carry umbrella");
            }
            if (codes.contains(WEATHER_CODE_CLOUDS)) {
                prediction.add("A Cloudy Day, Might Rain!");
            }
        }

        //High temprature message
        if(Objects.nonNull(maxTemp)){
            Float temp = Float.parseFloat(maxTemp);
            if (temp > HIGH_TEMPRATURE){
                prediction.add(HIGH_TEMPRATURE_MESSAGE);
            }
        }

        //windy message
        if (Objects.nonNull(maxWind)) {
            Float speed = Float.parseFloat(maxWind);
            if (speed > HIGH_SPEED_WIND) {
                prediction.add("It’s too windy, watch out!");
            }
        }

        if (prediction.isEmpty()){
            prediction.add("Just a regular day");
        }

        return prediction;
    }

}
