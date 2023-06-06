package org.example;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;
import io.netty.handler.codec.Delimiters;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashMap;
import java.util.Objects;

public class CSVFileReader {
    private static HashMap<String, String> toDeleteMap = new HashMap<>();
    public CSVFileReader(){
        try{
            FileReader fileReader = new FileReader(AdminLogin.getPath());
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
            String[] nextRecord;
            while((nextRecord = csvReader.readNext()) != null){
                toDeleteMap.put(nextRecord[0], nextRecord[1]);
                System.out.println();
            }
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getToDeleteMap() {
        return toDeleteMap;
    }
}
