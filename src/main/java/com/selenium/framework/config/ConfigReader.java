package com.selenium.framework.config;

import com.selenium.framework.base.BrowserType;

import java.io.FileInputStream;
import java.io.InputStream;
import java.util.Properties;


public class ConfigReader {


    public static  void PopulateSettings() throws Exception {
        ConfigReader reader = new ConfigReader();
        reader.ReadProperty();
    }


    private void ReadProperty() throws Exception
    {	
        //Create Property Object
        Properties p = new Properties();
        
        //Load the Property file available in same package
        InputStream inputStream = new FileInputStream("src/main/java/com/selenium/framework/config/GlobalConfig.properties");
        p.load(inputStream);
        
        //Get AUTConnection String
        Settings.AUTConnectionString = p.getProperty("AUTConnectionString");
        
        //Get Reporting String
        Settings.ReportingConnectionString = p.getProperty("ReportingConnectionString");
        
        //Get LogPath
        Settings.LogPath = p.getProperty("LogPath");
        
        //Get DriverType
        Settings.DriverType = p.getProperty("DriverType");
        
        //GEt ExcelSheetPath
        Settings.ExcelSheetPath = p.getProperty("ExcelSheetPath");
        
        //Get AUT
        Settings.AUT = p.getProperty("AUT");
        
        //Browser Type
        Settings.BrowserType = BrowserType.valueOf(p.getProperty("BrowserType"));
        
        //Default WebDriverWait
        Settings.WebDriverWait = Long.parseLong(p.getProperty("WebDriverWait"));
        
        //Highlight Functionality Flag
        switch(p.getProperty("HighlightElement").toLowerCase())
        {
	        case "true":
	        {
	        	Settings.HighlightElement = true;
	        	break;
	        }
	        case "false":
	        {
	        	Settings.HighlightElement = false;
	        	break;
	        }
	        default: throw new Exception();
        }
        
        //Web Driver Manager Enabled Flag
        switch(p.getProperty("WebDriverManagerEnabled").toLowerCase())
        {
	        case "true":
	        {
	        	Settings.WebDriverManagerEnabled = true;
	        	break;
	        }
	        case "false":
	        {
	        	Settings.WebDriverManagerEnabled = false;
	        	break;
	        }
	        default: throw new Exception();
        }
        
        //Headless Enabled Flag
        switch(p.getProperty("Headless").toLowerCase())
        {
	        case "true":
	        {
	        	Settings.Headless = true;
	        	break;
	        }
	        case "false":
	        {
	        	Settings.Headless = false;
	        	break;
	        }
	        default: throw new Exception();
        }
    }

}

