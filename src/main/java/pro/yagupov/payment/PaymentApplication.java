package pro.yagupov.payment;

import org.springframework.batch.core.configuration.annotation.EnableBatchProcessing;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.retry.annotation.EnableRetry;
import org.springframework.scheduling.annotation.EnableScheduling;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Yagupov Ruslan on 01.03.17.
 */
@EnableRetry
@ComponentScan
@EnableScheduling
@SpringBootApplication
@EnableAutoConfiguration
@EnableBatchProcessing
@EnableTransactionManagement
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{PaymentApplication.class}, args);
    }
}
