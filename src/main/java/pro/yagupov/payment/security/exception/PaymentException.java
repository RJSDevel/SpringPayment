package pro.yagupov.payment.security.exception;

import com.fasterxml.jackson.annotation.JsonInclude;
import lombok.AllArgsConstructor;
import lombok.Getter;

/**
 * Created by developer on 26.09.17.
 */
@AllArgsConstructor
@Getter
public abstract class PaymentException extends RuntimeException {

    @Getter
    public static class Error {
        private long code;
        private String error;

        @JsonInclude(JsonInclude.Include.NON_NULL)
        String message;

        Error(long pCode, String pError) {
            code = pCode;
            error = pError;
        }
    }

    private Error error;

    PaymentException(Error pError, String pMessage) {
        error = pError;
        error.message = pMessage;
    }
}
