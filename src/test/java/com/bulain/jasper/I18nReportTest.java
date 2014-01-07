package com.bulain.jasper;

import java.io.IOException;
import java.io.InputStream;
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
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.test.BaseTestCase;

public class I18nReportTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/i18nReport.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        Map<String, Object> parameters = new HashMap<String, Object>();
        Locale locale = Locale.getDefault();
        parameters.put(JRParameter.REPORT_LOCALE, locale);

        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, parameters, conn);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/i18nReport.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/i18nReport.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/i18nReport.html");
        JasperUtils.exportReportToXlsFile(jasperPrint, "target/i18nReport.xls");
        JasperUtils.exportReportToXlsxFile(jasperPrint, "target/i18nReport.xlsx");
    }
}
