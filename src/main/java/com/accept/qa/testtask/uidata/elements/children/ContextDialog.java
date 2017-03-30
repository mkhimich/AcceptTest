package com.accept.qa.testtask.uidata.elements.children;

import com.accept.qa.testtask.uidata.AbstractElement;
import com.accept.qa.testtask.uidata.UIData;
import com.accept.qa.testtask.uidata.elements.webcontrols.Button;
import com.accept.qa.testtask.uidata.elements.webcontrols.DropDown;
import com.accept.qa.testtask.uidata.elements.webcontrols.EditField;
import com.accept.qa.testtask.uidata.elements.webcontrols.Link;
import com.accept.qa.testtask.uidata.pages.AnswerPage;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.MessageFormat;

/**
 * Created by mkhimich on 30.03.2017.
 */
public class ContextDialog extends AbstractElement {
    public ContextDialog(UIData parent, String selector) {
        super(parent, selector);
    }

    public void selectIphone() {
        getPhoneButton().waitForElementToAppear();
        getPhoneButton().click();
        getIphoneButton().click();
    }

    public Button getPhoneButton() {
        return new Button(this, getContext().getProperty("context.dialog.phone.button"));
    }

    public Button getIphoneButton() {
        return new Button(this, getContext().getProperty("context.dialog.phone.iphone.button"));
    }

    public void searchFor(String text) {
        getSearchDevice().waitForElementToAppear();
        getSearchDevice().setText(text);
    }

    public void selectPhone(String phone) {
        getPhoneDropDown().waitForElementToAppear();
        getPhoneDropDown().select(phone);
    }

    public EditField getSearchDevice() {
        return new EditField(this, getContext().getProperty("context.dialog.phone.search"));
    }

    public DropDown getPhoneDropDown() {
        return new DropDown(this, getContext().getProperty("context.dialog.phone.list"));
    }

    public void expand(String text) {
        if (getDriver() instanceof InternetExplorerDriver) {
            //IE driver needs time for slide down
            Actions actions = new Actions(getDriver());
            actions.moveToElement(getAnswerOption(text).getWebElement());
            actions.build().perform();
        }
        getAnswerOption(text).click();

    }

    private Link getAnswerOption(String text) {
        return new Link(getParent(), new MessageFormat(getContext().getProperty("context.dialog.suggestion.list.option")).format(new String[]{text}));
    }

    public AnswerPage goToArticle() {
        getArticleLink().click();
        if (getDriver() instanceof FirefoxDriver) {
            //FireFox needs time to redirect. Instead of suspend "wait" method can be implelemnted
            suspend(1000);
        } else {
            //all other drivers can wait for element to disappear
            getArticleLink().waitForElementToDisappear();
        }

        getDriver().switchTo().defaultContent();
        return new AnswerPage(getDriver(), getContext());
    }

    public Link getArticleLink() {
        return new Link(getParent(), getContext().getProperty("context.dialog.article.link"));
    }
}
