package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import org.openqa.selenium.Capabilities;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

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
            setWebDriver(LocalDriverFactory.createInstance(null, null, null));
        }
        LOGGER.info("Getting instance of remote driver" + driver.get().getClass());
        return driver.get();
    }

    /**
     * Setup Basic WebDriver Browser Settings
     *
     * @param driver
     */
    public static void setWebDriver(RemoteWebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Constant.TIMEOUT_IMPLICIT, TimeUnit.MILLISECONDS);
        driver.manage().window().maximize();
        driver.manage().deleteAllCookies();
        driver.navigate().to(Constant.URL);
        DriverManager.driver.set(driver);
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
        String b = cap.getBrowserName();
        String os = cap.getPlatform().toString();
        String v = cap.getVersion();
        return String.format("%s v:%s %s", b, v, os);
    }
}
