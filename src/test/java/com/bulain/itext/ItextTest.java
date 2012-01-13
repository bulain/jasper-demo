package com.bulain.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;

import org.junit.Test;

import com.lowagie.text.Document;
import com.lowagie.text.DocumentException;
import com.lowagie.text.Paragraph;
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
}
