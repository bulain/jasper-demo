package com.bulain.poi;

import java.io.FileOutputStream;
import java.io.OutputStream;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.List;

import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.ss.usermodel.Sheet;
import org.apache.poi.ss.usermodel.Workbook;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.core.RowMapper;

import com.bulain.jasper.Referance;
import com.bulain.test.BaseTestCase;

public class WriterTest extends BaseTestCase {
    @Autowired
    private JdbcTemplate jdbcTemplate;

    @Test
    public void testReadXls() throws Exception {
        Workbook workbook = new HSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowIdx = 0;
        int cellIdx = 0;
        Row row;
        Cell cell;

        row = sheet.createRow(rowIdx++);
        String[] header = new String[]{"Name", "Lang", "Code", "Text", "Category"};
        for (String h : header) {
            cell = row.createCell(cellIdx++);
            cell.setCellValue(h);
        }

        List<Referance> listReferance = createListReferance();
        for (Referance ref : listReferance) {
            row = sheet.createRow(rowIdx++);
            cellIdx = 0;
            row.createCell(cellIdx++).setCellValue(ref.getName());
            row.createCell(cellIdx++).setCellValue(ref.getLang());
            row.createCell(cellIdx++).setCellValue(ref.getCode());
            row.createCell(cellIdx++).setCellValue(ref.getText());
            row.createCell(cellIdx++).setCellValue(ref.getCategory());
        }

        OutputStream os = new FileOutputStream("target/poiReferances.xls");
        workbook.write(os);
    }
    @Test
    public void testReadXlsx() throws Exception {
        Workbook workbook = new XSSFWorkbook();
        Sheet sheet = workbook.createSheet();

        int rowIdx = 0;
        int cellIdx = 0;
        Row row;
        Cell cell;

        row = sheet.createRow(rowIdx++);
        String[] header = new String[]{"Name", "Lang", "Code", "Text", "Category"};
        for (String h : header) {
            cell = row.createCell(cellIdx++);
            cell.setCellValue(h);
        }

        List<Referance> listReferance = createListReferance();
        for (Referance ref : listReferance) {
            row = sheet.createRow(rowIdx++);
            cellIdx = 0;
            row.createCell(cellIdx++).setCellValue(ref.getName());
            row.createCell(cellIdx++).setCellValue(ref.getLang());
            row.createCell(cellIdx++).setCellValue(ref.getCode());
            row.createCell(cellIdx++).setCellValue(ref.getText());
            row.createCell(cellIdx++).setCellValue(ref.getCategory());
        }

        OutputStream os = new FileOutputStream("target/poiReferances.xlsx");
        workbook.write(os);
    }

    private List<Referance> createListReferance() {
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

        return listReferance;
    }
}
