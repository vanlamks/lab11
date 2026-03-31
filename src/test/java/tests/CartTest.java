package tests;

import framework.base.BaseTest;
import framework.config.ConfigReader;
import framework.pages.CartPage;
import framework.pages.LoginPage;
import org.testng.Assert;
import org.testng.annotations.Test;

public class CartTest extends BaseTest {

    @Test
    public void testFluentInterfaceAddFirstItemAndGoToCart() {
        ConfigReader config = ConfigReader.getInstance();

        CartPage cartPage = new LoginPage(getDriver())
                .login(config.getProperty("standard.username"), config.getProperty("standard.password"))
                .addFirstItemToCart()
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 1, "So luong item trong gio phai la 1");
    }

    @Test
    public void testAddItemByName() {
        ConfigReader config = ConfigReader.getInstance();

        CartPage cartPage = new LoginPage(getDriver())
                .login(config.getProperty("standard.username"), config.getProperty("standard.password"))
                .addItemByName(config.getProperty("product.name"))
                .goToCart();

        Assert.assertTrue(
                cartPage.getItemNames().contains(config.getProperty("product.name")),
                "Khong tim thay san pham vua them trong gio hang");
    }

    @Test
    public void testRemoveFirstItem() {
        ConfigReader config = ConfigReader.getInstance();

        CartPage cartPage = new LoginPage(getDriver())
                .login(config.getProperty("standard.username"), config.getProperty("standard.password"))
                .addFirstItemToCart()
                .goToCart()
                .removeFirstItem();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Xoa xong thi gio hang phai rong");
    }

    @Test
    public void testEmptyCartReturnsZero() {
        ConfigReader config = ConfigReader.getInstance();

        CartPage cartPage = new LoginPage(getDriver())
                .login(config.getProperty("standard.username"), config.getProperty("standard.password"))
                .goToCart();

        Assert.assertEquals(cartPage.getItemCount(), 0, "Gio hang rong thi phai tra ve 0");
    }
}