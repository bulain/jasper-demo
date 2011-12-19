package com.bulain.jasper;

import java.io.IOException;
import java.io.InputStream;
import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.HashMap;
import java.util.List;

import javax.sql.DataSource;

import net.sf.jasperreports.engine.JRDataSource;
import net.sf.jasperreports.engine.JRException;
import net.sf.jasperreports.engine.JRResultSetDataSource;
import net.sf.jasperreports.engine.JasperCompileManager;
import net.sf.jasperreports.engine.JasperExportManager;
import net.sf.jasperreports.engine.JasperPrint;
import net.sf.jasperreports.engine.JasperReport;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.fill.JRFiller;

import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;
import org.springframework.test.context.ContextConfiguration;
import org.springframework.test.context.TestExecutionListeners;
import org.springframework.test.context.junit4.SpringJUnit4ClassRunner;
import org.springframework.test.context.support.DependencyInjectionTestExecutionListener;

@RunWith(SpringJUnit4ClassRunner.class)
@TestExecutionListeners(value = {DependencyInjectionTestExecutionListener.class})
@ContextConfiguration(locations = {"classpath*:spring/applicationContext*.xml",
        "classpath*:spring/propertyConfigurer-test.xml"})
public class JavaBeanReportTest {
    @Autowired
    private JdbcTemplate jdbcTemplate;
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport4JRResultSetDataSource() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/javaBeanReport.jr.xml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRDataSource jrDataSource = createJRResultSetDataSource();

        JasperPrint jasperPrint = JRFiller.fillReport(jasperReport, new HashMap<String, Object>(), jrDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/javaBeanReport-resultSet.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/javaBeanReport-resultSet.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/javaBeanReport-resultSet.html");
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

    @Test
    public void testReport4JRBeanCollectionDataSource() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/javaBeanReport.jr.xml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        JRDataSource jrDataSource = createJRBeanCollectionDataSource();

        JasperPrint jasperPrint = JRFiller.fillReport(jasperReport, new HashMap<String, Object>(), jrDataSource);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/javaBeanReport-beanCollection.pdf");
        JasperExportManager.exportReportToXmlFile(jasperPrint, "target/javaBeanReport-beanCollection.xml", false);
        JasperExportManager.exportReportToHtmlFile(jasperPrint, "target/javaBeanReport-beanCollection.html");
    }
    
    private JRDataSource createJRBeanCollectionDataSource() {
        String sql = "select * from referances where lang=? order by name, code";

        RowMapper<Referance> rowMapper = new RowMapper<Referance>() {
            public Referance mapRow(ResultSet rs, int rowNum) throws SQLException {
                Referance ref = new Referance();
                ref.setName(rs.getString("NAME"));
                ref.setCode(rs.getString("CODE"));
                ref.setLang(rs.getString("LANG"));
                ref.setText(rs.getString("TEXT"));
                ref.setCategory(rs.getString("CATEGORY"));
                return ref;
            }
        };

        List<Referance> listReferance = jdbcTemplate.query(sql, new Object[]{"en"}, rowMapper);

        JRDataSource jrDs = new JRBeanCollectionDataSource(listReferance);

        return jrDs;
    }
}
