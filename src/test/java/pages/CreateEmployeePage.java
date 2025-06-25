package pages;

import com.selenium.framework.base.BasePage;
import com.selenium.framework.base.DriverContext;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.How;

/**
 * Sample page with page objects
 */
public class CreateEmployeePage extends BasePage {

    @FindBy(how = How.ID, using = "Name")
    public WebElement txtName;

    @FindBy(how = How.ID, using = "Salary")
    WebElement txtSalary;
    
    @FindBy(how = How.ID, using = "DurationWorked")
    public WebElement txtDurationWorked;

    @FindBy(how = How.ID, using = "Grade")
    public WebElement txtGrade;

    @FindBy(how = How.ID, using = "Email")
    public WebElement txtEmail;

    @FindBy(how = How.XPATH, using = "//input[@value='Create']")
    WebElement btnCreateEmployee;

    public void ClickCreateButton() {
        btnCreateEmployee.submit();
    }


    public void CreateEmployee(String name, String salary, String durationworked, String grade, String email) {
        DriverContext.WaitForElementVisible(txtEmail);
        txtName.sendKeys(name);
        txtSalary.sendKeys(salary);
        txtDurationWorked.sendKeys(durationworked);
        txtGrade.sendKeys(grade);
        txtEmail.sendKeys(email);
        
    }
}
