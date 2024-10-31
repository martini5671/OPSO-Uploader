package org.example.PDFTools;

import java.io.BufferedInputStream;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.net.URL;
import java.net.URLConnection;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;

public class PDFDownloader {
    // Define the desired format
    private final DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd.MM.yyyy");
    private LocalDate localDate = LocalDate.now();
    private final String baseUrl = "https://opso.pl/wp-content/uploads";
    private final String pdfFileName = "output.pdf";

    public PDFDownloader() {
    }

    public void downloadPDF() throws IOException {
        String url = getUrl();
        URL website = new URL(url);
        URLConnection connection = website.openConnection();
        InputStream inputStream = connection.getInputStream();
        BufferedInputStream bufferedInputStream = new BufferedInputStream(inputStream);
        FileOutputStream outputStream = new FileOutputStream(pdfFileName);
        byte[] dataBuffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = bufferedInputStream.read(dataBuffer)) != -1) {
            outputStream.write(dataBuffer, 0, bytesRead);
        }
        outputStream.close();
        bufferedInputStream.close();
    }

    private String getUrl() {
        String endpoint = "/" + localDate.getYear() + "/" +
                localDate.getMonth().getValue() +
                "/menu-" + localDate.format(formatter) + ".pdf";
        return baseUrl + endpoint;

    }

    public static void main(String[] args) throws IOException {
        PDFDownloader pdfDownloader = new PDFDownloader();
        String url= pdfDownloader.getUrl();
        System.out.println(url);
        pdfDownloader.downloadPDF();
    }
}
