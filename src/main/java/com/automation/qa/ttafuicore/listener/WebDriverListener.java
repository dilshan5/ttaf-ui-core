package com.automation.qa.ttafuicore.listener;

import com.automation.qa.ttafuicore.driver.DriverManager;
import com.automation.qa.ttafuicore.driver.DriverFactory;
import com.automation.qa.ttafuicore.util.Constant;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestResult;
import java.util.logging.Logger;


public class WebDriverListener implements IInvokedMethodListener {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(WebDriverListener.class));

    @Override
    public void beforeInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        LOGGER.info("TTAF MESSAGE:BEGIN: com.automation.qa.ttafuicore.listener.WebDriverListener.beforeInvocation");
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
            DriverFactory.createInstance(browserName, browserVersion, platform);
            LOGGER.info("Done! Created " + browserName + " driver!");
        } else {
            LOGGER.info("TTAF MESSAGE: TTAF Provided method is NOT a TestNG testMethod!!!");
        }
        LOGGER.info("TTAF MESSAGE:END: com.automation.qa.ttafuicore.listener.WebDriverListener.beforeInvocation");
    }

    @Override
    public void afterInvocation(IInvokedMethod iInvokedMethod, ITestResult iTestResult) {
        LOGGER.info("BEGINNING: com.automation.qa.ttafuicore.listener.WebDriverListener.afterInvocation");
        if (iInvokedMethod.isTestMethod()) {
      /*      try {
                String browser = DriverManager.getBrowserInfo();
                // change the name of the test method that will appear in the report to one that will contain
                // also browser name, version and OS.
                // very handy when analysing results.
                BaseTestMethod bm = (BaseTestMethod) iTestResult.getMethod();
                Field f = bm.getClass().getSuperclass().getDeclaredField("m_methodName");
                f.setAccessible(true);
                String newTestName = iTestResult.getTestContext().getCurrentXmlTest().getName() + " - " + bm.getMethodName() + " - " + browser;
                LOGGER.info("Renaming test method name from: '" + bm.getMethodName() + "' to: '" + newTestName + "'");
                f.set(bm, newTestName);
            } catch (Exception ex) {
                System.out.println("afterInvocation exception:\n" + ex.getMessage());
                ex.printStackTrace();
            } finally {
            }*/
        }
        LOGGER.info("END: com.automation.qa.ttafuicore.listener.WebDriverListener.afterInvocation");
    }
}
