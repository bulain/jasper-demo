package com.bulain.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.fill.JRFiller;

import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;

import com.bulain.test.BaseTestCase;

public class XlsReportTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;
    
    @Test
    public void testReport4JRResultSetDataSource() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/xlsReport.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRDataSource jrDataSource = createJRResultSetDataSource();

        JasperPrint jasperPrint = JRFiller.fillReport(jasperReport, new HashMap<String, Object>(), jrDataSource);
        JasperUtils.exportReportToXlsFile(jasperPrint, "target/xlsReport.xls");
        JasperUtils.exportReportToXlsxFile(jasperPrint, "target/xlsReport.xlsx");
    }
    
    private JRDataSource createJRResultSetDataSource() throws SQLException {
        Connection conn = dataSource.getConnection();

        String sql = "select * from referances where lang=? order by name, code";
        PreparedStatement pstmt = conn.prepareStatement(sql);
        pstmt.setString(1, "en");
        ResultSet resultSet = pstmt.executeQuery();

        JRDataSource jrDs = new JRResultSetDataSource(resultSet);

        return jrDs;
    }
}
