package com.automation.qa.ttafuicore.test;

import org.openqa.selenium.SessionNotCreatedException;
import org.openqa.selenium.WebDriver;
import com.automation.qa.ttafuicore.driver.DriverConnection;

import java.io.IOException;

/**
 * Created by DilshanF on 10/23/2018.
 */
public class TestBase {
    public static WebDriver driver;

    /**
     * Create driver instance
     * @throws Exception
     */
    public void initializeBaseSetup() throws Exception {
        driver = DriverConnection.getDeriverInstance();
    }

    /**
     *
     * @returndriver object
     */
    public WebDriver getDriver() {
        return driver;
    }


    /**
     * Initiate close driver
     *
     * @throws SessionNotCreatedException
     * @throws IOException
     * @throws InterruptedException
     * @throws Exception
     */
    public void tearDown() throws SessionNotCreatedException, IOException, InterruptedException, Exception {
        DriverConnection.closeDriver();
    }
}
