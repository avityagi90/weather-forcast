package com.weather.weatherforcast.response;


import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Builder
@Data
@NoArgsConstructor
@AllArgsConstructor
public class DayWeatherPrediction {
    private String date;
    private String highTemperature;
    private String lowTemperature;
    private List<String> prediction;
}
