package com.weather.weatherforcast.exception;

public enum ErrorCode {
    HTTP_CALL_FAIL_EXCEPTION("HTTP.CALL.FAIL");

    private String errorCode;

    private ErrorCode(String errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorCode() {
        return this.errorCode;
    }
}
