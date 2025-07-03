package com.demo.utils;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.SelenideTargetLocator;
import com.codeborne.selenide.WebDriverRunner;
import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.Dimension;
import org.openqa.selenium.WebDriver;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;

import static com.codeborne.selenide.Selenide.$;

public class SelenideTools {
    protected static final Logger LOG = LoggerFactory.getLogger(SelenideTools.class);

    public static WebDriver getDriver() {
        return WebDriverRunner.getWebDriver();
    }

    public static SelenideTargetLocator switchTo() {
        return Selenide.switchTo();
    }
    public static void switchTo(int index) {
        Selenide.switchTo().window(index);
    }

    public static ArrayList<String> getTabsList() {
        return new ArrayList<>(getDriver().getWindowHandles());
    }

    public static int getTabsCount() {
        return getDriver().getWindowHandles().size();
    }

    public static void sleep(int sec) {
        Selenide.sleep(sec * 1000L);
    }

    public static void setTabSize(int width, int height) {
        getDriver().manage().window().setSize(new Dimension(width, height));
    }

    @Step("Closing current tab...")
    public static void closeCurrentTab() {
        LOG.info("Closing current tab...");
        Selenide.closeWindow();
    }

    @Step("Opening url - '{url}'")
    public static void openUrl(String url) {
        LOG.info("Opening url '{}'", url);
        Selenide.open(url);
    }

    @Step("Refreshing...")
    public static void refresh() {
        LOG.info("Refreshing...");
        Selenide.refresh();
    }

    @Step("Clearing cookies...")
    public static void clearCookies() {
        LOG.info("Clearing cookies...");
        Selenide.clearBrowserCookies();
    }

    /**
     * Custom methods
     */
    @Step("Switching to frame...")
    public static void switchToFrame(By by) {
        LOG.info("Switching to frame...");
        SelenideTools.switchTo().frame($(by));
    }

    @Step("Switching to default content...")
    public static void switchToDefaultContent() {
        LOG.info("Switching to default content...");
        SelenideTools.switchTo().defaultContent();
    }

    @Step("Switching to last tab...")
    public static void switchToLastTab() {
        LOG.info("Switching to last tab...");
        ArrayList<String> tabs = getTabsList();
        SelenideTools.switchTo().window(tabs.get(tabs.size() - 1));
    }

    @Step("Open url in new window")
    public static void openUrlInNewWindow(String url) {
        LOG.info("Open url in new window");
        Selenide.executeJavaScript("window.open('" + url + "')");
    }

    public static void closeAllTabsExceptCurrent() {
        String currentWindow = getDriver().getWindowHandle();
        ArrayList<String> tabs = getTabsList();
        for (String tab : tabs) {
            if (!tab.equals(currentWindow)) {
                Selenide.switchTo().window(tab);
                Selenide.closeWindow();
            }
        }
        Selenide.switchTo().window(currentWindow);
    }

    @Step("Get current url")
    public static String getCurrentUrl() {
        LOG.info("Get current url");
        return WebDriverRunner.url();
    }
}