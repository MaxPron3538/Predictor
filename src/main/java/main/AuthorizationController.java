package main;
import main.model.Account;
import main.model.AccountRepository;
import main.model.StatusCode;
import main.model.ValidationData;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.ui.ModelMap;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.HttpServerErrorException;
import org.springframework.web.servlet.ModelAndView;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import javax.servlet.http.HttpServletRequest;
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
        modelAndView.addObject("account", new Account());
        modelAndView.setViewName("indexSignIn");
        return modelAndView;
    }

    @GetMapping("/signUp")
    public ModelAndView signUp(@ModelAttribute("account") Account account) {
        ModelAndView modelAndView = new ModelAndView();
        modelAndView.addObject("account", new Account());
        modelAndView.setViewName("indexSignUp");
        return modelAndView;
    }

    @PutMapping("/signIn")
    public String signInAccount(Account account, RedirectAttributes attributes, Model model){
        List<String> listEmails = repository.findAll().stream().map(Account::getEmail).collect(Collectors.toList());
        List<String> listPasswords = repository.findAll().stream().map(Account::getPassword).collect(Collectors.toList());

        if(listEmails.contains(account.getEmail()) && listPasswords.contains(account.getPassword())){
            account.setStatusCode(StatusCode.Ok);
            attributes.addFlashAttribute("account",account);
            return "redirect:/products/";
        }
        account.setPassword("");
        model.addAttribute("account",account);
        model.addAttribute("status",StatusCode.NOT_EXIST);
        return "indexSignIn";
    }


    @PostMapping("/signUp")
    public String addAccount(Account account,RedirectAttributes attributes,Model model){
        if(!account.getName().isEmpty() && !account.getSurname().isEmpty() && !account.getEmail().isEmpty() && !account.getPassword().isEmpty()){
            if(ValidationData.validateEmail(account.getEmail()) && ValidationData.validatePassword(account.getPassword())){
                repository.save(account);
                account.setStatusCode(StatusCode.Ok);
                attributes.addFlashAttribute("account",account);
                return "redirect:/products/";
            }
            account.setPassword("");
            model.addAttribute("account",account);
        }
        model.addAttribute("status",StatusCode.BAD);
        return "indexSignUp";
    }
}
