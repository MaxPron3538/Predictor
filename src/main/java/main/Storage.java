package main;

import main.model.Product;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.stream.Collectors;

@Component
public class Storage implements IStorage{
    private static Map<Integer, Product> products = new HashMap<>();

    public void addProduct(Product product){
        int id = product.getId();
        synchronized (products) {
            products.put(id, product);
        }
    }

    public boolean updateProduct(Product product,int id){
        if(products.containsKey(id)){
            synchronized (products) {
                products.replace(id, product);
            }
            return true;
        }
        return false;
    }

    public Product getProduct(int id){
        if (products.containsKey(id)) {
            return products.get(id);
        }
        return null;
    }

    public List<Product> getAllProducts(){
        return new ArrayList<>(products.values());
    }

    public boolean deleteProduct(int id){
        if(products.containsKey(id)){
            synchronized (products) {
                products.remove(id);
            }
            return true;
        }
        return false;
    }

    public void deleteAllProducts(){
        Set<Integer> setProducts = products.keySet();
        for (int id : setProducts) {
            deleteProduct(id);
        }
    }
}