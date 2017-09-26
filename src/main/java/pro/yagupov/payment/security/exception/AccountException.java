package pro.yagupov.payment.security.exception;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public class AccountException extends PaymentException {

    public static final String
            SOURCE = "Source",
            DESTINATION = "Destination";

    public static final Error
            ERROR_ACCOUNT_IS_BLOCKED = new Error(2000, "Account is blocked"),
            ERROR_ACCOUNT_IS_NOT_ACTIVE = new Error(2000, "Account is not active"),
            ERROR_SOURCE_ACCOUNT_DOESNT_BELONG_USER = new Error(2001, "Source account doesn't belong user"),
            ERROR_DESTINATION_ACCOUNT_DOESNT_EXISTS = new Error(2002, "Destination account doesn't exists");


    public AccountException(Error error) {
        super(error);
    }

    public AccountException(Error error, String message) {
        super(error, message);
    }

    public AccountException(Error error, boolean dangerous) {
        super(error);
    }
}
