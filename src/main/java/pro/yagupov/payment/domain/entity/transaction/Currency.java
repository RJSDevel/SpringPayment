package pro.yagupov.payment.domain.entity.transaction;

import lombok.Getter;
import lombok.NoArgsConstructor;

import javax.persistence.*;

/**
 * Created by developer on 26.09.17.
 */
@Entity
@Table(name = "currency")
@NoArgsConstructor
@Getter
public class Currency {

    @Id
    @Column
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private long id;
    @Column(name = "name")
    private String name;
    @Column(name = "code")
    private String code;

}
