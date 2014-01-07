package com.bulain.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.test.BaseTestCase;

public class JdbcReportTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/jdbcReport.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        
        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, new HashMap<String,Object>(), conn);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/jdbcReport.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/jdbcReport.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/jdbcReport.html");
    }
}
