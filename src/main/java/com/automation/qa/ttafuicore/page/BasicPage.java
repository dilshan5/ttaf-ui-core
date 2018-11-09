package com.automation.qa.ttafuicore.page;

import com.automation.qa.ttafuicore.test.TestBase;
import org.openqa.selenium.WebDriver;


/**
 * Introduces an additional abstract layer for real Page classes template, every page class should be extended from this
 * Created by DilshanF on 10/23/2018.
 */
public class BasicPage extends TestBase {
    protected WebDriver driver;

    public BasicPage() {
    }

    /**
     * @param driver
     */
    public BasicPage(WebDriver driver) {
        this.driver = driver;
    }
}
