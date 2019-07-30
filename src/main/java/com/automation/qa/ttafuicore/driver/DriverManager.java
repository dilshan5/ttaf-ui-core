package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import io.qameta.allure.Allure;
import org.apache.log4j.Logger;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

/**
 * Created by DilshanF on 11/14/2018.
 */
public class DriverManager {

    /*
This simple line does all the multi thread magic.
For more details please refer to the src link above :)
*/
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverManager.class));

    public static RemoteWebDriver getDriver() {
        if (driver.get() == null) {
            // this is need when running tests from IDE
            LOGGER.info("Thread has no WedDriver, creating new one");
            Allure.parameter("Browser", Constant.BROWSER_NAME);
            DriverFactory.createInstance(Constant.BROWSER_NAME, Constant.BROWSER_VERSION, Constant.PLATFORM);
        }
        LOGGER.info("Getting instance of remote driver" + driver.get().getClass());
        return driver.get();
    }


    /**
     * This method is used in the the *WebDriverListeners to change the test name.
     *
     * @return Returns a string containing current browser name, its version and OS name.
     */
    public static String getBrowserInfo() {
        LOGGER.info("Getting browser info");
        // we have to cast WebDriver object to RemoteWebDriver here, because the first one does not have a method
        // that would tell you which browser it is driving.
        Capabilities cap = ((RemoteWebDriver) DriverManager.getDriver()).getCapabilities();
        String browserN = cap.getBrowserName();
        String os = cap.getPlatform().toString();
        String browserV = cap.getVersion();
        return String.format("%s v:%s %s", browserN, browserV, os);
    }
}
