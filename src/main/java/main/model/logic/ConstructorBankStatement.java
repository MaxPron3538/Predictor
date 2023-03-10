package main.model.logic;

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

    public List<Transaction> setDataInRepository(List<List<String>> bankStatement){
        List<Transaction> listTransactions = new ArrayList<>();

        for (List<String> strTransaction : bankStatement){
            Transaction saveTransaction = new Transaction();
            List<String> strDate = Arrays.asList(strTransaction.get(0).split("\\."));
            List<String> strTime = Arrays.asList(strTransaction.get(1).split("\\:"));
            LocalDate date = LocalDate.of(Integer.parseInt(strDate.get(2)),Integer.parseInt(strDate.get(1)),Integer.parseInt(strDate.get(0)));
            LocalTime time = LocalTime.of(Integer.parseInt(strTime.get(0)),Integer.parseInt(strTime.get(1)),Integer.parseInt(strTime.get(2)));
            saveTransaction.setDate(date);
            saveTransaction.setTime(time);
            saveTransaction.setDescription(strTransaction.get(2));
            saveTransaction.setMcc(Integer.parseInt(strTransaction.get(3)));
            saveTransaction.setAmount(Double.parseDouble(strTransaction.get(4)));
            saveTransaction.setCurrency(strTransaction.get(6));
            saveTransaction.setCommission(strTransaction.get(8));
            saveTransaction.setCashBack(Double.parseDouble(strTransaction.get(9)));
            saveTransaction.setBalance(Double.parseDouble(strTransaction.get(10)));
            listTransactions.add(saveTransaction);
        }
        return listTransactions;
    }
}
