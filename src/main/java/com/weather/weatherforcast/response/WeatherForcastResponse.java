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
public class WeatherForcastResponse {
    private List<DayWeatherPrediction> weatherData;
}
