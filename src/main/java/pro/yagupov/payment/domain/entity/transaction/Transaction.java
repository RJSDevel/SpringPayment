package pro.yagupov.payment.domain.entity.transaction;

import lombok.*;
import org.hibernate.annotations.*;
import org.hibernate.annotations.Generated;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.tdo.TransactionTDO;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;


/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

    public enum Operation {
        AUTHORIZE,
        CAPTURE,
        DECLINE,
        REFUND,
        VOID,
    }

    public enum Status {
        AUTHORIZED,
        CAPTURED,
        PARTIALLY_CAPTURED,
        PARTIALLY_CAPTURED_AND_PARTIALLY_REFUNDED,
        REFUNDED,
        PARTIALLY_REFUNDED,
        DECLINED,
        CANCELLED,
        VOIDED,
    }

    public enum PaymentType {
        CREDIT_DEBIT,
        CASH,
        CHEQUE,
        CUSTOM_FUNDING_SOURCE
    }

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, unique = true, length = 36)
    private String guid;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Transaction parent;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "child")
    private Transaction child;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType type;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private Operation operation;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    @Column
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(nullable = false, updatable = false, name = "currency")
    private Currency currency;

    @Column(updatable = false)
    private BigDecimal amount;

    @Column(name = "order_amount", updatable = false, precision = 19, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "tip_amount", updatable = false, precision = 19, scale = 2)
    private BigDecimal tipAmount;

    @Column(name = "cashback_amount", updatable = false, precision = 19, scale = 2)
    private BigDecimal cashbackAmount;

    @Column(updatable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "source", nullable = false, updatable = false)
    private Account source;

    @ManyToOne
    @JoinColumn(name = "destination", nullable = false, updatable = false)
    private Account destination;


    public Transaction(TransactionTDO pTransaction, Currency pCurrency, Account pSource, Account pDestination) {

        switch (pTransaction.getOperation()) {
            case AUTHORIZE:
                break;
            default:
                guid = pTransaction.getGuid();
        }

        type = pTransaction.getType();
        operation = pTransaction.getOperation();
        status = pTransaction.getStatus();

        currency = pCurrency;

        amount = pTransaction.getAmount();
        orderAmount = pTransaction.getOrderAmount();
        tipAmount = pTransaction.getTipAmount();
        cashbackAmount = pTransaction.getCashbackAmount();

        comment = pTransaction.getComment();

        source = pSource;
        destination = pDestination;
    }
}
