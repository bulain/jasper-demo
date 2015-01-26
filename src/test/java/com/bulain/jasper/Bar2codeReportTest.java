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

public class Bar2codeReportTest extends BaseTestCase {
    @Autowired
    private DataSource dataSource;

    @Test
    public void testReport() throws IOException, JRException, SQLException {
        ClassPathResource resource = new ClassPathResource("reports/bar2code.jrxml");
        InputStream inputStream = resource.getInputStream();

        JasperReport jasperReport = JasperCompileManager.compileReport(inputStream);

        Connection conn = dataSource.getConnection();
        JasperPrint jasperPrint = JasperUtils.fillReport(jasperReport, new HashMap<String, Object>(), conn);
        JasperExportManager.exportReportToPdfFile(jasperPrint, "target/bar2code.pdf");

        //        {
        //            JFrame frame = new JFrame("Report");
        //            frame.getContentPane().add(new JRViewer(jasperPrint));
        //            frame.pack();
        //            frame.setVisible(true);
        //
        //            while (true) {
        //                try {
        //                    Thread.currentThread().sleep(1000);
        //                } catch (InterruptedException e) {
        //                }
        //            }
        //        }

//        {
//            int zoom = 4;
//            int pageWidth = jasperPrint.getPageWidth();
//            int size = jasperPrint.getPages().size();
//            int pageHeight = jasperPrint.getPageHeight();
//
//            int imageWidth = pageWidth * zoom + 1;
//            int imageHeight = pageHeight * zoom * size + 1;
//
//            BufferedImage awtImage = new BufferedImage(imageWidth, imageHeight, BufferedImage.TYPE_INT_RGB);
//            Graphics2D g2d = (Graphics2D) awtImage.getGraphics();
//
//            JRGraphics2DExporter exporter = new JRGraphics2DExporter();
//            exporter.setParameter(JRExporterParameter.JASPER_PRINT, jasperPrint);
//            exporter.setParameter(JRGraphics2DExporterParameter.GRAPHICS_2D, g2d);
//            exporter.setParameter(JRGraphics2DExporterParameter.ZOOM_RATIO, Float.valueOf(zoom));
//            exporter.setParameter(JRExporterParameter.OFFSET_X, new Integer(1));
//            exporter.setParameter(JRExporterParameter.OFFSET_Y, new Integer(1));
//
//            exporter.exportReport();
//
//            g2d.setColor(Color.black);
//            g2d.setStroke(new BasicStroke(1.0F));
//            g2d.drawRect(0, 0, imageWidth - 1, imageHeight - 1);
//
//            g2d.dispose();
//
////            int[] data = ((DataBufferInt) awtImage.getData().getDataBuffer()).getData();
////            ImageData imageData = new ImageData(awtImage.getWidth(), awtImage.getHeight(), 32, palette);
////            imageData.setPixels(0, 0, data.length, data, 0);
////             new Image(getDisplay(), imageData);
//
//            ImageIO.write(awtImage, "JPEG", new File("target/bar2code.jpg"));
//        }

    }
}
