package pro.yagupov.payment.service.transaction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.AccountDao;
import pro.yagupov.payment.dao.CurrencyDao;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.dao.UserDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.transaction.Currency;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.domain.tdo.TransactionTDO;
import pro.yagupov.payment.security.exception.AccountException;
import pro.yagupov.payment.security.exception.PaymentException;
import pro.yagupov.payment.security.exception.ProcessingException;
import pro.yagupov.payment.service.transaction.processors.TransactionProcessorManager;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Service
public class PaymentService {

    @Autowired
    private UserDao userDao;

    @Autowired
    private AccountDao accountDao;

    @Autowired
    private TransactionDao transactionDao;

    @Autowired
    private TransactionProcessorManager transactionProcessorManager;

    @Autowired
    private CurrencyDao mCurrencyDao;


    private Transaction preProcessingChecks(User pUser, TransactionTDO pTransaction) {

        if (pTransaction.getSource() == pTransaction.getDestination()) {
            throw new ProcessingException(ProcessingException.ERROR_SOURCE_DESTINATION_EQUALS);
        }

        Account source = accountDao.getAccountByUserAndId(pTransaction.getSource(), pUser);
        if (source == Account.NOT_FOUND) {
            throw new AccountException(AccountException.ERROR_SOURCE_ACCOUNT_DOESNT_BELONG_USER);
        }

        if (source.getIsBlocked()) {
            throw new AccountException(AccountException.ERROR_ACCOUNT_IS_BLOCKED, AccountException.SOURCE);
        }

        if (!source.getIsActive()) {
            throw new AccountException(AccountException.ERROR_ACCOUNT_IS_NOT_ACTIVE, AccountException.DESTINATION);
        }

        Account destination = accountDao.getAccountById(pTransaction.getDestination());
        if (destination == Account.NOT_FOUND) {
            throw new AccountException(AccountException.ERROR_DESTINATION_ACCOUNT_DOESNT_EXISTS);
        }

        if (destination.getIsBlocked()) {
            throw new AccountException(AccountException.ERROR_ACCOUNT_IS_BLOCKED, AccountException.DESTINATION);
        }

        if (!destination.getIsActive()) {
            throw new AccountException(AccountException.ERROR_ACCOUNT_IS_NOT_ACTIVE, AccountException.DESTINATION);
        }

        Currency currency = mCurrencyDao.getCountryByCode(pTransaction.getCurrency());
        if (currency == null) {
            throw new ProcessingException(ProcessingException.ERROR_CURRENCY_CODE_IS_WRONG);
        }

        Transaction transaction = new Transaction(pTransaction, currency, source, destination);

        if (StringUtils.isNotEmpty(pTransaction.getParent())) {
            transaction.setParent(transactionDao.getTransactionByGuid(pTransaction.getParent()));
        }

        if (StringUtils.isNotEmpty(pTransaction.getChild())) {
            transaction.setChild(transactionDao.getTransactionByGuid(pTransaction.getChild()));
        }

        return transaction;
    }

    @Transactional
    @Retryable(backoff = @Backoff(delay = 2000), exclude = {PaymentException.class})
    public Transaction authorizeTransaction(User pUser, TransactionTDO pTransaction) {
        pTransaction.setOperation(Transaction.Operation.AUTHORIZE);
        return transactionProcessorManager.processing(preProcessingChecks(pUser, pTransaction));
    }

    @Transactional
    @Retryable(backoff = @Backoff(delay = 2000), exclude = {PaymentException.class})
    public Transaction captureTransaction(User pUser, TransactionTDO pTransaction) {
        if (StringUtils.isNotEmpty(pTransaction.getGuid())) {
            Transaction transaction =  transactionDao.getTransactionByGuid(pTransaction.getGuid());

            if (transaction == null) {
                throw new ProcessingException(ProcessingException.ERROR_TRANSACTION_NOT_FOUND);
            }

            if (transaction.getSource().getUser().getId() != pUser.getId()) {
                throw new AccountException(AccountException.ERROR_SOURCE_ACCOUNT_DOESNT_BELONG_USER);
            }

            transaction.setOperation(Transaction.Operation.CAPTURE);

            return transactionProcessorManager.processing(transaction);
        }

        throw new ProcessingException(ProcessingException.ERROR_TRANSACTION_ID_IS_WRONG);
    }

    @Transactional
    @Retryable(backoff = @Backoff(delay = 2000), exclude = {PaymentException.class})
    public Transaction refundTransaction(User pUser, TransactionTDO pTransaction) {
        return null;
    }

    @Transactional
    @Retryable(backoff = @Backoff(delay = 2000), exclude = {PaymentException.class})
    public Transaction voidTransaction(User pUser, TransactionTDO pTransaction) {
        return null;
    }

    @Transactional
    @Retryable(backoff = @Backoff(delay = 2000), exclude = {PaymentException.class})
    public List<Transaction> getAllTransactionsByUser(User pUser) {

        List<Transaction> transactions = new ArrayList<>();

        User user = userDao.getUserById(pUser.getId());
        for (Account account : user.getAccounts()) {
            transactions.addAll(transactionDao.getAllTransactionsByAccount(account));
        }

        return transactions;
    }
}
