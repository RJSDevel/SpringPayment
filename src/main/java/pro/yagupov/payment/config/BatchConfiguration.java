package pro.yagupov.payment.config;

import org.springframework.batch.core.*;
import org.springframework.batch.core.configuration.annotation.JobBuilderFactory;
import org.springframework.batch.core.configuration.annotation.StepBuilderFactory;
import org.springframework.batch.core.launch.JobLauncher;
import org.springframework.batch.core.launch.support.RunIdIncrementer;
import org.springframework.batch.item.ItemProcessor;
import org.springframework.batch.item.ItemReader;
import org.springframework.batch.item.ItemWriter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.scheduling.annotation.Scheduled;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.service.transaction.processors.TransactionProcessorManager;
import pro.yagupov.payment.service.transaction.processors.batch.TransactionBatchJobExecutionListener;
import pro.yagupov.payment.service.transaction.processors.batch.TransactionReader;
import pro.yagupov.payment.service.transaction.processors.batch.TransactionWriter;


/**
 * Created by developer on 17.11.17.
 */
@Configuration
public class BatchConfiguration {

    @Autowired
    private JobBuilderFactory jobBuilderFactory;

    @Autowired
    private StepBuilderFactory stepBuilderFactory;

    @Autowired
    private JobLauncher jobLauncher;

    @Autowired
    private Job processJob;


    @Scheduled(fixedRate = 15000)
    private void processingStarter() {
        JobParameters jobParameters = new JobParametersBuilder()
                .addLong("time", System.currentTimeMillis())
                .toJobParameters();
        try {
            jobLauncher.run(processJob, jobParameters);
        } catch (Exception pE) {
            pE.printStackTrace();
        }
    }

    @Bean
    public Job processJob() {
        return jobBuilderFactory.get("processJob")
                .incrementer(new RunIdIncrementer())
                .listener(listener())
                .flow(orderStep1()).end().build();
    }

    @Bean
    public Step orderStep1() {
        return stepBuilderFactory.get("Step1").<Transaction, Transaction>chunk(5)
                .reader(transactionItemReader())
                .processor(transactionBatchProcessor())
                .writer(transactionItemWriter())
                .build();
    }

    @Bean
    public JobExecutionListener listener() {
        return new TransactionBatchJobExecutionListener();
    }

    @Bean
    public ItemReader<Transaction> transactionItemReader() {
        return new TransactionReader();
    }

    @Bean
    public ItemWriter<Transaction> transactionItemWriter() {
        return new TransactionWriter();
    }

    @Bean
    public ItemProcessor<Transaction, Transaction> transactionBatchProcessor() {
        return new TransactionProcessorManager();
    }
}
