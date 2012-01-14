package com.bulain.itext;

import java.io.FileNotFoundException;
import java.io.FileOutputStream;
import java.io.IOException;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

import com.lowagie.text.DocumentException;
import com.lowagie.text.pdf.AcroFields;
import com.lowagie.text.pdf.PdfFormField;
import com.lowagie.text.pdf.PdfReader;
import com.lowagie.text.pdf.PdfStamper;

public class WriterTest {
    @Test
    public void testWriter4ManualRW_ProgramRW() throws FileNotFoundException, DocumentException, IOException {
        ClassPathResource resource = new ClassPathResource("pdf/formTemplate.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("target/ManualRW_ProgramRW.pdf"));
        reader.close();

        AcroFields form = stamper.getAcroFields();
        form.setField("textField_720", "textField_1");
        form.setField("textField_760", "textField_2");
        form.setField("textField_800", "textField_3");

        stamper.close();
    }

    @Test
    public void testWriter4ManualR() throws FileNotFoundException, DocumentException, IOException {
        ClassPathResource resource = new ClassPathResource("pdf/formTemplate.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("target/ManualR.pdf"));
        reader.close();

        AcroFields form = stamper.getAcroFields();
        form.setField("textField_720", "textField_1");
        form.setField("textField_760", "textField_2");
        form.setField("textField_800", "textField_3");

        stamper.setFormFlattening(true);

        stamper.close();
    }

    @Test
    public void testWriter4ManualR_Partical() throws FileNotFoundException, DocumentException, IOException {
        ClassPathResource resource = new ClassPathResource("pdf/formTemplate.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("target/ManualR_Partical.pdf"));
        reader.close();

        AcroFields form = stamper.getAcroFields();
        form.setField("textField_720", "textField_1");
        form.setField("textField_760", "textField_2");
        form.setField("textField_800", "textField_3");

        stamper.setFormFlattening(true);
        stamper.partialFormFlattening("textField_720");
        stamper.partialFormFlattening("textField_760");
        stamper.partialFormFlattening("textField_800");

        stamper.close();
    }

    @Test
    public void testWriter4ManualR_ProgramR() throws FileNotFoundException, DocumentException, IOException {
        ClassPathResource resource = new ClassPathResource("pdf/formTemplate.pdf");
        PdfReader reader = new PdfReader(resource.getInputStream());
        PdfStamper stamper = new PdfStamper(reader, new FileOutputStream("target/ManualR_ProgramR.pdf"));
        reader.close();

        AcroFields form = stamper.getAcroFields();
        form.setField("textField_720", "textField_1");
        form.setField("textField_760", "textField_2");
        form.setField("textField_800", "textField_3");

        form.setFieldProperty("textField_720", "setfflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty("textField_760", "setfflags", PdfFormField.FF_READ_ONLY, null);
        form.setFieldProperty("textField_800", "setfflags", PdfFormField.FF_READ_ONLY, null);

        stamper.close();
    }
}
