package com.demo.pages;

import com.codeborne.selenide.Selenide;
import com.demo.core.base.PageTools;
import org.openqa.selenium.By;

public class EditPage extends PageTools {
    private final By priceField = By.xpath("//input[@name='price']");
    private final By amountField = By.xpath("//input[@name='count']");
    private final By saveButton = By.xpath("//button[text()='Зберегти зміни']");
    private final By error = By.xpath("//div[text()='Упс щось пішло не так, спробуйте пізніше Response not successful: Received status code 403']");


    public void updatePrice(String value){
        scrollToPlaceElementInCenter(priceField);
        waitForElementPresent(priceField);
        type(value, priceField);
    }

    public void updateQuantity(String value){
        scrollToPlaceElementInCenter(amountField);
        waitForElementPresent(amountField);
        type(value, amountField);
    }

    public void clickSaveButton(){
        click(saveButton);
    }

    public boolean isSaveButtonVisible(){
        return isElementVisible(saveButton);
    }

    public boolean isError(){
        return isElementVisible(error);
    }
}
