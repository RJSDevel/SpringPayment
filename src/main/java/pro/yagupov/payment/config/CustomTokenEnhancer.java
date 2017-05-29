package pro.yagupov.payment.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.User;
import org.springframework.security.oauth2.common.DefaultOAuth2AccessToken;
import org.springframework.security.oauth2.common.OAuth2AccessToken;
import org.springframework.security.oauth2.provider.OAuth2Authentication;
import org.springframework.security.oauth2.provider.token.TokenEnhancer;
import org.springframework.stereotype.Component;
import pro.yagupov.payment.service.UserService;

import java.util.HashMap;
import java.util.Map;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@Component
public class CustomTokenEnhancer implements TokenEnhancer {

    @Autowired
    UserService userService;

    @Override
    public OAuth2AccessToken enhance(OAuth2AccessToken oAuth2AccessToken, OAuth2Authentication oAuth2Authentication) {
        final Map<String, Object> additionalInfo = new HashMap<>();
        Object principal = oAuth2Authentication.getPrincipal();
        if (principal instanceof User) {
            String userName = ((User) principal).getUsername();
            additionalInfo.put("status", "ok");
        }
        ((DefaultOAuth2AccessToken) oAuth2AccessToken).setAdditionalInformation(additionalInfo);
        return oAuth2AccessToken;
    }
}