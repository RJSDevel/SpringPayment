package pro.yagupov.payment.dao;

import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.entity.auth.User;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
public interface AccountDao {

    void createAccount(Account account);

    Account getAccountById(long id);

    Account getAccountByUserAndId(long id, User user);

    void updateAccount(Account account);

    void removeAccount(Account account);

}
