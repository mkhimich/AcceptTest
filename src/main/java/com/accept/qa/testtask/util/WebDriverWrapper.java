package com.accept.qa.testtask.util;

import com.accept.qa.testtask.util.environment.Browser;
import com.accept.qa.testtask.util.environment.EnvironmentUtil;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.ie.InternetExplorerDriver;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.util.Hashtable;
import java.util.Map;
import java.util.logging.Level;

/**
 * The wrapper for the WebDriver
 * Gives ability to initialize any browser.
 * Created by mkhimich on 30.03.2017.
 */
public class WebDriverWrapper {
    /**
     * Method get the browser instance and try to create WebDriver object for test
     *
     * @return WebDriver after initialization
     */
    public static WebDriver getWebDriver() {
        return getWebDriver(EnvironmentUtil.getInstance().getBrowser());
    }

    /**
     * Method helps create new WebDriver object for test accordingly to browser
     *
     * @param browser Browser
     * @return driver WebDriver
     */
    public static WebDriver getWebDriver(Browser browser) {
        EnvironmentUtil util = EnvironmentUtil.getInstance();
        WebDriver driver = null;
        switch (browser) {
            case IE:
                System.out.println("Setting iedriver...");
                System.setProperty("webdriver.ie.driver", PropertiesContext.getInstance().getProperty("win32.ie.driver"));
                DesiredCapabilities ieCap = DesiredCapabilities
                        .internetExplorer();
                ieCap.setCapability(
                        InternetExplorerDriver.IE_ENSURE_CLEAN_SESSION, true);
                driver = new InternetExplorerDriver(ieCap);
                break;
            case FIREFOX:
                System.out.println("Setting Geckodriver...");
                switch (util.getOs()) {
                    case WINDOWS:
                        System.setProperty("webdriver.gecko.driver", PropertiesContext.getInstance().getProperty("win.gecko.driver"));
                        break;
                    case LINUX:
                        //linux code here
                        break;
                    case MAC:
                        System.setProperty("webdriver.gecko.driver", PropertiesContext.getInstance().getProperty("mac.gecko.driver"));
                        break;
                }
                //todo: Remove redundant info and debug messages while running geckodriver using Capabilities or FireFoxOptions
                FirefoxOptions firefoxOptions = new FirefoxOptions();
                firefoxOptions.setLogLevel(Level.OFF);
                DesiredCapabilities capabilities = DesiredCapabilities.firefox();
                capabilities.setCapability("moz:firefoxOptions", firefoxOptions);
                driver = new FirefoxDriver(capabilities);
                break;
            case CHROME:
                System.out.println("Setting chromedriver...");
                switch (util.getOs()) {
                    case WINDOWS:
                        System.setProperty("webdriver.chrome.driver", PropertiesContext.getInstance().getProperty("win.chrome.driver"));
                        break;
                    case LINUX:
                        //linux code here
                        break;
                    case MAC:
                        System.setProperty("webdriver.chrome.driver", PropertiesContext.getInstance().getProperty("mac.chrome.driver"));
                        break;
                }
                Map<String, String> prefs = new Hashtable<String, String>();
                prefs.put("download.prompt_for_download", "false");
                prefs.put("download.default_directory", PropertiesContext.getInstance().getProperty("download.directory"));
                ChromeOptions options = new ChromeOptions();
                options.addArguments("start-maximized");
                options.addArguments("test-type");
                options.setExperimentalOption("prefs", prefs);
                try {
                    driver = new ChromeDriver(options);
                } catch (Exception e) {
                    throw new RuntimeException(e);
                }
                System.out.println("Set Driver to Chrome");
                break;
            case EDGE:
                System.out.println("Setting edgedriver...");
                System.setProperty("webdriver.edge.driver", PropertiesContext.getInstance().getProperty("win.edge.driver"));
                driver = new EdgeDriver();
                System.out.println("Set Driver to EdgeDriver");
                //For opening clean browser every time
                driver.get("about:InPrivate");
                break;
            case SAUCE:
                //Implement SAUCE code here
                break;
        }
        driver.manage().window().maximize();
        return driver;
    }
}