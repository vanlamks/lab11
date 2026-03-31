package framework.pages;

import framework.base.BasePage;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.ui.ExpectedConditions;

import java.util.ArrayList;
import java.util.List;

public class CartPage extends BasePage {

    @FindBy(id = "checkout")
    private WebElement checkoutButton;

    public CartPage(WebDriver driver) {
        super(driver);
    }

    public int getItemCount() {
        return driver.findElements(By.className("cart_item")).size();
    }

    public CartPage removeFirstItem() {
        List<WebElement> removeButtons = driver.findElements(By.xpath("//button[text()='Remove']"));
        if (!removeButtons.isEmpty()) {
            WebElement btn = removeButtons.get(0);
            waitAndClick(btn);
            // Chờ button đó biến mất để đảm bảo DOM đã cập nhật
            wait.until(ExpectedConditions.stalenessOf(btn));
        }
        return this;
    }

    public CheckoutPage goToCheckout() {
        waitAndClick(checkoutButton);
        return new CheckoutPage(driver);
    }

    public List<String> getItemNames() {
        List<String> names = new ArrayList<>();
        List<WebElement> itemNames = driver.findElements(By.className("inventory_item_name"));
        for (WebElement item : itemNames) {
            names.add(item.getText().trim());
        }
        return names;
    }
}