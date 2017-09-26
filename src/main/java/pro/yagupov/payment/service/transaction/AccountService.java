package pro.yagupov.payment.service.transaction;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.UserDao;
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

    @Transactional
    public Account createAccount(User pUser, AccountTDO pAccountTDO) {
        User user = mUserDao.getUserById(pUser.getId());
        Account account = new Account(pAccountTDO);
        user.addAccount(account);
        return account;
    }
}
