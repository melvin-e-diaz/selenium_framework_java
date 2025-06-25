package com.selenium.framework.base;

import org.openqa.selenium.WebDriver;

/**
 * Browser class
 */
public class Browser {
	
	//initializes the driver
	private WebDriver _driver;
	
	/**
	 * Sets the global instance of the driver to the local driver.
	 * @param driver
	 */
	public Browser(WebDriver driver)
	{
		_driver = driver;
	}
	
	//Initializes the browser type
	public BrowserType type;
	
	/**
	 * Navigates to the current URL
	 * @param url STRING representing the current URL
	 */
	public void GoToUrl(String url)
	{
		_driver.get(url);
	}
	
	/**
	 * Maximizes the driver window
	 */
	public void Maximize()
	{
		_driver.manage().window().maximize();
	}
	
}
