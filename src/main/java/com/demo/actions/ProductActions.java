package com.demo.actions;

import com.codeborne.selenide.Selenide;
import com.demo.core.base.PageTools;
import com.demo.pages.Pages;
import com.demo.utils.Constants;
import com.demo.utils.SelenideTools;
import com.demo.utils.DateTime;
import org.w3c.dom.Document;
import org.w3c.dom.Node;

import javax.xml.parsers.DocumentBuilder;
import javax.xml.parsers.DocumentBuilderFactory;
import javax.xml.parsers.ParserConfigurationException;
import javax.xml.xpath.XPath;
import javax.xml.xpath.XPathConstants;
import javax.xml.xpath.XPathFactory;
import java.io.*;
import java.util.Objects;


public class ProductActions extends PageTools {

    public void CheckActive(String index) throws Exception {

        String vendorCodeToFind = getCode(index);  // можно заменить на null, если ищем по имени
        String nameToFind = getTitle(index);       // если нет vendorCode

        // Получаем путь к загруженному файлу из переменной окружения Jenkins
        String filePathStr = System.getenv("UPLOAD_FILE");
        if (filePathStr == null) {
            throw new IllegalStateException("Змінна середи UPLOAD_FILE не встановлена!");
        }

        File xmlFile = new File(filePathStr);
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("Файл не знайдено: " + xmlFile.getAbsolutePath());
        }

        // Настраиваем парсер XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc;
        try (InputStream xmlInput = new FileInputStream(xmlFile)) {
            doc = builder.parse(xmlInput);
        }

        // Создаём инструмент для поиска в XML
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        // 1. Поиск по vendorCode
        Node offer = null;
        if (vendorCodeToFind != null) {
            offer = (Node) xpath.evaluate("//offer[vendorCode='" + vendorCodeToFind + "']", doc, XPathConstants.NODE);
        }

        // 2. Если не найдено — искать по name
        if (offer == null && nameToFind != null) {
            offer = (Node) xpath.evaluate("//offer[name=" + escapeXPathLiteral(nameToFind) + "]", doc, XPathConstants.NODE);
        }

