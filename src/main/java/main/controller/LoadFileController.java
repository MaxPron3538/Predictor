package main.controller;
import main.model.StatusCode;
import main.model.entities.Account;
import main.model.logic.ParseBankStatement;
import main.model.repositories.AccountRepository;
import main.model.repositories.ProductRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.multipart.commons.CommonsMultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.awt.image.BufferedImage;
import java.io.*;
import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("account")
public class LoadFileController {

    @Autowired
    private AccountRepository repositoryAccounts;

    @GetMapping("/uploadFile")
    public String getFormForUploadFile(@ModelAttribute Account account){
        if(account.getStatusCode() == StatusCode.Ok){
            return "indexUploadFile";
        }
        return "redirect:/";
    }

    @PostMapping("/uploadFile")
    public String uploadFileAndSave(@RequestParam("file") MultipartFile multipartFile) throws IOException {
        try{
            InputStream initialStream = multipartFile.getInputStream();
           // BufferedReader bufferedReader = new BufferedReader(new InputStreamReader(initialStream, "UTF-8"));
           // List<String> bankStatement = bufferedReader.lines().collect(Collectors.toList());
            //double[][] bankStatementTable;

            ParseBankStatement.printStatement(initialStream,multipartFile.getOriginalFilename());
            /*
            switch (multipartFile.getContentType()){
                case "application/pdf":
                    bankStatementTable = ParseBankStatement.parsePDFFormat(initialStream);
                    break;
                case "application/vnd.ms-excel":
                    bankStatementTable = ParseBankStatement.parseExelFormat(initialStream);
                    break;
                case "text/csv":
                    bankStatementTable = ParseBankStatement.parseCSVFormat(initialStream);
            }

             */
        }catch (IOException ex){
            ex.printStackTrace();
        }
    return "indexUploadFile";
    }

    @PostMapping("/skipUpload")
    public String skipUploadFile(@ModelAttribute Account account, RedirectAttributes attributes){
        account.setStatusCode(StatusCode.Ok);
        attributes.addFlashAttribute("account",account);
        return "redirect:/products/";
    }
}
