package pro.yagupov.payment.domain.entity.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.yagupov.payment.domain.tdo.AmountsTDO;

import javax.persistence.*;
import java.math.BigDecimal;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Getter
@Entity
@NoArgsConstructor
@Table(name = "amounts")
public class Amounts {

    public enum PaymentType {
        CREDIT_DEBIT,
        CASH,
        CHEQUE,
        CUSTOM_FUNDING_SOURCE
    }

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @ManyToOne
    @JoinColumn(name = "transaction_guid", updatable = false)
    private Transaction transaction;

    @Column(nullable = false, updatable = false)
    @Enumerated(EnumType.ORDINAL)
    private PaymentType type;

    @Column(updatable = false)
    private BigDecimal amount;

    @Column(name = "order_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "tip_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal tipAmount;

    @Column(name = "cashback_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal cashbackAmount;


    Amounts(AmountsTDO pAmounts, Transaction pTransaction) {
        type = pAmounts.getType();
        transaction = pTransaction;
        amount = pAmounts.getAmount();
        orderAmount = pAmounts.getOrderAmount();
        tipAmount = pAmounts.getTipAmount();
        cashbackAmount = pAmounts.getCashbackAmount();
    }
}
