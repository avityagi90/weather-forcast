//package com.weather.weatherforcast.test;
//
//import com.weather.weatherforcast.response.DayWeatherPrediction;
//import com.weather.weatherforcast.response.WeatherForcastResponse;
//import com.weather.weatherforcast.response.openweather.DayRecord;
//import com.weather.weatherforcast.response.openweather.OpenWeatherResponse;
//import com.weather.weatherforcast.response.openweather.WeatherData;
//import com.weather.weatherforcast.util.CommonUtils;
//
//import org.apache.commons.lang3.StringUtils;
//
//import java.io.IOException;
//import java.text.DateFormat;
//import java.text.ParseException;
//import java.text.SimpleDateFormat;
//import java.util.ArrayList;
//import java.util.Date;
//import java.util.List;
//import java.util.Map;
//import java.util.Objects;
//import java.util.OptionalDouble;
//import java.util.Set;
//import java.util.TimeZone;
//import java.util.stream.Collectors;
//
//import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_SPEED_WIND;
//import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_TEMPRATURE;
//import static com.weather.weatherforcast.constants.ApplicationConstants.HIGH_TEMPRATURE_MESSAGE;
//import static com.weather.weatherforcast.constants.ApplicationConstants.WEATHER_CODE_RAIN;
//import static com.weather.weatherforcast.constants.ApplicationConstants.WEATHER_CODE_THUNDERSTORM;
//
//public class MainEntryForApplicationTest {
//
//    static String str = "{\"cod\":\"200\",\"message\":0,\"cnt\":12,\"list\":[{\"dt\":1682920800,\"main\":{\"temp\":283.68,\"feels_like\":283.06,\"temp_min\":283.18,\"temp_max\":283.68,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1013,\"humidity\":87,\"temp_kf\":0.5},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":72},\"wind\":{\"speed\":2.21,\"deg\":260,\"gust\":4.94},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 06:00:00\"},{\"dt\":1682931600,\"main\":{\"temp\":286.19,\"feels_like\":285.48,\"temp_min\":286.19,\"temp_max\":287.32,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1013,\"humidity\":74,\"temp_kf\":-1.13},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":72},\"wind\":{\"speed\":2.93,\"deg\":288,\"gust\":4.02},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 09:00:00\"},{\"dt\":1682942400,\"main\":{\"temp\":288.41,\"feels_like\":287.71,\"temp_min\":288.41,\"temp_max\":288.41,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1014,\"humidity\":66,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":76},\"wind\":{\"speed\":4.04,\"deg\":305,\"gust\":5.39},\"visibility\":10000,\"pop\":0.48,\"rain\":{\"3h\":0.57},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 12:00:00\"},{\"dt\":1682953200,\"main\":{\"temp\":289.72,\"feels_like\":288.92,\"temp_min\":289.72,\"temp_max\":289.72,\"pressure\":1017,\"sea_level\":1017,\"grnd_level\":1014,\"humidity\":57,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":93},\"wind\":{\"speed\":4.39,\"deg\":327,\"gust\":6.49},\"visibility\":10000,\"pop\":0.46,\"rain\":{\"3h\":0.15},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 15:00:00\"},{\"dt\":1682964000,\"main\":{\"temp\":288.7,\"feels_like\":288.09,\"temp_min\":288.7,\"temp_max\":288.7,\"pressure\":1018,\"sea_level\":1018,\"grnd_level\":1015,\"humidity\":68,\"temp_kf\":0},\"weather\":[{\"id\":500,\"main\":\"Rain\",\"description\":\"light rain\",\"icon\":\"10d\"}],\"clouds\":{\"all\":77},\"wind\":{\"speed\":3.84,\"deg\":343,\"gust\":6.57},\"visibility\":10000,\"pop\":0.46,\"rain\":{\"3h\":0.13},\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-01 18:00:00\"},{\"dt\":1682974800,\"main\":{\"temp\":285,\"feels_like\":284.54,\"temp_min\":285,\"temp_max\":285,\"pressure\":1021,\"sea_level\":1021,\"grnd_level\":1018,\"humidity\":88,\"temp_kf\":0},\"weather\":[{\"id\":801,\"main\":\"Clouds\",\"description\":\"few clouds\",\"icon\":\"02n\"}],\"clouds\":{\"all\":18},\"wind\":{\"speed\":3.26,\"deg\":7,\"gust\":7.61},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-01 21:00:00\"},{\"dt\":1682985600,\"main\":{\"temp\":281.97,\"feels_like\":280.03,\"temp_min\":281.97,\"temp_max\":281.97,\"pressure\":1023,\"sea_level\":1023,\"grnd_level\":1020,\"humidity\":83,\"temp_kf\":0},\"weather\":[{\"id\":802,\"main\":\"Clouds\",\"description\":\"scattered clouds\",\"icon\":\"03n\"}],\"clouds\":{\"all\":40},\"wind\":{\"speed\":3.37,\"deg\":28,\"gust\":6.18},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-02 00:00:00\"},{\"dt\":1682996400,\"main\":{\"temp\":280.91,\"feels_like\":279.1,\"temp_min\":280.91,\"temp_max\":280.91,\"pressure\":1024,\"sea_level\":1024,\"grnd_level\":1021,\"humidity\":86,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04n\"}],\"clouds\":{\"all\":85},\"wind\":{\"speed\":2.81,\"deg\":29,\"gust\":6.24},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"n\"},\"dt_txt\":\"2023-05-02 03:00:00\"},{\"dt\":1683007200,\"main\":{\"temp\":280.59,\"feels_like\":278.59,\"temp_min\":280.59,\"temp_max\":280.59,\"pressure\":1026,\"sea_level\":1026,\"grnd_level\":1023,\"humidity\":81,\"temp_kf\":0},\"weather\":[{\"id\":803,\"main\":\"Clouds\",\"description\":\"broken clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":58},\"wind\":{\"speed\":3,\"deg\":44,\"gust\":6.01},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 06:00:00\"},{\"dt\":1683018000,\"main\":{\"temp\":283.61,\"feels_like\":282.36,\"temp_min\":283.61,\"temp_max\":283.61,\"pressure\":1027,\"sea_level\":1027,\"grnd_level\":1024,\"humidity\":63,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":88},\"wind\":{\"speed\":3.41,\"deg\":63,\"gust\":4.26},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 09:00:00\"},{\"dt\":1683028800,\"main\":{\"temp\":286.2,\"feels_like\":285,\"temp_min\":286.2,\"temp_max\":286.2,\"pressure\":1027,\"sea_level\":1027,\"grnd_level\":1024,\"humidity\":55,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":90},\"wind\":{\"speed\":2.77,\"deg\":68,\"gust\":2.75},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 12:00:00\"},{\"dt\":1683039600,\"main\":{\"temp\":284.71,\"feels_like\":283.54,\"temp_min\":284.71,\"temp_max\":284.71,\"pressure\":1028,\"sea_level\":1028,\"grnd_level\":1025,\"humidity\":62,\"temp_kf\":0},\"weather\":[{\"id\":804,\"main\":\"Clouds\",\"description\":\"overcast clouds\",\"icon\":\"04d\"}],\"clouds\":{\"all\":98},\"wind\":{\"speed\":3.35,\"deg\":84,\"gust\":3.09},\"visibility\":10000,\"pop\":0,\"sys\":{\"pod\":\"d\"},\"dt_txt\":\"2023-05-02 15:00:00\"}],\"city\":{\"id\":2643743,\"name\":\"London\",\"coord\":{\"lat\":51.5085,\"lon\":-0.1257},\"country\":\"GB\",\"population\":1000000,\"timezone\":3600,\"sunrise\":1682915578,\"sunset\":1682968927}}";
//
//    public static void main(String[] args) throws IOException, ParseException {
//
//        String cityName = "london";
//
//        //WeatherForcastController weatherForcastController = new WeatherForcastController();
//
//        //Testing for deserialization
//        OpenWeatherResponse openWeatherResponse = CommonUtils.cast(str, OpenWeatherResponse.class);
//        //System.out.println(openWeatherResponse.toString());
//
//
////        String dt_txt = "2023-05-01";
////        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
////        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
////        Date date = format.parse(dt_txt);
////
////        System.out.println(date);
////        System.out.println(date.getTime());
//
//
////        Map<Date, List<DayWeatherPrediction>>
////
//        Map<Date, List<DayRecord>> map = openWeatherResponse.getList()
//                        .stream()
//                        .collect(Collectors
//                                .groupingBy(dayRecord -> getDate(dayRecord.getDt_txt()), Collectors.toList()));
//
//        WeatherForcastResponse weatherForcastResponse = prepareDayWiseFoprcasting(map);
//
//        System.out.println(weatherForcastResponse.toString());
//
//
//
//
//    }
//
//    static Date getDate(String dt_txt){
//        DateFormat format = new SimpleDateFormat("yyyy-MM-dd");
//        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
//        Date date = null;
//        try {
//            date = format.parse(dt_txt);
//        } catch (ParseException e) {
//            e.printStackTrace();
//        }
//        return date;
//    }
//
//    private static WeatherForcastResponse prepareDayWiseFoprcasting(Map<Date, List<DayRecord>> map) {
//        WeatherForcastResponse weatherForcastResponse = new WeatherForcastResponse();
//        List<DayWeatherPrediction> dayWeatherPredictionList = new ArrayList<>();
//        map.entrySet()
//                .forEach(dateListEntry ->
//                        dayWeatherPredictionList.add(prepareDayResponse(dateListEntry.getKey(), dateListEntry.getValue())));
//
//        weatherForcastResponse.setWeatherData(dayWeatherPredictionList);
//        return weatherForcastResponse;
//    }
//
//    private static DayWeatherPrediction prepareDayResponse(Date date, List<DayRecord> dayRecordList) {
//        DayWeatherPrediction.DayWeatherPredictionBuilder builder = DayWeatherPrediction.builder();
//        builder.date(date.toString());
//
//        String minTemp = null;
//        minTemp = getLowTemprature(dayRecordList);
//        builder.lowTemperature(minTemp);
//
//        String maxTemp = getHighTemprature(dayRecordList);
//        builder.highTemperature(maxTemp);
//
//        String maxWind = getMaxWind(dayRecordList);
//        builder.highTemperature(maxTemp);
//
//
//        Set<String> weatherCodes = getCodes(dayRecordList);
//
//        List<String> prediction = predictWeatherData(maxTemp, maxWind, weatherCodes);
//
//        return builder.prediction(prediction).build();
//    }
//
//    private static String getMaxWind(List<DayRecord> dayRecordList) {
//        String maxSpeed = null;
//        OptionalDouble optionalDouble = dayRecordList.stream()
//                .map(dayRecord -> dayRecord.getWind())
//                .map(windData -> windData.getSpeed())
//                .mapToDouble(speed -> Double.parseDouble(speed))
//                .max();
//        if (optionalDouble.isPresent()){
//            maxSpeed = String.valueOf(optionalDouble.getAsDouble());
//        }
//        return maxSpeed;
//    }
//
//    private static String getLowTemprature(List<DayRecord> dayRecordList) {
//        String minTemp = null;
//        OptionalDouble minTempOptional = dayRecordList.stream()
//                .map(dayRecord -> dayRecord.getMain().getTemp_min())
//                .mapToDouble(minTemprature -> Double.parseDouble(minTemprature))
//                .min();
//
//        if (minTempOptional.isPresent())
//            minTemp = String.valueOf(minTempOptional.getAsDouble());
//        return minTemp;
//    }
//
//    private static String getHighTemprature(List<DayRecord> dayRecordList) {
//        String maxTemp = null;
//        OptionalDouble maxTempOptional = dayRecordList.stream()
//        .map(dayRecord -> dayRecord.getMain().getTemp_max())
//        .mapToDouble(maxTemprature -> Double.parseDouble(maxTemprature))
//        .max();
//        if (maxTempOptional.isPresent())
//            maxTemp = String.valueOf(maxTempOptional.getAsDouble());
//        return maxTemp;
//    }
//
//    private static Set<String> getCodes(List<DayRecord> dayRecordList) {
//        return dayRecordList
//                .stream()
//                .flatMap(dayRecord -> dayRecord.getWeather().stream())
//                .map(WeatherData::getMain)
//                .map(code -> StringUtils.lowerCase(code))
//                .collect(Collectors.toSet());
//    }
//
//    private static List<String> predictWeatherData(String maxTemp, String maxWind, Set<String> codes) {
//        List<String> prediction = new ArrayList<>();
//
//
//        if(Objects.nonNull(codes) && !codes.isEmpty()) {
//            if (codes.contains(WEATHER_CODE_THUNDERSTORM)) {
//                prediction.add("Don’t step out! A Storm is brewing!");
//            }
//            if (codes.contains(WEATHER_CODE_RAIN)) {
//                prediction.add("Carry umbrella");
//            }
//        }
//
//        //High temprature message
//        if(Objects.nonNull(maxTemp)){
//            Float temp = Float.parseFloat(maxTemp);
//            if (temp > HIGH_TEMPRATURE){
//                prediction.add(HIGH_TEMPRATURE_MESSAGE);
//            }
//        }
//
//        //windy message
//        if (Objects.nonNull(maxWind)) {
//            Float speed = Float.parseFloat(maxWind);
//            if (speed > HIGH_SPEED_WIND) {
//                prediction.add("It’s too windy, watch out!");
//            }
//        }
//
//        if (prediction.isEmpty()){
//            prediction.add("Just a regular day");
//        }
//
//        return prediction;
//    }
//
//
//
//}
