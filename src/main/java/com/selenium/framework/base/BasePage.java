package com.selenium.framework.base;

import java.awt.AWTException;
import java.awt.Robot;
import java.awt.Toolkit;
import java.awt.datatransfer.StringSelection;
import java.awt.event.KeyEvent;
import java.time.Duration;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.UUID;
import java.util.concurrent.TimeUnit;


import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;

import com.selenium.framework.config.Settings;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;

public abstract class BasePage extends Base {
	
    public static WebDriver Driver;
	
	private WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(Settings.WebDriverWait));
	
	public static ExtentTest logger;

	/**
	 * Initializes the BasePage which extends the Base.
	 * @param <TPage>
	 * @param pageInstance
	 * @return
	 */
	public <TPage extends BasePage> TPage As(Class<TPage> pageInstance)
	{
		try
		{
			return (TPage)this;
		}
		catch (Exception e)
		{
			e.getStackTrace();
		}
		return null;
	}
	
	/**
	 * Function that overrides the standard Selenium click command. This function will first check that the target element 
	 * is clickable. If TRUE, then it will click on the element. If false, an exception is thrown.
	 * @param element WEBELEMENT Web Element to be interacted with.
	 */
	public void click(WebElement element)
	{
		DriverContext.WaitElementClickable(element);
		this.highlightElement(element);
		element.click();
		this.writeDebugLog("Click", element);
	}
	
	/**
	 * Function that forces the webdriver to focus on the element before clicking. The function can be used if your webdriver is having a 
	 * difficult time finding the element
	 * @param element WEBELEMENT Web Element to be interacted with.
	 */
	public void click_action(WebElement element)
	{
		DriverContext.WaitElementClickable(element);
		this.highlightElement(element);
		
		Actions action = new Actions(Driver);
		action.moveToElement(element).click();
		action.build().perform();
		this.writeDebugLog("Click Action", element);

	}
	
	/**
	 * Function that performs a double click.
	 * @param element WEBELEMENT Web Element to be interacted with.
	 */
	public void doubleClick(WebElement element)
	{
		DriverContext.WaitElementClickable(element);
		this.highlightElement(element);
		
		Actions action = new Actions(Driver);
		action.moveToElement(element).doubleClick();
		action.build().perform();
		this.writeDebugLog("Double Click", element);
	}
	
	/**
	 * Function that uses Javascript to scroll down a webpage by a fixed amount.
	 */
	public void scrollDown()
	{
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Settings.WebDriverWait));
		JavascriptExecutor js = (JavascriptExecutor)Driver;
		js.executeScript("window.scrollBy(0,3000)", "");
	}
	
	/**
	 * Function that uses Javascript to scroll down to the bottom of the webpage.
	 */
	public void scrollToBottomOfPage()
	{
		Driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(Settings.WebDriverWait));
		JavascriptExecutor js = (JavascriptExecutor)Driver;
		js.executeScript("window.scrollTo(0, document.body.scrollHeight)");
	}
	
