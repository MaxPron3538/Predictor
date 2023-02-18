package main;
import main.model.Account;
import main.model.AccountRepository;
import main.model.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;

import java.io.IOException;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.stream.Collectors;

@Controller
public class AuthorizationController {

    @Autowired
    AccountRepository repository;

    @GetMapping("/")
    public String signIn(){
        return "indexSignIn";
    }

    @GetMapping("/signUp/")
    public String registration(){
        return "indexSignUp";
    }

    @PutMapping("/signIn")
    public String signInAccount(Account account,Model model){
        List<String> listEmails = repository.findAll().stream().map(Account::getEmail).collect(Collectors.toList());
        List<String> listPasswords = repository.findAll().stream().map(Account::getPassword).collect(Collectors.toList());

        if(listEmails.contains(account.getEmail()) && listPasswords.contains(account.getPassword())){
            return "redirect:/products/";
        }
        model.addAttribute("status",StatusCode.NOTEXIST);
        return "indexSignIn";
    }

    @PostMapping("/signUp")
    public String addAccount(Account account,Model model){
        if(!account.getName().isEmpty() && !account.getSurname().isEmpty() && !account.getEmail().isEmpty() && !account.getPassword().isEmpty()){
            repository.save(account);
            return "redirect:/products/";
        }
        model.addAttribute("status",StatusCode.BAD);
        return "indexSignUp";
    }
}
