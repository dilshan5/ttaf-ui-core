package com.automation.qa.ttafuicore.util;

import org.apache.log4j.Logger;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.FileReader;
import java.io.IOException;
import java.net.URL;
import java.util.Properties;

//commit to do sample_1
public class PropertyReader {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(PropertyReader.class));
    private static BufferedReader reader;
    private static Properties properties;
    private static URL congfigFile = null;

    static {
        properties = new Properties();
        // the configuration file name
        String fileName = "config/Configuation.properties";
        congfigFile = Constant.class.getClassLoader().getResource(fileName);


        try {
            reader = new BufferedReader(new FileReader(congfigFile.getPath()));
            properties = new Properties();
            try {
                properties.load(reader);
                reader.close();
            } catch (IOException e) {
                e.printStackTrace();
            }
        } catch (FileNotFoundException e) {
            LOGGER.error("Configuration.properties not found at " + fileName, e);
        }
    }
    public static  <E> E getValue(String key) {
        E keyValue;
        return keyValue = (E) properties.getProperty(key);
    }

}
