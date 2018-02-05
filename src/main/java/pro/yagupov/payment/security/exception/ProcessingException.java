package pro.yagupov.payment.security.exception;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public class ProcessingException extends PaymentException {

    public static final Error
            ERROR_TRANSACTION_NOT_FOUND = new Error(1, "Transaction not found"),
            ERROR_TRANSACTION_ID_IS_WRONG = new Error(1, "Transaction id is wrong"),
            ERROR_UNKNOWN_OPERATION = new Error(1000, "Unknown operation"),
            ERROR_SOURCE_DESTINATION_EQUALS = new Error(1001, "Source account and destination accounts can't be equal"),
            ERROR_TRANSACTION_HAD_AUTHORIZED = new Error(1005, "Transaction had authorized"),
            ERROR_SOURCE_ACCOUNT_DO_NOT_HAVE_NEED_AMOUNT = new Error(1006, "Source account don't have need amount"),
            ERROR_CURRENCY_CODE_IS_WRONG = new Error(2000, "Currency code is wrong");


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
