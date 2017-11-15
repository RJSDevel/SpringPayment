package pro.yagupov.payment.domain.tdo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import java.math.BigDecimal;
import java.sql.Timestamp;
import java.util.Objects;

/**
 * Created by Yagupov Ruslan on 19.04.17.
 */
@Getter
@NoArgsConstructor
public class TransactionTDO {

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String guid;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String parent;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String child;

    @Setter
    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Transaction.Operation operation;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY, defaultValue = "0")
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Transaction.Status status;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp updated;

    @JsonProperty(required = true)
    @JsonFormat(shape = JsonFormat.Shape.NUMBER)
    private Transaction.PaymentType type;

    @JsonProperty(required = true)
    private String currency;

    @JsonProperty(required = true)
    private BigDecimal amount;

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal orderAmount = new BigDecimal(0);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal tipAmount = new BigDecimal(0);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private BigDecimal cashbackAmount = new BigDecimal(0);

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

    private long source;

    private long destination;


    public TransactionTDO(Transaction pTransaction) {
        guid = pTransaction.getGuid();

        if (!Objects.isNull(pTransaction.getParent())) parent = pTransaction.getParent().getGuid();
        if (!Objects.isNull(pTransaction.getChild())) child = pTransaction.getChild().getGuid();

        operation = pTransaction.getOperation();
        status = pTransaction.getStatus();

        comment = pTransaction.getComment();

        created = pTransaction.getCreated();
        updated = pTransaction.getUpdated();

        type = pTransaction.getType();
        currency = pTransaction.getCurrency().getCode();
        amount = pTransaction.getAmount();
        orderAmount = pTransaction.getOrderAmount();
        tipAmount = pTransaction.getTipAmount();
        cashbackAmount = pTransaction.getCashbackAmount();
        comment = pTransaction.getComment();

        source = pTransaction.getSource().getId();
        destination = pTransaction.getDestination().getId();
    }
}
