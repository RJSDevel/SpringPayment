package pro.yagupov.payment.domain.entity.transaction;

import lombok.Getter;
import pro.yagupov.payment.domain.tdo.AmountsTDO;

import javax.persistence.*;

/**
 * Created by Yagupov Ruslan on 18.04.17.
 */
@Getter
@Entity
@Table(name = "transactions_amounts")
public class Amounts {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;

    @Column(updatable = false)
    private long amount;

    @Column(name = "order_amount", updatable = false)
    private long orderAmount;

    @Column(name = "tip_amount", updatable = false)
    private long tipAmount;

    @Column(name = "cashback_amount", updatable = false)
    private long cashbackAmount;


    public Amounts() {
    }

    public Amounts(AmountsTDO pAmounts) {
        amount = pAmounts.getAmount();
        orderAmount = pAmounts.getOrderAmount();
        tipAmount = pAmounts.getTipAmount();
        cashbackAmount = pAmounts.getCashbackAmount();
    }
}
