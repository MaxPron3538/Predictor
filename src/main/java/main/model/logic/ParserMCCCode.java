package main.model.logic;

import main.model.entities.TypeBusiness;
import main.model.repositories.TypeBusinessRepository;
import org.jsoup.Jsoup;
import org.jsoup.nodes.Document;
import org.jsoup.select.Elements;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.io.IOException;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class ParserMCCCode {

    private String indexUrl = "https://stripe.com/docs/issuing/categories";

    @Autowired
    TypeBusinessRepository repository;

    public void parseHTMLTableMCCCode() {
        try {
            Document doc = Jsoup.connect(indexUrl).get();
            Elements elem = doc.select("tbody").select("tr");
            List<String> mccList = elem.eachText();

            List<String> mccNameList = mccList.stream().map(s -> s.substring(0,s.lastIndexOf(" ")))
                    .map(s -> s.substring(0,s.lastIndexOf(" "))).collect(Collectors.toList());
            mccList = mccList.stream().map(s -> s.replaceFirst("\\D+\\s","")).collect(Collectors.toList());

            for (int i = 0;i < mccList.size();i++){
                TypeBusiness typeBusiness = new TypeBusiness(mccNameList.get(i),Integer.parseInt(mccList.get(i)));
                repository.save(typeBusiness);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    }
}
