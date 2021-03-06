package pro.yagupov.payment.domain.entity.transaction;

import lombok.AccessLevel;
import lombok.Data;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.GenericGenerator;
import org.hibernate.annotations.UpdateTimestamp;
import pro.yagupov.payment.domain.entity.account.Account;
import pro.yagupov.payment.domain.tdo.TransactionTDO;

import javax.persistence.*;
import java.sql.Timestamp;


/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Data
@NoArgsConstructor
@Entity
@Table(name = "transactions")
public class Transaction {

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
    }

    @Id
    @Setter(AccessLevel.NONE)
    @GeneratedValue(generator = "uuid2")
    @GenericGenerator(name = "uuid2", strategy = "uuid2")
    @Column(updatable = false, unique = true, length = 36)
    private String guid;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "parent")
    private Transaction parent;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "child")
    private Transaction child;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType type;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private Operation operation;

    @Column(nullable = false)
    @Enumerated(EnumType.ORDINAL)
    private Status status;

    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "amounts", updatable = false)
    private Amounts amounts;

    @Column
    private String comment;

    @Setter(AccessLevel.NONE)
    @CreationTimestamp
    @Column(updatable = false)
    private Timestamp created;

    @Setter(AccessLevel.NONE)
    @UpdateTimestamp
    @Column
    private Timestamp updated;

    @ManyToOne
    @JoinColumn(name = "source", nullable = false, updatable = false)
    private Account source;

    @ManyToOne
    @JoinColumn(name = "destination", nullable = false, updatable = false)
    private Account destination;


    public Transaction(TransactionTDO pTransaction, Account pSource, Account pDestination) {

        switch (pTransaction.getOperation()) {
            case AUTHORIZE:
                break;
            default:
                guid = pTransaction.getGuid();
        }

        operation = pTransaction.getOperation();
        type = pTransaction.getType();
        status = pTransaction.getStatus();
        amounts = new Amounts(pTransaction.getAmounts());
        comment = pTransaction.getComment();

        source = pSource;
        destination = pDestination;
    }
}
