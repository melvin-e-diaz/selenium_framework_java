package com.selenium.framework.utilities;

import java.io.File;
import java.util.Date;
import java.text.SimpleDateFormat;

import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.ITestResult;

import com.aventstack.extentreports.ExtentTest;
import com.aventstack.extentreports.MediaEntityBuilder;
import com.aventstack.extentreports.Status;
import com.google.common.io.Files;

import tests.BaseTest;

public class ScreenShotUtil {

	private static final SimpleDateFormat dateFormat = new SimpleDateFormat("yyyy_MM_dd SSS");
	
	public static String captureScreen(WebDriver driver, ExtentTest logger, String screenName)
	{
		try
		{
			String base64Screenshot = "data:image/png;base64,"+((TakesScreenshot)driver).getScreenshotAs(OutputType.BASE64);
			logger.log(Status.FAIL, screenName, MediaEntityBuilder.createScreenCaptureFromBase64String(base64Screenshot).build());
			return base64Screenshot;
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Unable to take screenshot: "+e.getMessage());
			e.printStackTrace();
			return "";
		}		
	}
	
	public static String captureScreenFile(WebDriver driver, ExtentTest logger, Status status, String screenName)
	{
		try
		{
			File src = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
			String dest = BaseTest.htmlReportFolder + screenName+".png";
			File target = new File(dest);
			Files.createParentDirs(target);
			Files.copy(src, target);
			logger.log(status, screenName, MediaEntityBuilder.createScreenCaptureFromPath(dest).build());
			return dest;
		}
		catch(Exception e)
		{
			System.out.println("ERROR: Unable to take Screenshot: "+e.getMessage());
			e.printStackTrace();
			return "";
		}
	}
	
	public static String generateFileName(ITestResult result)
	{
		Date date = new Date();
		String fileName = result.getName()+"_"+dateFormat.format(date);
		return fileName;
	}
}
