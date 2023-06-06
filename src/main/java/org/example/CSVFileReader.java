package org.example;

import com.opencsv.CSVParserBuilder;
import com.opencsv.CSVReader;
import com.opencsv.CSVReaderBuilder;

import java.io.FileReader;
import java.util.HashMap;

public class CSVFileReader {
    private final static HashMap<String, String> toDeleteMap = new HashMap<>();
    public CSVFileReader(){
        try{
            FileReader fileReader = new FileReader(PropertyReader.getPath());
            CSVReader csvReader = new CSVReaderBuilder(fileReader).withCSVParser(new CSVParserBuilder().withSeparator(';').build()).build();
            String[] nextRecord;
            while((nextRecord = csvReader.readNext()) != null){
                toDeleteMap.put(nextRecord[0], nextRecord[1]);
            }
            System.out.println(toDeleteMap);
        }catch(Exception e){
            e.printStackTrace();
        }
    }

    public static HashMap<String, String> getToDeleteMap() {
        return toDeleteMap;
    }
}
