package com.epam.rd.java.basic.finalProject.util;

import com.epam.rd.java.basic.finalProject.dto.PaymentDTO;
import com.itextpdf.text.*;
import com.itextpdf.text.pdf.PdfPCell;
import com.itextpdf.text.pdf.PdfPTable;
import com.itextpdf.text.pdf.PdfWriter;
import org.apache.log4j.Logger;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;

public class PDFGenerator {

    private static final Logger LOGGER = Logger.getLogger(PDFGenerator.class);

    private static void addTableTitle(PdfPTable pdfPTable, String... columns) {
        Font headFont = FontFactory.getFont(FontFactory.HELVETICA_BOLD);
        PdfPCell hcell;
        for (String column : columns) {
            hcell = new PdfPCell(new Phrase(column, headFont));
            hcell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(hcell);
        }
    }

    private static void addTableCells(PdfPTable pdfPTable, String... cells) {
        PdfPCell cell;
        for (String cellValue : cells) {
            cell = new PdfPCell(new Phrase(cellValue));
            cell.setVerticalAlignment(Element.ALIGN_MIDDLE);
            cell.setHorizontalAlignment(Element.ALIGN_CENTER);
            pdfPTable.addCell(cell);
        }
    }

    public static ByteArrayInputStream generateReport(PaymentDTO paymentDTO) {
        Document document = new Document();
        ByteArrayOutputStream out = new ByteArrayOutputStream();
        try {
            PdfPTable table = new PdfPTable(6);
            table.setWidthPercentage(80);
            table.setWidths(new int[]{1, 1, 1, 1, 1, 1});

            addTableTitle(table, "Payment Date", "Payment Number", "From Count", "To Count", "Status", "Amount");
            addTableCells(table, String.valueOf(paymentDTO.getPaymentDate()),
                    String.valueOf(paymentDTO.getPaymentNumber()),
                    String.valueOf(paymentDTO.getFromCount().getCountNumber()),
                    String.valueOf(paymentDTO.getToCount().getCountNumber()),
                    String.valueOf(paymentDTO.getStatusName()),
                    String.valueOf(paymentDTO.getAmount()));

            PdfWriter.getInstance(document, out);
            document.open();
            document.add(table);
            document.close();

        } catch (DocumentException ex) {
            LOGGER.error("Error while pdf generating", ex);
        }

        return new ByteArrayInputStream(out.toByteArray());
    }
}
