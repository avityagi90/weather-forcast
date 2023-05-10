package com.weather.weatherforcast;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import org.apache.logging.slf4j.SLF4JLogger;

public class TestClass {

//	BasicConfigurator.configure();

    static Logger LOGGER = LoggerFactory.getLogger(TestClass.class);

    public static void main(String args[])
    {
        LOGGER.info("hello world");
    }

}

