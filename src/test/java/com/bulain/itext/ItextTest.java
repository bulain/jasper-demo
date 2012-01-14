package com.bulain.itext;

import java.awt.Color;
import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
import com.lowagie.text.Rectangle;
import com.lowagie.text.pdf.PdfAnnotation;
import com.lowagie.text.pdf.PdfAppearance;
import com.lowagie.text.pdf.PdfBorderDictionary;
import com.lowagie.text.pdf.PdfContentByte;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfWriter;

public class ItextTest {
    @Test
    public void testItext() throws FileNotFoundException, DocumentException {
        Document document = new Document();
        PdfWriter.getInstance(document, new FileOutputStream("target/hello.pdf"));
        document.open();
        document.add(new Paragraph("Hello Pdf"));
        document.close();
    }

    @Test
    public void testForm() throws DocumentException, IOException {
        Document document = new Document();
        PdfWriter writer = PdfWriter.getInstance(document, new FileOutputStream("target/form.pdf"));
        document.open();

        PdfContentByte cb = new PdfContentByte(writer);

        createTextField(writer, cb, 800);
        createTextField(writer, cb, 760);
        createTextField(writer, cb, 720);

        document.close();
    }

    private void createTextField(PdfWriter writer, PdfContentByte cb, int ly) {
        PdfFormField textField = PdfFormField.createTextField(writer, false, false, 0);
        textField.setWidget(new Rectangle(20, ly, 220, ly + 20), PdfAnnotation.HIGHLIGHT_INVERT);
        textField.setFlags(PdfAnnotation.FLAGS_PRINT);
        textField.setFieldName("textField_" + ly);
        textField.setMKBorderColor(Color.RED);
        textField.setBorderStyle(new PdfBorderDictionary(1, PdfBorderDictionary.STYLE_SOLID));
        textField.setPage();
        PdfAppearance tp = cb.createAppearance(200, 20);
        tp.drawTextField(0, 0, 200, 20);
        textField.setAppearance(PdfAnnotation.APPEARANCE_NORMAL, tp);
        writer.addAnnotation(textField);
    }

}
