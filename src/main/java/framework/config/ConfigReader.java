package framework.config;

import java.io.FileInputStream;
import java.io.IOException;
import java.util.Properties;

public class ConfigReader {

    private static ConfigReader instance;
    private final Properties properties = new Properties();

    private ConfigReader() {
        String env = System.getProperty("env", "dev");
        String filePath = "src/test/resources/config-" + env + ".properties";

        try (FileInputStream fis = new FileInputStream(filePath)) {
            properties.load(fis);
            System.out.println("[ConfigReader] Dang dung moi truong: " + env);
        } catch (IOException e) {
            throw new RuntimeException("Khong tim thay file config: " + filePath, e);
        }
    }

    public static ConfigReader getInstance() {
        if (instance == null) {
            instance = new ConfigReader();
        }
        return instance;
    }

    public static void reset() {
        instance = null;
    }

    public String getBaseUrl() {
        return properties.getProperty("base.url");
    }

    public int getExplicitWait() {
        return Integer.parseInt(properties.getProperty("explicit.wait", "15"));
    }

    public int getImplicitWait() {
        return Integer.parseInt(properties.getProperty("implicit.wait", "5"));
    }

    public String getScreenshotPath() {
        return properties.getProperty("screenshot.path", "target/screenshots/");
    }

    public String getProperty(String key) {
        return properties.getProperty(key);
    }
}