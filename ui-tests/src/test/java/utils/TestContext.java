package utils;

/**
 * Shared context object used for dependency injection in Cucumber using PicoContainer.
 * This class gets automatically injected into step definitions and hooks.
 */
public class TestContext {

    private final DriverManager driverManager;
    private final PageManager pageManager;

    public TestContext() {
        driverManager = new DriverManager();
        pageManager = new PageManager(driverManager.getDriver());
    }

    public DriverManager getDriverManager() {
        return driverManager;
    }

    public PageManager getPageManager() {
        return pageManager;
    }
}
