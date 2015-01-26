package com.bulain.pdfprint;

import java.awt.print.Book;
import java.awt.print.PageFormat;
import java.awt.print.Paper;
import java.awt.print.PrinterJob;
import java.io.File;
import java.io.FileInputStream;
import java.nio.ByteBuffer;
import java.nio.channels.FileChannel;

import javax.print.Doc;
import javax.print.DocFlavor;
import javax.print.DocPrintJob;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.SimpleDoc;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;
import javax.print.attribute.standard.PrinterName;

import org.junit.Test;

import sun.print.PageableDoc;

import com.sun.pdfview.PDFFile;
import com.sun.pdfview.PDFPage;
import com.sun.pdfview.PDFPrintPage;

public class PrintServiceTest {
    @Test
    public void testPrintServiceLookup() {
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        System.out.println(printService);
        System.out.println();

        DocFlavor[] docFlavors = printService.getSupportedDocFlavors();
        for (DocFlavor df : docFlavors) {
            System.out.println(df);
        }
        System.out.println();

        PrintServiceAttributeSet attributes = printService.getAttributes();
        Attribute[] array = attributes.toArray();
        for (Attribute a : array) {
            System.out.println(a.getName() + " : " + a);
        }
        System.out.println();

        PrintService[] printServices = PrintServiceLookup.lookupPrintServices(null, null);
        for (PrintService ps : printServices) {
            System.out.println(ps);
        }
        System.out.println();

        AttributeSet attr = new HashPrintServiceAttributeSet();
        PrinterName printerName = new PrinterName("Foxit Reader PDF Printer", null);
        attr.add(printerName);
        printServices = PrintServiceLookup.lookupPrintServices(null, attr);
        for (PrintService ps : printServices) {
            System.out.println(ps);
        }
        System.out.println();
    }

    @Test
    public void testDocPrintJob() throws Exception {
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob printJob = printService.createPrintJob();

        DocFlavor flavor = DocFlavor.INPUT_STREAM.PNG;
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A6);
        FileInputStream fis = new FileInputStream("src/test/resources/png/ilogie_arch.png");
        Doc doc = new SimpleDoc(fis, flavor, null);
        printJob.print(doc, aset);
    }

    @Test
    public void testDocPrintJobPdf() throws Exception {
        PrintService printService = PrintServiceLookup.lookupDefaultPrintService();
        DocPrintJob printJob = printService.createPrintJob();
        PageFormat pf = new PageFormat();

        File f = new File("target/demoPortrait.pdf");
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);

        PDFPage page = pdfFile.getPage(0);
        Paper paper = pf.getPaper();
        paper.setImageableArea(0, 0, page.getWidth(), page.getHeight());
        paper.setSize(page.getWidth(), page.getHeight());
        pf.setPaper(paper);

        Book book = new Book();
        book.append(pages, pf, pdfFile.getNumPages());
        PageableDoc pd = new PageableDoc(book);
        PrintRequestAttributeSet aset = new HashPrintRequestAttributeSet();
        aset.add(MediaSizeName.ISO_A6);
        printJob.print(pd, aset);
    }

    @Test
    public void testPrintPdf() throws Exception {
        PrinterJob pjob = PrinterJob.getPrinterJob();
        PageFormat pf = pjob.defaultPage();

        File f = new File("target/demoPortrait.pdf");
        FileInputStream fis = new FileInputStream(f);
        FileChannel fc = fis.getChannel();
        ByteBuffer bb = fc.map(FileChannel.MapMode.READ_ONLY, 0, fc.size());
        PDFFile pdfFile = new PDFFile(bb);
        PDFPrintPage pages = new PDFPrintPage(pdfFile);

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
