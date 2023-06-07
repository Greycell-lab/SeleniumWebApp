package org.example;

import org.openqa.selenium.WebDriver;

import javax.swing.*;

public class ConfirmQuestion extends JFrame {
    public ConfirmQuestion(){
        init();
    }
    public void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        this.setAlwaysOnTop(true);
        StringBuilder warningString = new StringBuilder();
        warningString.append("Sind Sie sicher das Sie die Vergaben aus folgender Quelle löschen möchten?\n")
                .append("Aus Datei: ")
                .append(PropertyReader.getPath())
                .append("\nVon Plattform: ")
                .append(PropertyReader.getWebsite());
        if(JOptionPane.showConfirmDialog(this, warningString, "Warnung", JOptionPane.YES_NO_OPTION) == 0){
            new StartDelete();
            AdminLogin.driver.quit();
            JOptionPane.showMessageDialog(this, "Löschvorgang beendet. Weiter Information im activity.log Logfile", "Information", JOptionPane.PLAIN_MESSAGE);
            System.exit(0);
        }else{
            AdminLogin.driver.quit();
            System.exit(0);
        }
    }
    public static void errorPane(){
        JFrame errorFrame = new JFrame();
        JOptionPane.showMessageDialog(errorFrame, "Browser wurde geschlossen. Programm wird abgebrochen.", "Hinweis", JOptionPane.PLAIN_MESSAGE);
    }
}
