package com.automation.qa.ttafuicore.page;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.openqa.selenium.remote.RemoteWebDriver;


/**
 * Introduces an additional abstract layer for real Page classes template, every page class should be extended from this
 * Created by DilshanF on 11/06/2018.
 */
public class BasicPage {
    private static final Logger LOGGER = LogManager.getLogger(String.valueOf(BasicPage.class));
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
