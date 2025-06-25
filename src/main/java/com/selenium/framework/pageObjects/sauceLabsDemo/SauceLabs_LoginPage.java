package com.selenium.framework.pageObjects.sauceLabsDemo;

import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.selenium.framework.base.BasePage;

public class SauceLabs_LoginPage extends BasePage{

	//PAGE OBJECTS
	@FindBy(how = How.CLASS_NAME, using = "login_logo")
	public WebElement imgLoginLogo;
	
	@FindBy(how = How.ID, using = "user-name")
	public WebElement txtUserName;
	
	@FindBy(how = How.ID, using = "password")
	public WebElement txtPassword;
	
	@FindBy(how = How.ID, using = "login-button")
	public WebElement btnLogin;
	
	//METHODS
	/**
	 * Function to enter user name
	 * @param userName STRING user name to be entered
	 */
	public void enterUserName(String userName) 
	{
		this.writeText(txtUserName, userName);
	}
	
	/**
	 * Function to enter password
	 * @param password
	 */
	public void enterPassword(String password)
	{
		this.writeText(txtPassword, password);
	}
	
	/**
	 * Function to click login button.
	 */
	public void clickLoginButton()
	{
		this.click(btnLogin);
	}
	
	/**
	 * Combined function to use given user name and given password to log in to Swag Labs
	 * @param userName STRING representing the user name
	 * @param password STRING representing the password
	 */
	public void loginToSwagLabs(String userName, String password)
	{
		this.enterUserName(userName);
		this.enterPassword(password);
		this.clickLoginButton();
	}
	
	/**
	 * Function to verify the Swag Labs login page.
	 * @param logger EXTENTTEST to write to log file
	 * @return TRUE if login page is verified, FALSE otherwise.
	 */
	public boolean verifySwagLabsLoginPage()
	{
		try
		{
			this.assertDisplayed(imgLoginLogo, "Swag Labs Logo");
			this.assertEnabled(txtUserName, "User Name Text Box");
			this.assertEnabled(txtPassword, "Password Text Box");
			this.assertEnabled(btnLogin, "Login Button");
		}
		catch(Exception e)
		{
			logger.log(Status.FAIL, "ERROR: Swag Labs login page not verified.");
			return false;
		}
		return true;
	}
	
}
