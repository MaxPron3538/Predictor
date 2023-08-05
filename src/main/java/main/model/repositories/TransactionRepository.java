package main.model.repositories;

import main.model.entities.Transaction;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.CrudRepository;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TransactionRepository extends CrudRepository<Transaction,Integer> {

    List<Transaction> findAll();

    @Query(value =
            "SELECT " +
                    " tr.mcc AS mcc " +
                    ", SUM(ABS(tr.amount)) AS amountSum " +
                    "FROM transaction tr " +
                    "GROUP BY tr.mcc"
            ,nativeQuery = true)
    List<TransactionSumInterface>  findTransactionSumGroupByMCC();
}

