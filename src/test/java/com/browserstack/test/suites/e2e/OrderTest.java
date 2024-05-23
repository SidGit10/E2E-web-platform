package com.browserstack.test.suites.e2e;

import com.browserstack.PercySDK;
import com.browserstack.app.pages.ConfirmationPage;
import com.browserstack.app.pages.HomePage;
import com.browserstack.app.pages.OrdersPage;
import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.JavascriptExecutor;
import org.testng.Assert;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import java.util.HashMap;
import java.util.Random;

public class OrderTest extends TestBase {

    @Test
    public void placeOrder() {
        SoftAssert softly = new SoftAssert();
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        jse.executeScript("browserstack_executor: {\"action\": \"lighthouseAudit\"}");
        ConfirmationPage page = new HomePage(driver)
                .navigateToSignIn()
                .loginWith("fav_user", "testingisfun99")
                .addProductToCart("iPhone 11")
                .addProductToCart("iPhone XS Max")
                .addProductToCart("Galaxy S20")
                .getBag().waitForItemsInBag(3)
                .getBag().proceedToCheckout()
                .enterShippingDetails("firstname", "lastname", "address", "state", "12345");

        Assert.assertTrue(page.isConfirmationDisplayed());
        HashMap<String, Object> options1 = new HashMap<String, Object>();
        String randomColor = "#" + String.format("%06x", new Random().nextInt(0xffffff + 1));
        options1.put("percyCSS", ".cart-priceItem-value{background-color: " + randomColor + ";}");
        PercySDK.snapshot(driver,"Shipping details",options1);

        if (!isOnPremExecution()) {
            page.downloadPDF();
            softly.assertTrue(downloadedFileExists("confirmation.pdf"));
        }

        OrdersPage ordersPage = page.continueShopping().navigateToOrders();

        softly.assertEquals(ordersPage.getItemsFromOrder(), 3);
        PercySDK.snapshot(driver,"Orders Page");
        softly.assertAll();


    }

    private boolean downloadedFileExists(String fileName) {
        JavascriptExecutor jse = (JavascriptExecutor) driver;
        return Boolean.parseBoolean(jse.executeScript("browserstack_executor: {\"action\": \"fileExists\", \"arguments\": {\"fileName\": \"" + fileName + "\"}}").toString());
    }
}