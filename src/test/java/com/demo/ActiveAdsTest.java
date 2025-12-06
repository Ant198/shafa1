package com.demo;

import com.demo.actions.Actions;
import com.demo.core.base.BaseTest;
import com.demo.pages.Pages;
import com.demo.utils.Constants;
import com.demo.utils.FileUploader;
import com.demo.utils.SelenideTools;
import io.qameta.allure.Epic;
import io.qameta.allure.Owner;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.testng.AssertJUnit.fail;

@Epic("Shafa")
@Owner("QA Yaroslav Rymarchuk")
public class ActiveAdsTest extends BaseTest {

    @Test(description = "Active")
    public void shafaTest() {

        FileUploader.uploadFileToJenkins();

            Actions.loginActions().login();

            Actions.mainActions().goToAds();

            System.out.println("Обробка активних товарів ->");

            try{
                Constants.AMOUNTACTIVE = Pages.adsPage().getActiveProductsAmount();
                //обробка активних оголошень
                for(int i = 1; i <= Constants.AMOUNTACTIVE; i++) {

                    Pages.adsPage().scrollToProductByPixels(i);

                    System.out.println("Продукт " + i);

                    Actions.productActions().CheckActive(String.valueOf(i));
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
