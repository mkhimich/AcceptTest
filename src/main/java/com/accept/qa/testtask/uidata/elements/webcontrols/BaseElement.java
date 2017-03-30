package com.accept.qa.testtask.uidata.elements.webcontrols;

import com.accept.qa.testtask.uidata.AbstractElement;
import com.accept.qa.testtask.uidata.UIData;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

/**
 * General element on the page. Can be any kind of elements.
 * Created by mkhimich on 30.03.2017.
 */
public class BaseElement extends AbstractElement {

    public BaseElement(UIData parent, String selector) {
        super(parent, selector);
    }

    public Boolean isDisplayed() {
        WebElement element = getWebElement();
        return element.isDisplayed();
    }

    public void click() {
        WebElement element = getWebElement();
        System.out.println("Clicking on element: " + getAbsoluteSelector());
        element.click();
    }

    public String getAttributeValue(String name) {
        try {
            return getWebElement().getAttribute(name);
        } catch (StaleElementReferenceException e) {
            e.printStackTrace();
        }
        return getWebElement().getAttribute(name);
    }

    public void hover() {
        if (!(getDriver() instanceof FirefoxDriver)) {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(getWebElement()).build().perform();
        } else {
            //Workaround for hover due to bug: https://github.com/SeleniumHQ/selenium-google-code-issue-archive/issues/4136
            String mouseOverScript = "if(document.createEvent){var evObj = " +
                    "document.createEvent('MouseEvents');evObj.initEvent('mouseover', true, false); " +
                    "arguments[0].dispatchEvent(evObj);} else if(document.createEventObject) { arguments[0].fireEvent('onmouseover');}";
            JavascriptExecutor js = (JavascriptExecutor) getDriver();
            js.executeScript(mouseOverScript, getWebElement());
        }

    }
}
