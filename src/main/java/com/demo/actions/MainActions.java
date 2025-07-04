package com.demo.actions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.demo.pages.Pages;
import com.demo.utils.SelenideTools;
import org.openqa.selenium.logging.LogEntry;
import org.openqa.selenium.logging.LogType;
import org.openqa.selenium.logging.Logs;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.awt.*;
import java.awt.datatransfer.DataFlavor;
import java.awt.datatransfer.UnsupportedFlavorException;
import java.io.File;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static java.lang.Integer.parseInt;

public class MainActions {

    public void switchToTab(int index) {
        Selenide.switchTo().window(index);
    }

    public void openNewTab() {
        Selenide.executeJavaScript("window.open()");
    }

    public void openLinkFromClipboard() throws IOException, UnsupportedFlavorException {
        String clipboardValue = Toolkit.getDefaultToolkit()
                .getSystemClipboard().getData(DataFlavor.stringFlavor).toString();

        Selenide.executeJavaScript("window.location.href = '" + clipboardValue + "'");
    }

    public String getCurrentUrl() {
        return WebDriverRunner.getWebDriver().getCurrentUrl();
    }

    public void goToAds(){
        Pages.homePage().clickAvatar();

        Pages.homePage().clickMyAds();

        Selenide.sleep(1000);

        Pages.adsPage().closeBanner();
    }
    public String getBrowserLog() {
        if(Objects.equals(System.getProperty("selenide.browser", "chrome"), "chrome")) {
            Logs logs = SelenideTools.getDriver().manage().logs();
            List<String> logsString = new ArrayList<>();
            for (LogEntry entry : logs.get(LogType.BROWSER)) {
                logsString.add(new Date(entry.getTimestamp()) + " " + entry.getLevel() + " " + entry.getMessage());
            }
            if(!logsString.isEmpty())
                return logsString.get(logsString.size() - 1);
            else return "Logs are empty";
        }
        else return "Console log does not allowed in this browser";
    }

}