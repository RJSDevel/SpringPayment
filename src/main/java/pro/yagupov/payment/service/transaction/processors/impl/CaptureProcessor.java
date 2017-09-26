package pro.yagupov.payment.service.transaction.processors.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.transaction.Amounts;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.security.exception.ProcessingException;
import pro.yagupov.payment.service.transaction.processors.TransactionProcessor;

import java.math.BigDecimal;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
@Component("capture")
@Transactional
public class CaptureProcessor implements TransactionProcessor {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction processing(@NonNull Transaction transaction) throws ProcessingException {

        if (transaction.getStatus() == Transaction.Status.CAPTURED) {
            throw new ProcessingException(ProcessingException.ERROR_TRANSACTION_ALREADY_DONE);
        }

        Account source = transaction.getSource();
        Account destination = transaction.getDestination();

        BigDecimal amount = new BigDecimal(0);
        for (Amounts amounts : transaction.getAmounts()) {
            amount = amount.add(amounts.getAmount());
        }

        if (source.getScore().compareTo(amount) == -1) {
            throw new ProcessingException(ProcessingException.ERROR_SOURCE_ACCOUNT_DON_HAVE_NEED_AMOUNT, transaction.getStatus() != null);
        }

        source.setScore(source.getScore().subtract(amount));

        if (transaction.getStatus() != null && transaction.getStatus() == Transaction.Status.AUTHORIZED) {
            source.setHold(source.getHold().subtract(amount));
        }

        destination.setScore(destination.getScore().add(amount));

        if (transaction.getStatus() == null) {
            transaction.setStatus(Transaction.Status.CAPTURED);
            transactionDao.recordTransaction(transaction);
        } else {
            transaction.setStatus(Transaction.Status.CAPTURED);
            transactionDao.updateTransaction(transaction);
        }

        return transaction;
    }
}
