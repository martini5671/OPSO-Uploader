package org.example.PDFTools;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;
import org.example.entities.Meal;

import java.io.File;
import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

public class PDFParser {


    private String fileName = "output.pdf";

    public PDFParser(String fileName) {
        this.fileName = fileName;
    }

    public PDFParser() {

    }

    public String getFileName() {
        return fileName;
    }

    public void setFileName(String fileName) {
        this.fileName = fileName;
    }

    public List<Meal> getMeals() throws IOException {
        String output = extractRawTextFromPDF(fileName);
        List<String> cleared = cleanRawText(output);
        return getMealsFromStringList(cleared);
    }

    private String extractRawTextFromPDF(String fileName) throws IOException {
        File file = new File(fileName);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper textStripper = new PDFTextStripper();
        String text = textStripper.getText(document);
        document.close();
        return text;
    }

    private List<String> cleanRawText(String text) {
        return text.lines()
                .filter(s -> !s.isBlank())
                .map(String::strip)
                .map(String::trim)
                .filter(s -> !s.contains(")"))
                .toList();
    }

    private List<Meal> getMealsFromStringList(List<String> listString) {

        List<Meal> mealList = new ArrayList<>();
        List<String> soupsChunk = listString.subList(listString.indexOf("Zupy") + 1,
                listString.indexOf("Drugie danie mięsne lub rybne"));
        List<String> fishMeatChunk = listString.subList(listString.indexOf("Drugie danie mięsne lub rybne") + 1,
                listString.indexOf("Dodatki"));
        List<String> vegeChunk = listString.subList(listString.indexOf("Dania Wegetariańskie") + 1,
                listString.size());

        for (String s : soupsChunk) {
            mealList.add(new Meal(s, "soup"));
        }
        for (String s : fishMeatChunk) {
            mealList.add(new Meal(s, "fishMeat"));
        }
        for (String s : vegeChunk) {
            mealList.add(new Meal(s, "vege"));
        }

        return mealList;
    }

    public static void main(String[] args) throws IOException {
        PDFParser pdfParser = new PDFParser("output.pdf");
        List<Meal> listMeals = pdfParser.getMeals();
        System.out.println(listMeals);
    }


}
