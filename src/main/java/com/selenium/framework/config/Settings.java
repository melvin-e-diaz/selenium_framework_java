package com.selenium.framework.config;

import com.selenium.framework.base.BrowserType;
import com.selenium.framework.utilities.LogUtil;

import java.sql.Connection;

public class Settings
{
   
	//For Application Backend
    public static Connection AUTConnection;
   
    //For EARS Reporting
    public static Connection ReportingConnection;
    
    //For Application backend connection string
    public static String AUTConnectionString;
    public static String ReportingConnectionString;
    
    //Log Path for framework
    public static String LogPath;
    
    //Driver Type for SQL Server connectivity
    public static String DriverType;
    public static String ExcelSheetPath;
    public static String AUT;
    public static  BrowserType BrowserType;
    public static LogUtil Logs;
    
    //Global WebDriverWait
    public static long WebDriverWait;
    
    //Global Highlight Element Functionality
    public static boolean HighlightElement;
    
    //Web Driver Manager
    public static boolean WebDriverManagerEnabled;
    
    //Headless Execution
    public static boolean Headless;

}