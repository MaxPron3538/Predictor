package main.model.logic;
import com.itextpdf.text.pdf.PdfReader;
import com.itextpdf.text.pdf.parser.PdfTextExtractor;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;

import java.io.*;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParseBankStatement {
    public static double[][] parseExelFormat(InputStream inputStream) throws IOException {

        return null;
    }

    public static List<List<String>> parsePDFFormat(InputStream inputStream,String fileName) throws IOException {
        File contentFile = new File("src/main/resources/BankStatements/"+fileName);
        OutputStream outputStream = new FileOutputStream(contentFile.getAbsolutePath());
        outputStream.write(inputStream.readAllBytes());
        List<List<String>> bankStatement = new ArrayList<>();

        PDDocument document = PDDocument.load(contentFile);
        PDFTextStripper stripper = new PDFTextStripper();
        int numOfPages = document.getNumberOfPages();

        for(int i = 0;i < numOfPages;i++){
            stripper.setStartPage(i);
            stripper.setEndPage(i+1);
            String allContent = stripper.getText(document);
            String content = allContent.substring(allContent.lastIndexOf("Balance")+"Balance\n".length()+1);

            if(i == numOfPages-1){
                String regex = "(\\D+\\s){4,6}\\D";
                List<String> partsOfContent = Arrays.asList(content.split(regex));
                content = partsOfContent.get(0);
            }
            List<String> stElements = new LinkedList<>(Arrays.asList(content.split("\n")));

            while (!stElements.isEmpty()) {
                List<String> readyToSaveTransaction = new ArrayList<>();
                String lastStr = stElements.stream().filter(s -> s.contains("UAH")).findFirst().get();
                int lastIndex = stElements.indexOf(lastStr);
                List<String> transaction = new ArrayList<>(stElements.subList(0,lastIndex+1));
                stElements.subList(0,lastIndex+1).clear();

                List<String> desc = transaction.stream().filter(s -> !s.contains("UAH")
                        && !s.trim().matches("(\\d{2}\\.){2}\\d{4}|(\\d{2}\\:){2}\\d{2}")).map(String::trim).collect(Collectors.toList());
                String description = String.join(" ", desc);

                List<String> trElements = Arrays.asList(transaction.get(transaction.size()-1).split("\\s"));
                String partDesc = trElements.stream().filter(s -> s.matches("\\D+[^UAH][^—]|\\+\\d+|\\D\\.|ua")).map(s -> s.concat(" ")).collect(Collectors.joining());
                trElements = trElements.stream().filter(s -> !s.matches("\\D+[^UAH][^—]|\\+\\d+|\\D\\.|ua")).collect(Collectors.toList());
                description = description.concat(" " + partDesc).trim();

                readyToSaveTransaction.add(transaction.get(0));
                readyToSaveTransaction.add(transaction.get(1));
                readyToSaveTransaction.add(description);
                readyToSaveTransaction.addAll(trElements);
                bankStatement.add(readyToSaveTransaction);
            }
        }
        outputStream.close();
        document.close();

        if(contentFile.delete()){
            System.out.println("File "+fileName +" deleted!");
        }
        return bankStatement;
    }

    public static void printStatement(InputStream inputStream,String fileName) throws IOException {
         List<List<String>> statement = parsePDFFormat(inputStream,fileName);

         for(List<String> transactions : statement){
             for (String elTransaction : transactions){
                 System.out.print(elTransaction + "|");
             }
             System.out.println("----------------------------------------------------------------------------");
         }
    }

    public static double[][] parseCSVFormat(InputStream inputStream){

        return null;
    }
}
