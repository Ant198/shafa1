package com.demo.pages;

import com.demo.core.base.PageTools;
import org.openqa.selenium.By;

public class HomePage extends PageTools {
    private final By avatar = By.xpath("//button[img[@alt='avatar']]");
    private final By myAdsLink = By.xpath("//li[a[text()='Мої речі']]");

    public void clickAvatar(){
        click(avatar);
    }

    public void clickMyAds(){
        click(myAdsLink);
    }

}
