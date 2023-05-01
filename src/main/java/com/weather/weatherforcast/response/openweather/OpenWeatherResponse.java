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
public class OpenWeatherResponse {
    int cod;
    int count;
    List<DayRecord> list;

}
