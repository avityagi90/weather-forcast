package com.weather.weatherforcast.response.openweather;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class DayRecord {
    long dt;
    TempratureData main;
    List<WeatherData> weather;
    WindData wind;
    String dt_txt;
    //"2023-05-01 06:00:00"
}
