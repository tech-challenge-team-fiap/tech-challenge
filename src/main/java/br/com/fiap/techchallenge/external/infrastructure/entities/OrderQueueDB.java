package br.com.fiap.techchallenge.external.infrastructure.entities;

import br.com.fiap.techchallenge.domain.enums.StatusOrder;
import br.com.fiap.techchallenge.domain.type.StringRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import jakarta.validation.constraints.NotNull;
import java.time.LocalDateTime;
import java.util.UUID;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.EqualsAndHashCode;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "ORDER_QUEUE")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class OrderQueueDB {

    @Id
    @Builder.Default
    @Type(StringRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @Column(name = "ORDER_NUMBER")
    private String numberOrder;

    @Column(name = "ORDER_STATUS")
    @Enumerated(EnumType.STRING)
    private StatusOrder statusOrder;

    @Column(name = "DATE_REGISTER")
    @CreatedDate
    private LocalDateTime dateRegister;

    @NotNull
    private static UUID nextId() {
        return UlidCreator.getMonotonicUlid().toUuid();
    }

    public OrderQueueDB(String numberOrder, StatusOrder status, LocalDateTime dateRegister){
        this.numberOrder = numberOrder;
        this.statusOrder = status;
        this.dateRegister = dateRegister;
    }
}
