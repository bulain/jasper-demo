package com.bulain.printer;

import javax.print.DocFlavor;
import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.Attribute;
import javax.print.attribute.AttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.PrinterName;

import org.junit.Test;

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

}
