package steps;

import com.selenium.framework.base.DriverContext;
import com.selenium.framework.base.FrameworkInitialize;
import com.selenium.framework.config.ConfigReader;
import com.selenium.framework.config.Settings;
import com.selenium.framework.utilities.DatabaseUtil;
import com.selenium.framework.utilities.ExcelUtil;
import com.selenium.framework.utilities.LogUtil;
import com.selenium.framework.utilities.ReportingUtil;
import io.cucumber.java.Before;

/**
 * Created by Karthik-pc on 12/4/2018.
 */
public class TestInitialize extends FrameworkInitialize {


    @Before
    public void Initialize() throws Exception {
        //Initialize Config
        ConfigReader.PopulateSettings();

        //Logging
        Settings.Logs = new LogUtil();
        Settings.Logs.CreateLogFile();
        Settings.Logs.Write("Framework Initialize");

        //Create Test Cycle for Reporting
        Settings.ReportingConnection = DatabaseUtil.Open(Settings.ReportingConnectionString);
        ReportingUtil.CreateTestCycle(Settings.ReportingConnection);

        Settings.Logs.Write("Test Cycle Created");
        InitializeBrowser(Settings.BrowserType);
        Settings.Logs.Write("Browser Initialized");
        DriverContext.Browser.GoToUrl(Settings.AUT);
        Settings.Logs.Write("Navigated to URL " + Settings.AUT);

        try {
            ExcelUtil util = new ExcelUtil(Settings.ExcelSheetPath);
        } catch (Exception e) {
        }

    }
}
