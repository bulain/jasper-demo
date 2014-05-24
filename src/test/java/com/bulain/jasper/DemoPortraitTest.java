package com.bulain.jasper;

import java.awt.print.PrinterException;
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
import net.sf.jasperreports.engine.JasperPrintManager;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.test.BaseTestCase;

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

        jasperPrint.setOrientation(OrientationEnum.PORTRAIT);
        JasperPrintManager.printReport(jasperPrint, false);

    }

}
