package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.test.TestBase;
import com.automation.qa.ttafuicore.util.Constant;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.LoggingPreferences;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.logging.Level;
import java.util.logging.Logger;

/**
 * This class is for set up the driver connection
 * Created by DilshanF on 10/23/2018.
 */
public class DriverConnection {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverConnection.class));

    protected static DesiredCapabilities capability;
    protected static String node;

    /**
     * Get Currently Running Driver Connection
     *
     * @return WebDriver - Currently Running WebDriver Connection
     * @throws Exception
     */
    public static ThreadLocal<RemoteWebDriver> getDeriverInstance() throws Exception {
        if (getDriver() == null) {
            if (Constant.GRID_MODE.equals("on")) {
                enableGridMode();
            } else {
                setWebDriverLocation();
                setDesiredCapabilities();
                setWebBrowserInstance();
            }
        }
        setWebDriverSettings();
        LoggingPreferences logPrefs = new LoggingPreferences();
        logPrefs.enable(LogType.BROWSER, Level.ALL);
        LOGGER.info("TTAF MESSAGE: Created Remote web driver session");
        return TestBase.driver;
    }

    /**
     * @return driver object
     */
    public static RemoteWebDriver getDriver() {
        return TestBase.driver.get();
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
                LOGGER.info("TTAF MESSAGE: Failed to  Set the driver locations");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully Set the driver locations");
    }

    /**
     * Enable Browsers Grid Execution Mode
     *
     * @throws Exception
     */
    private static void enableGridMode() throws Exception {
        try {
            node = Constant.hubURL;
            setDesiredCapabilities();
            setPlatform();
            //   setWebDriverLocation();
            if (Constant.BROWSER_NAME.equalsIgnoreCase("ie"))
                capability.setBrowserName("internet explorer");//In the Selenium Grid ie refer as internet explorer
            else
                capability.setBrowserName(Constant.BROWSER_NAME);

            capability.setVersion(Constant.BROWSER_VERSION);
            TestBase.driver.set(new RemoteWebDriver(new URL(node), capability));

        } catch (Throwable e) {
            LOGGER.info("TTAF MESSAGE: Failed to set up Selenium grid.");
            throw new Exception(e.getCause().toString());
        }
        LOGGER.info("TTAF MESSAGE: Successfully set up Selenium grid.");
    }

    /**
     * Set Drivers Location System Properties
     *
     * @throws Exception
     */
    private static void setPlatform() throws Exception {
        switch (Constant.PLATFORM) {
            case "windows":
                capability.setPlatform(Platform.WINDOWS);
                break;
            case "mac":
                capability.setPlatform(Platform.MAC);
                break;
            case "linux":
                capability.setPlatform(Platform.LINUX);
                break;
            default:
                LOGGER.info("TTAF MESSAGE: Failed to set the Platform as: " + Constant.PLATFORM);
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully set the Platform as: " + Constant.PLATFORM);
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
                } catch (Exception e) {
                    throw new Exception(e.getCause().toString());
                }
            case "safari":
                try {
                    capability = DesiredCapabilities.safari();
                    capability.setJavascriptEnabled(true);
                    capability.setCapability("unexpectedAlertBehaviour", "accept");
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "ie":
                try {
                    capability = DesiredCapabilities.internetExplorer();
                    //"Enhanced Protected Mode" must be disabled for IE 10 and higher
                    capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS, true);
                    capability.setCapability("requireWindowFocus", true);
                    capability.setCapability(InternetExplorerDriver.INITIAL_BROWSER_URL, true);
                    capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                    capability.setCapability(InternetExplorerDriver.ENABLE_PERSISTENT_HOVERING, false);
                    capability.setCapability("ignoreZoomSetting", true);
                    // changing requireWindowFocus to default value 'false' to avoid window or
                    // page freeze issue when the focus is not on the window
                    capability.setCapability(InternetExplorerDriver.REQUIRE_WINDOW_FOCUS, false);
                    capability.setCapability(InternetExplorerDriver.NATIVE_EVENTS, false);
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            default:
                LOGGER.info("TTAF MESSAGE: Failed to set Browser Capabilities.");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully set Browser Capabilities.");
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
                    TestBase.driver.set(new ChromeDriver(capability));
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "firefox":
                try {
                    TestBase.driver.set(new FirefoxDriver(capability));
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "safari":
                try {
                    TestBase.driver.set(new SafariDriver(capability));
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            case "ie":
                try {
                    TestBase.driver.set(new InternetExplorerDriver(capability));
                    break;
                } catch (Throwable e) {
                    throw new Exception(e.getCause().toString());
                }
            default:
                LOGGER.info("TTAF MESSAGE: Failed to create a " + Constant.BROWSER_NAME + " browser instance.");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully create a " + Constant.BROWSER_NAME + " browser instance.");
    }

    /**
     * Setup Basic WebDriver Browser Settings
     *
     * @throws Exception
     */
    private static void setWebDriverSettings() throws Exception {
        try {
            LOGGER.info("TTAF MESSAGE: Initiate " + Constant.BROWSER_NAME.toUpperCase() + " Driver");

            getDriver().manage().timeouts().implicitlyWait(Constant.TIMEOUT_IMPLICIT, TimeUnit.MILLISECONDS);
            getDriver().manage().window().maximize();
            getDriver().manage().deleteAllCookies();
            getDriver().navigate().to(Constant.URL);

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
        getDriver().close();
        TestBase.driver = null;
    }
}
