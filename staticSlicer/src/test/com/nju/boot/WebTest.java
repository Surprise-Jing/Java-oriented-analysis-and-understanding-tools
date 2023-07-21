package com.nju.boot;

import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

public class WebTest {
    long interval = 10000;
    public void register(){

    }
    public void login(){
        WebElement usernameInput = driver.findElement(By.name("username"));
        usernameInput.sendKeys("usr01");
        WebElement passwordInput = driver.findElement(By.name("password"));
        passwordInput.sendKeys("a0000000");
        WebElement button = driver.findElement(By.className("el-button"));
        button.click();

    }
    WebDriver driver = null;
    @Test
    public void f() throws InterruptedException {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--remote-allow-origins=*");//
        System.setProperty("webdriver.chrome.driver","/usr/local/bin/chromedriver");


        driver = new ChromeDriver(options);
        driver.get("http://localhost:8080/");
        login();
        driver.quit();

    }
}
