package com.epam.rd.java.basic.finalProject.util;

import org.apache.log4j.Logger;

import java.io.IOException;
import java.util.Properties;

public class PropertiesUtils {

    private static final Logger LOGGER = Logger.getLogger(PropertiesUtils.class);

    public static Properties getProperties(String fileName) {
        Properties properties = new Properties();
        try {
            properties.load(PropertiesUtils.class.getClassLoader().getResourceAsStream(fileName));
        } catch (IOException e) {
            LOGGER.debug("Cant get properties", e);
        }
        return properties;
    }

}
