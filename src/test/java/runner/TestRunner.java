package runner;

//import cucumber.api.CucumberOptions;
//import cucumber.api.testng.CucumberFeatureWrapper;
//import cucumber.api.testng.TestNGCucumberRunner;
//import cucumber.runtime.model.CucumberTagStatement;
import org.testng.annotations.AfterClass;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

import io.cucumber.testng.CucumberOptions;
import io.cucumber.testng.FeatureWrapper;
import io.cucumber.testng.PickleWrapper;
import io.cucumber.testng.TestNGCucumberRunner;

import java.util.Iterator;
import java.util.List;

/**
 * Created by Karthik-pc on 24/4/2018.
 */


@CucumberOptions(features = {"src/test/java/features/"}, glue = {"steps"}, plugin = {"json:target/cucumber-json-report.json", "html:target/cucumber-report-html"})
public class TestRunner {


    private TestNGCucumberRunner testNGCucumberRunner;

    @BeforeClass(alwaysRun = true)
    public void setUpClass() {
        testNGCucumberRunner = new TestNGCucumberRunner(this.getClass());
    }


//    @Test(dataProvider = "features")
//    public void runTests(FeatureWrapper cucumberFeatureWrapper) {
//
//        List<CucumberTagStatement> elements = cucumberFeatureWrapper.getCucumberFeature().getFeatureElements();
//
//        for (Iterator<CucumberTagStatement> element = elements.iterator(); element.hasNext(); ) {
//            //ToDo: Bring the scenario name from Excel sheet using the out-of-box library we have in utilities package
//            //Pass the hardcoded scenario name
//
//            CucumberTagStatement scenarioName = element.next();
//            if (!scenarioName.getVisualName().equals("Scenario: Create Employee with all details")) {
//                element.remove();
//            }
//        }
//
//        testNGCucumberRunner.runCucumber(cucumberFeatureWrapper.getCucumberFeature());
//    }
    
    @Test(groups = "cucumber", description = "Runs Cucumber Scenarios", dataProvider = "features")
    public void runScenario(PickleWrapper pickle, FeatureWrapper cucumberFeature)
    {
    	testNGCucumberRunner.runScenario(pickle.getPickle());
    }

    @DataProvider
    public Object[][] scenarios() {
        //return testNGCucumberRunner.provideFeatures();
    	return testNGCucumberRunner.provideScenarios();
    }

    @AfterClass(alwaysRun = true)
    public void afterClass() {
        testNGCucumberRunner.finish();
    }


}
