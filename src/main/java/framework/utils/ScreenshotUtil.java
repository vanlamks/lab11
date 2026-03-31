package framework.utils;

import framework.config.ConfigReader;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.nio.file.StandardCopyOption;
import java.text.SimpleDateFormat;
import java.util.Date;

public class ScreenshotUtil {

    public static String capture(WebDriver driver, String testName) {
        try {
            String folderPath = ConfigReader.getInstance().getScreenshotPath();
            Files.createDirectories(Paths.get(folderPath));

            String timeStamp = new SimpleDateFormat("yyyyMMdd_HHmmss").format(new Date());
            String filePath = folderPath + testName + "_" + timeStamp + ".png";

            File srcFile = ((TakesScreenshot) driver).getScreenshotAs(OutputType.FILE);
            Files.copy(srcFile.toPath(), Path.of(filePath), StandardCopyOption.REPLACE_EXISTING);

            System.out.println("[Screenshot] Da luu: " + filePath);
            return filePath;
        } catch (IOException e) {
            throw new RuntimeException("Khong chup duoc screenshot", e);
        }
    }
}