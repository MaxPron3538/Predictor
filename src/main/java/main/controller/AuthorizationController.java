package main.controller;
import main.model.Account;
import main.model.AccountRepository;
import main.model.StatusCode;
import main.model.ValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("account")
public class AuthorizationController {

    @Autowired
    AccountRepository repository;

    @ModelAttribute("account")
    public Account createAccount() {
        return new Account();
    }

    @GetMapping("/")
    public ModelAndView signIn(@ModelAttribute("account") Account account) {
        ModelAndView modelAndView = new ModelAndView();

        if(account.getStatusCode() == StatusCode.SIGN_OUT){
            modelAndView.setViewName("indexSignInExist");
            return modelAndView;
        }
        if(account.getName() != null && account.getSurname() != null){
            modelAndView.setViewName("redirect:/products/");
            return modelAndView;
        }
        modelAndView.addObject("account", new Account());
        modelAndView.setViewName("indexSignIn");
        return modelAndView;
    }

    @GetMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("account") Account account) {
        ModelAndView modelAndView = new ModelAndView();

        if(account.getName() != null && account.getSurname() != null){
            modelAndView.setViewName("redirect:/products/");
            return  modelAndView;
        }
        modelAndView.addObject("account", new Account());
        modelAndView.setViewName("indexSignUp");
        return modelAndView;
    }

    @PutMapping("/signIn")
    public String signInAccount(Account account, RedirectAttributes attributes, Model model){
        List<String> listEmails = repository.findAll().stream().map(Account::getEmail).collect(Collectors.toList());
        List<String> listPasswords = repository.findAll().stream().map(Account::getPassword).collect(Collectors.toList());

        if(listEmails.contains(account.getEmail()) && listPasswords.contains(account.getPassword())){
            Account signInAccount = repository.findById(account.getEmail().hashCode()*account.getPassword().hashCode()).get();
            signInAccount.setStatusCode(StatusCode.Ok);
            attributes.addFlashAttribute("account",signInAccount);
            return "redirect:/products/";
        }
        account.setPassword("");
        model.addAttribute("account",account);
        model.addAttribute("status",StatusCode.NOT_EXIST);

        if(account.getStatusCode()==StatusCode.SIGN_OUT){
            return "indexSignInExist";
        }
        return "indexSignIn";
    }


    @PostMapping("/signUp")
    public String createAccount(Account account,RedirectAttributes attributes,Model model){
        List<String> listEmails = repository.findAll().stream().map(Account::getEmail).collect(Collectors.toList());

        if(listEmails.contains(account.getEmail())){
            model.addAttribute("status",StatusCode.ALREADY_EXIST);
        }
        else if(!account.getName().isEmpty() && !account.getSurname().isEmpty() && !account.getEmail().isEmpty() && !account.getPassword().isEmpty()){
            int id = Math.abs(account.getEmail().hashCode()*account.getPassword().hashCode());

            if(!repository.existsById(id)){
                if (ValidationData.validateEmail(account.getEmail()) && ValidationData.validatePassword(account.getPassword())) {
                    account.setId(id);
                    repository.save(account);
                    account.setStatusCode(StatusCode.Ok);
                    attributes.addFlashAttribute("account", account);
                    return "redirect:/products/";
                }
                account.setPassword("");
                model.addAttribute("account", account);
                model.addAttribute("status",StatusCode.BAD);
                return "indexSignUp";
            }
            model.addAttribute("status",StatusCode.ALREADY_EXIST);
        }
        return "indexSignUp";
    }

    @PostMapping("/signOut")
    public ModelAndView signOut(Account account){
        ModelAndView modelAndView = new ModelAndView();
        account.setStatusCode(StatusCode.SIGN_OUT);
        modelAndView.addObject(account);
        modelAndView.setViewName("redirect:/");
        return modelAndView;
    }
}
