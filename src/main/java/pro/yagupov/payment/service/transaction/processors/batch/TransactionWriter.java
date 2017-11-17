package pro.yagupov.payment.service.transaction.processors.batch;

import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import java.util.List;
import java.util.function.Consumer;

/**
 * Created by developer on 17.11.17.
 */
public class TransactionWriter implements ItemWriter<Transaction> {

    @Autowired
    private TransactionDao mTransactionDao;

    @Override
    public void write(List<? extends Transaction> items) throws Exception {
        items.forEach((Consumer<Transaction>) pTransaction -> mTransactionDao.updateTransaction(pTransaction));
    }
}
