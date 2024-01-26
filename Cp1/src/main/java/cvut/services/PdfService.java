package cvut.services;

import cvut.controllers.AuthorController;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.pdmodel.PDPageContentStream;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.font.Standard14Fonts;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service
public class PdfService {

    private static final Logger LOG = LoggerFactory.getLogger(AuthorController.class);

    public PDDocument generatePdf(String content, String title) {
        try (PDDocument document = new PDDocument()) {
            PDPage page = new PDPage();
            document.addPage(page);

            try (PDPageContentStream contentStream = new PDPageContentStream(document, page)) {
                contentStream.setFont(new PDType1Font(Standard14Fonts.FontName.HELVETICA_BOLD), 12);
                contentStream.beginText();
                contentStream.newLineAtOffset(20, 700); // Adjust the position as needed
                contentStream.showText("Title: " + title);
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText("Article Content:");
                contentStream.newLineAtOffset(0, -20);
                contentStream.showText(content);
                contentStream.endText();
            }
            return document;
        } catch (IOException e) {
            LOG.error("Error while generating PDF: {}", e.getMessage());
        }
        return null;
    }
}
