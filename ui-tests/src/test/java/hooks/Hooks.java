package hooks;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.Scenario;
import utils.ConfigReader;
import utils.DriverManager;
import utils.TestContext;

import static utils.LogFormatter.logScenarioStart;
import static utils.LogFormatter.logScenarioPass;
import static utils.LogFormatter.logScenarioFail;

public class Hooks {

    private final DriverManager driverManager;

    public Hooks(TestContext context) {
        this.driverManager = context.getDriverManager();
    }

    @Before("@UI")
    public void setUp(Scenario scenario) {
        logScenarioStart(scenario.getName());
        driverManager.getDriver().manage().window().maximize();
        driverManager.getDriver().get(ConfigReader.get("base.url"));
    }

    @After("@UI")
    public void tearDown(Scenario scenario) {
        if (scenario.isFailed())
            logScenarioFail(scenario.getName());
        else
            logScenarioPass(scenario.getName());
        driverManager.quitDriver();
    }
}
