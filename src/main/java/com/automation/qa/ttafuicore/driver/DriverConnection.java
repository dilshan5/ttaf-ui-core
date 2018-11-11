package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.firefox.internal.ProfilesIni;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;

import java.net.URL;
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
        URL driverLocation = Constant.class.getClassLoader().getResource("drivers/");
        switch (Constant.BROWSER_NAME) {
            case "chrome":
                try {
                    System.setProperty("webdriver.chrome.driver", driverLocation.getPath() + "chromedriver.exe");
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "firefox":
                try {
                    System.setProperty("webdriver.gecko.driver", driverLocation.getPath() + "geckodriver.exe");
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            default:
                System.exit(1);
                break;
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
            case "firefox":
                try {
                    FirefoxProfile firefoxProfile = new FirefoxProfile();
                    firefoxProfile.setPreference("enableNativeEvents", true);
                    firefoxProfile.setAssumeUntrustedCertificateIssuer(true);
                    firefoxProfile.setPreference("browser.download.folderList", 2);
                    firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                    capability = DesiredCapabilities.firefox();
                    //still run your existing tests on Firefox 46+ without modifying your tests.
                    capability.setCapability("marionette", true);
                    capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    capability.setCapability(FirefoxDriver.PROFILE, firefoxProfile);
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