//	public void scrollToLastElementInCollection(WebElement element)
//	{
//		WebElement elementToScrollTo = Driver.findElements(null)
//	}
	
	/**
	 * Function to perform the Selenium sendKeys function after first checking for the presence of the element, then clearing the element
	 * @param element WebElement used to locate element within a document
	 * @param text STRING expression of the text to be entered.
	 */
	public void writeText(WebElement element, String text)
	{
		clearText(element);
		element.sendKeys(text);
		this.writeDebugLog("Write Text", element);
	}

	/**
	 * Function to clear the Web Element
	 * @param element
	 */
	public void clearText(WebElement element) {
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		element.clear();
		this.writeDebugLog("Clear Text", element);
	}
	
	/**
	 * Function to clear text if the clearText() method does not work.
	 * @param element WebElement used to locate element within a document
	 */
	public void forceClearText(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		element.sendKeys(Keys.chord(Keys.CONTROL,"a",Keys.DELETE)+Keys.BACK_SPACE);
	}
	
	/**
	 * Function to return the visible text contained in the specified page object
	 * @param element WebElement used to locate element within a document
	 * @return STRING representing the text found in the given element.
	 */
	public String readText(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		return element.getText();
	}
	
	/**
	 * Function to select all options that display text matching the argument. If element is not present, 
	 * a NoSuchElementException is thrown
	 * @param element WebElement used to locate element within a document
	 * @param desiredElementText STRING representing the text to be searched in the dropdown list.
	 */
	public void selectFromDropdownList(WebElement element, String desiredElementText)
	{
		try
		{
			DriverContext.WaitForElementVisible(element);
			highlightElement(element);
			
			new Select(element).selectByVisibleText(desiredElementText);
		}
		catch(NoSuchElementException e)
		{
			this.handleTestFailure(e, "Desired element not found in dropdown list.");
		}
	}
	
	/**
	 * Function to select an element from a dropdown list based on the index indicated in the desiredElementIndex.
	 * Function will first check that the element is visible before attempting to select the element.
	 * @param element WebElement used to locate element within a document
	 * @param desiredElementIndex INT representing the index of the element to be selected (must be in the range of 0 to element.getSize()-1)
	 */
	public void selectFromDropdownList(WebElement element, int desiredElementIndex)
	{
		try
		{
			DriverContext.WaitForElementVisible(element);
			highlightElement(element);
			
			new Select(element).selectByIndex(desiredElementIndex);
		}
		catch(NoSuchElementException e)
		{
			this.handleTestFailure(e, "Desired element is outside the bounds of the dropdown list.");
		}
	}
	
	/**
	 * Function to clear all selections from the specified dropdown or multiselect list.
	 * @param element WebElement used to locate element within a document. Element must have a SELECT tagname and must be able to support multiple selections.
	 */
	public void deselectAllFromDropdownList(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		new Select(element).deselectAll();
	}
	
	/**
	 * Function to return a list of WebElements with all elements contained in a dropdown list.
	 * @param element WebElement used to locate element within a document. Element must have a SELECT tagname and must be able to support multiple selections.
	 * @return a List<WebElement> with all of the elements found by the selector.
	 */
	public List<WebElement> getAllElementsInDropdownList(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		return new Select(element).getOptions();
	}
	
	/**
	 * Function to return the first selected option in this select tag (or the currently selected option in a normal select) as a String value.
	 * @param element WebElement used to locate element within a document. Element must have a SELECT tagname and must be able to support multiple selections.
	 * @return STRING of the first selected option.
	 */
	public String getSelectedElementInDropdownList(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		return new Select(element).getFirstSelectedOption().getText();
	}
	
	/**
	 * Function to use if element to send keys to is out of focus
	 * @param element WebElement used to locate element within a document
	 * @param text STRING of text to send keys to the elenent
	 */
	public void focusAndWriteText(WebElement element, String text)
	{
		highlightElement(element);
		Actions actions = new Actions(Driver);
		actions.moveToElement(element);
		actions.click();
		actions.sendKeys(text);
		actions.build().perform();
	}

	/**
	 * Function to return randomly generated text to fill out a text box.
	 * @return STRING of random characters.
	 */
	public String enterRandomText()
	{
		return UUID.randomUUID().toString().replace("-", "");
	}
	
	/**
	 * Function to verify the text elements in a dropdown list
	 * @param element WebElement the location of the dropdown list to be verified.
	 * @param expectedDropdownValues Array of String values of the elements to be verified in the dropdown list.
	 * @param elementName Descriptive name of the dropdown list to be verified. Only used to identify the element for error reporting.
	 * @return TRUE if dropdown list is verified successfully, FALSE otherwise.
	 */
	public boolean verifyContentsOfDropdownList(WebElement element, String[] expectedDropdownValues, String elementName)
	{
		try
		{
			List<WebElement> actualDropdownValues = this.getAllElementsInDropdownList(element);
			List<String> actualDropdownValuesToString = new ArrayList<String>();
			for(int i=0; i<actualDropdownValues.size(); i++)
			{
				actualDropdownValuesToString.add(actualDropdownValues.get(i).getText());
			}
			List<String> expectedDropdownValuesList = Arrays.asList(expectedDropdownValues);
			
			this.assertEquals(actualDropdownValuesToString.size(), expectedDropdownValuesList.size());
			
			for(int i=0; i<actualDropdownValuesToString.size(); i++)
			{
				this.assertEquals(actualDropdownValuesToString.get(i), expectedDropdownValuesList.get(i));
			}
			this.assertTrue(actualDropdownValuesToString.equals(expectedDropdownValuesList));
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Contents of dropdown list for "+elementName+" not verified.");
			return false;
		}
		return true;
	}
	
	/**
	 * Sends the ENTER key to the target element.
	 * @param element WebElement used to locate the element within a document.
	 */
	public void sendEnterKey(WebElement element)
	{
		DriverContext.WaitForElementVisible(element);
		element.sendKeys(Keys.ENTER);
	}
	
	/**
	 * Function that deselects the specified element from the dropdown list.
	 * @param element WebElement used to locate the element within a document
	 * @param desiredElementText STRING text of the element to be deselected.
	 */
	public void deselectFromDropdownList(WebElement element, String desiredElementText)
	{
		DriverContext.WaitForElementVisible(element);
		highlightElement(element);
		
		new Select(element).deSelectByContainsVisibleText(desiredElementText);
	}
	
	/**
	 * Function that returns the current date in MM/dd/yyyy format for entry into applications.
	 * @return STRING of current date in MM/dd/yyyy format.
	 */
	public String returnTodaysDate()
	{
		DateTimeFormatter dtf = DateTimeFormatter.ofPattern("MM/dd/yyyy");
		LocalDateTime now = LocalDateTime.now();
		return dtf.format(now);
	}
	
	public void upload(String filepath) throws InterruptedException, AWTException
	{
		StringSelection ss = new StringSelection(filepath);
		Thread.sleep(1000);
		Toolkit.getDefaultToolkit().getSystemClipboard().setContents(ss, null);
		Thread.sleep(1000);
		
		Robot robot = new Robot();
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
		robot.keyPress(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_V);
		robot.keyRelease(KeyEvent.VK_CONTROL);
		robot.keyPress(KeyEvent.VK_ENTER);
		robot.keyRelease(KeyEvent.VK_ENTER);
	}
	
	/**
	 * Uses the testNG AssertEnabled functionality to determine whether a specified element is visible on the page.
	 * @param element WebElement used to locate element within a document.
	 * @param elementName STRING descriptive title for the element, used for debugging.
	 */
	public void assertEnabled(WebElement element, String elementName)
	{
		DriverContext.WaitForElementVisible(element);
		
		try
		{
			Assert.assertTrue(element.isEnabled());
			this.writeDebugLog("ASSERTION PASSED: Element "+elementName+" is visible and enabled on page.", element);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Expected element "+elementName+" is not enabled.");
		}
	}
	
	/**
	 * Uses the testNG AssertDisplayed functionality to determine whether a specified element is displayed on the page.
	 * @param element WebElement used to locate element within a document.
	 * @param elementName STRING descriptive title for the element, used for debugging.
	 */
	public void assertDisplayed(WebElement element, String elementName)
	{
		DriverContext.WaitForElementVisible(element);
		
		try
		{
			Assert.assertTrue(element.isDisplayed());
			this.writeDebugLog("ASSERTION PASSED: Element "+elementName+" is visible and displayed on page.", element);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Expected element "+elementName+" is not displayed.");
		}
	}
	
	/**
	 * Function to determine whether two values match
	 * @param <T> wild card to handle multiple data types
	 * @param actualValue Value displayed in application
	 * @param expectedValue Value that is expected to be displayed in the application.
	 */
	public <T> void assertEquals(T actualValue, T expectedValue)
	{
		try
		{
			Assert.assertEquals(actualValue, expectedValue);
			this.writeDebugLog("ASSERTION PASSED: Actual and expected values match: "+actualValue+" | "+expectedValue, null);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Assertion failed: Actual value is: "+actualValue+" | Expected value is: "+expectedValue);
		}
	}
	
	/**
	 * Function to determine whether an expression evaluates to NULL
	 * @param <T> wild card to handle multiple data types
	 * @param actualValue Value displayed in application
	 * @param description STRING description of the null condition
	 */
	public <T> void assertNull(T actualValue, String description)
	{
		try
		{
			Assert.assertNull(actualValue);
			this.writeDebugLog("ASSERTION PASSED: Expected null value is found for "+description, null);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Assertion failed: Expected null value is not null. Value for "+description+" is "+actualValue);
		}
	}
	
	/**
	 * Function to determine whether an expression evaluates as TRUE
	 * @param <T> wild card to handle multiple data types
	 * @param condition BOOLEAN expression of the test
	 * @param description STRING description of the null condition
	 */
	public <T> void assertTrue(boolean condition, String description)
	{
		try
		{
			Assert.assertTrue(condition);
			this.writeDebugLog("ASSERTION PASSED: Expected condition evaluated to be TRUE: "+description, null);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Assertion failed: Expected true condition evaluated to be false: "+description);
		}
	}
	
	/**
	 * Function to determine whether an expression evaluates as FALSE
	 * @param <T> wild card to handle multiple data types
	 * @param condition BOOLEAN expression of the test
	 * @param description STRING description of the null condition
	 */
	public <T> void assertFalse(boolean condition, String description)
	{
		try
		{
			Assert.assertFalse(condition);
			this.writeDebugLog("ASSERTION PASSED: Expected condition evaluated to be FALSE: "+description, null);
		}
		catch(Exception e)
		{
			this.handleTestFailure(e, "Assertion failed: Expected false condition evaluated to be true: "+description);
		}
	}
	
	public boolean verifyURL(WebElement element, String elementDescription)
	{
		try
		{
			this.assertEnabled(element, elementDescription);
			this.click(element);
			DriverContext.WaitForPageToLoad();
		}
		catch(Exception e)
		{
			return false;
		}
		return true;
	}
	
	
	
	private void assertTrue(boolean equals) {
		// TODO Auto-generated method stub
		
	}

	public void assertEquals(int size, int size2) {
		// TODO Auto-generated method stub
		
	}

	/**
	 * Function to check if the element is clickable
	 * @param element WEBELEMENT Web Element to be interacted with.
	 */
	private void checkIfClickable(WebElement element) {
		this.wait.until(ExpectedConditions.elementToBeClickable(element));
	}
	
	/**
	 * Function to check if the element is visible
	 * @param element WEBELEMENT Web Element to be interacted with.
	 */
	private void checkIfVisible(WebElement element)
	{
		By by = this.getByFromElement(element);
		this.wait.until(ExpectedConditions.visibilityOfElementLocated(by));
	}

	/**
	 * Function used to get the BY selector from a given web element. Used for logging purposes.
	 * @param element WebElement
	 * @return BY 
	 */
	private String getSelectorAndValueFromElement(WebElement element) {
	    By by = null;
	    //[[ChromeDriver: chrome on XP (d85e7e220b2ec51b7faf42210816285e)] -> xpath: //input[@title='Search']]
	    String[] pathVariables = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");
	
	    String selector = pathVariables[0].trim();
	    String value = pathVariables[1].trim();
	
	    return selector+value;
	}

	/**
	 * Function to write to debug log.
	 * @param actionPerformed STRING name of the action performed.
	 * @param element
	 */
	private void writeDebugLog(String actionPerformed, WebElement element)
	{
		String timestamp = String.valueOf(getCurrentTimestamp());
		System.out.println(timestamp+" | "+actionPerformed+" | "+this.getSelectorAndValueFromElement(element));
		logger.log(Status.INFO, timestamp+" | "+actionPerformed+" | "+this.getSelectorAndValueFromElement(element));
	}
	
