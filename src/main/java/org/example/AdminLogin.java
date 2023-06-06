package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

public class AdminLogin {
    PropertyReader reader = new PropertyReader();
    private static WebDriver driver;
    public AdminLogin(){

            getAdminLogin();
    }
    private void getAdminLogin(){
        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(PropertyReader.getWebsite());
        WebElement usernameBox = driver.findElement(By.name("j_username"));
        WebElement passwordBox = driver.findElement(By.name("j_password"));
        usernameBox.sendKeys(reader.getUsername());
        passwordBox.sendKeys(reader.getPassword());
        passwordBox.submit();
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
