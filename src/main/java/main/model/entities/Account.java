package main.model.entities;

import main.model.StatusCode;

import javax.persistence.Entity;
import javax.persistence.Id;
import javax.persistence.OneToMany;
import java.util.Date;
import java.util.List;

@Entity
public class Account {

    @Id
    private int id;
    private String name;
    private String surname;
    private String email;
    private String password;
    private StatusCode statusCode;

    @OneToMany(mappedBy = "account")
    List<Transaction> transactionList;

    public int getId() {
        return id;
    }

    public void setId(int id) {
        this.id = id;
    }

    public String getName() {
        return name;
    }

    public void setName(String name) {
        this.name = name;
    }

    public String getSurname() {
        return surname;
    }

    public void setSurname(String surname) {
        this.surname = surname;
    }

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public StatusCode getStatusCode() {
        return statusCode;
    }

    public void setStatusCode(StatusCode statusCode) {
        this.statusCode = statusCode;
    }

    public List<Transaction> getProductList() {
        return transactionList;
    }

    public void setProductList(List<Transaction> transactionList) {
        this.transactionList = transactionList;
    }

}
