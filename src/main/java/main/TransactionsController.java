package main;

import main.model.Account;
import main.model.Product;

import main.model.ProductRepository;
import main.model.StatusCode;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import javax.servlet.http.HttpServletRequest;
import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

@Controller
public class TransactionsController {

    @Autowired
    private ProductRepository repository;

    @GetMapping("/products/")
    public String list(@ModelAttribute Account account,Model model){
        if(account.getStatusCode() == StatusCode.Ok){
            model.addAttribute("products",repository.findAll());
            return "index";
        }
        return "redirect:/";
    }

    @PostMapping("/products/")
    public String add(Product product,Model model){
        repository.save(product);
        model.addAttribute("products",repository.findAll());
        return "index";
    }

    @GetMapping("/products/{id}")
    public String get(@PathVariable("id") int id,Model model){
        Optional<Product> productOptional = repository.findById(id);
        productOptional.ifPresent(product -> model.addAttribute("products", product));
        return "index";
    }

    @PutMapping("/products/{id}")
    public String update(Product product,@PathVariable int id){
        if(repository.existsById(id)){
            repository.save(product);
        }
        return "redirect:/products/";
    }

    @DeleteMapping("/products/{id}")
    public String delete(@PathVariable int id){
        if(repository.existsById(id)){
            repository.deleteById(id);
        }
        return "redirect:/products/";
    }

    @DeleteMapping("/products/")
    public String deleteList(){
        repository.deleteAll();
        return "index";
    }

}

