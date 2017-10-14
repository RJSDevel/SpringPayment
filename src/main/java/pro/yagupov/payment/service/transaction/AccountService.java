package pro.yagupov.payment.service.transaction;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.CurrencyDao;
import pro.yagupov.payment.dao.UserDao;
import pro.yagupov.payment.domain.entity.transaction.Currency;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.tdo.AccountTDO;

/**
 * Created by developer on 25.09.17.
 */
@Service
public class AccountService {

    @Autowired
    private UserDao mUserDao;

    @Autowired
    private CurrencyDao mCurrencyDao;


    @Transactional
    public Account createAccount(User pUser, AccountTDO pAccountTDO) {
        User user = mUserDao.getUserById(pUser.getId());

        Currency currency = null;
        if (pAccountTDO != null && StringUtils.isNotEmpty(pAccountTDO.getCurrency())) {
            currency = mCurrencyDao.getCountryByCode(pAccountTDO.getCurrency());
        } else {

        }

        Account account = new Account(pAccountTDO, currency);
        user.addAccount(account);
        return account;
    }
}
