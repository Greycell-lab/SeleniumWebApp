package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class AdminLogin {
    private static WebDriver driver;
    private String username;
    private String password;
    private static String website;
    private static String path;
    public AdminLogin(){
            try(InputStream input = getClass().getClassLoader().getResourceAsStream("websites.properties")){
                Properties properties = new Properties();
                properties.load(input);
                website = properties.getProperty("website");
                username = properties.getProperty("username");
                password = properties.getProperty("password");
                path = properties.getProperty("path");
            } catch(IOException e){
                e.printStackTrace();
            }
            getAdminLogin();
    }
    private void getAdminLogin(){
        System.setProperty("webdriver.chrome.driver", ".\\chromedriver.exe");
        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get(website);
        WebElement usernameBox = driver.findElement(By.name("j_username"));
        WebElement passwordBox = driver.findElement(By.name("j_password"));
        usernameBox.sendKeys(this.username);
        passwordBox.sendKeys(this.password);
        passwordBox.submit();
        this.username = null;
        this.password = null;
    }

    public static String getWebsite() {
        return website;
    }

    public static String getPath() {
        return path;
    }

    public static WebDriver getDriver() {
        return driver;
    }
}
