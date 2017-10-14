package pro.yagupov.payment.domain.tdo;

import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import pro.yagupov.payment.domain.entity.account.Account;

import java.math.BigDecimal;

/**
 * Created by developer on 25.09.17.
 */
@Getter
@NoArgsConstructor
public class AccountTDO {

    private long id;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String name;

    private String currency;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal score;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal holded;


    public AccountTDO(Account pAccount) {
        id = pAccount.getId();
        name = pAccount.getName();
        currency = pAccount.getCurrency().getCode();
        score = pAccount.getScore();
        holded = pAccount.getHold();
    }
}
