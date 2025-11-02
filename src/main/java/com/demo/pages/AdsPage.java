package com.demo.pages;

import com.codeborne.selenide.Selenide;
import com.demo.core.base.PageTools;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Selenide.$;
import static com.demo.utils.SelenideTools.*;


public class AdsPage extends PageTools {
    private final By activeAdsButton = By.xpath("//li[a[text()='Активні']]");
    private final By deActiveAdsButton = By.xpath("//li[a[text()='Деактивовані']]");
    private final By activateAdButton = By.xpath("(//button[text()='Активувати' and not(@disabled)])%s");
    private final String title = "(//a[@class='CnMTkD'])";
    private final String productPath = "//div[@class='qJICBqxWKFOZHxFSDLQr']/div";
    private final By bannerCross = By.xpath("//div[@class='xIxPTw']");
    private final int activeProductsAmount = Integer.parseInt($(By.xpath("//a[text()='Активні']/span")).getText());
    private final int deActiveProductsAmount = Integer.parseInt($(By.xpath("//li[a[text()='Деактивовані']]//span")).getText());

    public void clickActivateButton(String index) {
        waitForElementClickable(activateAdButton, String.format("[%s]", index));
        click(activateAdButton, String.format("[%s]", index));
        Selenide.sleep(500);
    }

    public void clickActiveAdsButton(){
        click(activeAdsButton);
    }

    public void clickDeActiveAdsButton(){
        click(deActiveAdsButton);
    }


    public void closeBanner(){
        if(isElementVisible(bannerCross)){
            click(bannerCross);
        };
    }

    public void openProductInNewTab(int index){
        By product = By.xpath(title + "[" + index + "]");
        if(!isElementVisible(product)) {
            for (int i = 1; i < (index / 16) + 1; i++) {
                int scrollIndex = 16 * i;
                scrollToPlaceElementInCenter(By.xpath(title + "[" + scrollIndex + "]"));
            }
        }
         else {
            scrollToPlaceElementInCenter(product);
        }
        waitForElementClickable(product);
        openUrlInNewWindow(getElementAttributeValue("href", product));
        switchToLastTab();
        //click(product);
    }

    public void scrollToProductByPixels(int index){
        By product = By.xpath(title + "[" + index + "]");
        while(!isElementVisible(product)){
            scrollByPixels("2000");
            System.out.println("Скролл...");
        }
        scrollToElement(product);
    }

    public int getActiveProductsAmount(){
        return  activeProductsAmount;
    }

    public int getDeActiveProductsAmount(){
        return deActiveProductsAmount;
    }

    public By getTitleLocator(String index){
        return By.xpath(title + "[" + index + "]") ;
    }


}
