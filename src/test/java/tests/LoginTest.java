package tests;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.InventoryPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class LoginTest extends BaseTest {

    @Test
    public void testValidLoginSuccess() {
        ConfigReader config = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

//        InventoryPage inventoryPage = loginPage.login(
//                config.getProperty("standard.username"),
//                config.getProperty("standard.password")
//        );
        
        InventoryPage inventoryPage = loginPage.login(
                config.getUsername(),
                config.getPassword()
        );

        Assert.assertTrue(inventoryPage.isLoaded(), "Dang nhap dung nhung khong vao duoc Inventory Page");
    }

    @Test
    public void testLockedOutUserShowsError() {
        ConfigReader config = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure(
                config.getProperty("locked.username"),
                config.getProperty("standard.password")
        );

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Khong hien thi error message");
        Assert.assertTrue(loginPage.getErrorMessage().contains("locked out"), "Noi dung loi khong dung");
    }

    @Test
    public void testWrongPasswordShowsError() {
        ConfigReader config = ConfigReader.getInstance();
        LoginPage loginPage = new LoginPage(getDriver());

        loginPage.loginExpectingFailure(
                config.getProperty("standard.username"),
                config.getProperty("invalid.password")
        );

        Assert.assertTrue(loginPage.isErrorDisplayed(), "Khong hien thi error message");
        Assert.assertTrue(loginPage.getErrorMessage().contains("do not match"), "Noi dung loi khong dung");
    }
}