package pro.yagupov.payment.security;

import org.springframework.core.MethodParameter;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.web.bind.support.WebDataBinderFactory;
import org.springframework.web.context.request.NativeWebRequest;
import org.springframework.web.method.support.HandlerMethodArgumentResolver;
import org.springframework.web.method.support.ModelAndViewContainer;
import pro.yagupov.payment.domain.entity.auth.User;

/**
 * Created by Yagupov Ruslan on 02.05.17.
 */
public class AuthorizationResolver implements HandlerMethodArgumentResolver {

    @Override
    public boolean supportsParameter(MethodParameter parameter) {
        return parameter.getParameterType().equals(User.class);
    }

    @Override
    public Object resolveArgument(MethodParameter parameter, ModelAndViewContainer mavContainer, NativeWebRequest webRequest, WebDataBinderFactory binderFactory) throws Exception {
        return ((OAuth2Authentication) webRequest.getUserPrincipal()).getPrincipal();
    }
}
