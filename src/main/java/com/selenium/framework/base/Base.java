package com.selenium.framework.base;

import org.openqa.selenium.support.PageFactory;

/**
 * Page that contains the initial base; serves as the foundation of the Selenium automated framework
 */
public class Base {

	//initializes the current page
	public static BasePage CurrentPage;
	
	//gets the current object
	public <TPage extends BasePage> TPage GetInstance(Class<TPage> page)
	{
		Object obj = PageFactory.initElements(DriverContext.Driver, page);
		return page.cast(obj);
	}
}
