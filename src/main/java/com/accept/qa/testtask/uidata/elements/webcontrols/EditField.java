package com.accept.qa.testtask.uidata.elements.webcontrols;

import com.accept.qa.testtask.uidata.UIData;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebElement;

/**
 * Base element for fields
 * Created by mkhimich on 30.03.2017.
 */
public class EditField extends BaseElement {
    public EditField(UIData parent, String selector) {
        super(parent, selector);
    }

    public void setText(String text) {
        WebElement edit = getWebElement();
        edit.clear();
        edit.sendKeys(text);
    }

    public void submit() {
        getWebElement().sendKeys(Keys.ENTER);
    }
}
