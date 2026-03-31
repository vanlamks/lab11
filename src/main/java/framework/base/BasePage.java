package framework.base;

import framework.config.ConfigReader;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.TimeoutException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public abstract class BasePage {

    protected WebDriver driver;
    protected WebDriverWait wait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(
                driver,
                Duration.ofSeconds(ConfigReader.getInstance().getExplicitWait())
        );
        PageFactory.initElements(driver, this);
    }

    /**
     * Cho element click được rồi mới click.
     * Dùng để tránh lỗi click khi element chưa sẵn sàng.
     */
    protected void waitAndClick(WebElement element) {
        wait.until(ExpectedConditions.elementToBeClickable(element)).click();
    }

    /**
     * Chờ element hiển thị, xóa dữ liệu cũ rồi nhập text mới.
     * Dùng cho input, textbox, field đăng nhập.
     */
    protected void waitAndType(WebElement element, String text) {
        wait.until(ExpectedConditions.visibilityOf(element));
        element.clear();
        element.sendKeys(text);
    }

    /**
     * Chờ element hiển thị rồi lấy text, đồng thời trim khoảng trắng hai đầu.
     * Dùng để đọc message, label, tiêu đề.
     */
    protected String getText(WebElement element) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getText().trim();
    }

    /**
     * Kiểm tra element có hiển thị hay không bằng locator.
     * Có xử lý StaleElementReferenceException để tránh lỗi khi DOM vừa render lại.
     */
    protected boolean isElementVisible(By locator) {
        try {
            wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
            return true;
        } catch (TimeoutException | NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }

    /**
     * Cuộn trang đến element.
     * Dùng khi element nằm ngoài màn hình nhìn thấy.
     */
    protected void scrollToElement(WebElement element) {
        wait.until(ExpectedConditions.visibilityOf(element));
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].scrollIntoView({block:'center'});",
                element
        );
    }

    /**
     * Chờ trang load hoàn tất.
     * Dùng sau khi mở trang hoặc sau khi điều hướng.
     */
    protected void waitForPageLoad() {
        wait.until(webDriver ->
                ((JavascriptExecutor) webDriver)
                        .executeScript("return document.readyState")
                        .equals("complete")
        );
    }

    /**
     * Chờ element hiển thị rồi lấy giá trị của attribute.
     * Dùng cho value, placeholder, class, href,...
     */
    protected String getAttribute(WebElement element, String attr) {
        return wait.until(ExpectedConditions.visibilityOf(element)).getAttribute(attr);
    }
}