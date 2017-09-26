package pro.yagupov.payment.security.exception;

import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by developer on 26.09.17.
 */
@AllArgsConstructor
@Getter
public class PaymentException extends RuntimeException {

    @AllArgsConstructor
    @Getter
    public static class Error {
        private long code;
        private String error;
    }

    private Error error;

}
