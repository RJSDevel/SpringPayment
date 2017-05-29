package pro.yagupov.payment.dao.impl;

import org.springframework.stereotype.Component;
import org.springframework.stereotype.Repository;
import pro.yagupov.payment.dao.UserDao;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.auth.UserGroup;

import javax.persistence.EntityManager;
import javax.persistence.PersistenceContext;
import java.util.List;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@Component
@Repository
@SuppressWarnings("unchecked")
public class UserDaoImpl implements UserDao {

    @PersistenceContext
    private EntityManager mEntityManager;


    @Override
    public void createUser(User user) {
        mEntityManager.persist(user);
    }

    @Override
    public void updateUser(User user) {
        mEntityManager.merge(user);
    }

    @Override
    public List<UserGroup> getUserAuthorities() {
        return mEntityManager
                .createQuery("from UserGroup")
                .getResultList();
    }

    @Override
    public UserGroup findGroupByName(String groupName) {
        return (UserGroup) mEntityManager
                .createQuery("from UserGroup where name = :name")
                .setParameter("name", groupName)
                .getSingleResult();
    }

    @Override
    public User findUserByName(String username) {
        try {
            return (User) mEntityManager
                    .createQuery("from User where username = :username")
                    .setParameter("username", username)
                    .getSingleResult();
        } catch (Exception pE) {
            return null;
        }
    }

    @Override
    public User getUserById(long id) {
        try {
            return (User) mEntityManager
                    .createQuery("from User where id = :id")
                    .setParameter("id", id)
                    .getSingleResult();
        } catch (Exception pE) {
            return null;
        }
    }

    @Override
    public boolean isUserExists(String username) {
        return findUserByName(username) != null;
    }
}
