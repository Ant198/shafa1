package com.demo.core.base;

import com.codeborne.selenide.Selenide;
import com.demo.core.allure.AllureLogger;
import com.demo.core.config.SelenideConfig;
import com.demo.utils.Constants;
import org.testng.ITestResult;
import org.testng.SkipException;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Listeners;

@Listeners({TestListener.class})
public class BaseTest extends AllureLogger {

    @BeforeMethod(alwaysRun = true, description = "Opening web browser...")
    public void setUp() throws Exception {
        try {
            Selenide.closeWebDriver();
            logInfo("Creating web driver configuration..."); //test
            SelenideConfig.createBrowserConfig(System.getProperty("selenide.browser", "chrome"));
            configLog(this.getClass().getSimpleName());
            logInfo("Open browser...");
            Selenide.open(Constants.URL);
        } catch (Exception e) {
            logError("Failed to open browser: " + e.getMessage());
            throw new SkipException("Skipping test due to browser init failure");
        }
    }

    @AfterMethod(alwaysRun = true, description = "Closing web browser...")
    public void tearDown(ITestResult result) {
        try{
            Selenide.closeWebDriver();
            logInfo("Web driver closed!");
        } catch (Exception e) {
            e.printStackTrace();
        }

    }
}
