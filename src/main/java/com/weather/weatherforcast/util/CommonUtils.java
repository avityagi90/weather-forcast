package com.weather.weatherforcast.util;


import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;

import com.fasterxml.jackson.annotation.JsonAutoDetect;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.PropertyAccessor;

import org.apache.commons.lang3.StringUtils;

import java.io.IOException;
import java.lang.reflect.Type;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.Collections;
import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.TimeZone;

import com.fasterxml.jackson.databind.AnnotationIntrospector;
import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.MapperFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.introspect.JacksonAnnotationIntrospector;
import com.fasterxml.jackson.databind.type.TypeFactory;

public class CommonUtils {

    private static final ObjectMapper fasterMapper;
    public static final Gson GSON;

    static {

        //Use this ObjectMapper when you  dont want Deserialization to fail when unknown attribute is passed.
        fasterMapper = new ObjectMapper();
        fasterMapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
        fasterMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);
        fasterMapper.setVisibility(PropertyAccessor.SETTER, JsonAutoDetect.Visibility.ANY);
        fasterMapper.setVisibility(PropertyAccessor.GETTER, JsonAutoDetect.Visibility.NONE);
        fasterMapper.setVisibility(PropertyAccessor.FIELD, JsonAutoDetect.Visibility.ANY);
        fasterMapper.setSerializationInclusion(JsonInclude.Include.NON_NULL);

        GSON = new Gson();
    }

    public static Integer getIntValueFromHeaders(Map<String, String> httpHeaders, String headerName) {
        try {
            if (httpHeaders.get(headerName) != null) {
                return Integer.parseInt(httpHeaders.get(headerName));
            }
        } catch (NumberFormatException ex){
            //handle later
        }
        return null;
    }

    public static Integer getIntValue(String intStr) {
        try {
                return Integer.parseInt(intStr);
        } catch (NumberFormatException ex){
            //handle later
        }
        return null;
    }

    public static <T> T cast(String json, Class<T> type) throws IOException {
        if(StringUtils.isBlank(json)) {
            return null;
        }
        return fasterMapper.readValue(json, type);
    }

    public static <T> List<T> castList(String jsonArray, Class<T> clazz) {
        if(StringUtils.isBlank(jsonArray)) {
            return Collections.emptyList();
        }
        if(clazz == null) {
            return Collections.emptyList();
        }
        Type typeOfT = TypeToken.getParameterized(List.class, clazz).getType();
        return GSON.fromJson(jsonArray, typeOfT);
    }

    public static String getLocalDate(long dt) {
        //Time given in seconds
        Date date = new Date(dt*1000);
        DateFormat format = new SimpleDateFormat("dd/MM/yyyy HH:mm:ss");
        format.setTimeZone(TimeZone.getTimeZone("Etc/UTC"));
        String formatted = format.format(date);
        return formatted;
    }
}
