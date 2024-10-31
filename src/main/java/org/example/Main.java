package org.example;

import org.example.PDFTools.PDFDownloader;
import org.example.PDFTools.PDFParser;
import org.example.entities.Meal;
import org.example.entities.Menu;

import java.io.IOException;
import java.time.LocalDate;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        PDFDownloader pdfDownloader = new PDFDownloader();
        pdfDownloader.downloadPDF();
        PDFParser pdfParser = new PDFParser("output.pdf");
        List<Meal> meals = pdfParser.getMeals();
        DatabaseLoader databaseLoader = new DatabaseLoader(meals, new Menu(meals, LocalDate.now()));
        databaseLoader.persistAll();

    }
}