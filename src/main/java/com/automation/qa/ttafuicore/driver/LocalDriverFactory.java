package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.logging.Logger;

/**
 * Created by DilshanF on 11/14/2018.
 */
public class LocalDriverFactory {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(LocalDriverFactory.class));

    protected static DesiredCapabilities capability;
    protected static String node;

    public static RemoteWebDriver createInstance(String browserName, String browserVersion, String platform) {
        RemoteWebDriver driver = null;
        browserName = (browserName != null) ? browserName : "chrome";

        setDesiredCapabilities(browserName);//set browser capabilities

        if (Constant.GRID_MODE.equals("on")) {
            setPlatform(platform);// set the running platform
            if (browserName.equalsIgnoreCase("ie"))
                capability.setBrowserName("internet explorer");//In the Selenium Grid ie refer as internet explorer
            else
                capability.setBrowserName(browserName);

            node = Constant.hubURL;
            capability.setVersion(browserVersion);
            try {
                driver = new RemoteWebDriver(new URL(node), capability);
                LOGGER.info("TTAF MESSAGE: Successfully set up Selenium grid.");
            } catch (Exception e) {
            }
        } else {
            setWebDriverLocation(browserName);
            driver = createLocalInstance(browserName);
        }

        return driver;
    }

    private static RemoteWebDriver createLocalInstance(String browserName) {
        RemoteWebDriver driver;
        switch (browserName) {
            case "chrome":
                driver = new ChromeDriver(capability);
                break;
            case "firefox":
                driver = new FirefoxDriver(capability);
                break;
            case "safari":
                driver = new SafariDriver(capability);
                break;
            case "ie":
                driver = new InternetExplorerDriver(capability);
                break;
            default:
                driver = new ChromeDriver();
                break;
        }
        return driver;
    }

    /**
     * Set Drivers Location System Properties
     */
    private static void setPlatform(String platform) {
        switch (platform) {
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
     */
    private static void setDesiredCapabilities(String browserName) {
        capability = new DesiredCapabilities();
        switch (browserName) {
            case "chrome":
                HashMap<String, Object> chromePrefs = new HashMap<String, Object>();
                chromePrefs.put("profile.default_content_settings.popups", 0);
                ChromeOptions options = new ChromeOptions();
                options.setExperimentalOption("prefs", chromePrefs);
                capability = DesiredCapabilities.chrome();
                capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capability.setCapability(ChromeOptions.CAPABILITY, options);
                System.out.println("Success : setDesiredCapabilities");
                break;
            case "firefox":
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
            case "safari":
                capability = DesiredCapabilities.safari();
                capability.setJavascriptEnabled(true);
                capability.setCapability("unexpectedAlertBehaviour", "accept");
                break;
            case "ie":
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
            default:
                LOGGER.info("TTAF MESSAGE: Failed to set Browser Capabilities.");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully set Browser Capabilities.");
    }

    /**
     * Set Drivers Location System Properties
     */
    private static void setWebDriverLocation(String browserName) {
        URL driverLocation = Constant.class.getClassLoader().getResource("drivers/");
        switch (browserName) {
            case "chrome":
                System.setProperty("webdriver.chrome.driver", driverLocation.getPath() + "chromedriver.exe");
                break;
            case "firefox":
                System.setProperty("webdriver.gecko.driver", driverLocation.getPath() + "geckodriver.exe");
                break;
            default:
                LOGGER.info("TTAF MESSAGE: Failed to  Set the driver locations");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully Set the driver locations");
    }

}

