package pro.yagupov.payment.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pro.yagupov.payment.dao.AccountDao;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.auth.User;

import javax.persistence.EntityManager;
import javax.persistence.NoResultException;
import javax.persistence.PersistenceContext;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Component
@Repository
public class AccountDaoImpl implements AccountDao {

    @PersistenceContext
    private EntityManager mEntityManager;


    @Override
    public void createAccount(Account account) {
        mEntityManager.persist(account);
    }

    @Override
    public Account getAccountById(long id) {
        try {
            return (Account) mEntityManager
                    .createQuery("from Account where id=:id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (NoResultException e) {
            return Account.NOT_FOUND;
        }
    }

    @Override
    public Account getAccountByUserAndId(long id, User user) {
        try {
            return (Account) mEntityManager
                    .createQuery("from Account where id=:id and user=:user")
                    .setParameter("id", id)
                    .setParameter("user", user)
                    .getSingleResult();
        } catch (NoResultException e) {
            return Account.NOT_FOUND;
        }
    }

    @Override
    public void updateAccount(Account account) {
        mEntityManager.merge(account);
    }

    @Override
    public void removeAccount(Account account) {
        mEntityManager.remove(account);
    }
}
