package org.example;

import javax.swing.*;

public class ConfirmQuestion extends JFrame {
    private JPanel panel = new JPanel();
    public ConfirmQuestion(){
        init();
    }
    public void init(){
        this.setDefaultCloseOperation(EXIT_ON_CLOSE);
        if(JOptionPane.showConfirmDialog(this, "Sind sie sicher das sie die Vergaben aus folgender Datei löschen möchten?\n" + PropertyReader.getPath()) == 0){
            new StartDelete();
        }else{
            System.exit(0);
        }

    }
}
