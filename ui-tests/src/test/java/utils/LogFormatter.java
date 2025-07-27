package utils;

import static org.testng.Reporter.log;

/**
 * Note: Emojis may not render correctly in all terminal environments.
 * They are primarily intended for clarity in IntelliJ's console output.
 * If running tests in CI pipelines or basic terminals, using plain text alternatives should be considered.
 */
public class LogFormatter {

    public static void logScenarioStart(String name) {
        log("==== Starting Scenario: '" + name + "' ====\n", true);
    }

    public static void logScenarioPass(String name) {
        log("==== ✔ Scenario PASSED: '" + name + "' ====", true);
    }

    public static void logScenarioFail(String name) {
        log("==== ❌ Scenario FAILED: '" + name + "' ====\n", true);
    }

    public static void logStepStart() {
        log("\t--> Executing Step:", true);
    }

    public static void logStepPass() {
        log("\t\t\t✔ Step passed\n", true);
    }

    public static void logStepFail() {
        log("\t\t\t❌ Step failed", true);
    }

    public static void logPrefix(String message, boolean isStep) {
        log((isStep ? "\t\t* " : "# ") + message, true);
    }
}
