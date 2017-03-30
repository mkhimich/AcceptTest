package com.accept.qa.testtask.test.functional;

import com.accept.qa.testtask.test.BaseTest;
import com.accept.qa.testtask.uidata.elements.children.ContextDialog;
import com.accept.qa.testtask.uidata.pages.AnswerPage;
import com.accept.qa.testtask.uidata.pages.VodafonePage;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

/**
 * Class for functional tests
 * Created by mkhimich on 30.03.2017.
 */
public class TestVodafone extends BaseTest {

    @Test
    public void testVodafoneSearch() {
        //test dependent data
        //Please note: if we need more phones, etc. Enums with correct data can be created.
        String expectedUrl = context.getProperty("expected.url");
        String searchTerm = "wifi";
        String searchPhone = "Galaxy";
        String phoneName = "Galaxy Y";
        String optionToExpand = "What is Wi-Fi Calling";
        //go to default page
        VodafonePage page = new VodafonePage(driver, context);
        page.navigate();
        //search for term
        ContextDialog dialog = page.searchFor(searchTerm);
        //select iphone
        dialog.selectIphone();
        //search for phone
        dialog.searchFor(searchPhone);
        //select correct phone
        dialog.selectPhone(phoneName);
        //expand option
        dialog.expand(optionToExpand);
        //go to article
        AnswerPage answerPage = dialog.goToArticle();
        //Verify: Url is correct
        assertEquals("URL is incorrect", expectedUrl, answerPage.getCurrentUrl());

    }
}
