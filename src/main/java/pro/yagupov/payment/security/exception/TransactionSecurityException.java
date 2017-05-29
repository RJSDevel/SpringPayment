package pro.yagupov.payment.security.exception;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public class TransactionSecurityException extends SecurityException {

    public TransactionSecurityException(String pS) {
        super(pS);
    }
}
