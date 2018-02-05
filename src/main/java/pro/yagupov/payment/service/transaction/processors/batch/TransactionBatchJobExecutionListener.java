package pro.yagupov.payment.service.transaction.processors.batch;

import org.springframework.batch.core.JobExecution;
import org.springframework.batch.core.listener.JobExecutionListenerSupport;

/**
 * Created by developer on 17.11.17.
 */
public class TransactionBatchJobExecutionListener extends JobExecutionListenerSupport {

    @Override
    public void afterJob(JobExecution jobExecution) {
        switch (jobExecution.getStatus()) {
            case COMPLETED:
                break;
            case FAILED:
                break;
        }
    }
}
