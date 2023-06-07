package org.example;

import org.apache.commons.beanutils.converters.DateTimeConverter;

import java.time.LocalDateTime;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

public class Main {
    public static void main(String[] args) {
        new AdminLogin();
        new CSVFileReader();
        new StartDelete();
    }
}