package main.model.logic;
import org.apache.pdfbox.pdmodel.PDDocument;
import org.apache.pdfbox.pdmodel.PDPage;
import org.apache.pdfbox.text.PDFTextStripper;
import org.apache.poi.hssf.usermodel.HSSFSheet;
import org.apache.poi.hssf.usermodel.HSSFWorkbook;
import org.apache.poi.openxml4j.exceptions.InvalidFormatException;
import org.apache.poi.ss.usermodel.*;
import org.apache.poi.xssf.usermodel.XSSFSheet;
import org.apache.poi.xssf.usermodel.XSSFWorkbook;

import java.io.*;
import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;
import java.util.stream.Collectors;

public class ParseBankStatement {
    private static String expForUALangStart = "операції";
    private static String expForEngLangStart = "Balance";
    private static String expForUALangEnd = "Заступник голови Правління";
    private static String expForEngLangEnd = "Deputy Chairman of the Board";


    public static double[][] parseExelFormat(InputStream inputStream,String fileName) throws IOException{
        File contentFile = new File("BankStatements" + fileName);
        OutputStream outputStream = new FileOutputStream(contentFile.getAbsolutePath());
        outputStream.write(inputStream.readAllBytes());
        FileInputStream fis = new FileInputStream(contentFile);
        String regex = "(\\d{2}\\.){2}\\d{4}\\s\\d{2}\\:\\d{2}\\:\\d{2}";
        Pattern pattern = Pattern.compile(regex);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        for (Row row : sheet) {
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext()) {
                Cell cell = cellIterator.next();
                switch (cell.getCellType()) {
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        Matcher matcher = pattern.matcher(cell.getStringCellValue());
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                }
            }
            System.out.println("");
        }

        return null;
    }

    public static String toDeterminateLangForStart(PDFTextStripper stripper,PDDocument document) throws IOException {
        String text = stripper.getText(document);

        if(text.contains(expForUALangStart)){
            return expForUALangStart;
        }
        if(text.contains(expForEngLangStart)){
            return expForEngLangStart;
        }
        return null;
    }

    public static String toDeterminateLangForEnd(PDFTextStripper stripper,PDDocument document) throws IOException {
        String text = stripper.getText(document);

        if(text.contains(expForUALangStart)){
            return expForUALangEnd;
        }
        if(text.contains(expForEngLangStart)){
            return expForEngLangEnd;
        }
        return null;
    }

    public static List<List<String>> parsePDFFormat(InputStream inputStream,String fileName) throws IOException {
        File contentFile = new File("BankStatements" + fileName);
        OutputStream outputStream = new FileOutputStream(contentFile.getAbsolutePath());
        outputStream.write(inputStream.readAllBytes());
        List<List<String>> bankStatement = new ArrayList<>();

        PDDocument document = PDDocument.load(contentFile);
        PDFTextStripper stripper = new PDFTextStripper();
        int numOfPages = document.getNumberOfPages();

        stripper.setStartPage(0);
        stripper.setEndPage(1);
        String expForStart = toDeterminateLangForStart(stripper,document);
        String expForEnd = toDeterminateLangForEnd(stripper,document);

        stripper.setStartPage(numOfPages);
        stripper.setEndPage(numOfPages+1);
        String text = stripper.getText(document);

        if(!text.contains(expForStart)){
            numOfPages-=1;
        }
        for(int i = 0;i < numOfPages;i++){
            String balance="";
            stripper.setStartPage(i);
            stripper.setEndPage(i+1);
            String allContent = stripper.getText(document);
            String content = allContent.substring(allContent.lastIndexOf(expForStart)+(expForStart+"\n").length()+1);

            if(i == numOfPages-1){
                List<String> partsOfContent = Arrays.asList(content.split(expForEnd));
                content = partsOfContent.get(0);
            }
            List<String> stElements = new LinkedList<>(Arrays.asList(content.split("\n"))).stream().filter(s -> !s.equals("\0")).collect(Collectors.toList());

            while (!stElements.isEmpty()) {
                List<String> readyToSaveTransaction = new ArrayList<>();
                String lastStr = stElements.stream().filter(s -> s.contains("UAH")).findFirst().get();
                int lastIndex = stElements.indexOf(lastStr);
                List<String> transaction = new ArrayList<>(stElements.subList(0, lastIndex + 1));
                stElements.subList(0, lastIndex + 1).clear();

                List<String> desc = transaction.stream().filter(s -> !s.contains("UAH")
                        && !s.trim().matches("(\\d{2}\\.){2}\\d{4}|(\\d{2}\\:){2}\\d{2}")).map(String::trim).collect(Collectors.toList());
                String description = String.join(" ", desc);

                List<String> trElements = Arrays.asList(transaction.get(transaction.size() - 1)
                        .substring(0,transaction.get(transaction.size()-1).indexOf("—")+1).split("\\s"));

                String partDesc = trElements.stream().filter(s -> s.matches("\\D+[^—]|\\+\\d+|\\D\\.|ua|\\d{2,3}|\\D+\\d+|\\D\\d\\D|\\d{6}\\*{4}\\d{4}"))
                        .filter(s -> !s.contains("UAH")).map(s -> s.concat(" ")).collect(Collectors.joining());
                trElements = trElements.stream().filter(s -> !partDesc.contains(s)).collect(Collectors.toList());
                description = description.concat(" " + partDesc).trim();

                List<String> lastElemTr = new LinkedList<>(Arrays.asList(transaction.get(transaction.size()-1)
                        .substring(transaction.get(transaction.size()-1).indexOf("—")+1).trim().split("\\s")));

                if(lastElemTr.size()> 3){
                    balance = lastElemTr.subList(2,lastElemTr.size()).stream().collect(Collectors.joining());
                    lastElemTr.subList(2,lastElemTr.size()-1).clear();
                }
                readyToSaveTransaction.add(transaction.get(0).trim());
                readyToSaveTransaction.add(transaction.get(1).trim());

                if (!description.equals("")) {
                    readyToSaveTransaction.add(description);
                }
                trElements.addAll(lastElemTr);
                readyToSaveTransaction.addAll(trElements);

                if(!balance.equals("")){
                   readyToSaveTransaction.add(balance);
                }
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

    public static void print(InputStream inputStream,String filename) throws IOException {
        List<List<String>> bankStatement = ParseBankStatement.parsePDFFormat(inputStream,filename);

        for(List<String> transaction : bankStatement){
            for(String elOfTr : transaction){
                System.out.print(elOfTr+"|");
            }
            System.out.println();
        }
    }

    public static List<List<String>> parseCSVFormat(InputStream inputStream) throws UnsupportedEncodingException {
        BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(inputStream, "UTF-8"));
        List<String> bankStatement = bufferedReader.lines().collect(Collectors.toList());
        bankStatement.forEach(System.out::println);
        return null;
    }
}
