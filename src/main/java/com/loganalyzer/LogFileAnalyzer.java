package com.loganalyzer;

import java.io.*;
import java.util.Scanner;

public class LogFileAnalyzer {

    // Color codes
    public static final String RESET = "\u001B[0m";
    public static final String RED = "\u001B[31m";
    public static final String GREEN = "\u001B[32m";
    public static final String YELLOW = "\u001B[33m";

    public static void main(String[] args) {

        Scanner scanner = new Scanner(System.in);

        // 1️⃣ Take log file path from user
        System.out.print("Enter log file path: ");
        String logFilePath = scanner.nextLine();

        // 2️⃣ Output directory & file
        String outputDir = "output";
        String outputFilePath = outputDir + "/analysis_report.txt";

        int infoCount = 0;
        int warningCount = 0;
        int errorCount = 0;

        System.out.print("Enter keyword (or press Enter to skip): ");
        String keyword = scanner.nextLine();

        System.out.print("Enter start date-time (yyyy-MM-dd HH:mm:ss) or press Enter: ");
        String startDate = scanner.nextLine();

        System.out.print("Enter end date-time (yyyy-MM-dd HH:mm:ss) or press Enter: ");
        String endDate = scanner.nextLine();

        try {
            // 3️⃣ Create output folder automatically if not exists
            File dir = new File(outputDir);
            if (!dir.exists()) {
                dir.mkdir();
            }

            BufferedReader reader = new BufferedReader(new FileReader(logFilePath));
            FileWriter writer = new FileWriter(outputFilePath);

            String line;

            System.out.println("\nFiltered Logs:\n");
            writer.write("Filtered Logs:\n\n");

            while ((line = reader.readLine()) != null) {

                // Count log levels
                if (line.contains("INFO")) infoCount++;
                else if (line.contains("WARNING")) warningCount++;
                else if (line.contains("ERROR")) errorCount++;

                // Extract date-time
                if (line.length() < 19) continue;
                String logDateTime = line.substring(0, 19);

                boolean keywordMatch = keyword.isEmpty() || line.contains(keyword);
                boolean startMatch = startDate.isEmpty() || logDateTime.compareTo(startDate) >= 0;
                boolean endMatch = endDate.isEmpty() || logDateTime.compareTo(endDate) <= 0;

                if (keywordMatch && startMatch && endMatch) {

                    // Color output
                    if (line.contains("ERROR"))
                        System.out.println(RED + line + RESET);
                    else if (line.contains("WARNING"))
                        System.out.println(YELLOW + line + RESET);
                    else
                        System.out.println(GREEN + line + RESET);

                    writer.write(line + "\n");
                }
            }

            // Summary
            writer.write("\n------ Log Summary ------\n");
            writer.write("INFO Count    : " + infoCount + "\n");
            writer.write("WARNING Count : " + warningCount + "\n");
            writer.write("ERROR Count   : " + errorCount + "\n");

            System.out.println("\n------ Log Summary ------");
            System.out.println("INFO Count    : " + infoCount);
            System.out.println("WARNING Count : " + warningCount);
            System.out.println("ERROR Count   : " + errorCount);

            reader.close();
            writer.close();

            System.out.println("\nReport saved at: " + outputFilePath);

        } catch (IOException e) {
            System.out.println("Error: " + e.getMessage());
        }

        scanner.close();
    }
}
