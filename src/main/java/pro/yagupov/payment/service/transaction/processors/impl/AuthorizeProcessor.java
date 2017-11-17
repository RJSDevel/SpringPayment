package pro.yagupov.payment.service.transaction.processors.impl;

import lombok.NonNull;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.security.exception.ProcessingException;
import pro.yagupov.payment.service.transaction.processors.TransactionProcessor;

import java.math.BigDecimal;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
@Component("authorize")
@Transactional
public class AuthorizeProcessor implements TransactionProcessor {

    @Autowired
    private TransactionDao transactionDao;

    @Override
    public Transaction processing(@NonNull Transaction transaction) throws ProcessingException {

        Account source = transaction.getSource();

        if (source.getScore().compareTo(new BigDecimal(0)) == 0 || source.getScore().subtract(source.getHold()).compareTo(transaction.getAmount()) == -1) {
            throw new ProcessingException(ProcessingException.ERROR_SOURCE_ACCOUNT_DO_NOT_HAVE_NEED_AMOUNT);
        }

        transaction.setStatus(Transaction.Status.AUTHORIZED);
        transactionDao.recordTransaction(transaction);

        return transaction;
    }
}
