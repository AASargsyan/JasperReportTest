package ru.mmtr.test.reports;

import net.sf.jasperreports.engine.*;
import net.sf.jasperreports.engine.data.JRBeanCollectionDataSource;
import net.sf.jasperreports.engine.design.JasperDesign;
import net.sf.jasperreports.engine.export.JRGraphics2DExporter;
import net.sf.jasperreports.engine.export.JRGraphics2DExporterParameter;
import net.sf.jasperreports.engine.export.JRPdfExporter;
import net.sf.jasperreports.engine.export.JRPrintServiceExporterParameter;
import net.sf.jasperreports.engine.xml.JRXmlLoader;
import net.sf.jasperreports.export.*;

import javax.imageio.ImageIO;
import javax.print.attribute.HashPrintRequestAttributeSet;
import javax.print.attribute.PrintRequestAttributeSet;
import javax.print.attribute.ResolutionSyntax;
import javax.print.attribute.standard.PrinterResolution;
import java.awt.*;
import java.awt.image.BufferedImage;
import java.io.*;
import java.util.HashMap;
import java.util.LinkedList;
import java.util.Map;

public class Test {
    private String REPORT_pdf     = "\\report.pdf";
    private String REPORT_pattern = "D:\\projects\\java\\spring\\JasperReportTest\\src\\main\\resources\\report1.jrxml";

    public void create() throws JRException, IOException {

        Map<String, Object> p = new HashMap<>();
        p.put("code", "MyCode");
        InputStream employeeReportStream
                = getClass().getClassLoader().getResourceAsStream("report1.jrxml");
        JasperReport jasperReport
                = JasperCompileManager.compileReport(employeeReportStream);

        JasperPrint jasperPrint = JasperFillManager.fillReport(jasperReport, p);

        JRPdfExporter exporter = new JRPdfExporter();

        exporter.setExporterInput(new SimpleExporterInput(jasperPrint));
        exporter.setExporterOutput(
                new SimpleOutputStreamExporterOutput("d:\\" +  REPORT_pdf));

        SimplePdfReportConfiguration reportConfig
                = new SimplePdfReportConfiguration();
        reportConfig.setForceLineBreakPolicy(false);

        SimplePdfExporterConfiguration exportConfig
                = new SimplePdfExporterConfiguration();
        exportConfig.setMetadataAuthor("baeldung");
        exportConfig.setEncrypted(true);
        exportConfig.setAllowedPermissionsHint("PRINTING");

        exporter.setConfiguration(reportConfig);
        exporter.setConfiguration(exportConfig);

        exporter.exportReport();

//        final String extension = "jpg";
//        final float zoom = 1f;
//        String fileName = "report";
////one image for every page in my report
//        int pages = jasperPrint.getPages().size();
//        for (int i = 0; i < pages; i++) {
//            try(OutputStream out = new FileOutputStream(fileName + "_p" + (i+1) +  "." + extension)){
//                BufferedImage image = (BufferedImage) JasperPrintManager.printPageToImage(jasperPrint, i,zoom);
//                ImageIO.write(image, extension, out); //write image to file
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
//        }

    }

    public static void main(String[] args)
    {
        System.out.println("Начало генерации отчёта");
        try {
            new Test().create();
            System.out.println("Генерация отчёта завершена");
        } catch (Exception e) {
            e.printStackTrace();
            System.err.println("Exception : " + e);
        }
    }
}
