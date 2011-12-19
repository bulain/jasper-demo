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
import net.sf.jasperreports.engine.fill.JRFiller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml",
        "classpath*:spring/propertyConfigurer-test.xml"})
public class JdbcReportTest {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/jdbcReport.jr.xml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);
        
        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JRFiller.fillReport(jasperReport, new HashMap<String,Object>(), conn);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/jdbcReport.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/jdbcReport.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/jdbcReport.html");
    }
}
