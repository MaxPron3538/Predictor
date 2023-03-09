package main.model.logic;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.Arrays;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class ParseBankStatement {
    public static double[][] parseExelFormat(InputStream inputStream) throws IOException {

        return null;
    }

    public static double[][] parsePDFFormat(InputStream inputStream,String fileName) throws IOException {
        File contentFile = new File("src/main/resources/BankStatements/"+fileName);
        OutputStream outputStream = new FileOutputStream(contentFile.getAbsolutePath());
        outputStream.write(inputStream.readAllBytes());

        PDDocument document = PDDocument.load(contentFile);
        PDFTextStripper stripper = new PDFTextStripper();

        String regex = "(\\D+\\s){4,6}\\D";
        Pattern pattern = Pattern.compile(regex);

        String allContent = stripper.getText(document);
        String content = allContent.substring(allContent.lastIndexOf("Balance")+"Balance\n".length()+1);
        String[] arr = content.split(regex);
        System.out.println(arr[0]);
        //content = content.substring(0,content.indexOf(matcher.start()));
        //List<String> bankStatement = Arrays.asList(content.split("\n")).stream().filter(s -> s.matches());
        outputStream.close();
        document.close();


        if(contentFile.delete()){
            System.out.println("File "+fileName +" deleted!");
        }
        return null;
    }

    public static double[][] parseCSVFormat(InputStream inputStream){

        return null;
    }
}
