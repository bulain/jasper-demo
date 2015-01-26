package com.bulain.printer;

import java.io.InputStream;
import java.sql.Connection;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.type.OrientationEnum;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.jasper.JasperUtils;
import com.bulain.test.BaseTestCase;

public class PrintReportTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testPortrait() throws Exception {
        ClassPathResource resource = new ClassPathResource("reports/portrait.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, new HashMap<String, Object>(), conn);
        //JasperUtils.exportReportToPdfFile(jasperPrint, "target/portrait.pdf");
        JasperUtils.exportReportToPrint(jasperPrint);
    }

    @Test
    public void testLandscape() throws Exception {
        ClassPathResource resource = new ClassPathResource("reports/landscape.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, new HashMap<String, Object>(), conn);
        //JasperUtils.exportReportToPdfFile(jasperPrint, "target/landscape.pdf");
        JasperUtils.exportReportToPrint(jasperPrint);
        //JasperUtils.exportReportToPrint(jasperPrint, OrientationEnum.PORTRAIT, true, false);
    }
    
    @Test
    public void testFixPrint() throws Exception {
        ClassPathResource resource = new ClassPathResource("reports/landscape.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, new HashMap<String, Object>(), conn);
        JasperUtils.exportReportToFixPrint(jasperPrint, "发送至 OneNote 2010", OrientationEnum.PORTRAIT);
    }

}
