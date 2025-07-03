package com.demo.actions;

import com.demo.pages.Pages;
import com.demo.utils.Constants;

public class LoginActions {

    public void login(){
        Pages.loginPage().typeLogin(Constants.LOGIN);

        Pages.loginPage().typePassword(Constants.PASSWORD);

        Pages.loginPage().clickSignUpButton();
    }

}
