package org.example;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.example.PDFTools.PDFDownloader;
import org.example.PDFTools.PDFParser;
import org.example.entities.Meal;
import org.example.entities.Menu;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.format.DateTimeParseException;
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        PDFDownloader pdfDownloader = new PDFDownloader();
        LocalDate localDate;

        // Check if a date is provided as a command line argument
        if (args.length > 0) {
            String inputDate = args[0];
            DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd"); // Define the expected date format

            try {
                localDate = LocalDate.parse(inputDate, formatter);
                logger.info("Using provided date: " + localDate);
            } catch (DateTimeParseException e) {
                logger.error("Invalid date format. Please use yyyy-MM-dd format.");
                System.out.println("Invalid date format. Please use yyyy-MM-dd format.");
                System.exit(1);
                return; // This return is not necessary but added for clarity
            }
        } else {
            localDate = LocalDate.now(); // Default to today's date if no argument is provided
            logger.info("No date provided, using current date: " + localDate);
        }
        pdfDownloader.setLocalDate(localDate);
        try {
            pdfDownloader.downloadPDF();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("The pdf file with menu could not be found in the OPSO website.");
            System.out.println("The pdf could not be downloaded from OPSO website");
            System.exit(1);
        }

        PDFParser pdfParser = new PDFParser();
        List<Meal> meals = pdfParser.getMeals();
        DatabaseLoader databaseLoader = new DatabaseLoader(meals, new Menu(meals, localDate));
        databaseLoader.persistAll();
        logger.info("The data was successfully uploaded to the database");
    }
}
