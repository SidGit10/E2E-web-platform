package com.browserstack.test.suites.login;

import com.browserstack.test.suites.TestBase;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.testng.Assert;
import org.testng.annotations.Test;


public class LoginFailTest extends TestBase {

    @Test
    public void loginFail() {
        driver.findElement(By.id("signin")).click();
        driver.findElement(By.cssSelector("#username input")).sendKeys("fav_user" + Keys.ENTER);
        driver.findElement(By.cssSelector("#password input")).sendKeys("testingisfun99" + Keys.ENTER);
        driver.findElement(By.id("login-btn")).click();

        if (Math.random() < 0.5) {
            Assert.assertEquals(driver.findElement(By.className("username")).getText(), "fav");
        } else {
            Assert.assertEquals(driver.findElement(By.className("username")).getText(), "fav_user");
        }
    }

}
