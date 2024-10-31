import org.example.PDFTools.PDFDownloader;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.time.LocalDate;

public class PDFDownloaderTests {
    PDFDownloader pdfDownloader;

    @BeforeEach
    public void setup() {
        this.pdfDownloader = new PDFDownloader();
    }

    @Test
    public void testDownloadFail() {
        pdfDownloader.setLocalDate(LocalDate.now().plusDays(100));
        Assertions.assertThrows(FileNotFoundException.class, () -> pdfDownloader.downloadPDF());
    }

}
