package main.runner;

import main.model.logic.ParserInflation;
import main.model.logic.ParserMCCCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

@Component
public class CommandLineRunnerMCC implements CommandLineRunner {

    @Autowired
    ParserMCCCode parserMCCCode;

    @Autowired
    ParserInflation parserInflation;

    @Override
    public void run(String... args){
        parserMCCCode.parseHTMLTableMCCCode();
    }
}
