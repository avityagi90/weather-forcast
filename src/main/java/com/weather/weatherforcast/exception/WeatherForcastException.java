package com.weather.weatherforcast.exception;

public class WeatherForcastException extends Exception {

    private String errorCode;
    private String errorMessage;
    private boolean isRetryable;

    public WeatherForcastException(String errorCode, String errorMessage) {
        super(errorMessage);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
    }

    public WeatherForcastException(String errorCode, String errorMessage, Throwable cause,
                                   boolean isRetryable) {
        super(errorMessage, cause);
        this.errorCode = errorCode;
        this.errorMessage = errorMessage;
        this.isRetryable = isRetryable;
    }


    public String getErrorCode() {
        return errorCode;
    }

    public String getErrorMessage() {
        return errorMessage;
    }

    public boolean isRetryable() {
        return isRetryable;
    }

    @Override
    public String toString() {
        return "WeatherForcastException{" +
                "errorCode='" + errorCode + '\'' +
                ", errorMessage='" + errorMessage + '\'' +
                ", isRetryable=" + isRetryable +
                '}';
    }
}
