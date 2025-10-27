package com.demo;

import com.demo.actions.Actions;
import com.demo.core.base.BaseTest;
import com.demo.pages.Pages;
import com.demo.utils.Constants;
import com.demo.utils.FileUploader;
import com.demo.utils.SelenideTools;
import io.qameta.allure.Epic;
import io.qameta.allure.Feature;
import io.qameta.allure.Owner;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.fail;

@Epic("Test Epic")
@Feature("Test feature")
@Owner("QA Yaroslav Rymarchuk")
public class ActiveAdsTest extends BaseTest {

    @Test(description = "shafaAutomatization")
    public void shafaTest() {

        FileUploader.uploadFileToJenkins();

            Actions.loginActions().login();

            Actions.mainActions().goToAds();

            System.out.println("Обработка активный товаров ->");

            try{
                Constants.AMOUNTACTIVE = Pages.adsPage().getActiveProductsAmount();
                //обробка активних оголошень
                for(int i = 1500; i <= Constants.AMOUNTACTIVE; i++) {

                    Pages.adsPage().scrollToProduct(i);

                    System.out.println("Продукт " + i);

                    Actions.productActions().CheckActive(String.valueOf(i));
                    /*if (i%500 == 0) {
                        SelenideTools.refresh();
                        Pages.adsPage().closeBanner();
                    }*/
                }


            } catch (Exception e){
                e.printStackTrace();
                fail("Тест упал из-за исключения: " + e.getMessage());
            }


    }
}