//	private void writeDebugLog(String actionPerformed, ExtentTest logger)
//	{
//		String timestamp = String.valueOf(getCurrentTimestamp());
//		System.out.println(timestamp+" | "+actionPerformed);
//		logger.log(Status.INFO, timestamp+" | "+actionPerformed)
//	}
	
	
	/**
	 * Function used to get the BY selector from a given web element. Used for logging purposes.
	 * @param element WebElement
	 * @return BY 
	 */
	private By getByFromElement(WebElement element) {
	    By by = null;
	    //[[ChromeDriver: chrome on XP (d85e7e220b2ec51b7faf42210816285e)] -> xpath: //input[@title='Search']]
	    String[] pathVariables = (element.toString().split("->")[1].replaceFirst("(?s)(.*)\\]", "$1" + "")).split(":");

	    String selector = pathVariables[0].trim();
	    String value = pathVariables[1].trim();

	    switch (selector) {
	        case "id":
	            by = By.id(value);
	            break;
	        case "className":
	            by = By.className(value);
	            break;
	        case "tagName":
	            by = By.tagName(value);
	            break;
	        case "xpath":
	            by = By.xpath(value);
	            break;
	        case "cssSelector":
	            by = By.cssSelector(value);
	            break;
	        case "linkText":
	            by = By.linkText(value);
	            break;
	        case "name":
	            by = By.name(value);
	            break;
	        case "partialLinkText":
	            by = By.partialLinkText(value);
	            break;
	        default:
	            throw new IllegalStateException("locator : " + selector + " not found!!!");
	    }
	    return by;
	}

	/**
	 * Function to return the current timestamp
	 * @return LONG representing the current system date and time.
	 */
	private long getCurrentTimestamp() {
		Instant instant = Instant.now();
		long timeStampMillis = instant.toEpochMilli();
		return timeStampMillis;
	}

	/**
	 * Function that highlights the element that the web driver is looking at on the browser, useful for debugging purposes
	 * @param element WEBELEMENT the element that the driver has selected.
	 */
	private void highlightElement(WebElement element) {
		
		if(Settings.HighlightElement)
		{
			try
			{
				JavascriptExecutor js = (JavascriptExecutor)Driver;
				for(int i=0; i<10; i++)
				{
					js.executeScript("arguments[0].style.border='4px groove red'", element);
					js.executeScript("arguments[0].style.border='4px groove green'", element);
					js.executeScript("arguments[0].style.border='4px groove blue'", element);
				}
				js.executeScript("arguments[0].style.border='4px groove blue'", element);
			}
			catch(Exception e)
			{
				this.handleTestFailure(e, "Highlight element functionality failed.");
			}
		}
	}

	/**
	 * Function to handle when a test fails
	 * @param e EXCEPTION
	 * @param message STRING describing the error message thrown
	 */
	private void handleTestFailure(Exception e, String message) {
		//todo
	}
	
	
	
}
