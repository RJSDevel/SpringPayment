package pro.yagupov.payment.service.transaction.processors.batch;

import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.NonTransientResourceException;
import org.springframework.batch.item.ParseException;
import org.springframework.batch.item.UnexpectedInputException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import java.util.List;

/**
 * Created by developer on 17.11.17.
 */
@Component
public class TransactionReader implements ItemReader<Transaction> {

    @Autowired
    private TransactionDao mTransactionDao;

    private int index = 0;

    private List<Transaction> mTransactions;


    @Override
    @Transactional
    public Transaction read() throws Exception {

        if (mTransactions == null || index > mTransactions.size() - 1) {
            mTransactions = mTransactionDao.getTransactionsForBatch();
            index = 0;
            if (mTransactions.isEmpty()) {
                return null;
            }
        }

        Transaction transaction = mTransactions.get(index++);

        transaction.setStatus(Transaction.Status.BATCHING);
        mTransactionDao.updateTransaction(transaction);

        return transaction;
    }
}
