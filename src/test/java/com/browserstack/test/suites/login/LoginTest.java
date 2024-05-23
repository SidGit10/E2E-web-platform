package com.browserstack.test.suites.login;

import com.browserstack.PercySDK;
import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Random;

public class LoginTest extends TestBase {

    @Test
    public void loginLockedUser() {

        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("locked_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();
        Assert.assertEquals(driver.findElement(By.className("api-error")).getText(), "Your account has been locked.");
        PercySDK.snapshot(driver, "Locked User");
    }

    @Test
    public void loginSuccess() {
        PercySDK.snapshot(driver, "Home Page");
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        HashMap<String, Object> options = new HashMap<String, Object>();
        String randomColorBtn = "#" + String.format("%06x", new Random().nextInt(0xffffff + 1));
        options.put("percyCSS", ".login_wrapper .Modal_modal__3I0HK .Button_root__24MxS{background-color: " + randomColorBtn + ";}");
        PercySDK.snapshot(driver, "Login Page");
        driver.findElement(By.id("login-btn")).click();
        Assert.assertEquals(driver.findElement(By.className("username")).getText(), "fav_user");
            HashMap<String, Object> options1 = new HashMap<String, Object>();
            String randomColor = "#" + String.format("%06x", new Random().nextInt(0xffffff + 1));
            options1.put("percyCSS", ".shelf-container .shelf-item__buy-btn{background-color: " + randomColor + ";}");
            PercySDK.snapshot(driver, "LoggedIn Page", options1);
    }

    @Test
    public void loginFail() {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("wrongpass" + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();
        Assert.assertEquals(driver.findElement(By.className("api-error")).getText(), "Invalid Password");
        PercySDK.snapshot(driver, "Invalid Login");
    }

}
