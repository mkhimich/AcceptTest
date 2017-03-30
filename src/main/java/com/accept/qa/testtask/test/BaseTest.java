package com.accept.qa.testtask.test;

import com.accept.qa.testtask.runner.AcceptTestRunner;
import com.accept.qa.testtask.runner.AfterFailure;
import com.accept.qa.testtask.util.PropertiesContext;
import com.accept.qa.testtask.util.ScreenshotManager;
import com.accept.qa.testtask.util.WebDriverWrapper;
import org.junit.After;
import org.junit.Before;
import org.junit.Rule;
import org.junit.rules.TestName;
import org.junit.rules.Timeout;
import org.junit.runner.RunWith;
import org.openqa.selenium.UnsupportedCommandException;
import org.openqa.selenium.WebDriver;

import java.util.concurrent.TimeUnit;

/**
 * BaseTest is parent for all Test* classes. All JUnit tests extend
 * from this class. This class provides some basic functionality for its
 * descendants, like obtaining driver instance . It also creates instance of
 * PropertyContext which is then shared between test classes.
 * <p>
 * Created by mkhimich on 30.03.2017.
 */
@RunWith(AcceptTestRunner.class)
public abstract class BaseTest {
    protected PropertiesContext context;
    protected WebDriver driver = null;
    @Rule
    public TestName testName = new TestName();
    //Any test that might hang will be forced to end within 120 seconds
    @Rule
    public Timeout globalTimeout = new Timeout(120, TimeUnit.SECONDS);

    public BaseTest() {
        context = PropertiesContext.getInstance();
    }

    @Before
    public void testSetup() {
        System.out.println("========================== START TEST " + testName.getMethodName() + " ==============================");
        System.out.println("Server URL = " + context.getProperty("default.url"));
        driver = WebDriverWrapper.getWebDriver();
    }

    @After
    public void tearDown() {
        System.out.println("========================== END TEST " + testName.getMethodName() + " ==============================");
        closeDriver();
    }

    private void closeDriver() {
        if (driver != null) {
            driver.quit();
        }
    }

    @AfterFailure
    public void takeScreenshot(Throwable th) {
        try {
            String screenshotName = getClass().getSimpleName() + "_" + testName.getMethodName();
            ScreenshotManager.takeScreenshot(screenshotName, driver);
        } catch (UnsupportedCommandException e) {
            e.printStackTrace();
            System.out.println("Not able to get screenshot as session might already finished");
        }
    }
}