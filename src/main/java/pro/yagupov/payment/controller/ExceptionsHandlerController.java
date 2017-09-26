package pro.yagupov.payment.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import pro.yagupov.payment.security.exception.PaymentException;
import pro.yagupov.payment.security.exception.ProcessingException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yagupov Ruslan on 27.04.17.
 */
@ControllerAdvice
public class ExceptionsHandlerController {

    @ExceptionHandler(value = {PaymentException.class})
    @ResponseBody
    public PaymentException.Error handleTransactionProcessingException(HttpServletRequest req, PaymentException exception) {
        return exception.getError();
    }
}
