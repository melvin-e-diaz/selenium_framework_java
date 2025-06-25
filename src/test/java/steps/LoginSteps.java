package steps;

import com.selenium.framework.base.Base;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.en.And;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;
import pages.HomePage;
import pages.LoginPage;

import java.util.List;

import org.testng.Assert;

/**
 * Created by Karthik-pc on 12/4/2018.
 */
public class LoginSteps extends Base {

    @And("^I ensure application opened$")
    public void iEnsureApplicationOpened() {
        CurrentPage = GetInstance(HomePage.class);
        Assert.assertTrue(CurrentPage.As(HomePage.class).IsLogin(), "The login page is not loaded");

    }


    @Then("^I click login link$")
    public void iClickLoginLink() {
        //Navigation to Login Page
        CurrentPage = CurrentPage.As(HomePage.class).ClickLogin();
    }

    @When("^I enter UserName and Password$")
    public void iEnterUserNameAndPassword(DataTable data) {
        //List<List<String>> table = data.raw();
    	List<List<String>> table = data.asLists();
        CurrentPage.As(LoginPage.class).Login(table.get(1).get(0).toString(), table.get(1).get(1).toString());
    }

    @Then("^I click login button$")
    public void iClickLoginButton() throws InterruptedException {
        //Home Page
        CurrentPage = CurrentPage.As(LoginPage.class).ClickLogin();
    }

    @Then("^I should see the username with hello$")
    public void iShouldSeeTheUsernameWithHello() throws Throwable {
        Assert.assertEquals("The user is not admin", "Hello admin!", CurrentPage.As(HomePage.class).GetLoggedInUser());
    }


}
