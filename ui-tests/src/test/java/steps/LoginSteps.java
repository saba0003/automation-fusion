package steps;

import io.cucumber.java.en.*;
import pages.LoginPage;
import utils.TestContext;

import static org.testng.Assert.assertTrue;
import static utils.LogFormatter.logPrefix;

public class LoginSteps {

    private final LoginPage loginPage;

    public LoginSteps(TestContext context) {
        this.loginPage = context.getPageManager().getLoginPage();
        logPrefix("LoginSteps initialized with PicoContainer injection", false);
    }

    @Given("User is on the login page")
    public void user_is_on_the_login_page() {
        logPrefix("Given user is on the login page", true);
        assertTrue(loginPage.isOnLoginPage(), "❌ User is not on the login page");
        logPrefix("Verified: User is on the SauceDemo login page", false);
    }

    @When("User logs in with username {string} and password {string}")
    public void user_logs_in_with_credentials(String username, String password) {
        logPrefix("When user logs in with username '" + username + "' and password '" + password + "'", true);
        loginPage.loginWith(username, password);
    }

    @When("User navigates directly to the inventory page")
    public void user_navigates_directly_to_inventory_page() {
        logPrefix("When user navigates directly to the inventory page", true);
        loginPage.tryToDirectlyAccessProductsPage();
    }

    @When("User navigates directly to the cart page")
    public void user_navigates_directly_to_cart_page() {
        logPrefix("When user navigates directly to the cart page", true);
        loginPage.tryToDirectlyAccessCartPage();
    }

    @Then("User should be redirected to the products page")
    public void user_should_be_redirected_to_the_products_page() {
        logPrefix("Then user should be redirected to the products page", true);
        assertTrue(loginPage.isOnProductsPage(), "❌ User is not on the products page");
        logPrefix("Login successful. User is on products page", false);
    }

    @Then("An error message {string} should be displayed")
    public void error_message_should_be_displayed(String expectedError) {
        logPrefix("Then an error message should be displayed", true);
        String actualError = loginPage.getErrorMessage();
        assertTrue(actualError.contains(expectedError), "Unexpected error message");
        logPrefix("Error message received: " + actualError, false);
    }
}
