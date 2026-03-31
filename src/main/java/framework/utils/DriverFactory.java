package framework.utils;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.RemoteWebDriver;

import io.github.bonigarcia.wdm.WebDriverManager;

import java.net.URL;
import java.time.Duration;

public class DriverFactory {

    public static WebDriver createDriver(String browser) {
        String browserName = (browser == null || browser.isBlank()) ? "chrome" : browser;
        String gridUrl = System.getProperty("grid.url");
        boolean isCI = System.getenv("CI") != null;

        if (gridUrl != null && !gridUrl.isBlank()) {
            return createRemoteDriver(browserName, gridUrl, isCI);
        }

        return switch (browserName.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(isCI);
            default -> createChromeDriver(isCI);
        };
    }

    private static WebDriver createChromeDriver(boolean headless) {
        WebDriverManager.chromedriver().setup();

        ChromeOptions options = new ChromeOptions();

        if (headless) {
            options.addArguments("--headless=new");
            options.addArguments("--no-sandbox");
            options.addArguments("--disable-dev-shm-usage");
            options.addArguments("--window-size=1920,1080");
        } else {
            options.addArguments("--start-maximized");
        }

        return new ChromeDriver(options);
    }

    private static WebDriver createFirefoxDriver(boolean headless) {
        WebDriverManager.firefoxdriver().setup();

        FirefoxOptions options = new FirefoxOptions();

        if (headless) {
            options.addArguments("-headless");
        }

        return new FirefoxDriver(options);
    }

    private static WebDriver createRemoteDriver(String browser, String gridUrl, boolean headless) {
        try {
            URL gridEndpoint = new URL(gridUrl + "/wd/hub");

            if ("firefox".equalsIgnoreCase(browser)) {
                FirefoxOptions options = new FirefoxOptions();

                if (headless) {
                    options.addArguments("-headless");
                }

                RemoteWebDriver driver = new RemoteWebDriver(gridEndpoint, options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                return driver;
            } else {
                ChromeOptions options = new ChromeOptions();
                options.addArguments("--no-sandbox");
                options.addArguments("--disable-dev-shm-usage");

                if (headless) {
                    options.addArguments("--headless=new");
                    options.addArguments("--window-size=1920,1080");
                }

                RemoteWebDriver driver = new RemoteWebDriver(gridEndpoint, options);
                driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
                return driver;
            }
        } catch (Exception e) {
            throw new RuntimeException("Không tạo được RemoteWebDriver. Kiểm tra grid.url", e);
        }
    }
}