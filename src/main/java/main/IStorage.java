package main;

import main.model.Product;

import java.util.List;

public interface IStorage {

    void addProduct(Product product);

    boolean updateProduct(Product product,int id);

    Product getProduct(int id);

    List<Product> getAllProducts();

    boolean deleteProduct(int id);

    void deleteAllProducts();
}
