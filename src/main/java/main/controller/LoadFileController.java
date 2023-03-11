package main.controller;
import main.model.StatusCode;
import main.model.entities.Account;
import main.model.entities.Transaction;
import main.model.logic.ConstructorBankStatement;
import main.model.logic.ParseBankStatement;
import main.model.repositories.AccountRepository;
import main.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.io.*;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("account")
public class LoadFileController {

    @Autowired
    private AccountRepository repositoryAccounts;
    @Autowired
    private TransactionRepository repositoryTransactions;

    @GetMapping("/uploadFile")
    public String getFormForUploadFile(@ModelAttribute Account account){
        if(account.getStatusCode() == StatusCode.Ok){
            return "indexUploadFile";
        }
        return "redirect:/";
    }

    @PostMapping("/uploadFile")
    public String uploadFileAndSave(@RequestParam("file") MultipartFile multipartFile,@ModelAttribute Account account) throws IOException {
        try{
            InputStream initialStream = multipartFile.getInputStream();
            List<List<String>> bankStatementTable;

            switch (Objects.requireNonNull(multipartFile.getContentType())){
                case "application/pdf":
                    bankStatementTable = ParseBankStatement.parsePDFFormat(initialStream,multipartFile.getOriginalFilename());
                    ConstructorBankStatement constructor = new ConstructorBankStatement();
                    List<Transaction> transactions = constructor.setDataInRepository(bankStatementTable,account);
                    repositoryTransactions.saveAll(transactions);

                    /*
                    ParseBankStatement.print(initialStream,multipartFile.getOriginalFilename());

                     */
                    break;
                case "application/vnd.ms-excel":
                    ParseBankStatement.parseExelFormat(initialStream,multipartFile.getOriginalFilename());
                    break;
                case "text/csv":
                    bankStatementTable = ParseBankStatement.parseCSVFormat(initialStream);
            }
        }catch (IOException ex){
            ex.printStackTrace();
        }
    return "indexUploadFile";
    }

    @PostMapping("/skipUpload")
    public String skipUploadFile(@ModelAttribute Account account, RedirectAttributes attributes){
        account.setStatusCode(StatusCode.Ok);
        attributes.addFlashAttribute("account",account);
        return "redirect:/transactions/";
    }
}
