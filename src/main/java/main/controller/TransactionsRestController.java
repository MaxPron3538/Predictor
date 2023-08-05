package main.controller;

import main.model.StatusCode;
import main.model.entities.Account;
import main.model.entities.TypeBusiness;
import main.model.entities.Transaction;
import main.model.logic.PredictorCardBalance;
import main.model.repositories.AccountRepository;
import main.model.repositories.TransactionSumInterface;
import main.model.repositories.TypeBusinessRepository;
import main.model.repositories.TransactionRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.*;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/transactions")
@SessionAttributes("account")
public class TransactionsRestController {

    @Autowired
    private AccountRepository repositoryAccounts;

    @Autowired
    private TransactionRepository repositoryTransactions;

    @Autowired
    private PredictorCardBalance predictorCardBalance;

    @Autowired
    private TypeBusinessRepository typeBusinessRepository;

    @GetMapping("/dates/{predictedPeriod}")
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

    @GetMapping("/oldBalances")
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

    @GetMapping("/predictedBalances/{predictedPeriod}")
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

    @GetMapping("/listNames")
    public ResponseEntity<?> listNameOfBusinessByMcc(@ModelAttribute Account account){
        if(account.getStatusCode() == StatusCode.Ok) {
            List<Integer> listCodeMCC = repositoryTransactions.findAll().stream().map(Transaction::getMcc).distinct().collect(Collectors.toList());
            List<TransactionSumInterface> sumTransactionsByMCC = repositoryTransactions.findTransactionSumGroupByMCC();
            List<String> listNamesByMCC = new ArrayList<>();

            for (Integer code : listCodeMCC) {
                Optional<TypeBusiness> typeBusiness = typeBusinessRepository.findByCode(code);
                typeBusiness.ifPresent(codeMCC -> listNamesByMCC.add(codeMCC.getName()));
            }

            if (!listNamesByMCC.isEmpty()) {
                Collections.reverse(listNamesByMCC);
                return new ResponseEntity<>(listNamesByMCC,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    @GetMapping("/listAmounts")
    public ResponseEntity<?> listTotalAmountByMCC(@ModelAttribute Account account){
        if(account.getStatusCode() == StatusCode.Ok) {
            List<BigDecimal> listAmount = repositoryTransactions.findTransactionSumGroupByMCC()
                    .stream().map(TransactionSumInterface::getAmountSum).collect(Collectors.toList());

            if(!listAmount.isEmpty()) {
                Collections.reverse(listAmount);
                return new ResponseEntity<>(listAmount,HttpStatus.OK);
            }
            return new ResponseEntity<>(HttpStatus.NOT_FOUND);
        }
        return new ResponseEntity<>(HttpStatus.FORBIDDEN);
    }

    public int calculatePeriod(List<LocalDate> listDates,int predictedPeriod){
        LocalDate initDate = listDates.get(0);
        LocalDate endDate = listDates.get(listDates.size()-1);
        int interval = listDates.stream().map(LocalDate::getDayOfMonth).reduce(0,Integer::sum)-initDate.getDayOfMonth()-endDate.getDayOfMonth();
        int countTransactions = repositoryTransactions.findAll().size();

        return countTransactions * predictedPeriod /interval;
    }
}
