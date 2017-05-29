package pro.yagupov.payment.service;

import lombok.extern.log4j.Log4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Service;
import pro.yagupov.payment.domain.entity.auth.User;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@Log4j
@Service
public class DatabaseUserDetailsService implements UserDetailsService {

    @Autowired
    private UserService userService;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {

        try {
            username = URLEncoder.encode(username, "UTF-8");
        } catch (UnsupportedEncodingException pE) {
            log.error(pE);
        }

        User user = userService.findUserByName(username);
        if (user == null) throw new UsernameNotFoundException("User " + username + " Not found");

        return user;
    }
}
