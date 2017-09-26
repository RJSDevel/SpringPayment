package pro.yagupov.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.auth.UserGroup;
import pro.yagupov.payment.domain.tdo.AccountTDO;
import pro.yagupov.payment.service.transaction.AccountService;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
@RestController
@RequestMapping("/payment/account")
public class AccountController {

    @Autowired
    private AccountService mAccountService;


    @PreAuthorize(UserGroup.ROLE_USER)
    @RequestMapping(value = "/create", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public AccountTDO createAccount(User user, @RequestBody(required = false) AccountTDO accountTDO) {
        return new AccountTDO(mAccountService.createAccount(user, accountTDO));
    }
}
