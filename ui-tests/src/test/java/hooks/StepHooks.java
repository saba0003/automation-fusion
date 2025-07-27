package hooks;

import io.cucumber.java.AfterStep;
import io.cucumber.java.BeforeStep;
import io.cucumber.java.Scenario;

import static utils.LogFormatter.logStepStart;
import static utils.LogFormatter.logStepFail;
import static utils.LogFormatter.logStepPass;

@SuppressWarnings("unused")
public class StepHooks {

    @BeforeStep
    public void logBeforeStep() {
        logStepStart();
    }

    @AfterStep
    public void logAfterStep(Scenario scenario) {
        if (scenario.isFailed())
            logStepFail();
        else
            logStepPass();
    }
}
