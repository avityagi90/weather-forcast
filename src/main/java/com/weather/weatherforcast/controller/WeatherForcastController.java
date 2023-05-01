package com.weather.weatherforcast.controller;

import com.weather.weatherforcast.constants.ApplicationConstants;
import com.weather.weatherforcast.exception.WeatherForcastException;
import com.weather.weatherforcast.response.WeatherForcastResponse;
import com.weather.weatherforcast.service.WeatherForcastService;
import com.weather.weatherforcast.util.CommonUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;
import java.util.Map;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.media.Schema;


@RestController
@RequestMapping("/weatherforcast")
@Api(value = "WeatherForcastAPI", protocols = "http")
public class WeatherForcastController {


    @Autowired
    private WeatherForcastService weatherForcastService;

    @GetMapping(value = "/v1/city/{cityName}",
            produces = MediaType.APPLICATION_JSON_VALUE)
    @ResponseBody
    @ApiOperation(value = "To get weather prediction for a city for next 3 days by default Or for the numOfDays", response = WeatherForcastResponse.class,
    code = 200)
    public WeatherForcastResponse getWeatherForcasting(
            @Parameter(name = "cityName", in = ParameterIn.PATH, required = true,
                    description = "City Name for Weather Prediction", allowEmptyValue = false,
                    schema = @Schema(type = "String")) @PathVariable("cityName") String cityName,
            @Parameter(name = "numOfDays", in = ParameterIn.QUERY, required = false,
                    description = "Number of Days for Weather Prediction", allowEmptyValue = false,
                    schema = @Schema(type = "String")) @RequestParam("numOfDays") String numOfDays,
                                                       @RequestHeader(required = false) Map<String, String> httpHeaders)
            throws WeatherForcastException, IOException {
        //log request
        //get Response
        Integer numOfDaysFromParams = CommonUtils.getIntValue(numOfDays);
        int numberOfDays = numOfDaysFromParams != null ? numOfDaysFromParams : ApplicationConstants.DEFAULT_DAYS;
        WeatherForcastResponse weatherForcastResponseList = weatherForcastService
                .getCityWeatherForcastForGivenDays(cityName, numberOfDays);
        //return response
        return weatherForcastResponseList;
    }


}


