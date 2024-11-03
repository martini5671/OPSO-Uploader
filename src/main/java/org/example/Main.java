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
import java.util.Map;

public class Main {
    private static final Logger logger = LogManager.getLogger(Main.class);

    public static void main(String[] args) throws IOException {

        CLIParser cliParser;
        Map<String, String> hibernateProps = null;
        LocalDate menuDate = null;

        if (args.length > 0) {
            cliParser = new CLIParser(args);
            hibernateProps = cliParser.getConfigMap();
            menuDate = cliParser.getLocalDate();
        }

        PDFDownloader pdfDownloader;
        if (menuDate != null) {
            pdfDownloader = new PDFDownloader(menuDate);
        } else {
            pdfDownloader = new PDFDownloader();
        }

        try {
            pdfDownloader.downloadPDF();
        } catch (FileNotFoundException fileNotFoundException) {
            logger.error("The pdf file with menu could not be found in the OPSO website.");
            System.out.println("The pdf could not be downloaded from OPSO website");
            System.exit(1);
        }

        PDFParser pdfParser = new PDFParser();
        List<Meal> meals = pdfParser.getMeals();

        DatabaseLoader databaseLoader;
        if (menuDate != null) {
            Menu menu = new Menu(meals);
            menu.setLocalDate(menuDate);
            databaseLoader = new DatabaseLoader(meals, menu, hibernateProps);
        } else {
            databaseLoader = new DatabaseLoader(meals, new Menu(meals));
        }
        databaseLoader.persistAll();
        logger.info("The data was successfully uploaded to the database");
    }
}
