package steps;

import io.cucumber.java.en.*;
import pages.CartPage;
import pages.InventoryPage;
import pages.LoginPage;
import utils.PageManager;
import utils.TestContext;

import static org.testng.Assert.*;
import static utils.LogFormatter.logPrefix;

public class CartSteps {

    private final LoginPage loginPage;
    private final InventoryPage inventoryPage;
    private final CartPage cartPage;

    public CartSteps(TestContext context) {
        PageManager pageManager = context.getPageManager();
        this.loginPage = pageManager.getLoginPage();
        this.inventoryPage = pageManager.getInventoryPage();
        this.cartPage = pageManager.getCartPage();
        logPrefix("CartSteps initialized with PicoContainer injection", false);
    }

    @Given("User is logged in with valid credentials")
    public void user_is_logged_in_with_valid_credentials() {
        logPrefix("Given user is logged in with valid credentials", true);
        loginPage.login(); // "standard_user"
    }

    @When("User adds {string} to the cart")
    public void user_adds_product_to_cart(String productName) {
        logPrefix("When user adds '" + productName + "' to the cart", true);
        logPrefix("Adding product to cart: '" + productName + "'", false);
        inventoryPage.addToCart(productName);
    }

    @When("User removes {string} from the cart")
    public void user_removes_product_from_cart(String productName) {
        logPrefix("When user removes '" + productName + "' from the cart", true);
        inventoryPage.removeFromCart(productName);
    }

    @When("User opens the cart")
    public void user_opens_cart() {
        logPrefix("When user opens the cart", true);
        inventoryPage.openCart();
        logPrefix("Checking if user is on his cart page", false);
        assertTrue(cartPage.isOnCartPage(), "User should be on his cart page");
        logPrefix("User is on his cart page", false);
    }

    @When("User tries to add a non-existent product {string}")
    public void user_tries_to_add_non_existent_product(String productName) {
        logPrefix("When user tries to add a non-existent product" + productName, true);
        inventoryPage.addToCart(productName);
    }

    @Then("The cart badge should show {int} item")
    public void cart_badge_should_show_item(int expectedCount) {
        logPrefix("Then the cart badge should show " + expectedCount + " item", true);
        int actualCount = inventoryPage.getCartBadgeCount();
        logPrefix("Cart badge shows: " + actualCount, false);
        assertEquals(actualCount, expectedCount, "Cart badge count mismatch");
        logPrefix("Cart badge validation passed", false);
    }

    @Then("The cart should be empty")
    public void cart_should_be_empty() {
        logPrefix("Then the cart should be empty", true);
        inventoryPage.openCart();
        logPrefix("Checking if cart is empty", false);
        assertTrue(cartPage.isCartEmpty(), "Cart is not empty");
        logPrefix("Cart is confirmed empty", false);
    }

    @And("The product {string} should appear in the cart")
    public void product_should_appear_in_cart(String productName) {
        logPrefix("And the product '" + productName + "' should appear in the cart", true);
        inventoryPage.openCart();
        logPrefix("Verifying product in cart: " + productName, false);
        assertTrue(cartPage.isProductInCart(productName), productName + " not found in cart");
        logPrefix("Product is present in the cart", false);
    }

    @And("The product {string} should not appear in the cart")
    public void product_should_not_appear_in_cart(String productName) {
        logPrefix("And the product '" + productName + "' should not appear in the cart", true);
        inventoryPage.openCart();
        boolean found = cartPage.isProductInCart(productName);
        logPrefix("Checking cart for: " + productName + " — Found: " + found, false);
        assertFalse(found, "❌ Product '" + productName + "' unexpectedly found in cart");
    }
}
