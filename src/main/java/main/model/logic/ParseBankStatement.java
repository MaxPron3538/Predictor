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
import java.util.stream.Collectors;

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
        System.out.println(document.getNumberOfPages());

        String regex = "(\\D+\\s){4,6}\\D";
        Pattern pattern = Pattern.compile(regex);

        String allContent = stripper.getText(document);
        String content = allContent.substring(allContent.lastIndexOf("Balance")+"Balance\n".length()+1);
        List<String> partsOfContent = Arrays.asList(content.split(regex));
        System.out.println(partsOfContent.get(0));
        /*
        List<String> elements = Arrays.asList(partsOfContent.get(0).split("\\s")).stream().filter(s -> !s.equals("")).collect(Collectors.toList());
        String elem1 = elements.stream().filter(s -> s.matches("\\D+[^UAH][^—]")).map(s -> s.concat(" ")).collect(Collectors.joining());
        elements = elements.stream().filter(s -> !s.matches("\\D+[^UAH]")).collect(Collectors.toList());
        elements.add(2,elem1);

         */
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
