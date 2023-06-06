package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.io.*;
import java.util.HashMap;

public class StartDelete {
    private final WebDriver driver;
    private final HashMap<String, String> itemsToDeleteMap = CSVFileReader.getToDeleteMap();
    public StartDelete(){
        driver = AdminLogin.getDriver();
        startDelete();
    }
    public void startDelete(){
        for(String tenderOID : itemsToDeleteMap.keySet()){
            String deleteString = PropertyReader.getWebsite() + "TenderServlet?function=Delete&item=" + tenderOID;
            driver.get(deleteString);
            try {
                WebElement textElement = driver.findElement(By.cssSelector(".entryLine"));
                String text = textElement.getText();
                if (checkFileNumber(text, itemsToDeleteMap.get(tenderOID))) {
                    driver.findElement(By.name("yes")).submit();
                    itemsToDeleteMap.remove(tenderOID);
                }
            }catch(NoSuchElementException e){
                System.out.println("Element not found");
            }
        }
        StringBuilder notDeleted = new StringBuilder();
        for(String tenderOID : itemsToDeleteMap.keySet()){
            notDeleted.append(tenderOID);
            notDeleted.append("\n");
        }
        try(BufferedWriter writer = new BufferedWriter(new FileWriter("activity.log"))){
            writer.write(notDeleted.toString() + " not deleted");
        }catch(Exception e){
            e.printStackTrace();
        }
    }
    public boolean checkFileNumber(String text, String fileNumber){
        System.out.println(text);
        System.out.println(fileNumber);
        return text.contains(fileNumber);
    }
}
