package service.sale;

import model.Sale;
import org.apache.pdfbox.pdmodel.font.PDFont;
import repository.sale.SaleRepository;
import org.apache.pdfbox.pdmodel.*;
import org.apache.pdfbox.pdmodel.font.PDType1Font;
import org.apache.pdfbox.pdmodel.common.PDRectangle;

import java.io.IOException;
import java.nio.file.Path;
import java.util.List;

public class SaleServiceImplementation implements SaleService{
    private final SaleRepository saleRepository;

    public SaleServiceImplementation(SaleRepository saleRepository) {
        this.saleRepository = saleRepository;
    }

    @Override
    public List<Sale> findAll() {
        return saleRepository.findAll();
    }

    @Override
    public void writeToPdf(List<Sale> sales, Path filePath) {
        try (PDDocument doc = new PDDocument()) {
            PDFont font = PDType1Font.HELVETICA;
            PDPage page = new PDPage(PDRectangle.A4);
            doc.addPage(page);
            PDPageContentStream content = new PDPageContentStream(doc, page);

            content.setFont(font, 12);

            float margin = 50;
            float yStart = page.getMediaBox().getHeight() - margin;
            float yPosition = yStart;

            float lineSpacing = 15; // Spacing between lines
            int maxLinesPerPage = (int)((page.getMediaBox().getHeight() - margin * 2) / lineSpacing);
            int lines = 0;

            for (Sale sale : sales) {
                if (lines >= maxLinesPerPage) {
                    content.close(); // Close current content stream
                    page = new PDPage(PDRectangle.A4);
                    doc.addPage(page);
                    content = new PDPageContentStream(doc, page);
                    content.setFont(font, 12);
                    yPosition = yStart;
                    lines = 0;
                }

                // Write Sale data in a structured format
                content.beginText();
                content.newLineAtOffset(margin, yPosition);
                content.showText(sale.toString());
                content.endText();

                yPosition -= lineSpacing;
                lines++;
            }

            content.close();
            doc.save(filePath.toFile());
        } catch (IOException ex) {
            ex.printStackTrace();
        }
    }
}
