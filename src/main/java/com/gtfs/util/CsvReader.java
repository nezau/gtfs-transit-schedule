package com.gtfs.util;

import com.opencsv.CSVReader;

import java.io.InputStream;
import java.io.InputStreamReader;
import java.util.List;

public class CsvReader {

    public static void readCSV(String filePath) {
        try(InputStream inputStream = CsvReader.class.getClassLoader().getResourceAsStream(filePath)) {
            if(inputStream == null) {
                System.err.println("File not found: " + filePath);
                return;
            }
            CSVReader csvReader = new CSVReader(new InputStreamReader(inputStream));
            List<String[]> allData = csvReader.readAll();
            // print data:
//            printCSV(allData);
        }
        catch (Exception e) {
            e.printStackTrace();
        }
    }

    public static void printCSV(List<String[]> data) {
        for (String[] row : data) {
            for (String cell : row) {
                System.out.print(cell + "\t");
            }
            System.out.println();
        }
    }
}
