package org.example;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;

import java.util.HashMap;

public class StartDelete {
    private WebDriver driver;
    private HashMap<String, String> itemsToDeleteMap = CSVFileReader.getToDeleteMap();
    public StartDelete(){
        driver = AdminLogin.getDriver();
        startDelete();
    }
    public void startDelete(){
        for(String tenderOID : itemsToDeleteMap.keySet()){
            String deleteString = AdminLogin.getWebsite() + "TenderServlet?function=Delete&item=" + tenderOID;
            System.out.println(itemsToDeleteMap.get(tenderOID));
            System.out.println(deleteString);
            driver.get(deleteString);
            WebElement textElement = driver.findElement(By.cssSelector(".entryLine"));
            String text = textElement.getText();
            if(checkFileNumber(text, itemsToDeleteMap.get(tenderOID))){
                driver.findElement(By.name("yes")).submit();
            }else{
                System.out.println("Vergabenummer stimmt nicht");
            }
        }
    }
    public boolean checkFileNumber(String text, String fileNumber){
        System.out.println(text);
        System.out.println(fileNumber);
        return text.contains(fileNumber);
    }
}
