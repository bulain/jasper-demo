package com.bulain.poi;

import java.io.InputStream;
import java.util.Iterator;

import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.ss.usermodel.Cell;
import org.apache.poi.ss.usermodel.Row;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;
import org.junit.Test;
import org.springframework.core.io.ClassPathResource;

public class ReadTest {
    @Test
    public void testReadXls() throws Exception {
        ClassPathResource resource = new ClassPathResource("excel/formTemplate.xls");
        InputStream is = resource.getInputStream();
        HSSFWorkbook workbook = new HSSFWorkbook(is);
        HSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            Iterator<Cell> cellIter = row.cellIterator();
            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();
                Object value = cell.getStringCellValue();
                System.out.print(value + "\t");
            }
            System.out.println();
        }
    }
    @Test
    public void testReadXlsx() throws Exception {
        ClassPathResource resource = new ClassPathResource("excel/formTemplate.xlsx");
        InputStream is = resource.getInputStream();
        XSSFWorkbook workbook = new XSSFWorkbook(is);
        XSSFSheet sheet = workbook.getSheetAt(0);
        Iterator<Row> rowIter = sheet.iterator();
        while (rowIter.hasNext()) {
            Row row = rowIter.next();
            Iterator<Cell> cellIter = row.cellIterator();
            while (cellIter.hasNext()) {
                Cell cell = cellIter.next();
                String value = cell.getStringCellValue();
                System.out.print(value + "\t");
            }
            System.out.println();
        }

    }
}
