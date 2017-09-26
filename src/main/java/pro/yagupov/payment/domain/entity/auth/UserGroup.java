package pro.yagupov.payment.domain.entity.auth;

import org.springframework.security.core.GrantedAuthority;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Yagupov Ruslan on 31.03.17.
 */
@Entity
@Table(name = "oauth_users_groups")
public class UserGroup implements GrantedAuthority {

    public static final String
            ROLE_USER = "#payment.clientHasRole('USER')";

    @Id
    @Column(name = "id")
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column
    private String name;

    @Column
    private String authority;

    @ManyToMany(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinTable(name = "oauth_users_authorities",
            joinColumns = @JoinColumn(name = "group_id"),
            inverseJoinColumns = @JoinColumn(name = "user_id"))
    private List<User> users;


    public UserGroup() {
    }

    public UserGroup(String pName, String pAuthority) {
        name = pName;
        authority = pAuthority;
    }

    @Override
    public String getAuthority() {
        return authority;
    }

    public void addUser(User user) {
        if (users == null) users = new ArrayList<>();
        users.add(user);
    }
}
