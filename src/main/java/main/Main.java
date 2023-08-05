package main;

import main.model.logic.ParserMCCCode;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.Bean;
import org.springframework.web.filter.HiddenHttpMethodFilter;

import java.io.IOException;

@SpringBootApplication
public class Main {

    @Bean
    HiddenHttpMethodFilter hiddenHttpMethodFilter() {
        return new HiddenHttpMethodFilter();
    }

    public static void main(String[] args) throws IOException {
        SpringApplication.run(Main.class,args);
    }
}
