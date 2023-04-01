package main.model.logic;

import main.model.entities.Account;
import main.model.entities.Transaction;
import main.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;

import javax.persistence.criteria.CriteriaBuilder;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.Date;
import java.util.List;

public class ConstructorBankStatement {

    @Autowired
    TransactionRepository repository;

    public List<Transaction> setDataFromPDFInRepository(List<List<String>> bankStatement, Account account){
        List<Transaction> listTransactions = new ArrayList<>();

        for (List<String> strTransaction : bankStatement){
            Transaction saveTransaction = new Transaction();
            saveTransaction.setAccount(account);
            saveTransaction.setProductId(Math.abs(account.getEmail().hashCode()*strTransaction.get(2).hashCode()*strTransaction.get(0).hashCode()));
            List<String> strDate = Arrays.asList(strTransaction.get(0).split("\\."));
            List<String> strTime = Arrays.asList(strTransaction.get(1).split("\\:"));
            LocalDate date = LocalDate.of(Integer.parseInt(strDate.get(2).trim()),Integer.parseInt(strDate.get(1).trim()),Integer.parseInt(strDate.get(0).trim()));
            LocalTime time = LocalTime.of(Integer.parseInt(strTime.get(0).trim()),Integer.parseInt(strTime.get(1).trim()),Integer.parseInt(strTime.get(2).trim()));
            saveTransaction.setDate(date);
            saveTransaction.setTime(time);

            if(strTransaction.size() < 11){
                saveTransaction.setDescription(strTransaction.get(2).substring(0,strTransaction.get(2).length()-4));
                saveTransaction.setMcc(Integer.parseInt((strTransaction.get(2).substring(strTransaction.get(2).length()-4))));
                saveTransaction.setAmount(Double.parseDouble(strTransaction.get(3).trim()));
                saveTransaction.setCurrency(strTransaction.get(5).trim());
                saveTransaction.setCommission(strTransaction.get(7).trim());
                saveTransaction.setCashBack(strTransaction.get(8).trim());
                saveTransaction.setBalance(Double.parseDouble(strTransaction.get(9).trim()));
            }
            else {
                saveTransaction.setDescription(strTransaction.get(2).trim());
                saveTransaction.setMcc(Integer.parseInt(strTransaction.get(3).trim()));
                saveTransaction.setAmount(Double.parseDouble(strTransaction.get(4).trim()));
                saveTransaction.setCurrency(strTransaction.get(6).trim());
                saveTransaction.setCommission(strTransaction.get(8).trim());
                saveTransaction.setCashBack(strTransaction.get(9).trim());
                saveTransaction.setBalance(Double.parseDouble(strTransaction.get(10).trim()));
            }
            listTransactions.add(saveTransaction);
        }
        return listTransactions;
    }

    public List<Transaction> setDataFromXLSInRepository(List<List<String>> bankStatement, Account account){
        List<Transaction> listTransactions = new ArrayList<>();

        for (List<String> strTransaction : bankStatement){
            Transaction saveTransaction = new Transaction();
            saveTransaction.setAccount(account);
            saveTransaction.setProductId(Math.abs(account.getEmail().hashCode()*strTransaction.get(2).hashCode()*strTransaction.get(0).hashCode()));
            List<String> strDate = Arrays.asList(strTransaction.get(0).split("\\."));
            List<String> strTime = Arrays.asList(strTransaction.get(1).split("\\:"));
            LocalDate date = LocalDate.of(Integer.parseInt(strDate.get(2).trim()),Integer.parseInt(strDate.get(1).trim()),Integer.parseInt(strDate.get(0).trim()));
            LocalTime time = LocalTime.of(Integer.parseInt(strTime.get(0).trim()),Integer.parseInt(strTime.get(1).trim()),Integer.parseInt(strTime.get(2).trim()));
            saveTransaction.setDate(date);
            saveTransaction.setTime(time);
            saveTransaction.setDescription(strTransaction.get(2).trim());
            saveTransaction.setMcc(Integer.parseInt(strTransaction.get(3).trim()));
            saveTransaction.setAmount(Double.parseDouble(strTransaction.get(4).trim()));
            saveTransaction.setCurrency(strTransaction.get(6).trim());
            saveTransaction.setCommission(strTransaction.get(8).trim());
            saveTransaction.setCashBack(strTransaction.get(9).trim());
            saveTransaction.setBalance(Double.parseDouble(strTransaction.get(10).trim()));
            listTransactions.add(saveTransaction);
        }
        return listTransactions;
    }
}
