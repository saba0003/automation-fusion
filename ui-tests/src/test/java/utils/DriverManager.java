package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;

public class DriverManager {

    private final WebDriver driver;

    /**
     * You can set your preferred browser in the config.properties file:
     * Path: /ui-tests/src/test/resources/config/config.properties
     * Supported values: chrome, firefox, edge.
     */
    public DriverManager() {
        String browser = ConfigReader.get("browser").toLowerCase();
        switch (browser) {
            case "chrome" -> {
                WebDriverManager.chromedriver().setup();
                driver = new ChromeDriver();
            }
            case "firefox" -> {
                WebDriverManager.firefoxdriver().setup();
                driver = new FirefoxDriver();
            }
            case "edge" -> {
                WebDriverManager.edgedriver().setup();
                driver = new EdgeDriver();
            }
            default -> throw new RuntimeException("Unsupported browser in config: " + browser);
        }
    }

    public WebDriver getDriver() {
        return driver;
    }

    public void quitDriver() {
        driver.quit();
    }
}
