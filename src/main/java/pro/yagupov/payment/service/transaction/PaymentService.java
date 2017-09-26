package pro.yagupov.payment.service.transaction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.AccountDao;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.dao.UserDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.domain.tdo.AmountsTDO;
import pro.yagupov.payment.domain.tdo.TransactionTDO;
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

    private Transaction preProcessingChecks(User pUser, TransactionTDO pTransaction) {

        if (pTransaction.getSource() == pTransaction.getDestination()) {
            throw new ProcessingException(ProcessingException.ERROR_SOURCE_DESTINATION_EQUALS);
        }

        AmountsTDO amounts = pTransaction.getAmounts();
        if (amounts.getOrderAmount() != null || amounts.getTipAmount() != null)
        if (amounts.getAmount().compareTo(amounts.getOrderAmount().add(amounts.getTipAmount())) != 0) {
            throw new ProcessingException(ProcessingException.ERROR_AMOUNT_IS_NOT_EQUALS_ORDER_PLUS_TIPS);
        }

        Account source = accountDao.getAccountByUserAndId(pTransaction.getSource(), pUser);
        if (source == Account.NOT_FOUND) {
            throw new ProcessingException(ProcessingException.ERROR_SOURCE_ACCOUNT_DOESNT_BELONG_USER);
        }

        Account destination = accountDao.getAccountById(pTransaction.getDestination());
        if (destination == Account.NOT_FOUND) {
            throw new ProcessingException(ProcessingException.ERROR_DESTINATION_ACCOUNT_DOESNT_EXISTS);
        }

        Transaction transaction = new Transaction(pTransaction, source, destination);

        if (StringUtils.isNotEmpty(pTransaction.getParent())) {
            transaction.setParent(transactionDao.getTransactionByGuid(pTransaction.getParent()));
        }

        if (StringUtils.isNotEmpty(pTransaction.getChild())) {
            transaction.setChild(transactionDao.getTransactionByGuid(pTransaction.getChild()));
        }

        return transaction;
    }

    @Transactional
    public TransactionTDO authorizeTransaction(User pUser, TransactionTDO pTransaction) {
        pTransaction.setOperation(Transaction.Operation.AUTHORIZE);
        return new TransactionTDO(transactionProcessorManager.processing(preProcessingChecks(pUser, pTransaction)));
    }

    @Transactional
    public Transaction captureTransaction(User pUser, TransactionTDO pTransaction) {

        pTransaction.setOperation(Transaction.Operation.CAPTURE);

        if (StringUtils.isNotEmpty(pTransaction.getGuid())) {
            Transaction transaction = transactionDao.getTransactionByGuid(pTransaction.getGuid());
            if (transaction != null) {
                transaction.setOperation(pTransaction.getOperation());
                return transactionProcessorManager.processing(transaction);
            }
        }

        return transactionProcessorManager.processing(preProcessingChecks(pUser, pTransaction));
    }

    public Transaction refundTransaction(User pUser, TransactionTDO pTransaction) {
        return null;
    }

    public Transaction voidTransaction(User pUser, TransactionTDO pTransaction) {
        return null;
    }

    @Transactional
    public List<Transaction> getAllTransactionsByUser(User pUser) {

        List<Transaction> transactions = new ArrayList<>();

        User user = userDao.getUserById(pUser.getId());
        for (Account account : user.getAccounts()) {
            transactions.addAll(transactionDao.getAllTransactionsByAccount(account));
        }

        return transactions;
    }
}
