package tests;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.DemoLoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class DemoBaseTest extends BaseTest {

    @Test
    public void testLoginButtonAttribute() {
        DemoLoginPage loginPage = new DemoLoginPage(getDriver());
        loginPage.waitUntilPageLoaded();

        Assert.assertEquals(loginPage.getLoginButtonValue(), "Login");
    }

    @Test
    public void testLockedOutUserShowsError() {
        DemoLoginPage loginPage = new DemoLoginPage(getDriver());
        ConfigReader config = ConfigReader.getInstance();

        loginPage.waitUntilPageLoaded();
        loginPage.enterUsername(config.getProperty("locked.username"));
        loginPage.enterPassword(config.getProperty("standard.password"));
        loginPage.scrollToLoginButton();
        loginPage.clickLogin();

        Assert.assertTrue(loginPage.isErrorDisplayed());
        Assert.assertTrue(loginPage.getErrorMessageText().contains("locked out"));
    }

    @Test
    public void testFailToCheckScreenshot() {
        DemoLoginPage loginPage = new DemoLoginPage(getDriver());
        loginPage.waitUntilPageLoaded();

        Assert.assertEquals(
                loginPage.getLoginButtonValue(),
                "Sign In",
                "Co tinh fail de kiem tra screenshot"
        );
    }
}