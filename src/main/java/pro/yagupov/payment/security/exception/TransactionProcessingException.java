package pro.yagupov.payment.security.exception;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public class TransactionProcessingException extends RuntimeException {

    public TransactionProcessingException(String pS) {
        super(pS);
    }
}
