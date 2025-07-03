package com.demo.core.config;

import com.codeborne.selenide.Configuration;
import org.openqa.selenium.chrome.ChromeDriverService;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.CapabilityType;
import org.openqa.selenium.remote.DesiredCapabilities;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;

import java.util.HashMap;
import java.util.Map;
import java.util.logging.Level;
import java.util.logging.Logger;

public class SelenideConfig {
    private static final String VIDEO_NAME_PATTERN = "HH:mm:ss:SSS";

    /*For Selenoid*/
    private static DesiredCapabilities getBrowserCapabilities() {
        DesiredCapabilities capabilities = new DesiredCapabilities();
        capabilities.setCapability(CapabilityType.ACCEPT_INSECURE_CERTS, true);
        return capabilities;
    }

    private static ChromeOptions getChromeOptions() {
        ChromeOptions options = new ChromeOptions();
        Map<String, Object> prefs = new HashMap<String, Object>();
        Map<String, Object> profile = new HashMap<String, Object>();
        Map<String, Object> profile2 = new HashMap<String, Object>();
        Map<String, Object> contentSettings = new HashMap<String, Object>();
        Map<String, Object> contentSettings2 = new HashMap<String, Object>();
        Map<String, Object> contentSettings3 = new HashMap<String, Object>();

//         Enable notifications
//         0 - Default, 1 - Allow, 2 - Block
        contentSettings.put("notifications", 1);
        profile.put("managed_default_content_settings", contentSettings);
        prefs.put("profile", profile);
        profile2.put("afp", true);
        profile2.put("data", true);
        profile2.put("disk", true);
        profile2.put("disks", true);
        profile2.put("file", true);
        profile2.put("hcp", true);
        profile2.put("itms-appss", true);
        profile2.put("itms", true);
        profile2.put("market", true);
        profile2.put("javascript", true);
        profile2.put("mailto", true);
        profile2.put("ms-help", true);
        profile2.put("news", true);
        profile2.put("nntp", true);
        profile2.put("shell", true);
        profile2.put("sip", true);
        profile2.put("snews", false);
        profile2.put("vbscript", true);
        profile2.put("view-source", true);
        contentSettings3.put("radio", true);
        contentSettings2.put("ms", contentSettings3);
        profile2.put("vnd", contentSettings2);

        prefs.put("protocol_handler.excluded_schemes", profile2);
        options.setExperimentalOption("prefs", prefs);

        return options;
    }

    private static FirefoxOptions getFirefoxOptions() {
        FirefoxOptions options = new FirefoxOptions();
        options.setAcceptInsecureCerts(true);
        options.addPreference("dom.webnotifications.enabled", true);
        options.addPreference("media.volume_scale", "0.0"); // отключение звука для медиа
        options.addPreference("browser.download.folderList", 2); // пользовательская папка для загрузок
        options.addPreference("browser.download.manager.showWhenStarting", false);
        options.addPreference("browser.helperApps.neverAsk.saveToDisk", "application/pdf");


        return options;
    }

    public static void createBrowserConfig(String browser) {
        Logger.getLogger("org.openqa.selenium").setLevel(Level.ALL);
        Configuration.browser = browser;
        DesiredCapabilities caps = getBrowserCapabilities();

        switch (browser.toLowerCase()) {
            case "chrome":
                System.setProperty(ChromeDriverService.CHROME_DRIVER_SILENT_OUTPUT_PROPERTY, "true");
                caps.setCapability(ChromeOptions.CAPABILITY, getChromeOptions());
                break;

            case "firefox":
                caps.merge(getFirefoxOptions());
                break;

            default:
                throw new IllegalArgumentException("Unsupported browser: " + browser);
        }

        Configuration.browserSize = "1440x900";
        Configuration.browserCapabilities = caps;
        Configuration.fastSetValue = false;
        Configuration.savePageSource = false;
        Configuration.screenshots = true;
        Configuration.headless = Boolean.parseBoolean(System.getProperty("headless", "true"));
        Configuration.pollingInterval = 5000;
        Configuration.pageLoadStrategy = "eager";
        Configuration.timeout = 18000;
        Configuration.reportsFolder = "screenshots/";

    }
}