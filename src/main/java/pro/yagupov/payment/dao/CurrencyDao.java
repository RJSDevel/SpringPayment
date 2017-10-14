package pro.yagupov.payment.dao;

import pro.yagupov.payment.domain.entity.transaction.Currency;

/**
 * Created by developer on 13.10.17.
 */
public interface CurrencyDao {

    Currency getCountryByCode(String code);

}
