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
@Table(name = "transactions_amounts")
public class Amounts {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    private BigDecimal amount;

    @Column(name = "order_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal orderAmount;

    @Column(name = "tip_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal tipAmount;

    @Column(name = "cashback_amount", updatable = false, precision = 8, scale = 2)
    private BigDecimal cashbackAmount;


    Amounts(AmountsTDO pAmounts) {
        amount = pAmounts.getAmount();
        orderAmount = pAmounts.getOrderAmount();
        tipAmount = pAmounts.getTipAmount();
        cashbackAmount = pAmounts.getCashbackAmount();
    }
}
