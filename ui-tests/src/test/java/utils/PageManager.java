package utils;

import org.openqa.selenium.WebDriver;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;

public class PageManager {

    private final WebDriver driver;
    private LoginPage loginPage;
    private InventoryPage inventoryPage;
    private CartPage cartPage;

    public PageManager(WebDriver driver) {
        this.driver = driver;
    }

    public LoginPage getLoginPage() {
        return (loginPage == null) ? loginPage = new LoginPage(driver) : loginPage;
    }

    public InventoryPage getInventoryPage() {
        return (inventoryPage == null) ? inventoryPage = new InventoryPage(driver) : inventoryPage;
    }

    public CartPage getCartPage() {
        return (cartPage == null) ? cartPage = new CartPage(driver) : cartPage;
    }
}
