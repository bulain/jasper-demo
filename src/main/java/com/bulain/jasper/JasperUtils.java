package com.bulain.jasper;

import java.sql.Connection;
import java.util.Map;

import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;
import javax.print.attribute.standard.MediaSizeName;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRFiller;

public class JasperUtils {
    public static JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters, Connection conn)
            throws JRException {
        return JRFiller.fillReport(jasperReport, parameters, conn);
        //return JRFiller.fill(DefaultJasperReportsContext.getInstance(), jasperReport, parameters, conn);
    }
    public static JasperPrint fillReport(JasperReport jasperReport, Map<String, Object> parameters,
            JRDataSource dataSource) throws JRException {
        return JRFiller.fillReport(jasperReport, parameters, dataSource);
        //return JRFiller.fill(DefaultJasperReportsContext.getInstance(), jasperReport, parameters, dataSource);
    }
    public static void exportReportToPdfFile(JasperPrint jasperPrint, String destFileName) throws JRException {
        JRPdfExporter exporter = new JRPdfExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();
    }
    public static void exportReportToXmlFile(JasperPrint jasperPrint, String destFileName, boolean isEmbeddingImages)
            throws JRException {
        JRXmlExporter exporter = new JRXmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.setParameter(JRXmlExporterParameter.IS_EMBEDDING_IMAGES, isEmbeddingImages
                ? Boolean.TRUE
                : Boolean.FALSE);
        exporter.exportReport();
    }
    public static void exportReportToHtmlFile(JasperPrint jasperPrint, String destFileName) throws JRException {
        JRXhtmlExporter exporter = new JRXhtmlExporter();
        //HtmlExporter exporter = new HtmlExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();
    }
    public static void exportReportToXlsFile(JasperPrint jasperPrint, String destFileName) throws JRException {
        JRXlsExporter exporter = new JRXlsExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();
    }
    public static void exportReportToXlsxFile(JasperPrint jasperPrint, String destFileName) throws JRException {
        JRXlsxExporter exporter = new JRXlsxExporter();
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
        exporter.setParameter(JRExporterParameter.OUTPUT_FILE_NAME, destFileName);
        exporter.exportReport();
    }
    public static void exportReportToPrint(JasperPrint jasperPrint) throws JRException {
        JRPrintServiceExporter exporter = new JRPrintServiceExporter();
       
        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        printRequestAttributeSet.add(MediaSizeName.ISO_A4);
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, Boolean.FALSE);
        exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRExporterParameter.IGNORE_PAGE_MARGINS, true);
        
        exporter.exportReport();
    }
}
