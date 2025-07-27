package pages;

import org.openqa.selenium.*;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.List;

import static org.testng.Reporter.log;

public class InventoryPage {

    private final WebDriver driver;

    // 🔎 Locators
    @FindBy(className = "shopping_cart_badge")
    private WebElement cartBadge;
    @FindBy(className = "shopping_cart_link")
    private WebElement cartLink;
    @FindBy(id = "react-burger-menu-btn")
    private WebElement menuButton;
    @FindBy(id = "logout_sidebar_link")
    private WebElement logoutLink;

    public InventoryPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public void addToCart(String productName) {
        try {
            WebElement button = driver.findElement(getAddToCartLocator(productName));
            button.click();
            log("# Added product to cart: '" + productName + "'", true);
        } catch (NoSuchElementException e) {
            log("# Product not found (could not add to cart): '" + productName + "'", true);
        }
    }

    public void removeFromCart(String productName) {
        List<WebElement> removeButtons = driver.findElements(getRemoveFromCartLocator(productName));
        if (!removeButtons.isEmpty()) {
            removeButtons.getFirst().click();
            log("# Removed '" + productName + "' from cart", true);
        } else {
            log("# Could not remove '" + productName + "': Remove button not found (possibly never added)", true);
        }
    }

    public int getCartBadgeCount() {
        try {
            String count = cartBadge.getText();
            return Integer.parseInt(count);
        } catch (Exception e) {
            return 0; // No badge means zero items
        }
    }

    public void openCart() {
        cartLink.click();
    }

    /** ensures that all elements are clickable (but it's optional - view clickLogout() method) */
    public void openSideMenu() {
        menuButton.click();
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.visibilityOf(logoutLink));
    }

    public void clickLogout() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        wait.until(ExpectedConditions.elementToBeClickable(logoutLink));
        logoutLink.click();
    }

    public boolean isLogoutLinkPresent() {
        return !driver.findElements(By.id("logout_sidebar_link")).isEmpty();
    }

    /** AUX */
    private By getAddToCartLocator(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        return By.id("add-to-cart-" + productId);
    }

    /** AUX */
    private By getRemoveFromCartLocator(String productName) {
        String productId = productName.toLowerCase().replace(" ", "-");
        return By.id("remove-" + productId);
    }
}
