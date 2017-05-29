package pro.yagupov.payment.config;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurerAdapter;
import pro.yagupov.payment.security.AuthorizationResolver;

import java.util.List;

/**
 * Created by Yagupov Ruslan on 02.05.17.
 */
@Configuration
public class ResolverConfiguration extends WebMvcConfigurerAdapter {

    @Override
    public void addArgumentResolvers(List<HandlerMethodArgumentResolver> argumentResolvers) {
        argumentResolvers.add(new AuthorizationResolver());
        super.addArgumentResolvers(argumentResolvers);
    }
}
