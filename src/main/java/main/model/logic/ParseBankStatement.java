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
    public static double[][] parseExelFormat(InputStream inputStream,String fileName) throws IOException{
        File contentFile = new File("BankStatements" + fileName);
        OutputStream outputStream = new FileOutputStream(contentFile.getAbsolutePath());
        outputStream.write(inputStream.readAllBytes());
        FileInputStream fis = new FileInputStream(contentFile);

        XSSFWorkbook workbook = new XSSFWorkbook(fis);
        XSSFSheet sheet = workbook.getSheetAt(0);

        Iterator<Row> rowIterator = sheet.iterator();
        while (rowIterator.hasNext())
        {
            Row row = rowIterator.next();
            Iterator<Cell> cellIterator = row.cellIterator();

            while (cellIterator.hasNext())
            {
                Cell cell = cellIterator.next();
                switch (cell.getCellType())
                {
                    case NUMERIC:
                        System.out.print(cell.getNumericCellValue() + "\t");
                        break;
                    case STRING:
                        System.out.print(cell.getStringCellValue() + "\t");
                        break;
                }
            }
            System.out.println("");
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
        stripper.setStartPage(numOfPages);
        stripper.setEndPage(numOfPages+1);
        String text = stripper.getText(document);

        if(!text.contains("Balance")){
            numOfPages-=1;
        }
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
                List<String> transaction = new ArrayList<>(stElements.subList(0, lastIndex + 1));
                stElements.subList(0, lastIndex + 1).clear();

                List<String> desc = transaction.stream().filter(s -> !s.contains("UAH")
                        && !s.trim().matches("(\\d{2}\\.){2}\\d{4}|(\\d{2}\\:){2}\\d{2}")).map(String::trim).collect(Collectors.toList());
                String description = String.join(" ", desc);

                List<String> trElements = Arrays.asList(transaction.get(transaction.size() - 1).split("\\s"));
                String partDesc = trElements.stream().filter(s -> s.matches("\\D+[^—]|\\+\\d+|\\D\\.|ua|\\d{1,3}|\\D+\\d+|\\D\\d\\D|\\d{6}\\*{4}\\d{4}"))
                        .filter(s -> !s.contains("UAH")).map(s -> s.concat(" ")).collect(Collectors.joining());

                trElements = trElements.stream().filter(s -> !partDesc.contains(s)).collect(Collectors.toList());
                description = description.concat(" " + partDesc).trim();

                readyToSaveTransaction.add(transaction.get(0).trim());
                readyToSaveTransaction.add(transaction.get(1).trim());

                if (!description.equals("")) {
                    readyToSaveTransaction.add(description);
                }
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