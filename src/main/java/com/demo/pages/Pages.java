package com.demo.pages;

import com.demo.core.allure.AllureLogger;

public class Pages extends AllureLogger {
    /**
     * Pages
     */
    private static HomePage homePage;
    private static AdPage adPage;
    private static AdsPage adsPage;
    private static LoginPage loginPage;
    private static EditPage editPage;

    /**
     * This function return an instance of `NavigationPage`
     */
    public static HomePage homePage(){
        if(homePage == null) {
            homePage = new HomePage();
        }
        return homePage;
    }

    public static AdPage adPage(){
        if(adPage == null) {
            adPage = new AdPage();
        }
        return adPage;
    }

    public static AdsPage adsPage(){
        if(adsPage == null) {
            adsPage = new AdsPage();
        }
        return adsPage;
    }

    public static LoginPage loginPage(){
        if(loginPage == null) {
            loginPage = new LoginPage();
        }
        return loginPage;
    }

    public static EditPage editPage(){
        if(editPage == null) {
            editPage = new EditPage();
        }
        return editPage;
    }
}