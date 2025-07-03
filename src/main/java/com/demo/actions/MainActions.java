package com.demo.actions;

import com.codeborne.selenide.Selenide;
import com.codeborne.selenide.WebDriverRunner;
import com.demo.pages.Pages;
import org.w3c.dom.Document;
import org.w3c.dom.Node;
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

}