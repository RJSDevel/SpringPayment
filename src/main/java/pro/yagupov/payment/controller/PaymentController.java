package pro.yagupov.payment.controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.MediaType;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.*;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.auth.UserGroup;
import pro.yagupov.payment.domain.tdo.TransactionTDO;
import pro.yagupov.payment.service.transaction.PaymentService;

import java.util.List;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@RestController
@RequestMapping("/payment")
public class PaymentController {

    @Autowired
    private PaymentService paymentService;


    @PreAuthorize(UserGroup.USER)
    @RequestMapping(value = "/auth", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionTDO authorizeTransaction(User user, @RequestBody TransactionTDO pTransactionTDO) {
        return paymentService.authorizeTransaction(pTransactionTDO, user);
    }

    @PreAuthorize(UserGroup.USER)
    @RequestMapping(value = "/capture", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionTDO captureTransaction(User user, @RequestBody TransactionTDO pTransactionTDO) {
        return paymentService.captureTransaction(pTransactionTDO, user);
    }

    @PreAuthorize(UserGroup.USER)
    @RequestMapping(value = "/refund", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionTDO refundTransaction(User user, @RequestBody TransactionTDO pTransactionTDO) {
        return paymentService.refundTransaction(pTransactionTDO, user);
    }

    @PreAuthorize(UserGroup.USER)
    @RequestMapping(value = "/void", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public TransactionTDO voidTransaction(User user, @RequestBody TransactionTDO pTransactionTDO) {
        return paymentService.voidTransaction(pTransactionTDO, user);
    }

    @PreAuthorize(UserGroup.USER)
    @RequestMapping(value = "/transactions", method = RequestMethod.POST, produces = MediaType.APPLICATION_JSON_UTF8_VALUE)
    @ResponseBody
    public List<TransactionTDO> getAllTransactions(User user) {
        return paymentService.getAllTransactionsByUser(user);
    }
}
