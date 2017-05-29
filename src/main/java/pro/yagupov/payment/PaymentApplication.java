package pro.yagupov.payment;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.transaction.annotation.EnableTransactionManagement;

/**
 * Created by Yagupov Ruslan on 01.03.17.
 */
@ComponentScan
@SpringBootApplication
@EnableAutoConfiguration
@EnableTransactionManagement
public class PaymentApplication {

    public static void main(String[] args) {
        SpringApplication.run(new Class<?>[]{PaymentApplication.class}, args);
    }
}
