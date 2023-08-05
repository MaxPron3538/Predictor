package main.model.repositories;

import java.math.BigDecimal;

public interface TransactionSumInterface {
    Integer getMccTransaction();
    BigDecimal getAmountSum();
}
