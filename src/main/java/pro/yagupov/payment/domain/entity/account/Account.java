package pro.yagupov.payment.domain.entity.account;

import lombok.Data;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import javax.persistence.*;
import java.util.List;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Data
@Entity
@Table(name = "accounts")
public class Account {

    public static final Account NOT_FOUND = new Account();

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(nullable = false)
    private User user;

    @Column(length = 56)
    private String name;

    @Column
    private long score;

    @Column
    private long holded;

    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> outTransactions;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> receiveTransactions;

}
