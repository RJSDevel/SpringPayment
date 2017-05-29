package pro.yagupov.payment.controller;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import pro.yagupov.payment.security.exception.TransactionProcessingException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yagupov Ruslan on 27.04.17.
 */
@ControllerAdvice
public class ExceptionsHandlerController {

    @ExceptionHandler(value = {TransactionProcessingException.class})
    public Exception handleTransactionProcessingException(HttpServletRequest req, TransactionProcessingException exception) {
        return exception;
    }
}
