package pro.yagupov.payment.security.exception;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public class ProcessingException extends PaymentException {

    public static final Error
            ERROR_UNKNOWN_OPERATION = new Error(1000, "Unknown operation"),
            ERROR_SOURCE_DESTINATION_EQUALS = new Error(1001, "Source account and destination accounts can't be equal"),
            ERROR_AMOUNT_IS_NOT_EQUALS_ORDER_PLUS_TIPS = new Error(1004, "Amount isn't equal order + tips"),
            ERROR_TRANSACTION_ALREADY_DONE = new Error(1005, "Transaction already done"),
            ERROR_SOURCE_ACCOUNT_DO_NOT_HAVE_NEED_AMOUNT = new Error(1006, "Source account don't have need amount");

    
    public ProcessingException(Error error) {
        super(error);
    }

    public ProcessingException(Error error, String message) {
        super(error, message);
    }

    public ProcessingException(Error error, boolean dangerous) {
        super(error);
    }
}
