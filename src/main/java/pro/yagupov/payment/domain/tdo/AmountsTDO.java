package pro.yagupov.payment.domain.tdo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.yagupov.payment.domain.entity.transaction.Amounts;

import java.math.BigDecimal;

/**
 * Created by Yagupov Ruslan on 19.04.17.
 */
@Getter
@NoArgsConstructor
public class AmountsTDO {

    @JsonProperty(required = true)
    private BigDecimal amount;
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal orderAmount = new BigDecimal(0);
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal tipAmount = new BigDecimal(0);
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal cashbackAmount = new BigDecimal(0);


    public AmountsTDO(Amounts pAmounts) {
        amount = pAmounts.getAmount();
        orderAmount = pAmounts.getOrderAmount();
        tipAmount = pAmounts.getTipAmount();
        cashbackAmount = pAmounts.getCashbackAmount();
    }
}
