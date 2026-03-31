package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

public class DemoLoginPage extends BasePage {

    @FindBy(id = "user-name")
    private WebElement usernameField;

    @FindBy(id = "password")
    private WebElement passwordField;

    @FindBy(id = "login-button")
    private WebElement loginButton;

    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public DemoLoginPage(WebDriver driver) {
        super(driver);
    }

    public void waitUntilPageLoaded() {
        waitForPageLoad();
    }

    public void scrollToLoginButton() {
        scrollToElement(loginButton);
    }

    public void enterUsername(String username) {
        waitAndType(usernameField, username);
    }

    public void enterPassword(String password) {
        waitAndType(passwordField, password);
    }

    public void clickLogin() {
        waitAndClick(loginButton);
    }

    public boolean isErrorDisplayed() {
        return isElementVisible(By.cssSelector("[data-test='error']"));
    }

    public String getErrorMessageText() {
        return getText(errorMessage);
    }

    public String getLoginButtonValue() {
        return getAttribute(loginButton, "value");
    }
}