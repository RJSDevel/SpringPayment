package pro.yagupov.payment.service;

import org.apache.commons.lang3.StringUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import pro.yagupov.payment.dao.UserDao;
import pro.yagupov.payment.domain.entity.auth.Login;
import pro.yagupov.payment.domain.entity.auth.User;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@Service
public class UserService {

    @Autowired
    private UserDao userDao;

    @Autowired
    PasswordEncoder passwordEncoder;


    @Transactional
    public void createUser(Login pLogin) {

        if (StringUtils.isEmpty(pLogin.getUsername())) {
            throw new SecurityException("Username can't be empty");
        }

        if (StringUtils.isEmpty(pLogin.getPassword())) {
            throw new SecurityException("Password can't be empty");
        }

        if (!userDao.isUserExists(pLogin.getUsername())) {

            User user = new User(
                    pLogin.getUsername(),
                    passwordEncoder.encode(pLogin.getPassword()),
                    userDao.findGroupByName("USER"),
                    true,
                    true,
                    true,
                    true);

            userDao.createUser(user);
        } else {
            throw new SecurityException("User with name " + pLogin.getUsername() + " already exists");
        }
    }

    public User findUserByName(String pUsername) {
        return userDao.findUserByName(pUsername);
    }
}
