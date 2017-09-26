package pro.yagupov.payment.domain.tdo;

import com.fasterxml.jackson.annotation.JsonFormat;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import pro.yagupov.payment.domain.entity.transaction.Amounts;
import pro.yagupov.payment.domain.entity.transaction.Transaction;

import java.sql.Timestamp;
import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.function.Consumer;

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

    @JsonProperty(required = true)
    @JsonInclude(JsonInclude.Include.NON_EMPTY)
    private List<AmountsTDO> amounts = new ArrayList<>();

    @JsonInclude(JsonInclude.Include.NON_NULL)
    private String comment;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp created;

    @JsonProperty(access = JsonProperty.Access.READ_ONLY)
    @JsonInclude(JsonInclude.Include.NON_NULL)
    private Timestamp updated;

    private long source;

    private long destination;


    public TransactionTDO(Transaction pTransaction) {
        guid = pTransaction.getGuid();

        if (!Objects.isNull(pTransaction.getParent())) parent = pTransaction.getParent().getGuid();
        if (!Objects.isNull(pTransaction.getChild())) child = pTransaction.getChild().getGuid();

        pTransaction
                .getAmounts()
                .forEach(pAmounts -> amounts.add(new AmountsTDO(pAmounts)));

        operation = pTransaction.getOperation();
        status = pTransaction.getStatus();

        comment = pTransaction.getComment();

        created = pTransaction.getCreated();
        updated = pTransaction.getUpdated();

        source = pTransaction.getSource().getId();
        destination = pTransaction.getDestination().getId();
    }
}
