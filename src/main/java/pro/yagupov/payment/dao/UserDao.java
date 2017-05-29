package pro.yagupov.payment.dao;

import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.auth.UserGroup;

import java.util.List;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
public interface UserDao {

    void createUser(User user);

    void updateUser(User user);

    List<UserGroup> getUserAuthorities();

    UserGroup findGroupByName(String groupName);

    User findUserByName(String username);

    User getUserById(long id);

    boolean isUserExists(String username);
}
