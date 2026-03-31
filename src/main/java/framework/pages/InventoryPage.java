package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;

import java.util.List;

public class InventoryPage extends BasePage {

    @FindBy(className = "inventory_list")
    private WebElement inventoryList;

    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;

    @FindBy(xpath = "(//button[contains(@id,'add-to-cart')])[1]")
    private WebElement firstAddToCartButton;

    public InventoryPage(WebDriver driver) {
        super(driver);
    }

    public boolean isLoaded() {
        return isElementVisible(By.className("inventory_list"));
    }

    public InventoryPage addFirstItemToCart() {
        waitAndClick(firstAddToCartButton);
        return this;
    }

    public InventoryPage addItemByName(String name) {
        WebElement addButton = driver.findElement(
                By.xpath("//div[@class='inventory_item_name' and text()='" + name + "']/ancestor::div[@class='inventory_item']//button")
        );
        waitAndClick(addButton);
        return this;
    }

    public int getCartItemCount() {
        List<WebElement> badges = driver.findElements(By.className("shopping_cart_badge"));
        if (badges.isEmpty()) {
            return 0;
        }
        return Integer.parseInt(badges.get(0).getText().trim());
    }

    public CartPage goToCart() {
        waitAndClick(cartLink);
        return new CartPage(driver);
    }
}