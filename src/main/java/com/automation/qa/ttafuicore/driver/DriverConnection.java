package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Logger;

/**
 * This class is for set up the driver connection
 * Created by DilshanF on 10/23/2018.
 */
public class DriverConnection {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverConnection.class));

    protected static WebDriver driver = null;
    protected static DesiredCapabilities capability;

    /**
     * Get Currently Running Driver Connection
     *
     * @return WebDriver - Currently Running WebDriver Connection
     * @throws Exception
     */
    public static WebDriver getDeriverInstance() throws Exception {
        if (driver == null) {
            setWebDriverLocation();
            setDesiredCapabilities();
            setWebBrowserInstance();
        }
        setWebDriverSettings();
        return driver;
    }

    /**
     * Set Drivers Location System Properties
     *
     * @throws Exception
     */
    private static void setWebDriverLocation() throws Exception {
        try {
            System.setProperty(Constant.DRIVER_TYPE, Constant.DRIVER_LOCATION);
        } catch (Throwable e) {
            throw new Exception(e.getCause().toString());
        }
    }

    /**
     * Apply DesiredCapabilities For Specific Browsers
     *
     * @throws Exception
     */
    private static void setDesiredCapabilities() throws Exception {
        capability = new DesiredCapabilities();
        switch (Constant.BROWSER_NAME) {
            case "chrome":
                try {
                    HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                    chromePrefs.put("profile.default_content_settings.popups", 0);
                    ChromeOptions options = new ChromeOptions();
                    options.setExperimentalOption("prefs", chromePrefs);
                    capability = DesiredCapabilities.chrome();
                    capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    capability.setCapability(ChromeOptions.CAPABILITY, options);
                    System.out.println("Success : setDesiredCapabilities");
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
        }
    }

    /**
     * Initiate Specific Browser Instance
     *
     * @throws Exception
     */
    private static void setWebBrowserInstance() throws Exception {
        switch (Constant.BROWSER_NAME) {
            case "chrome":
                try {
                    driver = new ChromeDriver(capability);
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "firefox":
                try {
                    driver = new FirefoxDriver(capability);
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
        }
    }

    /**
     * Setup Basic WebDriver Browser Settings
     *
     * @throws Exception
     */
    private static void setWebDriverSettings() throws Exception {
        try {
            LOGGER.info("TTAF MESSAGE: Initiate " + Constant.BROWSER_NAME.toUpperCase() + " Driver");

            driver.manage().timeouts().implicitlyWait(Constant.TIMEOUT_IMPLICIT, TimeUnit.MILLISECONDS);
            driver.manage().window().maximize();
            driver.manage().deleteAllCookies();
            driver.navigate().to(Constant.URL);

            LOGGER.info("TTAF MESSAGE: Browser Loaded And Navigated To : [" + Constant.URL + " ]");
        } catch (Throwable e) {
            throw new Exception(e.getCause().toString());
        }
    }

    /**
     * Close Currently Running WebDriver Instance
     *
     * @throws Exception
     */
    public static void closeDriver() throws Exception {
        driver.quit();
        driver = null;
    }
}
