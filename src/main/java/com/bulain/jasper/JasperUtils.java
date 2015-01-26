package com.bulain.jasper;

import java.sql.Connection;
import java.util.Map;

import javax.print.PrintService;
import javax.print.PrintServiceLookup;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.HashPrintServiceAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.PrintServiceAttributeSet;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRExporterParameter;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.export.JRXhtmlExporter;
import net.sf.jasperreports.engine.export.JRXlsExporter;
import net.sf.jasperreports.engine.export.JRXmlExporter;
import net.sf.jasperreports.engine.export.JRXmlExporterParameter;
import net.sf.jasperreports.engine.export.ooxml.JRXlsxExporter;
import net.sf.jasperreports.engine.fill.JRFiller;
import net.sf.jasperreports.engine.type.OrientationEnum;

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
    public static void exportReportToFixPrint(JasperPrint jasperPrint, String printServiceName) throws JRException {
        exportReportToFixPrint(jasperPrint, printServiceName, OrientationEnum.PORTRAIT);
    }
    public static void exportReportToFixPrint(JasperPrint jasperPrint, String printServiceName, OrientationEnum orientation) throws JRException {
        HashPrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        PrintService[] services = PrintServiceLookup.lookupPrintServices(null, printServiceAttributeSet);
        if (services.length <= 0) {
            throw new JRException("No suitable print service found.");
        }

        PrintService printService = null;
        for (int k = 0; k < services.length; k++) {
            PrintService s = services[k];
            if (((s.getName().compareTo(printServiceName) == 0) || (s.toString().compareTo(printServiceName) == 0))) {
                printService = s;
                break;
            }
        }
        if (printService == null) {
            throw new JRException("No suitable print service found.");
        }

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        jasperPrint.setOrientation(orientation);

        PrintServiceExporter exporter = new PrintServiceExporter();
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, false);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, false);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE, printService);
        exporter.setParameter(JRPrintServiceExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.IGNORE_PAGE_MARGINS, true);

        exporter.exportReport();
    }

    public static void exportReportToPrint(JasperPrint jasperPrint) throws JRException {
        exportReportToPrint(jasperPrint, OrientationEnum.PORTRAIT, false, false);
    }

    public static void exportReportToPrint(JasperPrint jasperPrint, OrientationEnum orientation,
            boolean displayPrintDialog, boolean displayPageDialog) throws JRException {

        PrintRequestAttributeSet printRequestAttributeSet = new HashPrintRequestAttributeSet();
        PrintServiceAttributeSet printServiceAttributeSet = new HashPrintServiceAttributeSet();
        jasperPrint.setOrientation(orientation);

        PrintServiceExporter exporter = new PrintServiceExporter();
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_REQUEST_ATTRIBUTE_SET, printRequestAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.PRINT_SERVICE_ATTRIBUTE_SET, printServiceAttributeSet);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PAGE_DIALOG, displayPageDialog);
        exporter.setParameter(JRPrintServiceExporterParameter.DISPLAY_PRINT_DIALOG, displayPrintDialog);
        exporter.setParameter(JRPrintServiceExporterParameter.JASPER_PRINT, jasperPrint);
        exporter.setParameter(JRPrintServiceExporterParameter.IGNORE_PAGE_MARGINS, true);

        exporter.exportReport();
    }
}
