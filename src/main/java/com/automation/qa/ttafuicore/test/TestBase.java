package com.automation.qa.ttafuicore.test;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import com.automation.qa.ttafuicore.driver.DriverConnection;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.io.IOException;

/**
 * Created by DilshanF on 10/23/2018.
 */
public class TestBase {
    public static ThreadLocal<RemoteWebDriver> driver = new ThreadLocal<RemoteWebDriver>();

    /**
     * Create driver instance
     * @throws Exception
     */
    public void initializeBaseSetup() throws Exception {
         DriverConnection.getDeriverInstance();
    }

    /**
     * @return driver object
     */
    public RemoteWebDriver getDriver() {
        return driver.get();
    }


    /**
     * Initiate close driver
     *
     * @throws SessionNotCreatedException
     * @throws IOException
     * @throws InterruptedException
     * @throws Exception
     */
    public void tearDown() throws Exception {
        try {
            getDriver().quit();
        } catch (Throwable e) {

        } finally {
            driver.set(null);
        }
    }
}
