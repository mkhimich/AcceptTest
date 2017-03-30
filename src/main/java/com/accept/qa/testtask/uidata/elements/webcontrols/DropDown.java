package com.accept.qa.testtask.uidata.elements.webcontrols;

import com.accept.qa.testtask.uidata.AbstractElement;
import com.accept.qa.testtask.uidata.UIData;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;

import java.text.MessageFormat;

/**
 * Created by mkhimich on 30.03.2017.
 */
public class DropDown extends BaseElement {
    public DropDown(UIData parent, String selector) {
        super(parent, selector);
    }

    public void selectByText(String text) {
        getOptionButton(text).click();
    }

    private Button getOptionButton(String text) {
        return new Button(this, new MessageFormat(getContext().getProperty("vodafone.page.search.option")).format(new String[]{text}));
    }

    public void select(String phone) {
        if (getDriver() instanceof EdgeDriver) {
            Actions actions = new Actions(getDriver());
            actions.moveToElement(this.getWebElement());
            actions.build().perform();
        }
        getOptionByName(phone).click();
    }

    private Button getOptionByName(String phone) {
        return new Button(this, new MessageFormat(getContext().getProperty("context.dialog.phone.search.option")).format(new String[]{phone}));
    }
}
