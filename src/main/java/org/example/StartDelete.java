package org.example;

import org.openqa.selenium.*;

import java.io.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.HashMap;

public class StartDelete {
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy-hh:mm:ss");
    private final WebDriver driver;
    private String tempFileNumber;
    private final HashMap<String, String> itemsToDeleteMap = CSVFileReader.getToDeleteMap();
    public StartDelete(){
        driver = AdminLogin.getDriver();
        startDelete();
    }
    public void startDelete(){
        StringBuilder notDeleted = new StringBuilder();
            for (String tenderOID : itemsToDeleteMap.keySet()) {
                String deleteString = PropertyReader.getWebsite() + "TenderServlet?function=Delete&item=" + tenderOID;
                notDeleted.append(formatter.format(LocalDateTime.now())).append(": ");
                driver.get(deleteString);
                try {
                    AdminLogin.waitSeconds();
                    WebElement textElement = driver.findElement(By.cssSelector(".entryLine"));
                    String text = textElement.getText();
                    if (checkFileNumber(text, itemsToDeleteMap.get(tenderOID))) {
                        AdminLogin.waitSeconds();
                        driver.findElement(By.name("yes")).submit();
                        notDeleted.append(tenderOID);
                        notDeleted.append(" deleted.\n");
                    } else {
                        notDeleted.append(tenderOID)
                                .append(" not deleted! Cause: Falsches Aktenzeichen. (")
                                .append("Von CSV Datei: ")
                                .append(itemsToDeleteMap.get(tenderOID))
                                .append(" -> Von Vergabeplattform: ")
                                .append(tempFileNumber)
                                .append(").\n");
                    }
                } catch (NoSuchElementException e) {
                    notDeleted.append(tenderOID).append(" not deleted! Cause: Vergabe nicht gefunden.\n");
                }catch (NoSuchWindowException e){
                    System.exit(0);
                }
            }
            try (BufferedWriter writer = new BufferedWriter(new FileWriter("activity.log"))) {
                writer.write(notDeleted.toString());
            } catch (Exception e) {
                e.printStackTrace();
            }
            driver.get(PropertyReader.getWebsite());
    }
    private boolean checkFileNumber(String text, String fileNumber){
        tempFileNumber = text.substring(24, 36);
        return text.contains(fileNumber);
    }
}
