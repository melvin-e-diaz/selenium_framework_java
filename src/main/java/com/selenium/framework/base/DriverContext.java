package com.selenium.framework.base;

import com.selenium.framework.config.Settings;

import java.time.Duration;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedCondition;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;


public class DriverContext {

    public static WebDriver Driver;

    public static Browser Browser;
    
    public static long WebDriverWait;


    public static void setDriver(WebDriver driver) {
        Driver = driver;
    }

	/**
	 * Function that uses Javascript to wait for the webpage to load
	 */
    public static void WaitForPageToLoad(){
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
        JavascriptExecutor jsExecutor = (JavascriptExecutor)Driver;

        ExpectedCondition<Boolean> jsLoad = webDriver ->  ((JavascriptExecutor)Driver)
                .executeScript("return document.readyState").toString().equals("complete");

        //Get JS Ready
        boolean jsReady = jsExecutor.executeScript("return document.readyState").toString().equals("complete");

        if(!jsReady)
            wait.until(jsLoad);
        else
            Settings.Logs.Write("Page is ready !");

    }

    /**
     * Waits for the given web element to be visible.
     * @param elementFindBy WEBELEMENT representing the page object
     */
    public static void WaitForElementVisible(final WebElement elementFindBy){
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
        wait.until(ExpectedConditions.visibilityOf(elementFindBy));
    }

    /**
     * Waits for the given element text to be visible
     * @param elementFindBy WEBELEMENT representing the page object
     * @param text STRING visbible text that driver will wait for until found
     */
    public static void WaitForElementTextVisible(final WebElement elementFindBy, String text){
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
        wait.until(ExpectedConditions.textToBePresentInElement(elementFindBy, text));
    }

    /**
     * Waits for the given BY element text to be visible
     * @param element BY representation of the page object
     * @param text STRING visible text that the driver will wait for until found.
     */
    public static void WaitUntilTextDisplayed(final By element, String text){
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
        wait.until(textDisplayed(element, text));
    }

    /**
     * Function that returns whether text is displayed
     * @param elementFindBy BY element representation of the page object
     * @param text STRING visible text that the driver will wait for until it is found
     * @return TRUE if text is displayed, FALSE otherwise
     */
    private static ExpectedCondition<Boolean> textDisplayed (final By elementFindBy, final String text){
        return webDriver -> webDriver.findElement(elementFindBy).getText().contains(text);
    }

    /**
     * Checks whether the element is enabled.
     * @param elementFindBy BY representation of the page object.
     */
    public static void WaitElementEnabled(final By elementFindBy){
        WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
        wait.until(webDriver -> webDriver.findElement(elementFindBy).isEnabled());
    }
    
    /**
     * Checks whether an element is clickable
     * @param elementFindBy
     */
    public static void WaitElementClickable(final WebElement elementFindBy)
    {
    	WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(WebDriverWait));
    	wait.until(ExpectedConditions.elementToBeClickable(elementFindBy));
    }


}

