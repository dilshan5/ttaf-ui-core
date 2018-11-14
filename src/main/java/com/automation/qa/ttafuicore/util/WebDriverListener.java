package com.automation.qa.ttafuicore.util;

import com.automation.qa.ttafuicore.driver.DriverConnection;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import org.testng.annotations.DataProvider;

import java.util.logging.Logger;

/**
 * Created by DilshanF on 11/11/2018.
 */
public class WebDriverListener implements IInvokedMethodListener {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(WebDriverListener.class));

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        LOGGER.info("TTAF MESSAGE:BEGIN: com.automation.qa.ttafuicore.util.WebDriverListener.beforeInvocation");
        if (iInvokedMethod.isTestMethod()) {

            // get browser name specified in the TestNG XML test suite file
            String browserName = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
            String browserVersion = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("browserVersion");
            String platform = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("platform");

            Constant.BROWSER_NAME = browserName;// set Browser Name
            Constant.BROWSER_VERSION = browserVersion;// set Browser Version
            Constant.PLATFORM = platform;// set platform
            LOGGER.info("Execution Browser set as: " + browserName);
            LOGGER.info("Execution Browser Version set as: " + browserVersion);
            LOGGER.info("Execution Platform set as: " + platform);
            LOGGER.info("Selenium Grid is: " + Constant.GRID_MODE);
            LOGGER.info("Selenium Grid url: " + Constant.hubURL);

        } else {
            LOGGER.info("TTAF MESSAGE: TTAF Provided method is NOT a TestNG testMethod!!!");
        }
        LOGGER.info("TTAF MESSAGE:END: com.automation.qa.ttafuicore.util.WebDriverListener.beforeInvocation");
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }
}
