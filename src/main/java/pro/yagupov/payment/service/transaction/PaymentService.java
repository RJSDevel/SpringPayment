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
import pro.yagupov.payment.security.exception.TransactionProcessingException;
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

    private Transaction preProcessingChecks(TransactionTDO pTransaction, User pUser) {

        if (pTransaction.getSource() == pTransaction.getDestination()) {
            throw new TransactionProcessingException("Source account and destination accounts can't be equal");
        }

        AmountsTDO amounts = pTransaction.getAmounts();
        if (amounts.getAmount() != amounts.getOrderAmount() + amounts.getTipAmount()) {
            throw new TransactionProcessingException("Amount doesn't equal order + tips");
        }

        Account source = accountDao.getAccountByUserAndId(pTransaction.getSource(), pUser);
        if (source == Account.NOT_FOUND) {
            throw new TransactionProcessingException("Source account doesn't belong user");
        }

        Account destination = accountDao.getAccountById(pTransaction.getDestination());
        if (destination == Account.NOT_FOUND) {
            throw new TransactionProcessingException("Destination account doesn't exists");
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
    public TransactionTDO authorizeTransaction(TransactionTDO pTransaction, User pUser) {
        pTransaction.setOperation(Transaction.Operation.AUTHORIZE);
        return new TransactionTDO(transactionProcessorManager.processing(preProcessingChecks(pTransaction, pUser)));
    }

    @Transactional
    public TransactionTDO captureTransaction(TransactionTDO pTransaction, User pUser) {

        pTransaction.setOperation(Transaction.Operation.CAPTURE);

        if (StringUtils.isNotEmpty(pTransaction.getGuid())) {
            Transaction transaction = transactionDao.getTransactionByGuid(pTransaction.getGuid());
            if (transaction != null) {
                transaction.setOperation(pTransaction.getOperation());
                return new TransactionTDO(transactionProcessorManager.processing(transaction));
            }
        }

        return new TransactionTDO(transactionProcessorManager.processing(preProcessingChecks(pTransaction, pUser)));
    }

    public TransactionTDO refundTransaction(TransactionTDO pTransaction, User pUser) {
        return null;
    }

    public TransactionTDO voidTransaction(TransactionTDO pTransaction, User pUser) {
        return null;
    }

    @Transactional
    public List<TransactionTDO> getAllTransactionsByUser(User pUser) {
        List<TransactionTDO> transactions = new ArrayList<>();

        User user = userDao.getUserById(pUser.getId());
        for (Account account : user.getAccounts()) {
            for (Transaction transaction : transactionDao.getAllTransactionsByAccount(account)) {
                transactions.add(new TransactionTDO(transaction));
            }
        }

        return transactions;
    }
}
