package com.automation.qa.ttafuicore.page;

import com.automation.qa.ttafuicore.test.TestBase;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.remote.RemoteWebDriver;

import java.util.logging.Logger;


/**
 * Introduces an additional abstract layer for real Page classes template, every page class should be extended from this
 * Created by DilshanF on 11/06/2018.
 */
public class BasicPage extends TestBase {
    private static final Logger LOGGER = Logger.getLogger(String.valueOf(BasicPage.class));
    protected RemoteWebDriver driver;

    public BasicPage() {
    }

    /**
     * Initialize Page Base
     *
     * @param driver
     */
    public BasicPage(RemoteWebDriver driver) {
        this.driver = driver;
    }
}
