package com.loganalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;

public class LogFileAnalyzer {

    public static void main(String[] args) {

        String logFilePath = "logs/sample.log";

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
            String line;

            System.out.println("Reading log file...\n");

            while ((line = reader.readLine()) != null) {
                System.out.println(line);
            }

            reader.close();

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }
    }
}
