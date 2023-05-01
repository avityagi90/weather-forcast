package com.weather.weatherforcast;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;

@SpringBootApplication
@ComponentScan({
		"com.weather.weatherforcast.controller",
		"com.weather.weatherforcast.service",
		"com.weather.weatherforcast.http",
		"com.weather.weatherforcast.config"})
public class WeatherForcastApplication {

	public static void main(String[] args) {
		SpringApplication.run(WeatherForcastApplication.class, args);
	}

}
