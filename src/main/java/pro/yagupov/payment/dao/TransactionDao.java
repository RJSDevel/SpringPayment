package pro.yagupov.payment.dao;

import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import java.util.List;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
public interface TransactionDao {

    void recordTransaction(Transaction transaction);

    void updateTransaction(Transaction transaction);

    Transaction getTransactionByGuid(String guid);

    List<Transaction> getAllTransactionsByAccount(Account account);

    List<Transaction> getTransactionsForBatch();
}
