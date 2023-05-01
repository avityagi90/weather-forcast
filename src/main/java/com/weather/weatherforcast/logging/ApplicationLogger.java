package com.weather.weatherforcast.logging;

import org.apache.logging.slf4j.SLF4JLogger;
import org.slf4j.Logger;


public class ApplicationLogger extends SLF4JLogger {
    public ApplicationLogger(String name, Logger logger) {
        super(name, logger);
    }
}

