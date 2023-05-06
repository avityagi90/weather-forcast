package com.weather.weatherforcast.cache;

import com.weather.weatherforcast.response.WeatherForcastResponse;

import org.springframework.stereotype.Component;

import java.util.HashMap;
import java.util.Map;

import javax.annotation.PostConstruct;

@Component
public class WeatherCache {
    Map<String, WeatherForcastResponse> weatherCache;

    @PostConstruct
    public void initialize(){
        weatherCache = new HashMap<>();
    }

    //key in the form of city_date
    public WeatherForcastResponse getCityData(String city){
        return weatherCache.get(city);
    }

    public void setCityData(String city, WeatherForcastResponse weatherForcastResponse){
        weatherCache.put(city, weatherForcastResponse);
    }

}