package com.selenium.framework.base;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.safari.SafariDriver;

import com.selenium.framework.config.Settings;

import io.github.bonigarcia.wdm.WebDriverManager;

public class FrameworkInitialize extends Base {


	/**
	 * Function to initialize the web browser based on the configuration file
	 * @param browserType BROWSERTYPE enum representing the browser
	 */
    public static void InitializeBrowser(BrowserType browserType)
    {

        WebDriver driver = null;
        switch (browserType)
        {
            case Chrome:
            {
                if(Settings.WebDriverManagerEnabled)
                {
                	WebDriverManager.chromedriver().setup();
                	break;
                }
                else 
                {
	            	driver = chromeSetup(driver);
	                break;
                }
            }
            case Edge:
            {
                if (Settings.WebDriverManagerEnabled) 
                {
                	WebDriverManager.edgedriver().setup();
                	break;
				}
                else
                {
					driver = edgeSetup(driver);
					break;
                }
            }
            case Firefox:
            {
                if (Settings.WebDriverManagerEnabled) 
                {
                	WebDriverManager.firefoxdriver().setup();
                	break;
				}
                else
                {
					driver = firefoxSetup(driver);
					break;
                }
            }
            case Safari:
            {
                if (Settings.WebDriverManagerEnabled) 
                {
                	WebDriverManager.safaridriver().setup();
                	break;
				}
                else
                {
					driver = new SafariDriver();
					break;
                }
            }
        }


        //Set the Driver
        DriverContext.setDriver(driver);
        //Browser
        DriverContext.Browser = new Browser(driver);

    }

    /**
	 * Function to set up Google Chrome Browser
	 * @param driver WEBDRIVER local instance of current web driver
	 * @return WEBDRIVER with Chrome initialized
	 */
	private static WebDriver chromeSetup(WebDriver driver) {
		ChromeOptions driverOpts = new ChromeOptions();
		if(System.getProperty("os.name").contains("Mac")||System.getProperty("os.name").contains("Linux"))
		{
			System.setProperty("webdriver.chrome.driver", "libs/chromedriver");
		}
		else System.setProperty("webdriver.chrome.driver", "libs/chromedriver.exe");
		driverOpts.addArguments("start-maximized");
		driverOpts.addArguments("--disable-extensions");
		driverOpts.addArguments("--remote-allow-origins=*");
		driverOpts.setExperimentalOption("useAutomationExtension", false);
		if(Settings.Headless)
		{
			driverOpts.addArguments("--headless");
		}
		return driver = new ChromeDriver();
	}

	/**
     * Function to set up Microsoft Edge Browser
     * @param driver WEBDRIVER local instance of current web driver
     * @return WEBDRIVER with Edge initialized
     */
	private static WebDriver edgeSetup(WebDriver driver) {
		if(System.getProperty("os.name").contains("Mac")||System.getProperty("os.name").contains("Linux"))
		{
			System.setProperty("webdriver.edge.driver", "libs/msedgedriver");
		}
		else System.setProperty("webdriver.edge.driver", "libs/msedgedriver.exe");
		return driver = new EdgeDriver();
	}

    /**
     * Function to set up Mozilla Firefox Browser
     * @param driver WEBDRIVER local instance of current web driver
     * @return WEBDRIVER with Firefox initialized
     */
	private static WebDriver firefoxSetup(WebDriver driver) {

		//Open the browser
		if(System.getProperty("os.name").contains("Mac")||System.getProperty("os.name").contains("Linux"))
		{
			System.setProperty("webdriver.gecko.driver", "libs/geckodriver");
		}
		else System.setProperty("webdriver.gecko.driver", "libs/geckodriver.exe");
		return driver = new FirefoxDriver();
	}


}

