package pro.yagupov.payment.controller;

import org.springframework.http.HttpStatus;
import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;
import org.springframework.web.bind.annotation.ResponseStatus;
import org.springframework.web.servlet.ModelAndView;
import pro.yagupov.payment.security.exception.PaymentException;
import pro.yagupov.payment.security.exception.ProcessingException;

import javax.servlet.http.HttpServletRequest;

/**
 * Created by Yagupov Ruslan on 27.04.17.
 */
@ControllerAdvice
public class ExceptionsHandlerController {

    @ExceptionHandler(value = {PaymentException.class})
    @ResponseStatus(HttpStatus.BAD_REQUEST)
    @ResponseBody
    public PaymentException.Error handlePaymentException(HttpServletRequest req, PaymentException exception) {
        return exception.getError();
    }
}
