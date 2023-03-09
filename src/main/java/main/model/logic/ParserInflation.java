package main.model.logic;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.Arrays;
import java.util.Date;
import java.util.List;
import java.util.stream.Collectors;

public class ParserInflation {

    private static String indexUrl = "https://index.minfin.com.ua/economy/index/inflation/";
    private static final String regex = "\\d{4}[\\s\\d+\\,\\d]+";
    private static double matrixDoubleIndex[][];
    private static int count = 0;

    public static void InitializeMatrix(){
        int sizeMonth = 12;
        Date date = new Date();
        int sizeYear = date.getYear() - 99;
        matrixDoubleIndex = new double[sizeYear][sizeMonth];
    }

    public static double[][] parseHTMLTableInflation() throws IOException {
        Document doc = Jsoup.connect(indexUrl).get();
        Elements elem = doc.select("table").select("tr");
        List<String> tableOfIndex = elem.next().eachText().stream().filter(s -> s.matches(regex)).map(s -> s.replaceFirst("\\d+",""))
                .map(s -> s.replaceFirst(s.substring(s.lastIndexOf(" ")),"")).map(s -> s.replaceAll(",",".")).collect(Collectors.toList());
        InitializeMatrix();

        for(String strIndexes : tableOfIndex){
            double[] arrIndexes = Arrays.stream(strIndexes.trim().split("\\s")).mapToDouble(Double::parseDouble).toArray();
            matrixDoubleIndex[count] = arrIndexes;
            count++;
        }
        return matrixDoubleIndex;
    }
}
