package pro.yagupov.payment.service.transaction.processors.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.security.exception.TransactionProcessingException;
import pro.yagupov.payment.service.transaction.processors.TransactionProcessor;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
@Component("authorize")
@Transactional
public class AuthorizeProcessor implements TransactionProcessor {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction processing(@NonNull Transaction transaction) throws TransactionProcessingException {

        Account destination = transaction.getDestination();

        long amount = transaction.getAmounts().getAmount();

        //if (amount > destination.getScore()) throw new TransactionProcessingException("");

        transaction.setStatus(Transaction.Status.AUTHORIZED);
        destination.setScore(destination.getScore() - amount);
        transactionDao.recordTransaction(transaction);

        return transaction;
    }
}
