package pro.yagupov.payment.domain.entity.account;

import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.util.StringUtils;
import pro.yagupov.payment.domain.entity.auth.User;
import pro.yagupov.payment.domain.entity.transaction.Transaction;
import pro.yagupov.payment.domain.tdo.AccountTDO;

import javax.persistence.*;
import java.math.BigDecimal;
import java.util.List;
import java.util.Objects;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Data
@Entity
@NoArgsConstructor
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

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal score = new BigDecimal(0);

    @Column(precision = 8, scale = 2, nullable = false)
    private BigDecimal hold = new BigDecimal(0);

    @Column(name = "is_active")
    private boolean isActive;

    @Column(name = "is_blocked")
    private boolean isBlocked;

    @OneToMany(mappedBy = "source", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> outTransactions;

    @OneToMany(mappedBy = "destination", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Transaction> receiveTransactions;


    public Account(AccountTDO pAccountTDO) {
        if (!Objects.isNull(pAccountTDO) && !StringUtils.isEmpty(pAccountTDO.getName())) name = pAccountTDO.getName();
    }
}
