package com.loganalyzer;

import java.io.BufferedReader;
import java.io.FileReader;
import java.io.IOException;
import java.util.Scanner;

public class LogFileAnalyzer {

    public static void main(String[] args) {

        String logFilePath = "logs/sample.log";

        int infoCount = 0;
        int warningCount = 0;
        int errorCount = 0;

        Scanner scanner = new Scanner(System.in);

        System.out.print("Enter keyword to search (or press Enter to skip): ");
        String keyword = scanner.nextLine();

        System.out.print("Enter start date-time (yyyy-MM-dd HH:mm:ss) or press Enter to skip: ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date-time (yyyy-MM-dd HH:mm:ss) or press Enter to skip: ");
        String endDate = scanner.nextLine();

        try {
            BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
            String line;

            System.out.println("\nFiltered Logs:\n");

            while ((line = reader.readLine()) != null) {

                // Count log levels
                if (line.contains("INFO")) {
                    infoCount++;
                } else if (line.contains("WARNING")) {
                    warningCount++;
                } else if (line.contains("ERROR")) {
                    errorCount++;
                }

                // Extract date-time from log line
                String logDateTime = line.substring(0, 19);

                boolean keywordMatch = keyword.isEmpty() || line.contains(keyword);
                boolean startMatch = startDate.isEmpty() || logDateTime.compareTo(startDate) >= 0;
                boolean endMatch = endDate.isEmpty() || logDateTime.compareTo(endDate) <= 0;

                if (keywordMatch && startMatch && endMatch) {
                    System.out.println(line);
                }
            }

            reader.close();

            System.out.println("\n------ Log Summary ------");
            System.out.println("INFO Count    : " + infoCount);
            System.out.println("WARNING Count : " + warningCount);
            System.out.println("ERROR Count   : " + errorCount);

        } catch (IOException e) {
            System.out.println("Error reading file: " + e.getMessage());
        }

        scanner.close();
    }
}
