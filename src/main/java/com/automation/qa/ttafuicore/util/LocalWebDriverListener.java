package com.automation.qa.ttafuicore.util;

import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;

import java.util.logging.Logger;

/**
 * Created by DilshanF on 11/11/2018.
 */
public class LocalWebDriverListener implements IInvokedMethodListener {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(LocalWebDriverListener.class));

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        LOGGER.info("TTAF MESSAGE:BEGIN: com.automation.qa.ttafuicore.util.LocalWebDriverListener.beforeInvocation");
        if (iInvokedMethod.isTestMethod()) {

            // get browser name specified in the TestNG XML test suite file
            String browserName = iInvokedMethod.getTestMethod().getXmlTest().getLocalParameters().get("browserName");
            // set new instance of local WebDriver
            Constant.BROWSER_NAME = browserName;
            LOGGER.info("Execution Browser set as: " + browserName);

        } else {
            LOGGER.info("TTAF MESSAGE: TTAFProvided method is NOT a TestNG testMethod!!!");
        }
        LOGGER.info("TTAF MESSAGE:END: com.automation.qa.ttafuicore.util.LocalWebDriverListener.beforeInvocation");
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {

    }
}
