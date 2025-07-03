package com.demo.pages;

import com.demo.core.base.PageTools;
import org.openqa.selenium.By;

public class LoginPage extends PageTools {
    private final By loginField = By.xpath("//input[@name='username']");
    private final By passwordField = By.xpath("//input[@name='password']");
    private final By signUpButton = By.xpath("//button[text()='Увійти']");


    public void typeLogin(String value){
        type(value, loginField);
    }

    public void typePassword(String value){
        type(value, passwordField);
    }

    public void clickSignUpButton(){
        click(signUpButton);
    }
}
