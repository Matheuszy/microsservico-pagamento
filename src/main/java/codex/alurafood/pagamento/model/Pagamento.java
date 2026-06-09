package codex.alurafood.pagamento.model;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Getter;
import lombok.Setter;
import java.math.BigDecimal;

@Entity
@Table(name = "pagamentos")
@Inheritance(strategy = InheritanceType.JOINED)
@Getter
@Setter
public abstract class Pagamento {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NotNull
    @Positive
    private BigDecimal valor;

    @NotNull
    @Enumerated(EnumType.STRING)
    private Status status = Status.CRIADO;

    @NotNull
    private Long pedidoId;

    public Pagamento() {}


}