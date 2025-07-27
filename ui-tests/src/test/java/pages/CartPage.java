package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.ConfigReader;

import java.util.List;

public class CartPage {

    private final WebDriver driver;

    // 🔎 Locator
    @FindBy(className = "cart_item")
    private List<WebElement> cartItems;

    public CartPage(WebDriver driver) {
        this.driver = driver;
        PageFactory.initElements(driver, this);
    }

    public boolean isOnCartPage() {
        String url = driver.getCurrentUrl(), title = driver.getTitle();
        return url != null && title != null && url.contains(ConfigReader.get("cart.url")) && title.contains("Swag Labs");
    }

    public boolean isProductInCart(String productName) {
        return cartItems.stream()
                .map(item -> item.findElement(By.className("inventory_item_name")).getText())
                .anyMatch(name -> name.equalsIgnoreCase(productName));
    }

    public boolean isCartEmpty() {
        return cartItems.isEmpty();
    }
}
