package com.accept.qa.testtask.uidata.pages;

import com.accept.qa.testtask.uidata.AbstractPage;
import com.accept.qa.testtask.uidata.elements.children.ContextDialog;
import com.accept.qa.testtask.uidata.elements.webcontrols.BaseElement;
import com.accept.qa.testtask.uidata.elements.webcontrols.Button;
import com.accept.qa.testtask.uidata.elements.webcontrols.DropDown;
import com.accept.qa.testtask.uidata.elements.webcontrols.EditField;
import com.accept.qa.testtask.util.PropertiesContext;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

/**
 * Created by mkhimich on 30.03.2017.
 */
public class VodafonePage extends AbstractPage {

    public VodafonePage(WebDriver driver, PropertiesContext context) {
        super(driver, context);
    }

    public EditField getSearchBar() {
        return new EditField(this, context.getProperty("vodafone.page.search.bar"));
    }

    public ContextDialog searchFor(String text) {
        getFrameContainer().waitForElementToAppear();
        driver.switchTo().frame(getFrameContainer().getWebElement());
        getSearchBar().setText(text);
        getSearchBar().submit();
        return new ContextDialog(this, context.getProperty("vodafone.page.search.context.dialog"));
    }

    @Override
    public void navigate() {
        driver.get(context.getProperty("default.url"));
    }


    public BaseElement getFrameContainer() {
        return new BaseElement(this, context.getProperty("vodafone.page.iframe"));
    }
}
