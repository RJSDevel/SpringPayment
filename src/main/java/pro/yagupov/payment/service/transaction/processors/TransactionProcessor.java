package pro.yagupov.payment.service.transaction.processors;

import lombok.NonNull;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.security.exception.ProcessingException;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
public interface TransactionProcessor {

    Transaction processing(@NonNull Transaction transaction) throws ProcessingException;

}
