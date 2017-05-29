package pro.yagupov.payment.domain.tdo;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import pro.yagupov.payment.domain.entity.transaction.Amounts;

/**
 * Created by Yagupov Ruslan on 19.04.17.
 */
@Getter
public class AmountsTDO {

    @JsonProperty(required = true)
    private long amount;

    private long orderAmount;

    private long tipAmount;

    private long cashbackAmount;


    public AmountsTDO() {
    }

    public AmountsTDO(Amounts pAmounts) {
        amount = pAmounts.getAmount();
        orderAmount = pAmounts.getOrderAmount();
        tipAmount = pAmounts.getTipAmount();
        cashbackAmount = pAmounts.getCashbackAmount();
    }
}
