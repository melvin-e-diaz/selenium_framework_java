package tests.sauceLabs;

import org.openqa.selenium.support.PageFactory;
import org.testng.annotations.Test;

import com.aventstack.extentreports.Status;
import com.selenium.framework.pageObjects.sauceLabsDemo.SauceLabs_LoginPage;

import tests.BaseTest;


public class SauceLabs_SmokeTest extends BaseTest
{
	boolean isHomePageOpen = false;
	private static final String SAUCE_LABS_URL = "https://www.saucedemo.com";
	
	@Test(priority=0, description="Opens the Sauce Labs test website.")
	public void openSauceLabsWebsite()
	{
		logger = extent.createTest("Open Sauce Labs test website", "Opens the Sauce Labs test website in the browser specified in the config file.");
		
		Driver.get(SAUCE_LABS_URL);
		
		logger.log(Status.PASS, "Sauce Labs website opened successfully in web browser.");
		
		isHomePageOpen = true;
	}
	
	@Test(priority=1, description="Verifies the Sauce Labs login page.")
	public void verifySauceLabsLoginPage()
	{
		logger = extent.createTest("Verify Sauce Labs Home Page", "Verifies the page objects on the Sauce Labs Home Page.");
		
		if(!isHomePageOpen) //home page is not opened, open home page
			Driver.get(SAUCE_LABS_URL);
		
		//initialize Sauce Labs Login Page
		SauceLabs_LoginPage loginPage = PageFactory.initElements(Driver, SauceLabs_LoginPage.class);
		
		//verify Sauce Labs Login Page
		loginPage.verifySwagLabsLoginPage();
		
		logger.log(Status.PASS, "Sauce Labs website verified successfully.");		
	}

}
