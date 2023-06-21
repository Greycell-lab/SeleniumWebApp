package org.example;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.io.*;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class StartDelete {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-hh:mm:ss");
    private final WebDriver driver;
    private final String expectedURL = PropertyReader.getWebsite() + "TenderServlet?function=Show&menu=tender";
    private String tempFileNumber;
    private final HashMap<String, String> itemsToDeleteMap = CSVFileReader.getToDeleteMap();
    public StartDelete(){
        driver = AdminLogin.getDriver();
        startDelete();
    }
    public void startDelete() {
        StringBuilder log = new StringBuilder();
            for (String tenderOID : itemsToDeleteMap.keySet()) {
                String deleteString = PropertyReader.getWebsite() + "TenderServlet?function=Delete&item=" + tenderOID;
                log.append(formatter.format(LocalDateTime.now())).append(": ");
                try {
                    driver.get(deleteString);
                    AdminLogin.waitSeconds(AdminLogin.reader.getWaitTime());
                    WebElement textElement = driver.findElement(By.cssSelector(".entryLine"));
                    String text = textElement.getText();
                    if (checkFileNumber(text, itemsToDeleteMap.get(tenderOID))) {
                        AdminLogin.waitSeconds(AdminLogin.reader.getWaitTime());
                        driver.findElement(By.name("yes")).submit();
                        Wait<WebDriver> wait = new FluentWait<>(driver)
                                .withTimeout(Duration.ofSeconds(10))
                                        .pollingEvery(Duration.ofSeconds(5))
                                                .ignoring(NoSuchElementException.class);
                        wait.until(ExpectedConditions.urlContains("NetServer/admin/TenderServlet"));
                        log.append(tenderOID);
                        log.append(" deleted.\n");
                    } else {
                        log.append(tenderOID)
                                .append(" not deleted! Cause: Falsches Aktenzeichen. (")
                                .append("Von CSV Datei: ")
                                .append(itemsToDeleteMap.get(tenderOID))
                                .append(" -> Von Vergabeplattform: ")
                                .append(tempFileNumber)
                                .append(").\n");
                    }
                } catch (NoSuchElementException e) {
                    log.append(tenderOID).append(" not deleted! Cause: Vergabe nicht gefunden.\n");
                }catch (NoSuchWindowException e){
                    ConfirmQuestion.errorPane();
                    log.append("Error: Browser closed");
                    writeLog(log.toString());
                    driver.quit();
                    System.exit(0);
                }
            }
            writeLog(log.toString());
    }
    private boolean checkFileNumber(String text, String fileNumber){
        tempFileNumber = text.substring(24, 36);
        return text.contains(fileNumber);
    }
    public void writeLog(String log){
        try (BufferedWriter writer = new BufferedWriter(new FileWriter("activity.log"))) {
            writer.write(log);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
    public static boolean isExpectedURL(WebDriver driver, String expectedURL){
        return driver.getCurrentUrl().equals(expectedURL);
    }
}
