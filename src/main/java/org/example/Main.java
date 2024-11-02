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
import java.util.List;

public class Main {

    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {
        PDFDownloader pdfDownloader = new PDFDownloader();
        pdfDownloader.setLocalDate(LocalDate.parse("2024-10-31"));
        try {
            pdfDownloader.downloadPDF();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("The pdf file with menu could not be found in the OPSO website.");
            System.out.println("The pdf could not be downloaded from OPSO website");
            System.exit(1);
        }
        PDFParser pdfParser = new PDFParser();
        List<Meal> meals = pdfParser.getMeals();
        DatabaseLoader databaseLoader = new DatabaseLoader(meals, new Menu(meals, LocalDate.now()));
        databaseLoader.persistAll();
        logger.info("The data was successfully uploaded to the database");
    }
}