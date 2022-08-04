package gok.data_logic;

import com.itextpdf.io.image.ImageData;
import com.itextpdf.io.image.ImageDataFactory;
import com.itextpdf.kernel.pdf.PdfDocument;
import com.itextpdf.kernel.pdf.PdfReader;
import com.itextpdf.kernel.pdf.PdfWriter;
import com.itextpdf.layout.Document;
import com.itextpdf.layout.element.Image;
import com.itextpdf.layout.element.Paragraph;
import com.itextpdf.layout.properties.TextAlignment;
import fr.opensagres.poi.xwpf.converter.pdf.PdfConverter;
import fr.opensagres.poi.xwpf.converter.pdf.PdfOptions;
import org.apache.poi.xwpf.usermodel.XWPFDocument;

import java.io.*;

public class toPdf {
    public static PdfDocument imgToPdf(File file) throws IOException {
        String newPdfPath = file.getParent() + "" + file.getName().split("\\.")[0] + ".pdf";
        PdfDocument pdfDocument = new PdfDocument(new PdfWriter(newPdfPath));
        Document document = new Document(pdfDocument);
        ImageData imageData = ImageDataFactory.create(file.getAbsolutePath());
        document.add(new Image(imageData));
        document.close();
        pdfDocument.close();
        return new PdfDocument(new PdfReader(newPdfPath));
    }

    public static PdfDocument docToPdf(File file) throws IOException {
        String newPdfPath = file.getParent() + "" + file.getName().split("\\.")[0] + ".pdf";
        InputStream in = new FileInputStream(file);
        XWPFDocument document = new XWPFDocument(in);
        PdfOptions options = PdfOptions.create();
        OutputStream out = new FileOutputStream(newPdfPath);
        PdfConverter.getInstance().convert(document, out, options);
        document.close();
        out.close();
        return new PdfDocument(new PdfReader(newPdfPath));
    }

    public static PdfDocument txtToPdf(File file) throws IOException {
        String newPdfPath = file.getParent() + "" + file.getName().split("\\.")[0] + ".pdf";
        PdfDocument pdf = new PdfDocument(new PdfWriter(newPdfPath));
        Document document = new Document(pdf).setTextAlignment(TextAlignment.JUSTIFIED);
        BufferedReader br = new BufferedReader(new FileReader(file));
        String line;
        while ((line = br.readLine()) != null) {
            document.add(new Paragraph(line));
        }
        br.close();
        pdf.close();
        document.close();
        return new PdfDocument(new PdfReader(newPdfPath));
    }

}
