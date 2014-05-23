package com.bulain.jasper;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterException;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.IOException;
import java.io.InputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.Locale;
import java.util.Map;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRParameter;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.test.BaseTestCase;
import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;

public class DemoPortraitTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport() throws IOException, JRException, SQLException, PrinterException {
        ClassPathResource resource = new ClassPathResource("reports/demoPortrait.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        Map<String, Object> parameters = new HashMap<String, Object>();
        Locale locale = Locale.getDefault();
        parameters.put(JRParameter.REPORT_LOCALE, locale);

        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, parameters, conn);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/demoPortrait.pdf");
        directPrintPdf();

        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
        JasperPrintManager.printReport(jasperPrint, false);

    }

    private void directPrintPdf() throws FileNotFoundException, IOException, PrinterException {
        File f = new File("target/demoPortrait.pdf");
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = PrinterJob.getPrinterJob().defaultPage();

        PDFPage page = pdfFile.getPage(0);
        Paper paper = pf.getPaper();
        paper.setImageableArea(0, 0, page.getWidth(), page.getHeight());
        paper.setSize(page.getWidth(), page.getHeight());
        pf.setPaper(paper);

        pjob.setJobName(f.getName());
        Book book = new Book();
        book.append(pages, pf, pdfFile.getNumPages());
        pjob.setPageable(book);
        pjob.print();
    }
}
