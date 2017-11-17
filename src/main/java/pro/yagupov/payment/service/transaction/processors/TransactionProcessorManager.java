package pro.yagupov.payment.service.transaction.processors;

import lombok.NonNull;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Lazy;
import org.springframework.stereotype.Component;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.security.exception.ProcessingException;

/**
 * Created by Yagupov Ruslan on 26.04.17.
 */
@Component
public class TransactionProcessorManager implements TransactionProcessor, ItemProcessor<Transaction, Transaction> {

    @Lazy
    @Autowired
    @Qualifier(value = "authorize")
    private TransactionProcessor authorize;

    @Lazy
    @Autowired
    @Qualifier(value = "capture")
    private TransactionProcessor capture;


    @Override
    public Transaction processing(@NonNull Transaction transaction) throws ProcessingException {
        switch (transaction.getOperation()) {
            case AUTHORIZE:
                return authorize.processing(transaction);
            case CAPTURE:
                return capture.processing(transaction);
            case DECLINE:
            case REFUND:
            case VOID:
        }

        throw new ProcessingException(ProcessingException.ERROR_UNKNOWN_OPERATION);
    }

    @Override
    public Transaction process(Transaction item) throws Exception {

        switch (item.getPreviousStatus()) {
            case CAPTURED:
                item.setOperation(Transaction.Operation.CAPTURE);
                break;
        }

        return processing(item);
    }
}
