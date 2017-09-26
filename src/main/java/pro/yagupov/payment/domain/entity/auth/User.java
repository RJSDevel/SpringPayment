package pro.yagupov.payment.domain.entity.auth;

import lombok.Data;
import lombok.ToString;
import org.springframework.security.core.CredentialsContainer;
import org.springframework.security.core.userdetails.UserDetails;
import pro.yagupov.payment.domain.entity.account.Account;

import javax.persistence.*;
import java.util.ArrayList;
import java.util.HashSet;
import java.util.List;
import java.util.Set;

/**
 * Created by Yagupov Ruslan on 20.03.17.
 */
@Data
@Entity
@Table(name = "oauth_users")
@ToString(exclude = {"accounts"})
public class User implements UserDetails, CredentialsContainer {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column
    private String username;
    @Column
    private String password;

    @ManyToMany(mappedBy = "users", cascade = CascadeType.ALL, fetch = FetchType.EAGER)
    private Set<UserGroup> authorities;

    @Column
    private boolean accountNonExpired;
    @Column
    private boolean accountNonLocked;
    @Column
    private boolean credentialsNonExpired;
    @Column
    private boolean enabled;
    @Column
    private boolean confirmed;

    @OneToMany(mappedBy = "user", cascade = CascadeType.ALL, fetch = FetchType.LAZY, orphanRemoval = true)
    private List<Account> accounts;


    public User() {
    }

    public User(String pUsername, String pPassword, UserGroup pAuthority, boolean pAccountNonExpired,  boolean pAccountNonLocked, boolean pCredentialsNonExpired, boolean pEnabled) {
        username = pUsername;
        password = pPassword;

        addAuthority(pAuthority);

        accountNonExpired = pAccountNonExpired;
        accountNonLocked = pAccountNonLocked;
        credentialsNonExpired = pCredentialsNonExpired;
        enabled = pEnabled;
    }

    public User(String pUsername, String pPassword, List<UserGroup> pAuthorities, boolean pAccountNonExpired, boolean pAccountNonLocked, boolean pCredentialsNonExpired, boolean pEnabled) {
        username = pUsername;
        password = pPassword;

        pAuthorities.forEach(this::addAuthority);

        accountNonExpired = pAccountNonExpired;
        accountNonLocked = pAccountNonLocked;
        credentialsNonExpired = pCredentialsNonExpired;
        enabled = pEnabled;
    }

    public void addAuthority(UserGroup pAuthority) {
        if (authorities == null) authorities = new HashSet<>();
        authorities.add(pAuthority);
        pAuthority.addUser(this);
    }

    public void addAccount(Account pAccount) {
        if (accounts == null) accounts = new ArrayList<>();
        accounts.add(pAccount);
        pAccount.setUser(this);
    }


    @Override
    public void eraseCredentials() {
        password = null;
    }
}
