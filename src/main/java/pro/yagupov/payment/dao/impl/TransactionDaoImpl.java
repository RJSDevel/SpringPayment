package pro.yagupov.payment.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pro.yagupov.payment.dao.TransactionDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Component
@Repository
public class TransactionDaoImpl implements TransactionDao {

    @PersistenceContext
    private EntityManager mEntityManager;

    @Override
    public void recordTransaction(Transaction transaction) {
        mEntityManager.persist(transaction);
    }

    @Override
    public void updateTransaction(Transaction transaction) {
        mEntityManager.merge(transaction);
    }

    @Override
    public Transaction getTransactionByGuid(String guid) {
        return (Transaction) mEntityManager
                .createQuery("from Transaction where guid=:guid")
                .setParameter("guid", guid)
                .getSingleResult();
    }

    @Override
    public List<Transaction> getAllTransactionsByAccount(Account account) {
        return (List<Transaction>) mEntityManager
                .createQuery("from Transaction where source=:id or destination=:id")
                .setParameter("id", account)
                .getResultList();
    }
}
