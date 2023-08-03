package main.controller;

import main.model.StatusCode;
import main.model.entities.Account;
import main.model.entities.Transaction;
import main.model.logic.PredictorCardBalance;
import main.model.repositories.AccountRepository;
import main.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.ArrayList;
import java.util.Collections;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@RestController
@SessionAttributes("account")
public class TransactionsRestController {

    @Autowired
    private AccountRepository repositoryAccounts;

    @Autowired
    private TransactionRepository repositoryTransactions;

    @Autowired
    private PredictorCardBalance predictorCardBalance;

    @GetMapping("/transactions/dates/{predictedPeriod}")
    public ResponseEntity<?> listDates(@ModelAttribute Account account,@PathVariable int predictedPeriod){
        if(account.getStatusCode() == StatusCode.Ok){
            List<LocalDate> listDates = repositoryTransactions.findAll().stream().map(Transaction::getDate).collect(Collectors.toList());

            if(!listDates.isEmpty()) {
                Collections.reverse(listDates);
                int periodInUnits = calculatePeriod(listDates,predictedPeriod);
                int predictedDay = predictedPeriod/periodInUnits;
                LocalDate date = listDates.get(listDates.size()-1);

                for(int i = 0;i < periodInUnits;i++){
                    LocalDate newDate = date.plusDays(predictedDay);
                    listDates.add(newDate);
                }
                return new ResponseEntity<>(listDates, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/transactions/oldBalances")
    public ResponseEntity<?> listOldBalances(@ModelAttribute Account account){
        if(account.getStatusCode() == StatusCode.Ok){
            List<Double> listBalances = repositoryTransactions.findAll().stream().map(Transaction::getBalance).collect(Collectors.toList());

            if(!listBalances.isEmpty()) {
                Collections.reverse(listBalances);
                return new ResponseEntity<>(listBalances, HttpStatus.OK);
            }
        }
        return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    @GetMapping("/transactions/predictedBalances/{predictedPeriod}")
    public ResponseEntity<?> listProjectedBalances(@ModelAttribute Account account,@PathVariable int predictedPeriod){
        if(account.getStatusCode() == StatusCode.Ok){
            List<Double> listBalances = repositoryTransactions.findAll().stream().map(Transaction::getBalance).collect(Collectors.toList());

            if(!listBalances.isEmpty()) {
                List<LocalDate> listDates = repositoryTransactions.findAll().stream().map(Transaction::getDate).collect(Collectors.toList());
                int period = calculatePeriod(listDates,predictedPeriod);
                Collections.reverse(listBalances);

                return new ResponseEntity<>(predictorCardBalance.predictBalance(listBalances, period), HttpStatus.OK);
            }
        }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
    }

    public int calculatePeriod(List<LocalDate> listDates,int predictedPeriod){
        LocalDate initDate = listDates.get(0);
        LocalDate endDate = listDates.get(listDates.size()-1);
        int interval = listDates.stream().map(LocalDate::getDayOfMonth).reduce(0,Integer::sum)-initDate.getDayOfMonth()-endDate.getDayOfMonth();
        int countTransactions = repositoryTransactions.findAll().size();

        return countTransactions * predictedPeriod /interval;
    }
}
