package com.automation.qa.ttafuicore.util;

import java.net.URL;
import java.util.Properties;
import java.util.logging.Logger;

/**
 * Constant.java - properties accessed as constants through out the application.( Global Access)
 *
 * Created by DilshanF on 10/23/2018.
 */
public class Constant {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(Constant.class));
    private static final String DRIVER_TYPE_KEY = "driverType";
    private static final String DRIVER_LOCATION_KEY = "driverLocation";
    private static final String BROWSER_NAME_KEY = "browser";
    private static final String TIMEOUT_IMPLICIT_KEY = "implicitWaitTime";
    private static final String URL_KEY = "url";

    private static Properties properties;
    public static String DRIVER_TYPE;
    public static String DRIVER_LOCATION;
    public static String BROWSER_NAME;
    public static int TIMEOUT_IMPLICIT;
    public static String URL;

    static {
        try {
            loadXmlProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading  values to global variables.
     * @throws Exception
     */
    public static void loadXmlProperties() throws Exception {
        FrameworkProperties loadProperties = new FrameworkProperties();
        URL congfigFile = null;
        URL driverLocation = null;
        congfigFile = Constant.class.getClassLoader().getResource("config/Configuation.properties");
        driverLocation = Constant.class.getClassLoader().getResource("drivers/");
        properties = loadProperties.readProjEnvConfig(congfigFile.getPath());


        try {
            // Display elements and assign values to public variables so it could be accessed globally.
            DRIVER_LOCATION = (properties.getProperty(DRIVER_LOCATION_KEY) == null ? driverLocation.getPath() + "chromedriver.exe" : driverLocation.getPath() + properties.getProperty(DRIVER_LOCATION_KEY));
            DRIVER_TYPE = (properties.getProperty(DRIVER_TYPE_KEY) == null ? "webdriver.chrome.driver" : properties.getProperty(DRIVER_TYPE_KEY));
            ;
            BROWSER_NAME = (properties.getProperty(BROWSER_NAME_KEY) == null ? "chrome" : properties.getProperty(BROWSER_NAME_KEY));
            URL = properties.getProperty(URL_KEY);
            TIMEOUT_IMPLICIT = Integer.parseInt(properties.getProperty(TIMEOUT_IMPLICIT_KEY));
        } catch (Exception e) {
        }

    }
}
