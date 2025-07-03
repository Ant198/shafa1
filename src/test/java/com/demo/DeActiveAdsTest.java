package com.demo;

import com.demo.actions.Actions;
import com.demo.actions.ProductActions;
import com.demo.core.base.BaseTest;
import com.demo.pages.Pages;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.fail;

@Epic("Test Epic")
@Feature("Test feature")
@Owner("QA Yaroslav Rymarchuk")
public class DeActiveAdsTest extends BaseTest {

    @Test(description = "shafaAutomatization")
    public void shafaTest() {

        Actions.loginActions().login();

        Actions.mainActions().goToAds();

        Pages.adsPage().clickDeActiveAdsButton();

        System.out.println("Обработка неактивных товаров ->");


        try{
            //обробка неактивних оголошень
            for(int i = 1; i < Pages.adsPage().getDeActiveProductsAmount(); i++){

                Pages.adsPage().scrollToProduct(i);

                System.out.println("Продукт " + i);

                Actions.productActions().CheckDeActive(String.valueOf(i));
                if (i ==20 ) break;
            }
        } catch (Exception e){
            e.printStackTrace();
            fail("Тест упал из-за исключения: " + e.getMessage());
        }


    }
}
