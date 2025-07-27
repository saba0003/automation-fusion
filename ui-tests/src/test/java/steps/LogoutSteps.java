package steps;

import io.cucumber.java.en.*;
import org.openqa.selenium.WebDriver;
import pages.InventoryPage;
import pages.LoginPage;
import utils.ConfigReader;
import utils.PageManager;
import utils.TestContext;

import static org.testng.Assert.assertFalse;
import static org.testng.Assert.assertTrue;
import static utils.LogFormatter.logPrefix;

public class LogoutSteps {

    private final WebDriver driver;
    private final InventoryPage inventoryPage;
    private final LoginPage loginPage;

    public LogoutSteps(TestContext context) {
        PageManager pageManager = context.getPageManager();
        this.driver = context.getDriverManager().getDriver();
        this.inventoryPage = pageManager.getInventoryPage();
        this.loginPage = pageManager.getLoginPage();
        logPrefix("LogoutSteps initialized with PicoContainer injection", false);
    }

    @When("User clicks the menu button")
    public void user_clicks_menu_button() {
        logPrefix("When user clicks the menu button", true);
        inventoryPage.openSideMenu();
        logPrefix("Menu button clicked", false);
    }

    @When("User logs out")
    public void user_logs_out() {
        logPrefix("When user logs out", true);
        inventoryPage.openSideMenu();
        logPrefix("Menu button clicked (via 'logs out' step)", false);
        inventoryPage.clickLogout();
        logPrefix("Logout link clicked (via 'logs out' step)", false);
    }

    @When("User tries to access the logout link directly")
    public void user_tries_logout_without_login() {
        logPrefix("When user tries to access the logout link directly", true);
        driver.get(ConfigReader.get("inventory.url"));
        boolean found = inventoryPage.isLogoutLinkPresent();
        logPrefix(found ? "Logout link was unexpectedly present!" : "Logout link not found (as expected)", false);
        assertFalse(found, "Logout link should not be visible without login");
    }

    @Then("User should be redirected to the login page")
    public void user_should_be_redirected_to_login_page() {
        logPrefix("Then user should be redirected to login page", true);
        assertTrue(loginPage.isOnLoginPage(), "❌ User was not redirected to login page");
        logPrefix("User is back on login page", false);
    }

    @Then("Logout should not occur and user should remain on login page")
    public void logout_should_not_occur_user_should_remain_on_login_page() {
        logPrefix("Then logout should not occur and user should remain on login page", true);
        assertTrue(loginPage.isOnLoginPage(), "❌ User was incorrectly allowed into inventory page");
        logPrefix("Verified: User is still on login page (not logged in)", false);
    }

    @And("User clicks the logout button")
    public void user_clicks_logout_link() {
        logPrefix("And user clicks the logout button", true);
        inventoryPage.clickLogout();
        logPrefix("Logout link clicked", false);
    }
}
