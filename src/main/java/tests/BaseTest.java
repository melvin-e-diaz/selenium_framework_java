package tests;

import java.sql.Timestamp;
import java.text.SimpleDateFormat;
import java.time.Duration;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeClass;

import com.aventstack.extentreports.ExtentReports;
import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.Status;
import com.aventstack.extentreports.markuputils.ExtentColor;
import com.aventstack.extentreports.markuputils.MarkupHelper;
import com.aventstack.extentreports.reporter.ExtentSparkReporter;
import com.selenium.framework.base.BasePage;
import com.selenium.framework.base.Browser;
import com.selenium.framework.base.DriverContext;
import com.selenium.framework.base.FrameworkInitialize;
import com.selenium.framework.config.ConfigReader;
import com.selenium.framework.config.Settings;
import com.selenium.framework.utilities.ScreenShotUtil;

public class BaseTest {
	
	public static WebDriver Driver;
	private WebDriverWait wait = new WebDriverWait(Driver, Duration.ofSeconds(Settings.WebDriverWait));
	public static ExtentTest logger;
	public Browser browser;
	
	protected ExtentReports extent;
	ExtentSparkReporter htmlReporter;
	
	private static final SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMddHHmmss");
	public static Timestamp timestamp = new Timestamp(System.currentTimeMillis());
	
	public static String htmlReportFolder = "\\"+sdf.format(timestamp)+"\\";
	
	/**
	 * SETUP Method: Run before every class.
	 * Initializes ExtentReports
	 * Initializes driver based on config settings
	 * Initializes default wait time.
	 * Maximize browser window.
	 * @throws Exception 
	 */
	@BeforeClass(alwaysRun = true)
	public void setup() throws Exception
	{
		ConfigReader.PopulateSettings();
		
		//create a new test folder and update the paths for reports
		if(!(Settings.LogPath.isEmpty()))
		{
			timestamp = new Timestamp(System.currentTimeMillis());
			htmlReportFolder = Settings.LogPath+"\\"+sdf.format(timestamp)+"\\";
			System.out.println(htmlReportFolder);
		}
		
		extent = new ExtentReports();
		htmlReporter = new ExtentSparkReporter(htmlReportFolder+"report.html");
		extent.attachReporter(htmlReporter);
		
		String os = this.OSDetector();
		String javaVersion = System.getProperty("java.version");
		
		extent.setSystemInfo("Selenium Version", "4.29.00");
		extent.setSystemInfo("Operating System", os);
		extent.setSystemInfo("Java Version", javaVersion);
		
		//set up browser
		this.setUpBrowser();
		
		long implicitlyWait = Settings.WebDriverWait;
		WebDriverWait await = new WebDriverWait(Driver, Duration.ofSeconds(implicitlyWait));
		wait = await;
		
		BaseTest.Driver = DriverContext.Driver;
		BaseTest.logger = BasePage.logger;
		Driver.manage().window().maximize();		
	}

	/**
	 * Function to set up web browser.
	 */
	private void setUpBrowser() {
		FrameworkInitialize framework = new FrameworkInitialize();
		framework.InitializeBrowser(Settings.BrowserType);
	}
	
	/**
	 * Run after every test method completion. Collects the result of the test and screenshots and logs them into the report.
	 * @param result ITESTRESULT file used for reporting
	 * @throws Exception for test failure.
	 */
	@AfterMethod(alwaysRun=true)
	public void getResult(ITestResult result) throws Exception
	{
		String debugString = String.format("Ran Test: %s with status of %s", result.getName(), result.getStatus());
		System.out.println(debugString);
		
		switch(result.getStatus())
		{
			case ITestResult.FAILURE:
			{
				String screenshot = ScreenShotUtil.captureScreenFile(Driver, logger, Status.FAIL, ScreenShotUtil.generateFileName(result));
				logger.log(Status.FAIL, MarkupHelper.createLabel(result.getName() + " Test case FAILED due to below issues:", ExtentColor.RED));
				logger.log(Status.FAIL, result.getThrowable());
				logger.fail("Screenshot: "+logger.addScreenCaptureFromPath(screenshot));
				break;
			}
			case ITestResult.SUCCESS:
			{
				String screenshot = ScreenShotUtil.captureScreenFile(Driver, logger, Status.PASS, ScreenShotUtil.generateFileName(result));
				logger.log(Status.PASS, MarkupHelper.createLabel(result.getName() + " Test case PASSED", ExtentColor.GREEN));
				logger.pass("Screenshot: "+logger.addScreenCaptureFromPath(screenshot));
				break;
			}
			case ITestResult.SKIP:
			{
				String screenshot = ScreenShotUtil.captureScreenFile(Driver, logger, Status.SKIP, ScreenShotUtil.generateFileName(result));
				logger.log(Status.SKIP, MarkupHelper.createLabel(result.getName() + " Test case SKIPPED", ExtentColor.BLUE));
				logger.skip("Screenshot: "+logger.addScreenCaptureFromPath(screenshot));
			}
			default: throw new Exception("ERROR: Invalid ITestResult");			
		}
	}
	
	/**
	 * Flushes report after every test.
	 */
	@AfterTest(alwaysRun=true)
	public void testEnd()
	{
		extent.flush();
	}

	/**
	 * Function to detect the current operating system and return it for report purposes.
	 * @return STRING containing the operating system name.
	 */
	private static String OSDetector() {
		String os = System.getProperty("os.name").toLowerCase();
		
		if(os.contains("win")) return "Windows";
		else if(os.contains("nux")||os.contains("nix")) return "Linux";
		else if(os.contains("mac")) return "Mac";
		else if(os.contains("sunos")) return "Solaris";
		else return "Other";
			
	}
	
	

}
