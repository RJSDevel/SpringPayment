package pro.yagupov.payment.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pro.yagupov.payment.dao.CurrencyDao;
import pro.yagupov.payment.domain.entity.transaction.Currency;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;

/**
 * Created by developer on 13.10.17.
 */
@Component
@Repository
public class CurrencyDaoImpl implements CurrencyDao {

    @PersistenceContext
    private EntityManager mEntityManager;


    @Override
    public Currency getCountryByCode(String code) {
        try {
            return (Currency) mEntityManager
                    .createQuery("from Currency where code = :code")
                    .setParameter("code", code)
                    .getSingleResult();
        } catch (Exception e) {
            return null;
        }
    }
}
