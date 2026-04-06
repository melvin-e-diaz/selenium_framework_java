package com.selenium.framework.base;

import com.selenium.framework.config.Settings;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

public class FrameworkInitialize extends Base {


    /**
     * Function to initialize the web browser based on the configuration file
     *
     * @param browserType BROWSERTYPE enum representing the browser
     */
    public static void InitializeBrowser(BrowserType browserType) {

        WebDriver driver;
        switch (browserType) {
            case Chrome:
                if (Settings.WebDriverManagerEnabled) {
                    WebDriverManager.chromedriver().setup();
                    driver = new ChromeDriver(buildChromeOptions());
                } else {
                    driver = chromeSetup();
                }
                break;
            case Edge:
                if (Settings.WebDriverManagerEnabled) {
                    WebDriverManager.edgedriver().setup();
                    driver = new EdgeDriver();
                } else {
                    driver = edgeSetup();
                }
                break;
            case Firefox:
                if (Settings.WebDriverManagerEnabled) {
                    WebDriverManager.firefoxdriver().setup();
                    driver = new FirefoxDriver();
                } else {
                    driver = firefoxSetup();
                }
                break;
            case Safari:
                if (Settings.WebDriverManagerEnabled) {
                    WebDriverManager.safaridriver().setup();
                }
                driver = new SafariDriver();
                break;
        }

        DriverContext.WebDriverWait = Settings.WebDriverWait;
        DriverContext.setDriver(driver);
        DriverContext.Browser = new Browser(driver);

    }

    private static ChromeOptions buildChromeOptions() {
        ChromeOptions driverOpts = new ChromeOptions();
        driverOpts.addArguments("start-maximized");
        driverOpts.addArguments("--disable-extensions");
        driverOpts.addArguments("--remote-allow-origins=*");
        driverOpts.setExperimentalOption("useAutomationExtension", false);
        if (Settings.Headless) {
            driverOpts.addArguments("--headless");
        }
        return driverOpts;
    }

    /**
     * Function to set up Google Chrome Browser
     *
     * @return WEBDRIVER with Chrome initialized
     */
    private static WebDriver chromeSetup() {
        if (System.getProperty("os.name").contains("Mac") || System.getProperty("os.name").contains("Linux")) {
            System.setProperty("webdriver.chrome.driver", "libs/chromedriver");
        } else {
            System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
        }
        return new ChromeDriver(buildChromeOptions());
    }

    /**
     * Function to set up Microsoft Edge Browser
     *
     * @return WEBDRIVER with Edge initialized
     */
    private static WebDriver edgeSetup() {
        if (System.getProperty("os.name").contains("Mac") || System.getProperty("os.name").contains("Linux")) {
            System.setProperty("webdriver.edge.driver", "libs/msedgedriver");
        } else {
            System.setProperty("webdriver.edge.driver", "libs/msedgedriver.exe");
        }
        return new EdgeDriver();
    }

    /**
     * Function to set up Mozilla Firefox Browser
     *
     * @return WEBDRIVER with Firefox initialized
     */
    private static WebDriver firefoxSetup() {
        if (System.getProperty("os.name").contains("Mac") || System.getProperty("os.name").contains("Linux")) {
            System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
        } else {
            System.setProperty("webdriver.gecko.driver", "libs/geckodriver.exe");
        }
        return new FirefoxDriver();
    }


}
