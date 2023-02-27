package main.controller;

import main.model.*;
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
    private ProductRepository repositoryProducts;

    @GetMapping("/products/")
    public String list(@ModelAttribute Account account,Model model){
        if(account.getStatusCode() == StatusCode.Ok){
            Account existAccount = repositoryAccounts.findById(account.getId()).get();
            model.addAttribute("products", existAccount.getProductList());
            return "index";
        }
        return "redirect:/";
    }

    @PostMapping("/products/")
    public String add(@ModelAttribute Account account,Product product,Model model){
        int id = Math.abs(product.getProductName().hashCode()*product.getCategory().hashCode());
        product.setProductId(id);
        product.setAccount(account);
        repositoryProducts.save(product);
        model.addAttribute("products", repositoryAccounts.findById(account.getId()).get().getProductList());
        return "index";
    }

    @GetMapping("/products/{productId}")
    public String get(@PathVariable("productId") int id,@ModelAttribute Account account,Model model){
        Optional<Product> optional = repositoryProducts.findAll().stream().
                filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();
        optional.ifPresent(product -> model.addAttribute("products", product));
        return "index";
    }

    @PutMapping("/products/{productId}")
    public String update(@PathVariable("productId") int id,@ModelAttribute Account account,Product product){
        if(repositoryProducts.existsById(id)){
            Optional<Product> optional = repositoryProducts.findAll().stream().
                    filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();

            if(optional.isPresent()){
                product.setAccount(account);
                repositoryProducts.save(product);
            }
        }
        return "redirect:/products/";
    }

    @DeleteMapping("/products/{productId}")
    public String delete(@PathVariable("productId") int id,@ModelAttribute Account account){
        if(repositoryProducts.existsById(id)){
            Optional<Product> optional = repositoryProducts.findAll().stream().
                    filter(s -> s.getProductId()==id).filter(s -> s.getAccount().getId() == account.getId()).findFirst();
            optional.ifPresent(product -> repositoryProducts.deleteById(product.getProductId()));
        }
        return "redirect:/products/";
    }

    @DeleteMapping("/products/")
    public String deleteList(@ModelAttribute Account account){
        List<Product> productList = repositoryProducts.findAll().stream().filter(s -> s.getAccount().getId() == account.getId()).collect(Collectors.toList());
        productList.forEach(s -> repositoryProducts.deleteById(s.getProductId()));
        return "index";
    }

}

