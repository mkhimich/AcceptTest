package com.accept.qa.testtask.uidata.pages;

import com.accept.qa.testtask.uidata.AbstractPage;
import com.accept.qa.testtask.util.PropertiesContext;
import org.openqa.selenium.WebDriver;

/**
 * Created by mkhimich on 30.03.2017.
 */
public class AnswerPage extends AbstractPage {

    public AnswerPage(WebDriver driver, PropertiesContext context) {
        super(driver, context);
    }

    @Override
    public void navigate() {
        //in case we need direct access to the page
        driver.get(context.getProperty("expected.url"));
    }
}
