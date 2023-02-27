package main.controller;

import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.GetMapping;

@Controller
public class LoadFileController {

    @GetMapping("/addExtract")
    public String getFormForExtract(){
        return "indexExtract";
    }
}
