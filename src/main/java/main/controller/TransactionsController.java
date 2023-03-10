package main.controller;

import main.model.*;
import main.model.entities.Account;
import main.model.entities.Transaction;
import main.model.repositories.AccountRepository;
import main.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Controller
@SessionAttributes("account")
public class TransactionsController {

    @Autowired
    private AccountRepository repositoryAccounts;
    @Autowired
    private TransactionRepository repositoryTransactions;

    @GetMapping("/transactions/")
    public String list(@ModelAttribute Account account, Model model){
        if(account.getStatusCode() == StatusCode.Ok){
            Account existAccount = repositoryAccounts.findById(account.getId()).get();
            model.addAttribute("transactions", existAccount.getProductList());
            return "index";
        }
        return "redirect:/";
    }

    @PostMapping("/transactions/")
    public String add(@ModelAttribute Account account, Transaction transaction, Model model){
        int id = Math.abs(transaction.getDescription().hashCode()*account.getEmail().hashCode());
        transaction.setProductId(id);
        transaction.setAccount(account);
        repositoryTransactions.save(transaction);
        model.addAttribute("transactions", repositoryAccounts.findById(account.getId()).get().getProductList());
        return "index";
    }

    @GetMapping("/transactions/{transactionId}")
    public String get(@PathVariable("transactionId") int id,@ModelAttribute Account account,Model model){
        Optional<Transaction> optional = repositoryTransactions.findAll().stream().
                filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();
        optional.ifPresent(transaction -> model.addAttribute("transactions", transaction));
        return "index";
    }

    @PutMapping("/transactions/{transactionId}")
    public String update(@PathVariable("productId") int id, @ModelAttribute Account account, Transaction transaction){
        if(repositoryTransactions.existsById(id)){
            Optional<Transaction> optional = repositoryTransactions.findAll().stream().
                    filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();

            if(optional.isPresent()){
                transaction.setAccount(account);
                repositoryTransactions.save(transaction);
            }
        }
        return "redirect:/transactions/";
    }

    @DeleteMapping("/transactions/{transactionId}")
    public String delete(@PathVariable("transactionId") int id,@ModelAttribute Account account){
        if(repositoryTransactions.existsById(id)){
            Optional<Transaction> optional = repositoryTransactions.findAll().stream().
                    filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();
            optional.ifPresent(transaction -> repositoryTransactions.deleteById(transaction.getProductId()));
        }
        return "redirect:/transactions/";
    }

    @DeleteMapping("/transactions/")
    public String deleteList(@ModelAttribute Account account){
        List<Transaction> transactionList = repositoryTransactions.findAll().stream().filter(s -> s.getAccount().getId() == account.getId()).collect(Collectors.toList());
        transactionList.forEach(s -> repositoryTransactions.deleteById(s.getProductId()));
        return "index";
    }

}

