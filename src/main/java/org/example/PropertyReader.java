package org.example;

import java.io.FileInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Properties;

public class PropertyReader {
    private static String website;
    private String username;
    private String password;
    private int waitTime;
    private static String path;
    public PropertyReader(){
        try(InputStream input = new FileInputStream("websites.properties")){
            Properties properties = new Properties();
            properties.load(input);
            website = properties.getProperty("website");
            username = properties.getProperty("username");
            password = properties.getProperty("password");
            path = properties.getProperty("path");
            waitTime = Integer.parseInt(properties.getProperty("wait"));
            System.out.println(waitTime);
        } catch(IOException e){
            e.printStackTrace();
        }
    }
    public static String getWebsite() {
        return website;
    }

    public String getUsername() {
        return username;
    }

    public String getPassword() {
        return password;
    }

    public static String getPath() {
        return path;
    }

    public int getWaitTime() {
        return waitTime;
    }
}
