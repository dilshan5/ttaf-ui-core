package com.automation.qa.ttafuicore.driver;

import com.automation.qa.ttafuicore.util.Constant;
import org.apache.log4j.Logger;
import org.openqa.selenium.Platform;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxProfile;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.BrowserType;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;
import org.openqa.selenium.remote.RemoteWebDriver;
import org.openqa.selenium.safari.SafariDriver;

import java.net.URL;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;


/**
 * Created by DilshanF on 11/14/2018.
 */
public class DriverFactory {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(DriverFactory.class));

    protected static DesiredCapabilities capability;
    protected static String node;
    protected static String oPlatform;

    public static void createInstance(String browserName, String browserVersion, String platform) {
        browserName = (browserName != null) ? browserName : "chrome";
        oPlatform = platform;
        setDesiredCapabilities(browserName);//set browser capabilities
        setPlatform(platform);// set the running platform
        setBrowserName(browserName); //set browser name
        capability.setVersion(browserVersion);

        if (Constant.GRID_MODE.equals("on")) {

            node = Constant.hubURL;

            try {
                DriverManager.driver.set(new RemoteWebDriver(new URL(node), capability));
                LOGGER.info("Selenium Grid is: " + Constant.GRID_MODE);
                LOGGER.info("Selenium Grid url: " + Constant.hubURL);
                LOGGER.info("TTAF MESSAGE: Successfully set up Selenium grid.");
            } catch (Exception e) {
                LOGGER.error("TTAF MESSAGE: Failed to set up Selenium grid.");
            }
        } else {
            setWebDriverLocation(browserName);
            createLocalInstance(browserName);
        }
        setWebDriver();
    }

    private static void setBrowserName(String browserName) {
        switch (browserName) {
            case "chrome":
                capability.setBrowserName(BrowserType.CHROME);
                break;
            case "firefox":
                capability.setBrowserName(BrowserType.FIREFOX);
                break;
            case "safari":
                capability.setBrowserName(BrowserType.SAFARI);
                break;
            case "ie":
                capability.setBrowserName("internet explorer");//In the Selenium Grid ie refer as internet explorer
                break;
            case "edge":
                capability.setBrowserName(BrowserType.EDGE);
                break;
        }
    }

    private static void createLocalInstance(String browserName) {
        switch (browserName) {
            case "chrome":
                DriverManager.driver.set(new ChromeDriver(capability));
                break;
            case "firefox":
                DriverManager.driver.set(new FirefoxDriver(capability));
                break;
            case "safari":
                DriverManager.driver.set(new SafariDriver(capability));
                break;
            case "ie":
                DriverManager.driver.set(new InternetExplorerDriver(capability));
                break;
            case "edge":
                DriverManager.driver.set(new EdgeDriver(capability));
                break;
            default:
                DriverManager.driver.set(new ChromeDriver(capability));
                break;
        }
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
                //firefoxProfile.setPreference("browser.download.dir", DOWNLOAD_PATH);
                firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                firefoxProfile.setPreference("browser.helperApps.neverAsk.saveToDisk", "application/msword, application/csv, application/ris, text/csv, image/png, application/pdf, text/html, text/plain, application/zip, application/x-zip, application/x-zip-compressed, application/download, application/octet-stream");
                firefoxProfile.setPreference("browser.download.manager.showWhenStarting", false);
                firefoxProfile.setPreference("browser.download.manager.focusWhenStarting", false);
                firefoxProfile.setPreference("browser.download.useDownloadDir", true);
                firefoxProfile.setPreference("browser.helperApps.alwaysAsk.force", false);
                firefoxProfile.setPreference("browser.download.manager.alertOnEXEOpen", false);
                firefoxProfile.setPreference("browser.download.manager.closeWhenDone", true);
                firefoxProfile.setPreference("browser.download.manager.showAlertOnComplete", false);
                firefoxProfile.setPreference("browser.download.manager.useWindow", false);
                firefoxProfile.setPreference("services.sync.prefs.sync.browser.download.manager.showWhenStarting", false);
                firefoxProfile.setPreference("pdfjs.disabled", true);
                firefoxProfile.setPreference("browser.cache.disk.enable", false);
                firefoxProfile.setPreference("browser.cache.memory.enable", false);
                firefoxProfile.setPreference("browser.cache.offline.enable", false);
                firefoxProfile.setPreference("network.http.use-cache", false);
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
            case "edge":
                capability = DesiredCapabilities.edge();
                capability.setCapability(CapabilityType.ACCEPT_SSL_CERTS, true);
                capability.setCapability(InternetExplorerDriver.INTRODUCE_FLAKINESS_BY_IGNORING_SECURITY_DOMAINS,true);
                capability.setCapability(CapabilityType.SUPPORTS_JAVASCRIPT , true);
                capability.setCapability(CapabilityType.PAGE_LOAD_STRATEGY,"eager");
                break;
            default:
                LOGGER.error("TTAF MESSAGE: Failed to set Browser Capabilities.");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully set Capabilities for " + browserName + " browser");
    }

    /**
     * Set Drivers Location System Properties
     */
    private static void setWebDriverLocation(String browserName) {
        URL driverLocation = Constant.class.getClassLoader().getResource("drivers/");
        switch (browserName) {
            case "chrome":
                if (oPlatform.equals("windows"))
                    System.setProperty("webdriver.chrome.driver", driverLocation.getPath() + "chromedriver.exe");
                else if (oPlatform.equals("linux"))
                    System.setProperty("webdriver.chrome.driver", driverLocation.getPath() + "chromedriver");
                break;
            case "firefox":
                if (oPlatform.equals("windows"))
                    System.setProperty("webdriver.gecko.driver", driverLocation.getPath() + "geckodriver.exe");
                else if (oPlatform.equals("linux"))
                    System.setProperty("webdriver.gecko.driver", driverLocation.getPath() + "geckodriver");
                break;
            case "ie":
                    System.setProperty("webdriver.ie.driver", driverLocation.getPath() + "IEDriverServer.exe");
                break;
            case "edge":
                System.setProperty("webdriver.edge.driver", driverLocation.getPath() + "MicrosoftWebDriver.exe");
                break;
            default:
                LOGGER.info("TTAF MESSAGE: Failed to  Set the driver locations");
                System.exit(1);
                break;
        }
        LOGGER.info("TTAF MESSAGE: Successfully Set the driver locations");
    }

    /**
     * Setup Basic WebDriver Browser Settings
     */
    public static void setWebDriver() {
        DriverManager.driver.get().manage().timeouts().implicitlyWait(Constant.TIMEOUT_IMPLICIT, TimeUnit.MILLISECONDS);
        DriverManager.driver.get().manage().window().maximize();
        DriverManager.driver.get().manage().deleteAllCookies();
        DriverManager.driver.get().navigate().to(Constant.URL);
    }

}

