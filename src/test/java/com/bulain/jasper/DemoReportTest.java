package com.bulain.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.util.HashMap;

import net.sf.jasperreports.engine.JREmptyDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperFillManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class DemoReportTest {
    @Test
    public void testJasperReports() throws IOException, JRException {
        ClassPathResource resource = new ClassPathResource("reports/demoReport.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, new HashMap<String, Object>(),
                new JREmptyDataSource());
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/demoReport.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/demoReport.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/demoReport.html");
        
    }
}
