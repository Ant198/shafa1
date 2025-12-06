package com.demo;

import com.demo.actions.Actions;
import com.demo.core.base.BaseTest;
import com.demo.pages.Pages;
import com.demo.utils.Constants;
import com.demo.utils.SelenideTools;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;


@Epic("Shafa")
@Owner("QA Yaroslav Rymarchuk")
public class DeActiveAdsTest extends BaseTest {

    @Test(description = "DeActive")
    public void shafaTest() {

        Actions.loginActions().login();

        Actions.mainActions().goToAds();

        Pages.adsPage().clickDeActiveAdsButton();

        System.out.println("Обробка неактивних товарів ->");


        try{
            Constants.AMOUNTDEACTIVE = Pages.adsPage().getDeActiveProductsAmount();
            //обробка неактивних оголошень
            for(int i = 1; i <= Constants.AMOUNTDEACTIVE; i++){

                Pages.adsPage().scrollToProductByPixels(i);

                System.out.println("Продукт " + i);

                Actions.productActions().CheckDeActive(String.valueOf(i));
                if (i%500 == 0) {
                    SelenideTools.refresh();
                    Pages.adsPage().closeBanner();
                }
            }
        } catch (Exception e){
            e.printStackTrace();
            Assert.fail("Тест впав через помилку: " + e.getMessage());
        }


    }
}
