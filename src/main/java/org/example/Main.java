package org.example;

import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.net.URL;
import java.net.URLConnection;
import java.util.List;

public class Main {
    public static void main(String[] args) throws IOException {
        String url = "https://opso.pl/wp-content/uploads/2024/10/menu-31.10.2024.pdf";
        String name = "output.pdf";
        downloadPDF(url, name);

        // extract text:
        String text = extractTextFromPDF("C:\\Users\\Marcin\\IdeaProjects\\OpenSourceParser\\output.pdf");
        System.out.println(text);

        // split by new line
        List<String> listString = text.lines()
                .filter(s -> !s.isBlank())
                .map(String::strip)
                .map(String::trim)
                .filter(s -> !s.contains(")"))
                .toList();

        // make chunks
        List<String> soupsChunk = listString.subList(listString.indexOf("Zupy") + 1,
                listString.indexOf("Drugie danie mięsne lub rybne"));
        List<String> mainChunk = listString.subList(listString.indexOf("Drugie danie mięsne lub rybne") + 1,
                listString.indexOf("Dodatki"));
        List<String> vegeChunk = listString.subList(listString.indexOf("Dania Wegetariańskie") + 1,
                listString.size());

        System.out.println("<SOUPS>");
        System.out.println(soupsChunk);
        System.out.println("<MAIN>");
        System.out.println(mainChunk);
        System.out.println("<VEGE>");
        System.out.println(vegeChunk);

        // now get those strings
        System.out.println("lists:");

        // now make

    }

    // Download the PDF file from the given URL
    public static void downloadPDF(String url, String fileName) throws IOException {
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);

        FileOutputStream outputStream = new FileOutputStream(fileName);

        byte[] dataBuffer = new byte[1024];
        int bytesRead;

        while ((bytesRead = bufferedInputStream.read(dataBuffer)) != -1) {
            outputStream.write(dataBuffer, 0, bytesRead);
        }
        outputStream.close();
        bufferedInputStream.close();
    }

    // Extract text from the given PDF file
    public static String extractTextFromPDF(String filePath) throws IOException {
        File file = new File(filePath);
        PDDocument document = PDDocument.load(file);
        PDFTextStripper textStripper = new PDFTextStripper();
        String text = textStripper.getText(document);
        document.close();
        return text;
    }

}