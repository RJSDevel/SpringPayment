package pro.yagupov.payment.domain.entity.transaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.Setter;
import org.hibernate.annotations.*;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.tdo.TransactionTDO;

import javax.persistence.*;
import javax.persistence.CascadeType;
import javax.persistence.Entity;
import javax.persistence.Table;
import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;


/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Data
@Entity
@DynamicInsert
@Table(name = "transactions")
public class Transaction {

    public enum Type {
        TRANSFER,
        ORDER
    }

    public enum PaymentType {
        CREDIT_DEBIT,
        CASH,
        CHEQUE,
        CUSTOM_FUNDING_SOURCE
    }

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
        BATCHING
    }

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, insertable = false, unique = true, length = 36)
    private String guid;

    @Generated(GenerationTime.ALWAYS)
    @Column(updatable = false, insertable = false, unique = true)
    private long number;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "parent")
    private Transaction parent;

    @OneToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    @JoinColumn(name = "child")
    private Transaction child;

    @Column(name = "type", nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private Type type;

    @Column(name = "payment_type", nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType paymentType;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private Operation operation;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @Setter(AccessLevel.PRIVATE)
    @Column(name = "previous_status")
    @Enumerated(EnumType.ORDINAL)
    private Status previousStatus;

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

    @Column(updatable = false)
    private String comment;

    @ManyToOne
    @JoinColumn(name = "source", nullable = false, updatable = false)
    private Account source;

    @ManyToOne
    @JoinColumn(name = "destination", nullable = false, updatable = false)
    private Account destination;


    public Transaction(TransactionTDO pTransaction, Currency pCurrency, Account pSource, Account pDestination) {
        operation = pTransaction.getOperation();
        status = pTransaction.getStatus();
        type = pTransaction.getType();
        paymentType = pTransaction.getPaymentType();

        if (Objects.nonNull(pCurrency)) currency = pCurrency;

        amount = pTransaction.getAmount();

        comment = pTransaction.getComment();

        source = pSource;
        destination = pDestination;
    }

    public void setStatus(Status pStatus) {
        setPreviousStatus(status);
        status = pStatus;
    }
}
