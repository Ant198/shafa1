package com.demo.pages;

import com.demo.core.base.PageTools;
import org.openqa.selenium.By;
import org.testng.Assert;


import static com.codeborne.selenide.Selenide.$;
import static java.lang.Integer.parseInt;

public class AdPage extends PageTools {
    private final By titleLocator = By.xpath("//h1[@class='b-product__title']");
    private final By price = By.xpath("//span[@itemprop='price']");
    private final By editButton = By.xpath("//a[contains(text(), 'Редагувати')]");
    private final By deActivateButton = By.xpath("//button[text()='Деактивувати']");
    private final By activateButton = By.xpath("//button[text()='Активувати']");
    private final By size = By.xpath("//div[@class='b-properties__label' and text()='Розмір:']");
    private final By primaryPrice = By.xpath("//div[contains(@class,'b-product__price')]//p");
    private final By primaryQty = By.xpath("//li[span[text()='Наявність:']]//span[@class='b-product-stats__value']");
    public String priceToUpdate = "";
    public String quantityToUpdate = "";
    private By deActivated = By.xpath("//div[p[text()='Деактивований']]");
    private final By qty = By.xpath("//li[span[text()='Наявність:']]//span[contains(@class, 'value')]");


    public void clickEditButton(){
        waitForElementVisibility(editButton);
        click(editButton);
    }

    private String getPriceStr() {
        return getElementText(primaryPrice);
    }

    private String getQtyStr() {
        return getElementText(primaryQty);
    }

    public void clickActivateButton(){
        click(activateButton);
    }

    public void clickDeActivateButton(){
        click(deActivateButton);
    }

    public By getSizeLocator(){
        return size;
    }

    public By getTitleLocator(){
        return titleLocator;
    }

    public By getPrice(){
        return price;
    }

    public By getActivateButton(){
        return activateButton;
    }

    public By getDeActivateButton(){
        return deActivateButton;
    }

    public boolean isPageLoaded(){
        if(isElementVisible(deActivateButton) || isElementVisible(activateButton)){
            return true;
        }
        return false;
    }

    public boolean isAdActivated(){
        return !isElementVisible(deActivated);
    }

    public int getPrimaryQty(){
        return Integer.parseInt(getQtyStr());
    }

    public float getPrimaryPrice(){
        String price = getPriceStr().substring(0, getPriceStr().indexOf(' '));
        return Float.parseFloat(price);
    }


}
    