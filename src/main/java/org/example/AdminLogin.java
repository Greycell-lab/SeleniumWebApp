package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.IOException;
import java.net.HttpURLConnection;
import java.net.MalformedURLException;
import java.net.URL;
import java.time.Duration;

public class AdminLogin {
    public static final PropertyReader reader = new PropertyReader();
    public static HttpURLConnection con = null;
    public static WebDriver driver;
    public AdminLogin() {
            getAdminLogin();
    }
    private void getAdminLogin() {
        System.setProperty("webdriver.chrome.driver", "drivers/chromedriver.exe");
        driver = new ChromeDriver();
        new WebDriverWait(driver, Duration.ofMillis(reader.getWaitTime()));
        driver.manage().window().maximize();
        driver.get(PropertyReader.getWebsite());
        waitSeconds(1000);
        WebElement usernameBox = driver.findElement(By.name("j_username"));
        WebElement passwordBox = driver.findElement(By.name("j_password"));
        usernameBox.sendKeys(reader.getUsername());
        passwordBox.sendKeys(reader.getPassword());
        passwordBox.submit();
    }
    public static WebDriver getDriver() {
        return driver;
    }
    public static void waitSeconds(int millis){
        try{
            Thread.sleep(millis);
        }catch(InterruptedException e){
            e.printStackTrace();
        }
    }
    public static int getResponseCode(){
        try {
            con = (HttpURLConnection) new URL(PropertyReader.getWebsite()).openConnection();
            con.setRequestMethod("HEAD");
            con.connect();
            int rc = con.getResponseCode();
            con.disconnect();
            con = null;
            return rc;
        }catch(IOException e){
            e.printStackTrace();
        }
        return 0;
    }
}
