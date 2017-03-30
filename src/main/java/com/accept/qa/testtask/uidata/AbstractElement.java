package com.accept.qa.testtask.uidata;

import com.accept.qa.testtask.util.PropertiesContext;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.function.Function;

/**
 * Parent for all elements on the page.
 * Created by mkhimich on 30.03.2017.
 */
public abstract class AbstractElement extends AbstractUIData {
    private static final long DEFAULT_TIMEOUT = 10;
    protected UIData parent;
    protected String selector;

    protected AbstractElement(UIData parent, String selector) {
        this.parent = parent;
        this.selector = selector;
        parent.addChild(this);
    }

    public UIData getParent() {
        return parent;
    }

    public PropertiesContext getContext() {
        return parent.getContext();
    }

    public WebDriver getDriver() {
        return parent.getDriver();
    }

    public String getSelector() {
        return selector;
    }

    public WebElement getWebElement() {
        System.out.println("Getting element: " + getAbsoluteSelector());
        final AbstractElement that = this;
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        Function<WebDriver, WebElement> function = new Function<WebDriver, WebElement>() {
            public WebElement apply(WebDriver input) {
                return getWebElementImmediately(that);
            }
        };
        try {
            return wait.until(function);
        } catch (TimeoutException e) {
            throw new RuntimeException("Timeout for element: " + getAbsoluteSelector(), e);
        } catch (Exception e) {
            throw new RuntimeException("Exception getting element: " + getAbsoluteSelector(), e);
        }
    }

    public WebElement getViaSizzleSelector(String selector) {
        return (WebElement) ((JavascriptExecutor) getDriver()).executeScript("$('" + selector + "')");
    }

    private WebElement getWebElementImmediately(AbstractElement chunk) {
        return getDriver().findElement(By.cssSelector(chunk.getAbsoluteSelector()));
    }

    public boolean exists() {
        return getWebElementImmediately(this) != null;
    }

    public void waitForElementToAppear() {
        WebDriverWait wait = new WebDriverWait(getDriver(), DEFAULT_TIMEOUT);
        System.out.println("Waiting for element to appear " + getAbsoluteSelector());
        WebElement element = getWebElement();
        wait.until(ExpectedConditions.visibilityOf(element));
        wait.until(ExpectedConditions.elementToBeClickable(element));
    }

    public void waitForElementToDisappear() {
        Long startTime = System.currentTimeMillis();
        try {
            while (System.currentTimeMillis() - startTime < DEFAULT_TIMEOUT * 1000 && getDriver().findElement(By.cssSelector(getAbsoluteSelector())).isDisplayed()) {
            }
        } catch (StaleElementReferenceException e) {
            return;
        } catch (NoSuchElementException ne) {
            return;
        }
    }
}