        // 3. Обработка найденного предложения
        if (offer != null) {
            String id = offer.getAttributes().getNamedItem("id").getNodeValue();
            String name = xpath.evaluate("name", offer);
            String price = xpath.evaluate("price", offer);
            String quantityStr = xpath.evaluate("quantity_in_stock", offer);

            Pages.adsPage().openProductInNewTab(Integer.parseInt(index));
            Selenide.sleep(2000);
            Pages.adPage().priceToUpdate = price;
            if(Integer.parseInt(quantityStr) > 100){
                quantityStr = "100";
            }
            Pages.adPage().quantityToUpdate = quantityStr;

            int realQty = Integer.parseInt(quantityStr.trim());
            int realPrice = Integer.parseInt(price.substring(0,price.indexOf('.')-1));

            System.out.println("Кількість в файлі: " + realQty + ", Кількість на сайті: " + Pages.adPage().getPrimaryQty());
            System.out.println("Ціна в файлі: " + realPrice + ", Ціна на сайті: " + Pages.adPage().getPrimaryPrice());

            if (realQty != Pages.adPage().getPrimaryQty() || realPrice != Pages.adPage().getPrimaryPrice()) {
                if (realQty == 0 ) {
                    System.out.println("Товару нема в наявності... Деактивуємо");
                    Pages.adPage().clickDeActivateButton();
                    Constants.AMOUNTACTIVE =- 1;
                    writeError(id + " | " + name + " | оголошення було деактивоване(немає в наявності) ", "success.txt");
                    goBack();
                }
                else {
                    System.out.println("Оновлюємо дані");
                    editActions(name, id);
                }
            }
            else {
                System.out.println("Товар є в списку, але дані залишаються актуальними");
                writeError(id + " | " + name + " | Товар є в наявності, але дані все ще актуальні", "skipped.txt");
                goBack();
            }
        }
        else {
            System.out.println("Пропозиція не знайдена.");
            writeError("Пропозиція " + nameToFind + " | " + vendorCodeToFind + " | не найдена | Активне", "errors.txt");
        }
    }

    public void CheckDeActive(String index) throws Exception {

        String vendorCodeToFind = getCode(index);  // можно заменить на null, если ищем по имени
        String nameToFind = getTitle(index);       // если нет vendorCode

        // Получаем путь к загруженному файлу из переменной окружения Jenkins
        String filePathStr = System.getenv("UPLOAD_FILE");
        if (filePathStr == null) {
            throw new IllegalStateException("Змінна середи UPLOAD_FILE не встановлена!");
        }

        File xmlFile = new File(filePathStr);
        if (!xmlFile.exists()) {
            throw new FileNotFoundException("Файл не знайдено: " + xmlFile.getAbsolutePath());
        }

        // Настраиваем парсер XML
        DocumentBuilderFactory factory = DocumentBuilderFactory.newInstance();
        factory.setValidating(false);
        factory.setNamespaceAware(false);
        factory.setFeature("http://apache.org/xml/features/nonvalidating/load-external-dtd", false);

        DocumentBuilder builder = factory.newDocumentBuilder();
        Document doc;
        try (InputStream xmlInput = new FileInputStream(xmlFile)) {
            doc = builder.parse(xmlInput);
        }

        // Создаём инструмент для поиска в XML
        XPathFactory xpathFactory = XPathFactory.newInstance();
        XPath xpath = xpathFactory.newXPath();

        // 1. Поиск по vendorCode
        Node offer = null;
        if (vendorCodeToFind != null) {
            offer = (Node) xpath.evaluate("//offer[vendorCode='" + vendorCodeToFind + "']", doc, XPathConstants.NODE);
        }

        // 2. Если не найдено — искать по name
        if (offer == null && nameToFind != null) {
            offer = (Node) xpath.evaluate("//offer[name=" + escapeXPathLiteral(nameToFind) + "]", doc, XPathConstants.NODE);
        }

        // 3. Обработка найденного предложения
        if (offer != null) {
            String id = offer.getAttributes().getNamedItem("id").getNodeValue();
            String name = xpath.evaluate("name", offer);
            String priceStr = xpath.evaluate("price", offer);
            String quantityStr = xpath.evaluate("quantity_in_stock", offer);

            int realQty = Integer.parseInt(quantityStr.trim());
            int realPrice = Integer.parseInt(priceStr.substring(0,priceStr.indexOf('.')-1));

            if (realQty != 0) {
                Pages.adsPage().clickActivateButton(index);
                Constants.AMOUNTDEACTIVE =- 1;
                Pages.adsPage().openProductInNewTab(Integer.parseInt(index));
                Selenide.sleep(2000);

                Pages.adPage().priceToUpdate = priceStr;
                if (Integer.parseInt(quantityStr) > 100) {
                    quantityStr = "100";
                }
                Pages.adPage().quantityToUpdate = quantityStr;

                System.out.println("Кількість в файлі: " + realQty + ", Кількість на сайті: " + Pages.adPage().getPrimaryQty());
                System.out.println("Ціна в файлі: " + realPrice + ", Ціна на сайті: " + Pages.adPage().getPrimaryPrice());
                System.out.println("Пропозиція активована");

                if (realQty != Pages.adPage().getPrimaryQty() || realPrice != Pages.adPage().getPrimaryPrice()) {
                    System.out.println("Оновлюємо дані");
                    editActions(name, id);
                }
                else {
                    System.out.println("Товар Активовано");
                    writeLine(id, name, Pages.adPage().priceToUpdate, Pages.adPage().quantityToUpdate, "success.txt");
                    goBack();
                }
            }
            else {
                System.out.println("Товар є у списку, але дані залишаються актуальними");
                writeError(id + " | (Деактивований) " + name + " | Товар є в списку, але дані все ще актуальні", "skipped.txt");
            }


        }
        else {
            System.out.println("Пропозиція не знайдена");
            writeError("Пропозиція " + nameToFind + " | " + vendorCodeToFind + " | не найдена | Деактивоване", "errors.txt");
        }
    }


    public void writeLine(String id, String name, String price, String quantity, String csvFile){
        try (FileWriter writer = new FileWriter(csvFile,true)) {
            writer.append("Оновлено | " + id + " | " + name + "| кількість" + quantity + " | ціна: " + price  + " | " + DateTime.getLocalDateTimeByPattern("yyyy MM dd'|'HH:mm:ss") + "\n");
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    public void writeError(String message, String csvFile){
        if(csvFile.equals("skipped.txt") || csvFile.equals("errors.txt")){
            try(FileWriter writer = new FileWriter(csvFile, true)){
                writer.append("Товар пропущено: " + message + " | " + DateTime.getLocalDateTimeByPattern("yyyy MM dd'|'HH:mm:ss") + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
        else{
            try(FileWriter writer = new FileWriter(csvFile, true)){
                writer.append("Error: " + message + " | " + SelenideTools.getCurrentUrl() + " | " + DateTime.getLocalDateTimeByPattern("yyyy MM dd'|'HH:mm:ss") + "\n");
            } catch (IOException e) {
                e.printStackTrace();
            }
        }
    }

    public String getCode(String index){
        String title = getTrimmedText(Pages.adsPage().getTitleLocator(index));

        String[] parts = title.split("[|│]");
        String lastPart = parts[parts.length-1].trim();

        if (lastPart.matches("\\d+")){
            System.out.println("Код продукту: " + lastPart);
            return lastPart;
        } else {
            System.out.println("Код не знайден");
            return null;
        }
    }

    public String getTitle(String index){
        String title = getTrimmedText(Pages.adsPage().getTitleLocator(index));
        String[] parts = title.split("[|│]");
        String lastPart = parts[0].trim();
        System.out.println("Назва: " + lastPart);

        return lastPart;
    }

    public static String escapeXPathLiteral(String value) {
        if (!value.contains("'")) {
            return "'" + value + "'";
        }

        // разбиваем по одинарной кавычке
        String[] parts = value.split("'");
        StringBuilder result = new StringBuilder("concat(");
        for (int i = 0; i < parts.length; i++) {
            result.append("'").append(parts[i]).append("'");
            if (i != parts.length - 1) {
                result.append(", \"'\", ");
            }
        }
        result.append(")");
        return result.toString();
    }

    /*public void goBackToAds(){
        Selenide.sleep(2000);

        Pages.homePage().clickAvatar();

        Pages.homePage().clickMyAds();
    }*/

    public static void goBack(){
        SelenideTools.closeCurrentTab();

        SelenideTools.switchTo(0);
    }

    /*public static void goBackToDeActiveAds(){
        Actions.productActions().goBackToAds();

        Pages.adsPage().clickDeActiveAdsButton();
    }*/



    public void editActions(String name, String id){
        Pages.adPage().clickEditButton();

        if(!Objects.equals(Pages.adPage().priceToUpdate, "")){
            Pages.editPage().updatePrice(Pages.adPage().priceToUpdate);
            System.out.println("Оновили ціну");
        }

        if(!Objects.equals(Pages.adPage().quantityToUpdate, "")){
            Pages.editPage().updateQuantity(Pages.adPage().quantityToUpdate);
            System.out.println("Оновили кількість");

        }

        Pages.editPage().clickSaveButton();
        System.out.println("Намагаємось зберегти");
        Selenide.sleep(3000);

        if(Pages.editPage().isError()){
            System.out.println("Помилка на сторінці редагування");
            writeError(id + " | " + name + " | Не оновилось через помилку на етапі редагування | " + Actions.mainActions().getBrowserLog(), "blocked.txt");

        }
        else if(Pages.adPage().isPageLoaded()){
            System.out.println("Успішно оновлено");
                writeLine(id, name, Pages.adPage().priceToUpdate, Pages.adPage().quantityToUpdate, "success.txt");
        }
        else{
            System.out.println("Помилки нема але й оновлення також");
            writeError(id + " | " + name + " | Не оновилось через відсутність подій після натискання на кнопку \"Зберегти зміни\" | ", "blocked.txt");
        }
        goBack();

    }


}

