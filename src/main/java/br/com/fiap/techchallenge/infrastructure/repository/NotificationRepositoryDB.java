package br.com.fiap.techchallenge.infrastructure.repository;

import br.com.fiap.techchallenge.common.enums.StatusOrder;
import br.com.fiap.techchallenge.common.enums.TypeStatus;
import br.com.fiap.techchallenge.common.type.NumericRepresentationUUIDType;
import com.github.f4b6a3.ulid.UlidCreator;
import jakarta.persistence.*;
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

import java.util.Date;
import lombok.experimental.SuperBuilder;
import org.hibernate.annotations.Type;
import org.springframework.data.annotation.CreatedDate;

@Entity
@Table(name = "NOTIFICATION")
@Data
@NoArgsConstructor
@AllArgsConstructor
@SuperBuilder(toBuilder = true)
@EqualsAndHashCode(onlyExplicitlyIncluded = true)
@Getter
@Setter
public class NotificationRepositoryDB {

    @Id
    @Builder.Default
    @Type(NumericRepresentationUUIDType.class)
    @Column(name = "ID")
    @NotNull
    @EqualsAndHashCode.Include
    private UUID id = nextId();

    @Column(name = "ORDER_NUMBER")
    private String numberOrder;

    @Column(name = "MESSAGE")
    private String message;

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

    public NotificationRepositoryDB(String numberOrder, String message, StatusOrder status, LocalDateTime dateRegister){
        this.numberOrder = numberOrder;
        this.message = message;
        this.statusOrder = status;
        this.dateRegister = dateRegister;
    }
}
