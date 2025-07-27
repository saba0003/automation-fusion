package pages;

import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.StaleElementReferenceException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import utils.ConfigReader;

import java.time.Duration;
import java.util.List;

import static org.testng.Reporter.log;

public class LoginPage {

    public static final List<String> VALID_USERNAMES = List.of(
            "standard_user",
            "locked_out_user",
            "problem_user",
            "performance_glitch_user",
            "error_user",
            "visual_user"
    );

    private static final String VALID_PASSWORD = "secret_sauce";

    private final WebDriver driver;

    // 🔎 Locators
    @FindBy(id = "user-name")
    private WebElement usernameField;
    @FindBy(id = "password")
    private WebElement passwordField;
    @FindBy(id = "login-button")
    private WebElement loginButton;
    @FindBy(css = "[data-test='error']")
    private WebElement errorMessage;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void loginWith(String username, String password) {
        log("# Logging in with user: '" + username + "'", true);
        type(usernameField, username);
        type(passwordField, password);
        loginButton.click();
        waitForInventoryRedirectIfNeeded(username);
    }

    public void login() {
        loginWith(VALID_USERNAMES.getFirst(), VALID_PASSWORD);
    }

    public String getErrorMessage() {
        try {
            return errorMessage.getText();
        } catch (NoSuchElementException e) {
            return "";
        }
    }

    public boolean isOnLoginPage() {
        String url = driver.getCurrentUrl();
        return url != null && url.contains(ConfigReader.get("base.url")) && isVisible(loginButton);
    }

    public void tryToDirectlyAccessProductsPage() {
        driver.get(ConfigReader.get("inventory.url"));
    }

    public void tryToDirectlyAccessCartPage() {
        driver.get(ConfigReader.get("cart.url"));
    }

    public boolean isOnProductsPage() {
        String url = driver.getCurrentUrl(), title = driver.getTitle();
        return url != null && title != null && url.contains("inventory.html") && title.contains("Swag Labs");
    }

    /** AUX */
    private void type(WebElement element, String text) {
        element.clear();
        element.sendKeys(text);
    }

    /** AUX */
    private void waitForInventoryRedirectIfNeeded(String username) {
        if ("performance_glitch_user".equals(username)) {
            log("🐢 Waiting for inventory page to load due to performance glitch user", true);
            new WebDriverWait(driver, Duration.ofSeconds(10))
                    .until(ExpectedConditions.urlContains(ConfigReader.get("inventory.url")));
        }
    }

    /** AUX */
    private boolean isVisible(WebElement element) {
        try {
            return element.isDisplayed();
        } catch (NoSuchElementException | StaleElementReferenceException e) {
            return false;
        }
    }
}
