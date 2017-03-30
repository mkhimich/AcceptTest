package com.accept.qa.testtask.uidata;

import com.accept.qa.testtask.util.PropertiesContext;
import org.openqa.selenium.WebDriver;

/**
 * General Parent for All pages.
 * Created by mkhimich on 30.03.2017.
 */
public abstract class AbstractPage extends AbstractUIData {
    protected WebDriver driver;
    protected PropertiesContext context;

    protected AbstractPage(WebDriver driver, PropertiesContext context) {
        this.driver = driver;
        this.context = context;
    }

    public UIData getParent() {
        return null;
    }

    public PropertiesContext getContext() {
        return context;
    }

    public WebDriver getDriver() {
        return driver;
    }

    public String getSelector() {
        return "";
    }

    public void reload() {
        driver.navigate().refresh();
    }


    /**
     * A Page must implement this to perform step-by-step proceeding (by clicking on various navigation elements)
     * to corresponding page in the application
     */
    public abstract void navigate();

    public String getCurrentUrl() {
        return driver.getCurrentUrl();
    }
}
