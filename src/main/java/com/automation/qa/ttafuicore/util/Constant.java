package com.automation.qa.ttafuicore.util;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;

import java.net.URL;
import java.util.Properties;


/**
 * Constant.java - properties accessed as constants through out the application.( Global Access)
 * <p/>
 * Created by DilshanF on 10/23/2018.
 */
public class Constant {
    private static final Logger LOGGER = LogManager.getLogger(String.valueOf(Constant.class));
    private static final String DRIVER_TYPE_KEY = "driverType";
    private static final String DRIVER_LOCATION_KEY = "driverLocation";
    private static final String BROWSER_NAME_KEY = "browser";
    private static final String TIMEOUT_IMPLICIT_KEY = "implicitWaitTime";
    private static final String URL_KEY = "url";
    private static final String BROWSER_VERSION_KEY = "browserVersion";
    private static final String PLATFORM_KEY = "platform";
    private static final String HUBURL_KEY = "hubURL";
    private static final String GRID_MODE_KEY = "grid-mode";

    private static Properties properties;
    public static String DRIVER_TYPE;
    public static String DRIVER_LOCATION;
    public static String BROWSER_NAME;
    public static int TIMEOUT_IMPLICIT;
    public static String URL;
    public static String BROWSER_VERSION;
    public static String PLATFORM;
    public static String hubURL;
    public static String GRID_MODE;
    public static String SCENARIO_NAME;
    public static String SCENARIO_NAME1;

    static {
        try {
            loadXmlProperties();
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * Loading  values to global variables.
     *
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
            BROWSER_VERSION = properties.getProperty(BROWSER_VERSION_KEY);
            PLATFORM = properties.getProperty(PLATFORM_KEY);
            hubURL = properties.getProperty(HUBURL_KEY);
            GRID_MODE = properties.getProperty(GRID_MODE_KEY).toLowerCase();
            LOGGER.info("Set up Framework variables");
        } catch (Exception e) {
            LOGGER.error("Error: Unable to load framework variables ", e);
        }

    }
}
