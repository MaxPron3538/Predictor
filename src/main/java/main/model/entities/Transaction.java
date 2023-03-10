package main.model.entities;

import javax.persistence.*;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.Date;

@Entity
public class Transaction {

    @Id
    private int productId;

    @Column(name = "local_date", columnDefinition = "DATE")
    private LocalDate date;

    @Column(name = "local_time", columnDefinition = "TIME")
    private LocalTime time;

    private String description;
    private int mcc;
    private double amount;
    private String currency;
    private String commission;
    private double cashBack;
    private double balance;

    @ManyToOne
    @JoinColumn(name ="account_id",nullable = false)
    private Account account;

    public int getProductId() {
        return productId;
    }

    public void setProductId(int productId) {
        this.productId = productId;
    }

    public LocalDate getDate() {
        return date;
    }

    public void setDate(LocalDate date) {
        this.date = date;
    }

    public LocalTime getTime() {
        return time;
    }

    public void setTime(LocalTime time) {
        this.time = time;
    }

    public double getAmount() {
        return amount;
    }

    public void setAmount(double amount) {
        this.amount = amount;
    }

    public double getBalance() {
        return balance;
    }

    public void setBalance(double balance) {
        this.balance = balance;
    }

    public double getCashBack() {
        return cashBack;
    }

    public void setCashBack(double cashBack) {
        this.cashBack = cashBack;
    }

    public String getCommission() {
        return commission;
    }

    public void setCommission(String commission) {
        this.commission = commission;
    }

    public String getCurrency() {
        return currency;
    }

    public void setCurrency(String currency) {
        this.currency = currency;
    }

    public int getMcc() {
        return mcc;
    }

    public void setMcc(int mcc) {
        this.mcc = mcc;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Account getAccount() {
        return account;
    }

    public void setAccount(Account account) {
        this.account = account;
    }
}
